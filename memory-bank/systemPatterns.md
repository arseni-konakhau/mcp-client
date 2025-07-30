# System Patterns

## Architecture Patterns
- **REST API Pattern**: Standard HTTP endpoints for MCP operations
- **Service Layer Pattern**: Business logic separation in McpService
- **Configuration Pattern**: Externalized configuration management
- **Controller Pattern**: Request/response handling in McpController

## Design Patterns
- **Singleton**: Spring Boot application context
- **Factory**: MCP client creation and management
- **Strategy**: Different MCP server configurations
- **Observer**: Async operation callbacks
- **Builder**: MCP request construction

## Error Handling Patterns
- **Global Exception Handler**: Centralized error processing
- **Response Wrapper**: Consistent API response format
- **Logging Strategy**: Structured logging for debugging
- **Retry Pattern**: Connection resilience

## Integration Patterns
- **Client-Server**: MCP protocol communication
- **REST Gateway**: API endpoint exposure
- **Configuration Management**: Environment-based settings
- **Health Check**: Connection status monitoring

## Code Organization
- **Package Structure**: com.mcpclient.mcpclient.*
- **Resource Management**: Static resources and templates
- **Test Structure**: Unit and integration tests
- **Documentation**: README and API documentation
