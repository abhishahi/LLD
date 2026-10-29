# SplitWiseApp

SplitWiseApp is a Java 21 / Spring Boot REST API for tracking shared expenses and settling balances. It uses Spring Data JPA with a local H2 database and HTTP Basic authentication. Passwords are stored as BCrypt hashes.

## Run

From this directory:

```powershell
mvn spring-boot:run
```

The H2 database is stored in `./data/splitwise`. Registration is unauthenticated; all other endpoints require HTTP Basic credentials using the registered phone number and password.

## API

All routes are prefixed with `/api`.

| Method | Route | Description |
|---|---|---|
| `POST` | `/users` | Register a user (`name`, `phoneNumber`, `password`) |
| `GET` | `/users/me` | Read the authenticated user's profile |
| `PATCH` | `/users/me` | Update the authenticated user's name and phone number |
| `POST` | `/groups` | Create a group; the creator becomes its owner and a member |
| `GET` | `/groups` | List the authenticated user's groups |
| `GET` | `/groups/{groupId}` | Read a group (members only) |
| `POST` | `/groups/{groupId}/members` | Add `{ "userId": 2 }` (owner only) |
| `DELETE` | `/groups/{groupId}/members/{userId}` | Remove a member (owner only) |
| `POST` | `/expenses` | Create a group or non-group expense |
| `GET` | `/expenses` | List expenses in which the user paid or has a share |
| `GET` | `/groups/{groupId}/expenses` | List group expenses (members only) |
| `GET` | `/balances/me` | Get the user's net balance across all expenses |
| `GET` | `/groups/{groupId}/balances` | Get current member balances for one group |
| `GET` | `/settlements/preview` | Preview personal settlement transactions |
| `GET` | `/settlements/preview?groupId={id}` | Preview a group-wide settlement |
| `POST` | `/settlements/execute` | Record personal settlement transactions |
| `POST` | `/settlements/execute?groupId={id}` | Record group-wide settlement transactions |

An expense request has this shape:

```json
{
  "amount": 120.00,
  "description": "Dinner",
  "splitType": "PERCENT",
  "groupId": 1,
  "payments": [
    { "userId": 1, "amount": 120.00 }
  ],
  "shares": [
    { "userId": 1, "value": 40 },
    { "userId": 2, "value": 60 }
  ]
}
```

`groupId` is omitted for non-group expenses. `shares` identifies participants for all split types. `value` is omitted for `EQUAL`, is the amount owed for `EXACT`, and is the percentage owed for `PERCENT`. Payer amounts and exact shares must add up to the expense total; percentages must add up to 100. Money is limited to two decimal places.

Settlement previews are computed from outstanding balances. Executing a preview records the corresponding transfers so later balance queries reflect them. Personal settlement only creates transactions involving the authenticated user and only covers non-group expenses; group settlement covers only expenses in that group.

## Main classes

- `domain.User`, `ExpenseGroup`, `Expense`, `ExpensePayment`, `ExpenseShare`, and `Settlement` define the persisted model.
- `service.UserService`, `GroupService`, and `ExpenseService` implement account, membership, and expense rules.
- `service.SettlementService` computes balances and settlement plans.
- `api.*Controller` expose the REST endpoints; `api.ApiDtos` defines validated request and response types.
- `config.SecurityConfig` configures BCrypt password storage and HTTP Basic authentication.

Run the integration tests with `mvn test`.
