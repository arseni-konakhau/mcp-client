# MCP Client Project Brief

## Project Overview
A Spring Boot-based MCP (Model Context Protocol) client designed to connect to and interact with MCP servers, specifically the Atlassian MCP server for Jira and Confluence operations.

## Core Objectives
- Provide a reliable REST API interface for MCP server interactions
- Enable tool discovery and execution
- Support resource browsing and retrieval
- Manage prompt execution
- Ensure real-time server communication with comprehensive error handling

## Technical Stack
- **Framework**: Spring Boot 3.5.3
- **Language**: Java 21
- **Build Tool**: Gradle 8.5+
- **Dependencies**: Spring AI MCP Client, Spring Boot Web
- **Protocol**: Model Context Protocol (MCP)

## Key Features
- Full MCP protocol implementation
- Atlassian integration (Jira/Confluence)
- REST API endpoints for all operations
- Async operations with CompletableFuture
- Robust error handling and logging
- Flexible server configuration management

## Project Status
- ✅ Basic Spring Boot application structure
- ✅ MCP client dependencies configured
- ✅ Core API endpoints defined
- ✅ HTTP validation tests available
- 🔄 Ready for implementation and testing

## Next Steps
- Implement core MCP client functionality
- Test connection with Atlassian MCP server
- Validate all API endpoints
- Ensure proper error handling
- Document usage patterns
