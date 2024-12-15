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

### 3. CREATE database with liquibase: add the liquibase dependency and plugin to [pom.xml](car-part-core-micro%2Fpom.xml)

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

### 6. Add the [initiating_database_2024_10_27_001.xml](car-part-core-micro%2Fsrc%2Fmain%2Fresources%2Fliquibase%2Fchangelog%2Finitiating_database_2024_10_27_001.xml) file to [master.xml](car-part-core-micro%2Fsrc%2Fmain%2Fresources%2Fliquibase%2Fmaster.xml) file

### 7. Delete the PostgresSQL container and the volume

### 8. Recreate the PostgresSQL container and start the application. When you start the application liquibase will get into action
### and create the database and all the tables using [master.xml](car-part-core-micro%2Fsrc%2Fmain%2Fresources%2Fliquibase%2Fmaster.xml)  and [initiating_database_2024_10_27_001.xml](car-part-core-micro%2Fsrc%2Fmain%2Fresources%2Fliquibase%2Fchangelog%2Finitiating_database_2024_10_27_001.xml)
### Ho to use liquibase with sql sripts: https://forum.liquibase.org/t/liquibase-works-with-plain-old-sql/6082

### For more information about liquibase: https://www.baeldung.com/liquibase-refactor-schema-of-java-app


# Generate the Api interfaceace using [car-parts-openapi.yaml](car-part-core-micro%2Fsrc%2Fmain%2Fresources%2Fcar-parts-openapi.yaml)

 run the command:
```yaml
mvn clean install
```
to generate the API interface.

## Generate Criteria Query metamodel using the domain model

add this library to the [pom.xml](car-part-core-micro%2Fpom.xml), start the project and metamodel
will be generated in the package: target/generated-sources/annotations

````xml
<dependency>
    <groupId>org.hibernate.orm</groupId>
    <artifactId>hibernate-jpamodelgen</artifactId>
    <version>6.6.1.Final</version>
    <scope>provided</scope>
</dependency>
````

## 10. Blaze-persistence
It is used to create complex queries using criteria api, and escape multiple fetch bag exception
related info:
<br>
https://persistence.blazebit.com/documentation/1.6/entity-view/manual/en_US/#spring-data-features
<br>
https://vladmihalcea.com/blaze-persistence-multiset/
<br>
https://github.com/Blazebit/blaze-persistence/discussions/1410
<br>
In order to make Blaze-persistence work we need to add @EnableBlazeRepositories on the main class. It doesn't work if you add it
on a @Configuration class.
Add @EnableEntityViews(basePackages = "com.gini.model.views") on a configuration class or on the main class so we don't need to 
create a @Bean of type EntityViewConfiguration.
<br>
Dependencies need it for Blaze persistence are:
````xml
        
<dependency>
    <groupId>com.blazebit</groupId>
    <artifactId>blaze-persistence-integration-spring-data-3.3</artifactId>
    <version>${blaze-persistence.version}</version>
    <scope>compile</scope>
</dependency>
<dependency>
    <groupId>com.blazebit</groupId>
    <artifactId>blaze-persistence-integration-hibernate-6.2</artifactId>
    <version>${blaze-persistence.version}</version>
    <scope>runtime</scope>
</dependency>
````

#ANGULAR
in order to use angular material you need to add it to the Angular app:
Go to the Angular parent directory (where angular.json is) and run the command:
````
ng add @angular/material
````
install font awesome:
https://github.com/FortAwesome/angular-fontawesome
https://github.com/FortAwesome/angular-fontawesome/blob/main/docs/usage/icon-library.md#using-the-icon-library

fixing imports of _ scss files in components scss files: https://stackoverflow.com/questions/70058183/cant-use-sass-variables-in-angular-from-global-file-despite-other-styles-worki
