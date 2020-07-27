## Development Environment

- Language: Java
- JDK version: JDK 11
- Build Tool: Gradle
- Testing Framework: Spock

## How to build
This app uses gradle as the build tool. The project includes gradle wrapper so that 
you don't need to install it on your local machine to run it.

- To build the package, under the project root
```shell script
./gradlew build
```

- To run the tests, under the project root
```shell script
./gradlew test
```

## Run example App

The example application defined in App.java
You can simply run: <br>
NOTE: In some OS gradlew run could mess up the input and output together!
```shell script
./gradlew run
```

Or you can go into build folder/libs and fin rpn.jar, 
and then run java command like this:
```shell script
java -cp rpn.jar io.shuozhao.rpn.App
```
