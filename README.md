# Establishment

Application for small family establishment where users watch and order dishes. New users are provided with guest role. If Guest authenticates or rigisters, he acquires Cusetomer role. Such user is able to make orders, watch previous ones and edit profile. Administrator is able to manage users and do casual job to suport work of establishment.

Role | Description
--- | ---
Guest | Unauthenticated user
Customer | Authenticated user
Admin | Authenticated user

User status |
--- |
In registration |
Confirmed |
Blocked |

### Guest ###
* Authenticate in order to get Customer status
* Sign up in order to get Customer status
* Watch available dishes and find them according to the parameters
* Watch dish information
* Change application language

### Customer ###
* Sign out
* Manage Customer profile
    * Change photo
	* Change phone number
	* Change email
	* Change card number
	* Reset password
* Watch available dishes and find them according to the parameters
* Watch dish information
* Add particular dish to the basket
* Manage basket
    * Increment dish amount
	* Decrement dish amount
	* Delete dish
	* Set using bonuses amount
* Buy dishes in the basket
* Watch previous orders and find them according to the parameters
* Change application language

### Admin ###
* Sign out
* Manage Admin profile
    * Change photo
	* Change phone number
	* Change email
	* Reset password
* Watch all dishes and find them according to the parameters
* Watch dish information
* Manage dish
    * Create new one
	* Edit dish parameters
	* Add ingredient to the dish
	* Remove dish ingredient
	* Make dish available
	* Make dish disabled
* Watch all ingredients
* Create new ingredient
* Watch all orders and find them according to the parameters
* Watch order information
* Manage order
    * Increment dish amount
	* Decrement dish amount
	* Delete dish
	* Add available dish
	* Set using bonuses amount
	* Change order status
* Watch all users and find them according to the parameters
* Watch user information
* Manage user
    * Change photo
	* Change phone number
	* Change email
	* change card number
	* Reset password
	* Change user status
    * Watch user orders
    * Change user bonuses amount	
* Change application language

![Database scheme](https://github.com/Sergey-Dvoraninovich/tmp_establishment/blob/master/schema/schema.png)
