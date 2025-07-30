# Technical Context

## Technology Stack
- **Framework**: Spring Boot 3.5.3
- **Language**: Java 21
- **Build Tool**: Gradle 8.5+
- **Dependencies**: 
  - Spring Boot Web Starter
  - Spring AI MCP Client
  - Spring Boot DevTools
  - Spring Boot Test

## MCP Protocol Implementation
- **Protocol**: Model Context Protocol (MCP)
- **Client Library**: Spring AI MCP Client
- **Server Integration**: Atlassian MCP Server
- **Communication**: Async operations with CompletableFuture

## API Design
- **Port**: 3332 (configurable)
- **Protocol**: HTTP/REST
- **Content Type**: application/json
- **Authentication**: TBD (currently none)
- **Error Handling**: Standard HTTP status codes

## Development Environment
- **IDE**: Any Java IDE (IntelliJ, Eclipse, VS Code)
- **Package Manager**: Gradle
- **Version Control**: Git
- **Testing**: JUnit 5, Spring Boot Test

## Deployment
- **Container**: Docker (TBD)
- **Platform**: Any JVM-compatible platform
- **Configuration**: application.properties
- **Monitoring**: Spring Boot Actuator (TBD)

## Dependencies
- **Core**: Spring Boot ecosystem
- **MCP**: Spring AI MCP Client
- **Testing**: JUnit, Spring Test
- **Development**: DevTools for hot reload
