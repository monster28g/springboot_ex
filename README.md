# springboot_ex

### `git clone https://github.com/monster28g/springboot_ex`

## MAVEN
### BUILD
` mvn clean package`

### run the service
` mvn exec:java`

## GRADLE
### BUILD
./gradlew clean build -x test

### Test the service
./gradlew bootRun

### JPA test(embedded H2 web console) 
http://localhost:18080/console
jdbc:h2:~/queryHistory