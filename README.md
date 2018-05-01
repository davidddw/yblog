David Blog Study Demo
=====================
A simple Java blog with SpringBoot and MyBatis (MySQL).

## Prerequisites ##
- JDK 7
- Maven 3.0.3 or newer

## Configure MySQL database ##

Run the following commands:  
```
mysql -e "GRANT ALL PRIVILEGES ON *.* TO 'cloud'@'localhost' IDENTIFIED BY 'd05660' WITH GRANT OPTION;"
mysql -e "GRANT ALL PRIVILEGES ON *.* TO 'cloud'@'127.0.0.1' IDENTIFIED BY 'd05660' WITH GRANT OPTION;"
mysql -e 'FLUSH PRIVILEGES;'
mysql -e 'DROP DATABASE IF EXISTS `meblog`;'
mysql -e 'CREATE DATABASE `meblog` DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci;'
```

Now, populate the database with the script provided: 
```
mysql meblog < newblog.sql
```

## Test ##
In order to build a WAR package, run the following command:  
```
mvn clean package
```

In order to run program, type the following command:
```
java -jar ylog-4.0.0.jar
```

Point your browser to http://localhost:8080
    