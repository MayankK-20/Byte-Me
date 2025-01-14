# Byte-Me

## Terminal-Based System and Graphic System to view Menu and Order

This is a menu-driven system designed to manage two main types of users:
Customers and Administrators. Each user type has specific interface for its use within the system.

Additional functionality to see menu and orders graphically.

Used swing to implement graphic interface where user can view menu as well as orders.

Used Input, Output Stream Management to save order histories for every user.

Also used JUnit to test ordering out of stock items and invalid login attempts.

***

## How to run code:
- Download folder
- Open intellij
- Change directory to the directory where you downloaded the folder to.
- Compile and run code.
- Read the terminal and input.

***

## Assumptions:

### Admin:-
#### Keyword:
- For any item on the menu while adding the item the admin would provide all keywords that may be used to look up the item.

#### Update Order Status:
- Admin would only update status of the orders which have been processed.

#### Process Refunds:
- Admin will process refunds of all the denied and cancelled orders.

#### Handle Specific Requests:
- While processing the request admin will be provided any specific requests if mentioned and then admin takes care of the request.

#### Report Generation:
- This will display the number of orders the total sales.
- It will also display the sold items in popularity order.

<br><br>
**
<br><br>

### Customer:-
#### Add to cart:
- Customers would place items in cart by browsing the menu.

#### Cancel Order:
- If customer cancels order he would not be refunded right away but will wait for admin to refund.

***
