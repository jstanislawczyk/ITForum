# ITForum
  ITForum is my first project using Spring MVC. Forum is simple, allows to register users and create categories. 
  Users after successful login can create and comment posts.
  Technology stack:
  1. Java
  2. Spring Framework, Spring MVC - application framework
  3. Spring Security - password hashing, login handling etc.
  4. MySQL + Workbench - database
  5. JPA - ORM
  6. Thymeleaf - views handling
  7. JUnit, Mockito - testing
  8. Maven - build automation tool
  9. Tomcat - application container
  
# How to run?
  1. Create WAR file
  2. Copy WAR file to Tomcat container (e.g. /Tomcat/webapps/ITForum.WAR)
  3. Create 'itforum' database on MySQL server (on localhost/127.0.0.1)
  4. Create user 'root' with 'root' password and grant all privileges to itforum database
  5. Run Tomcat
  6. You can access ITForum in 'localhost:8080/itforum' address
  7. If you want to create admin, you need to register normal user then change role manually in MySQL to 'ADMIN'
  
# Preview
![preview](https://user-images.githubusercontent.com/31374669/36448072-ab75ed2e-1686-11e8-8df1-3151df437acc.JPG)
