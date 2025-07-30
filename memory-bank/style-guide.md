# Style Guide

## Code Style
- **Language**: Java 21
- **Naming**: camelCase for variables/methods, PascalCase for classes
- **Package**: com.mcpclient.mcpclient.*
- **Indentation**: 4 spaces
- **Line Length**: 120 characters max

## API Design
- **REST Endpoints**: /api/mcp/*
- **HTTP Methods**: GET, POST, PUT, DELETE
- **Response Format**: JSON
- **Status Codes**: Standard HTTP codes
- **Error Format**: Consistent error response structure

## Documentation
- **README**: Project overview and setup
- **API Docs**: Endpoint documentation
- **Code Comments**: Javadoc for public methods
- **HTTP Tests**: Validation files in _http_/

## File Organization
- **Controllers**: REST API endpoints
- **Services**: Business logic
- **Config**: Configuration classes
- **Tests**: Unit and integration tests
- **Resources**: Static files and templates

## Naming Conventions
- **Classes**: McpClient, McpService, McpController
- **Methods**: initialize(), getCapabilities(), executeTool()
- **Variables**: mcpClient, serverInfo, toolName
- **Constants**: UPPER_SNAKE_CASE
- **Packages**: lowercase with dots
