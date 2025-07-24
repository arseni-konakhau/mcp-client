# MCP Client - Spring Boot Application

A reliable Spring Boot-based MCP (Model Context Protocol) client designed to connect to and interact with MCP servers, specifically the Atlassian MCP server for Jira and Confluence operations.

## Overview

This MCP client provides a REST API interface to interact with MCP servers, enabling:
- Tool discovery and execution
- Resource browsing and retrieval
- Prompt management and execution
- Real-time server communication
- Comprehensive error handling

## Features

### ✅ Core Functionality
- **MCP Protocol Support**: Full implementation of MCP specification
- **Atlassian Integration**: Pre-configured for Atlassian MCP server
- **REST API**: Complete HTTP API for all MCP operations
- **Async Operations**: Non-blocking operations with CompletableFuture
- **Error Handling**: Robust error handling and logging
- **Configuration**: Flexible server configuration management

### 🔄 API Endpoints

#### Connection Management
- `POST /api/mcp/initialize` - Initialize MCP connection
- `GET /api/mcp/ready` - Check client readiness
- `POST /api/mcp/ping` - Ping the server
- `POST /api/mcp/close` - Close connection gracefully

#### Server Information
- `GET /api/mcp/capabilities` - Get server capabilities
- `GET /api/mcp/server-info` - Get server information
- `GET /api/mcp/server-instructions` - Get server instructions

#### Tools
- `GET /api/mcp/tools` - List available tools
- `POST /api/mcp/tools/{toolName}/execute` - Execute a tool

#### Resources
- `GET /api/mcp/resources` - List available resources
- `GET /api/mcp/resources/read?uri={uri}` - Read a specific resource

#### Prompts
- `GET /api/mcp/prompts` - List available prompts
- `POST /api/mcp/prompts/{promptName}` - Get a specific prompt

## Quick Start

### Prerequisites
- Java 21 or higher
- Gradle 8.5+
- Python 3.11+ (for Atlassian MCP server)
- Atlassian MCP server running

### 1. Clone and Build
```bash
git clone <repository-url>
cd mcp-client
./gradlew build
```

### 2. Configure Atlassian MCP Server
Ensure the Atlassian MCP server is properly configured and running. The client is pre-configured to connect to:
- **Command**: `python -m mcp_atlassian.servers.main`
- **Working Directory**: `../mcp-server--atlassian`

### 3. Start the Client
```bash
./gradlew bootRun
```

The application will start on `http://localhost:3332`

### 4. Test the Connection
```bash
# Initialize the connection
curl -X POST http://localhost:3332/api/mcp/initialize

# Check if ready
curl http://localhost:3332/api/mcp/ready

# List available tools
curl http://localhost:3332/api/mcp/tools
```

## Configuration

### Application Properties
```properties
# Server configuration
server.port=3332

# MCP Server Configuration
mcp.servers.atlassian.command=python
mcp.servers.atlassian.arguments=-m,mcp_atlassian.servers.main
mcp.servers.atlassian.working-directory=../mcp-server--atlassian

# Logging
logging.level.io.modelcontextprotocol=DEBUG
logging.level.com.mcpclient=DEBUG
```

### Environment Variables
You can override configuration using environment variables:
- `SERVER_PORT` - Application port (default: 3332)
- `MCP_ATLASSIAN_COMMAND` - MCP server command
- `MCP_ATLASSIAN_WORKING_DIR` - MCP server working directory

## Usage Examples

### Using HTTP Test File
The project includes a comprehensive HTTP test file at `_http_/mcp-client-test.http` with examples for all endpoints.

### Example: Execute a Jira Tool
```bash
# List Jira projects
curl -X POST http://localhost:3332/api/mcp/tools/jira_list_projects/execute \
  -H "Content-Type: application/json" \
  -d '{"max_results": 10}'
```

### Example: Search Confluence Pages
```bash
# Search Confluence pages
curl -X POST http://localhost:3332/api/mcp/tools/confluence_search_pages/execute \
  -H "Content-Type: application/json" \
  -d '{"query": "documentation", "limit": 5}'
```

### Example: Read a Resource
```bash
# Read Jira projects resource
curl "http://localhost:3332/api/mcp/resources/read?uri=jira://projects"
```

## Architecture

### Components
- **McpConfiguration**: Spring configuration for MCP client beans
- **McpServerProperties**: Configuration properties for server connection
- **McpService**: Business logic layer for MCP operations
- **McpController**: REST API endpoints
- **McpSyncClient**: Synchronous MCP client wrapper

### Design Patterns
- **Dependency Injection**: Spring-managed beans
- **Async Processing**: CompletableFuture for non-blocking operations
- **Configuration Properties**: Type-safe configuration binding
- **Error Handling**: Comprehensive exception handling

## Development

### Project Structure
```
src/main/java/com/mcpclient/mcpclient/
├── McpclientApplication.java          # Main application class
├── config/
│   ├── McpConfiguration.java          # MCP client configuration
│   └── McpServerProperties.java       # Server properties
├── service/
│   └── McpService.java                # Business logic
└── controller/
    └── McpController.java             # REST endpoints

src/main/resources/
├── application.properties             # Configuration
└── static/                           # Static web resources

_http_/
└── mcp-client-test.http              # HTTP test examples
```

### Building
```bash
# Build the project
./gradlew build

# Run tests
./gradlew test

# Run the application
./gradlew bootRun

# Create executable JAR
./gradlew bootJar
```

### Testing
The project includes:
- Unit tests for service layer
- Integration tests for REST endpoints
- HTTP test file for manual testing

## Troubleshooting

### Common Issues

1. **Connection Failed**
   - Ensure Atlassian MCP server is running
   - Check server configuration in application.properties
   - Verify Python environment and dependencies

2. **Port Already in Use**
   - Change port in application.properties: `server.port=8080`
   - Or set environment variable: `SERVER_PORT=8080`

3. **Tool Execution Errors**
   - Check tool parameters match expected schema
   - Verify Atlassian credentials are configured
   - Review server logs for detailed error messages

### Logging
Enable debug logging for troubleshooting:
```properties
logging.level.io.modelcontextprotocol=DEBUG
logging.level.com.mcpclient=DEBUG
logging.level.root=INFO
```

## Contributing

1. Fork the repository
2. Create a feature branch
3. Make your changes
4. Add tests for new functionality
5. Submit a pull request

## License

[Add your license information here]

## Support

For issues and questions:
1. Check the troubleshooting section
2. Review the HTTP test examples
3. Check server logs for detailed error messages
4. Open an issue in the repository

## Related Projects

- [Atlassian MCP Server](../mcp-server--atlassian/) - The companion MCP server for Jira and Confluence
- [MCP Specification](https://github.com/modelcontextprotocol/specification) - Official MCP protocol specification
