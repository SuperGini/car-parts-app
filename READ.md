## Creating the database:

### 1. Run the docker-compose file to generate the PostgresSQL container

### To start the [docker-compose.yml](docker-compose.yml)  container run the below command in the root folder:

````
docker-compose start
````

### 2. Start the application with: ddl-auto: update. This will create the database for you.

````yaml
  jpa:
    hibernate:
      ddl-auto: update
````

### 3. CREATE database with liquibase: add the liquibase dependency and plugin to [pom.xml](pom.xml)

````xml

<dependency>
    <groupId>org.liquibase</groupId>
    <artifactId>liquibase-core</artifactId>
    <version>4.29.2</version>
</dependency>
````
````xml
<plugin>
    <groupId>org.liquibase</groupId>
    <artifactId>liquibase-maven-plugin</artifactId>
    <version>4.29.2</version>
    <configuration>
        <driver>org.postgresql.Driver</driver>
        <url>jdbc:postgresql://localhost:5432/warehouse</url>
        <username>ginitoru</username>
        <password>1234</password>
        <outputChangeLogFile>src/main/resources/liquibase-outputChangeLog.xml</outputChangeLogFile>
    </configuration>
</plugin>
````

### 4. Run the command:
````
mvn liquibase:generateChangeLog
````
### this will generate a xml file that will be used to generate the database for you with liquibase. If you don't want to use
### liquibase for database migration keep ddl-auto: update

### 5. Add the location where the master.xml file is located and set ddl-auto: none (so hibernate will not generate tables etc.)
````yaml
jpa:
  hibernate:
    ddl-auto: none

liquibase:
  change-log: classpath:/liquibase/master.xml
````

### 6. Add the [initiating_database_2024_10_27_001.xml](src%2Fmain%2Fresources%2Fliquibase%2Fchangelog%2Finitiating_database_2024_10_27_001.xml) file to [master.xml](src%2Fmain%2Fresources%2Fliquibase%2Fmaster.xml) file

### 7. Delete the PostgresSQL container and the volume

### 8. Recreate the PostgresSQL container and start the application. When you start the application liquibase will get into action
### and create the database and all the tables using [master.xml](src%2Fmain%2Fresources%2Fliquibase%2Fmaster.xml) and [initiating_database_2024_10_27_001.xml](src%2Fmain%2Fresources%2Fliquibase%2Fchangelog%2Finitiating_database_2024_10_27_001.xml)

### For more information about liquibase: https://www.baeldung.com/liquibase-refactor-schema-of-java-app

