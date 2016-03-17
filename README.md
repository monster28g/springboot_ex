# springboot_ex

### `git clone https://github.com/monster28g/springboot_ex`

### BUILD
` mvn clean package`

### Test the service

Now that the service is up, visit http://localhost:8080/greeting, where you see:

{"id":1,"content":"Hello, World!"}
Provide a name query string parameter with http://localhost:8080/greeting?name=User. Notice how the value of the content attribute changes from "Hello, World!" to "Hello User!":

{"id":2,"content":"Hello, User!"}
This change demonstrates that the @RequestParam arrangement in GreetingController is working as expected. The name parameter has been given a default value of "World", but can always be explicitly overridden through the query string.

Notice also how the id attribute has changed from 1 to 2. This proves that you are working against the same GreetingController instance across multiple requests, and that its counter field is being incremented on each call as expected.