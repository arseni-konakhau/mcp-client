# System Patterns: MCP Client

## Architecture Overview

### High-Level Architecture
```
┌─────────────────────────────────────────────────────────────┐
│                    MCP Client Application                   │
├─────────────────────────────────────────────────────────────┤
│  Web Layer (Controllers, REST APIs)                        │
├─────────────────────────────────────────────────────────────┤
│  Service Layer (Business Logic)                            │
├─────────────────────────────────────────────────────────────┤
│  MCP Client Layer (Spring AI MCP Client)                   │
├─────────────────────────────────────────────────────────────┤
│  Transport Layer (HTTP, WebSocket, Stdio)                  │
└─────────────────────────────────────────────────────────────┘
                              │
                              ▼
┌─────────────────────────────────────────────────────────────┐
│                    MCP Servers                             │
│  ┌─────────────┐  ┌─────────────┐  ┌─────────────┐        │
│  │   Server A  │  │   Server B  │  │   Server C  │        │
│  │   (Tools)   │  │ (Resources) │  │  (Prompts)  │        │
│  └─────────────┘  └─────────────┘  └─────────────┘        │
└─────────────────────────────────────────────────────────────┘
```

## Design Patterns

### 1. Dependency Injection (Spring Framework)
- **Pattern**: Constructor-based dependency injection
- **Usage**: All service dependencies injected via constructors
- **Benefits**: Immutable dependencies, easier testing, clear contracts

### 2. Auto-Configuration (Spring Boot)
- **Pattern**: Convention over configuration
- **Usage**: Spring Boot auto-configures MCP client based on classpath
- **Benefits**: Minimal configuration, rapid development

### 3. Factory Pattern (Spring AI)
- **Pattern**: MCP client factory for creating connections
- **Usage**: Spring AI provides factory beans for MCP client instances
- **Benefits**: Abstraction of client creation, lifecycle management

### 4. Observer Pattern (Event-Driven)
- **Pattern**: Spring Application Events
- **Usage**: MCP connection events, tool execution events
- **Benefits**: Loose coupling, extensible event handling

## Component Relationships

### Core Components
```
McpclientApplication
    │
    ├── WebController (REST endpoints)
    │   └── McpService (business logic)
    │       └── McpClientManager (connection management)
    │           └── Spring AI MCP Client
    │
    ├── ConfigurationProperties (MCP server configs)
    │
    └── EventListeners (connection/execution events)
```

### Data Flow
1. **Incoming Request**: Web controller receives HTTP request
2. **Service Layer**: Controller delegates to service for business logic
3. **MCP Client**: Service uses MCP client to communicate with servers
4. **Response Processing**: Results processed and returned to client

## Key Technical Decisions

### 1. Spring AI Integration
- **Decision**: Use Spring AI's MCP client starter
- **Rationale**: 
  - Official Spring support for MCP
  - Handles protocol complexity
  - Integrates with Spring ecosystem
- **Trade-offs**: Dependency on Spring AI release cycle

### 2. Web-First Architecture
- **Decision**: Build as web application with REST APIs
- **Rationale**:
  - User-friendly interface
  - API-first design for integration
  - Standard Spring Boot patterns
- **Trade-offs**: Additional complexity vs. CLI-only approach

### 3. Configuration-Driven Connections
- **Decision**: Configure MCP servers via application properties
- **Rationale**:
  - Externalized configuration
  - Environment-specific setups
  - Spring Boot best practices
- **Trade-offs**: Static vs. dynamic server discovery

### 4. Asynchronous Operations
- **Decision**: Support async MCP operations where appropriate
- **Rationale**:
  - Better user experience
  - Handle long-running operations
  - Scalability considerations
- **Trade-offs**: Complexity vs. responsiveness

## Error Handling Patterns

### 1. Graceful Degradation
- MCP server unavailable → Continue with available servers
- Tool execution failure → Return error details, don't crash

### 2. Circuit Breaker (Future)
- Protect against cascading failures
- Automatic recovery mechanisms

### 3. Retry Logic
- Transient network failures
- Configurable retry policies

## Security Patterns

### 1. Authentication Delegation
- Delegate authentication to MCP servers
- Support various auth mechanisms (API keys, OAuth, etc.)

### 2. Input Validation
- Validate all inputs before sending to MCP servers
- Sanitize responses before displaying

### 3. Connection Security
- Support secure transport (HTTPS, WSS)
- Certificate validation

## Testing Patterns

### 1. Layer Testing
- **Unit Tests**: Individual components in isolation
- **Integration Tests**: Spring context with test configurations
- **Contract Tests**: MCP protocol compliance

### 2. Mock Strategies
- Mock MCP servers for testing
- Test doubles for external dependencies

### 3. Test Configurations
- Separate test application properties
- Test-specific Spring profiles
