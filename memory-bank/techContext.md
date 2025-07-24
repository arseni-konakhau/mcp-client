# Technical Context: MCP Client

## Technology Stack

### Core Framework
- **Java 21**: Latest LTS version with modern language features
- **Spring Boot 3.5.3**: Enterprise-grade application framework
- **Spring AI 1.0.0**: AI integration framework with MCP support
- **Gradle**: Build automation and dependency management

### Key Dependencies
- **spring-boot-starter-web**: Web application capabilities
- **spring-ai-starter-mcp-client**: MCP client implementation
- **spring-boot-devtools**: Development-time enhancements
- **spring-boot-starter-test**: Testing framework
- **junit-platform-launcher**: Test execution platform

## Development Setup

### Prerequisites
- Java 21 JDK installed
- Gradle (wrapper included in project)
- IDE with Java support (IntelliJ IDEA, Eclipse, VS Code)

### Project Structure
```
mcp-client/
├── build.gradle                 # Build configuration
├── settings.gradle              # Project settings
├── gradlew                      # Gradle wrapper (Unix)
├── gradlew.bat                  # Gradle wrapper (Windows)
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/mcpclient/mcpclient/
│   │   │       └── McpclientApplication.java
│   │   └── resources/
│   │       ├── application.properties
│   │       ├── static/          # Static web resources
│   │       └── templates/       # Template files
│   └── test/
│       └── java/
│           └── com/mcpclient/mcpclient/
│               └── McpclientApplicationTests.java
├── memory-bank/                 # Project documentation
└── clinerules                   # Development guidelines
```

### Build Commands
- **Run Application**: `./gradlew bootRun`
- **Build JAR**: `./gradlew build`
- **Run Tests**: `./gradlew test`
- **Clean Build**: `./gradlew clean build`

## Technical Constraints

### Java Version
- **Target**: Java 21 (LTS)
- **Toolchain**: Configured in build.gradle
- **Features**: Can leverage modern Java features (records, pattern matching, etc.)

### Spring Boot Configuration
- **Version**: 3.5.3 (latest stable)
- **Auto-configuration**: Leverages Spring Boot's auto-configuration
- **Profiles**: Can use Spring profiles for different environments

### MCP Integration
- **Spring AI**: Uses Spring AI's MCP client starter
- **Protocol**: Implements MCP specification
- **Transport**: Supports various MCP transport mechanisms

## Development Environment

### IDE Configuration
- Java 21 language level
- Gradle project import
- Spring Boot run configurations
- Code formatting and style guides

### Testing Strategy
- Unit tests with JUnit 5
- Integration tests with Spring Boot Test
- MCP client functionality testing
- Web layer testing

## Deployment Considerations

### Packaging
- Executable JAR with embedded Tomcat
- Docker containerization ready
- Cloud-native deployment support

### Configuration
- Externalized configuration via application.properties
- Environment-specific profiles
- MCP server connection configuration

### Monitoring
- Spring Boot Actuator endpoints
- Health checks and metrics
- Logging configuration
