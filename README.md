# ITForum
  ITForum is my first project using Spring MVC. Forum is simple, allows to register users and create categories. 
  Users after successful login can create and comment posts.
  Technology stack:
  *Java
  *Spring Framework, Spring MVC - application framework
  *Spring Security - password hashing, login handling etc.
  *MySQL + Workbench - database
  *JPA - ORM
  *Thymeleaf - views handling
  *JUnit, Mockito - testing
  *Maven - build automation tool
  *Tomcat - application container
  
  How to run?
  1. Create WAR file
  2. Copy WAR file to Tomcat container (e.g. /Tomcat/webapps/ITForum.WAR)
  3. Create 'itforum' database on MySQL server (on localhost/127.0.0.1)
  4. Create user 'root' with 'root' password and grant all privileges to itforum database
  5. Run Tomcat
  6. You can access ITForum in 'localhost:8080/itforum' address
  7. If you want to create admin, you need to do it manually in MySQL
  8. You can access admin panel in 'http://localhost:8080/itforum/admin'
