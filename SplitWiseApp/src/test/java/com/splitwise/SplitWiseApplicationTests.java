package com.splitwise;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(properties = {
        "spring.datasource.url=jdbc:h2:mem:splitwise-test;DB_CLOSE_DELAY=-1",
        "spring.jpa.hibernate.ddl-auto=create-drop"
})
class SplitWiseApplicationTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void createsGroupExpenseRestrictsMembershipAndSettlesGroup() throws Exception {
        long aliceId = register("Alice", "+15550000001");
        long bobId = register("Bob", "+15550000002");
        register("Charlie", "+15550000003");

        String alice = "+15550000001";
        String bob = "+15550000002";
        String charlie = "+15550000003";

        String groupBody = mockMvc.perform(post("/api/groups")
                        .with(httpBasic(alice, "password123"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Weekend trip\"}"))
                .andExpect(status().isCreated())
                .andReturn().getResponse().getContentAsString();
        long groupId = objectMapper.readTree(groupBody).get("id").asLong();

        mockMvc.perform(post("/api/groups/{groupId}/members", groupId)
                        .with(httpBasic(alice, "password123"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"userId\":" + bobId + "}"))
                .andExpect(status().isOk());

        mockMvc.perform(get("/api/groups/{groupId}", groupId)
                        .with(httpBasic(charlie, "password123")))
                .andExpect(status().isForbidden());

        mockMvc.perform(post("/api/expenses")
                        .with(httpBasic(alice, "password123"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "amount": 10.00,
                                  "description": "Shared meal",
                                  "splitType": "EQUAL",
                                  "groupId": %d,
                                  "payments": [{"userId": %d, "amount": 10.00}],
                                  "shares": [
                                    {"userId": %d},
                                    {"userId": %d}
                                  ]
                                }
                                """.formatted(groupId, aliceId, aliceId, bobId)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.shares[0].amount").value(5.00))
                .andExpect(jsonPath("$.shares[1].amount").value(5.00));

        mockMvc.perform(post("/api/groups/{groupId}/members", groupId)
                        .with(httpBasic(bob, "password123"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"userId\":" + aliceId + "}"))
                .andExpect(status().isForbidden());

        mockMvc.perform(get("/api/settlements/preview")
                        .param("groupId", Long.toString(groupId))
                        .with(httpBasic(alice, "password123")))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.transactions[0].fromUserId").value(bobId))
                .andExpect(jsonPath("$.transactions[0].toUserId").value(aliceId))
                .andExpect(jsonPath("$.transactions[0].amount").value(5.00));

        mockMvc.perform(post("/api/settlements/execute")
                        .param("groupId", Long.toString(groupId))
                        .with(httpBasic(alice, "password123")))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.executedTransactions.length()").value(1));

        mockMvc.perform(get("/api/groups/{groupId}/balances", groupId)
                        .with(httpBasic(alice, "password123")))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].netBalance").value(0.00))
                .andExpect(jsonPath("$[1].netBalance").value(0.00));
    }

    @Test
    void personalSettlementOnlySettlesTheAuthenticatedUser() throws Exception {
        long aliceId = register("Alice", "+15550000011");
        long bobId = register("Bob", "+15550000012");
        long charlieId = register("Charlie", "+15550000013");
        String alice = "+15550000011";
        String bob = "+15550000012";

        mockMvc.perform(post("/api/expenses")
                        .with(httpBasic(alice, "password123"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "amount": 20.00,
                                  "description": "Cafe",
                                  "splitType": "PERCENT",
                                  "payments": [{"userId": %d, "amount": 20.00}],
                                  "shares": [
                                    {"userId": %d, "value": 50},
                                    {"userId": %d, "value": 50}
                                  ]
                                }
                                """.formatted(aliceId, aliceId, bobId)))
                .andExpect(status().isCreated());

        mockMvc.perform(post("/api/expenses")
                        .with(httpBasic(alice, "password123"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "amount": 8.00,
                                  "description": "Ticket",
                                  "splitType": "EXACT",
                                  "payments": [{"userId": %d, "amount": 8.00}],
                                  "shares": [{"userId": %d, "value": 8.00}]
                                }
                                """.formatted(aliceId, charlieId)))
                .andExpect(status().isCreated());

        mockMvc.perform(get("/api/settlements/preview").with(httpBasic(bob, "password123")))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.transactions.length()").value(1))
                .andExpect(jsonPath("$.transactions[0].fromUserId").value(bobId))
                .andExpect(jsonPath("$.transactions[0].toUserId").value(aliceId))
                .andExpect(jsonPath("$.transactions[0].amount").value(10.00));

        mockMvc.perform(post("/api/settlements/execute").with(httpBasic(bob, "password123")))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.executedTransactions.length()").value(1));

        mockMvc.perform(get("/api/balances/me").with(httpBasic(bob, "password123")))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.netBalance").value(0.00));

        mockMvc.perform(get("/api/balances/me").with(httpBasic("+15550000013", "password123")))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.netBalance").value(-8.00));
    }

    @Test
    void percentageSplitDistributesRoundingCentsWithoutNegativeShares() throws Exception {
        long aliceId = register("Alice", "+15550000021");
        long bobId = register("Bob", "+15550000022");
        long charlieId = register("Charlie", "+15550000023");
        long daveId = register("Dave", "+15550000024");
        String groupBody = mockMvc.perform(post("/api/groups")
                        .with(httpBasic("+15550000021", "password123"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Tiny expense group\"}"))
                .andExpect(status().isCreated())
                .andReturn().getResponse().getContentAsString();
        long groupId = objectMapper.readTree(groupBody).get("id").asLong();

        for (long memberId : new long[]{bobId, charlieId, daveId}) {
            mockMvc.perform(post("/api/groups/{groupId}/members", groupId)
                            .with(httpBasic("+15550000021", "password123"))
                            .contentType(MediaType.APPLICATION_JSON)
                            .content("{\"userId\":" + memberId + "}"))
                    .andExpect(status().isOk());
        }

        mockMvc.perform(post("/api/expenses")
                        .with(httpBasic("+15550000021", "password123"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "amount": 0.02,
                                  "description": "Tiny shared cost",
                                  "splitType": "PERCENT",
                                  "groupId": %d,
                                  "payments": [{"userId": %d, "amount": 0.02}],
                                  "shares": [
                                    {"userId": %d, "value": 25},
                                    {"userId": %d, "value": 25},
                                    {"userId": %d, "value": 25},
                                    {"userId": %d, "value": 25}
                                  ]
                                }
                                """.formatted(groupId, aliceId, aliceId, bobId, charlieId, daveId)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.shares[0].amount").value(0.01))
                .andExpect(jsonPath("$.shares[1].amount").value(0.01))
                .andExpect(jsonPath("$.shares[2].amount").value(0.00))
                .andExpect(jsonPath("$.shares[3].amount").value(0.00));
    }

    private long register(String name, String phoneNumber) throws Exception {
        String response = mockMvc.perform(post("/api/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {"name":"%s","phoneNumber":"%s","password":"password123"}
                                """.formatted(name, phoneNumber)))
                .andExpect(status().isCreated())
                .andReturn().getResponse().getContentAsString();
        JsonNode json = objectMapper.readTree(response);
        return json.get("id").asLong();
    }
}
