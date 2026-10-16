# Design SplitWise

## Intro
Splitwise is a popular application designed to help friends and roommates track shared expenses, 
making it easier to split bills and ensure that everyone gets paid back. Whether it's splitting rent, 
utilities, restaurant bills, or any other shared expense, Splitwise provides a simple platform to manage 
and settle dues.

<img src="SplitWise.png" />


## Requirements

Below are the requirements shared by the interviewer:

Certainly! Given the new requirement about the split types (EQUAL, EXACT, PERCENT), 
here's a revised list of requirements:

1. **User Management:**
   - Users can register and update their profiles.
   - A user's profile should contain at least their name, phone number, and password.

2. **Group Management:**
   - Users can participate in groups.
   - Only the user who has created a group can add/remove members to/from the group.
   - Users shouldn't be able to query about groups they are not a member of.

3. **Expense Management:**
   - Users can participate in expenses with other users.
   - To add an expense, a user must specify either the group or the other users involved in the expense.
   - Expenses can be split in one of three ways:
     - **EQUAL:** Amount is divided equally among all participants.
     - **EXACT:** The user specifies the exact amount owed by each participant.
     - **PERCENT:** The user specifies the percentage of the total amount owed by each participant.
   - While adding an expense, the user must also specify:
     - Who paid what (payer and amount).
     - How the amount should be split among the participants (based on one of the split types).
     - A description for the expense.
   - A user can see a history of the expenses they're involved in.
   - A user can see a history of the expenses made in a group that they're participating in.

4. **Settlement:**
   - A user can see their total owed amount.
   - Users can request a settle-up outside of groups. The application should show a list of transactions, 
   which when executed will ensure that the user no longer owes or receives money from any other user.
   Note: this need not settle-up any other users.
   - Users can request a settle-up for any group they're participating in. The application should show a 
   list of transactions, which if executed, will ensure that everyone participating in the group is settled up
   (owes a net of 0). This will only deal with the expenses made inside that group. Expenses outside the group
   need not be settled.


## RACE 

Follow the RACE Template and come up with the required **APIs** and **Classes** for the above detailed 
requirements. 

### APIs

> What will be some APIs that you would design for this system? Come up with use cases from the 
requirements and try to design APIs for each of them.
```

















‎ 

```

### Classes
> What do you think are the entities along with attributes & their relationships that will be required here? Add them below.
```

















‎ 
```