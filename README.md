# Scratch Game
This project is a scratch game that generates a matrix of symbols based on configured probabilities and calculates a reward based on winning combinations and bonus symbols.

## Prerequisites
- **Java 21**
- **Maven 3.x**

## How to Build
To build the project using Maven, run the following command from the project root:

```bash
mvn clean package
```

## How to Run
After building the project, you can run the application with the following command:

```bash
java -jar target/scratch-game-1.0-SNAPSHOT.jar --config src/main/resources/config.json --betting-amount 100
```

### Command-Line Arguments
- **--config**:
Specifies the path to the configuration file. In this example, it points to `src/main/resources/config.json`.
- **--betting-amount**:
Specifies the betting amount (an integer value). In the example above, the betting amount is 100.

## Additional Information
### Dependencies:
- **Gson** for JSON serialization/deserialization.
- **Guava** for utility methods and collections.
- **Lombok** to reduce boilerplate code.
- **SLF4J** with **Logback** for logging.
- **JUnit 5** and **Mockito** for testing.