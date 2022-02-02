Url Shortcut
=============
## Description
This project is the REST-app: Url shortcut service. Users can register their sites and then receive shortcuts for urls. 

### Used technologies:
#### Backend
1. Spring Boot(Web, Data, Security)
2. JWT (for authentication and authorization)
3. PostgreSQL

### Functionality:

User sends url. After successful registration of this url user receives login and password;
![ScreenShot](images/1.png)

After successful authentication user receives unique authorization token
![ScreenShot](images/2.png)

Authorized user sends url and receives shortcut for this url
![ScreenShot](images/3.png)

Sending shortcut to "/redirect" redirects user to mapped url (authorization not required)
![ScreenShot](images/4.png)

Authorized user can get statistics for all registered urls
![ScreenShot](images/5.png)


### Contact
If you have any questions, feel free to contact me: oleg.kolchin.j@gmail.com