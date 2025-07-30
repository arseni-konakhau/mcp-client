# MCP End-to-End Setup Guide

This guide provides complete instructions for setting up and running both the MCP Client (Java Spring Boot) and MCP Server (Atlassian) in this workspace.

## 📋 Overview

This workspace contains two complementary projects:

- **MCP Client** (`mcp-client/`): Java Spring Boot application that implements an MCP client using Spring AI
- **MCP Server** (`mcp-server--atlassian/`): Python-based MCP server that provides tools for Atlassian products (Jira & Confluence)

## 🎯 Quick Start

### Prerequisites Check
```bash
# Check Java 21
java -version  # Should show version 21.x.x

# Check Python 3.10+
python3 --version  # Should show 3.10 or higher

# Check Docker
docker --version

# Check Git
git --version
```

### 1. Setup MCP Server (Atlassian)
```bash
cd mcp-server--atlassian

# Install dependencies
python3 install_dependencies.py

# Setup environment
cp .env.debug .env
# Edit .env with your Atlassian credentials (see detailed setup below)

# Test the server
uv run python3 simple_test.py --verbose
```

### 2. Setup MCP Client
```bash
cd ../mcp-client

# Run the application
./gradlew bootRun

# Access at http://localhost:3332
```

## 🔧 Detailed Setup Instructions

## Part 1: MCP Server (Atlassian) Setup

### Step 1: Environment Setup

#### Install Python Dependencies
```bash
cd mcp-server--atlassian
python3 install_dependencies.py
```

#### Configure Authentication

**For Atlassian Cloud (Recommended):**
1. Go to https://id.atlassian.com/manage-profile/security/api-tokens
2. Click "Create API token", name it, and copy the token
3. Edit `.env` file:

```bash
cp .env.debug .env
```

Edit `.env` with your credentials:
```bash
# Atlassian URLs
JIRA_URL=https://your-company.atlassian.net
CONFLUENCE_URL=https://your-company.atlassian.net/wiki

# Authentication (API Token method)
JIRA_USERNAME=your.email@company.com
JIRA_API_TOKEN=your_api_token_here
CONFLUENCE_USERNAME=your.email@company.com
CONFLUENCE_API_TOKEN=your_api_token_here

# Debug settings
MCP_VERBOSE=true
READ_ONLY_MODE=true
```

**For Atlassian Server/Data Center:**
```bash
# Personal Access Token method
JIRA_URL=https://jira.your-company.com
CONFLUENCE_URL=https://confluence.your-company.com
JIRA_PERSONAL_TOKEN=your_jira_pat
CONFLUENCE_PERSONAL_TOKEN=your_confluence_pat

# SSL verification (for self-signed certificates)
JIRA_SSL_VERIFY=false
CONFLUENCE_SSL_VERIFY=false
```

### Step 2: Test MCP Server

#### Basic Validation Test
```bash
# This is the proven workflow that works
uv run python3 simple_test.py --verbose
```

Expected output:
- ✅ All import tests passed
- ✅ Configuration tests passed  
- ✅ API connectivity tests passed
- ✅ MCP server tests passed
- ✅ "All tests passed! The MCP Atlassian server appears to be working correctly."

#### Advanced Testing (Optional)
```bash
# Direct API testing
uv run python3 manual_test_debug.py --mode api --verbose

# HTTP transport testing
uv run python3 manual_test_debug.py --mode http --port 8000

# Full MCP server test
uv run mcp-atlassian --env-file .env --read-only --verbose
```

### Step 3: Run MCP Server

#### Option A: Direct Python Execution
```bash
# Read-only mode (safe for testing)
uv run mcp-atlassian --env-file .env --read-only --verbose

# Full functionality
uv run mcp-atlassian --env-file .env --verbose
```

#### Option B: Docker Execution
```bash
# Pull the image
docker pull ghcr.io/sooperset/mcp-atlassian:latest

# Run with environment file
docker run --rm -i --env-file .env ghcr.io/sooperset/mcp-atlassian:latest

# Run with HTTP transport
docker run --rm -p 9000:9000 --env-file .env \
  ghcr.io/sooperset/mcp-atlassian:latest \
  --transport streamable-http --port 9000 --verbose
```

## Part 2: MCP Client Setup

### Step 1: Prerequisites
Ensure Java 21 is installed:

**macOS (using Homebrew):**
```bash
brew install openjdk@21
```

**Ubuntu/Debian:**
```bash
sudo apt update
sudo apt install openjdk-21-jdk
```

**Windows:**
Download from [Oracle JDK 21](https://www.oracle.com/java/technologies/downloads/#java21) or [OpenJDK 21](https://adoptium.net/)

### Step 2: Build and Run

```bash
cd mcp-client

# Make gradlew executable (Unix/Linux/macOS only)
chmod +x gradlew

# Run the application
./gradlew bootRun
```

### Step 3: Verify Client

Access the application:
- **URL**: http://localhost:3332
- **Expected**: 404 error with JSON response (this is normal - no controllers implemented yet)

The 404 response confirms the Spring Boot application is running correctly.

## 🔗 Integration Setup

### IDE Integration (Claude Desktop/Cursor)

#### For MCP Server with Docker
Create or edit your MCP configuration file:

**Windows**: `%APPDATA%\Claude\claude_desktop_config.json`
**macOS**: `~/Library/Application Support/Claude/claude_desktop_config.json`
**Linux**: `~/.config/Claude/claude_desktop_config.json`

```json
{
  "mcpServers": {
    "mcp-atlassian": {
      "command": "docker",
      "args": [
        "run",
        "-i",
        "--rm",
        "-e", "CONFLUENCE_URL",
        "-e", "CONFLUENCE_USERNAME",
        "-e", "CONFLUENCE_API_TOKEN",
        "-e", "JIRA_URL",
        "-e", "JIRA_USERNAME",
        "-e", "JIRA_API_TOKEN",
        "ghcr.io/sooperset/mcp-atlassian:latest"
      ],
      "env": {
        "CONFLUENCE_URL": "https://your-company.atlassian.net/wiki",
        "CONFLUENCE_USERNAME": "your.email@company.com",
        "CONFLUENCE_API_TOKEN": "your_confluence_api_token",
        "JIRA_URL": "https://your-company.atlassian.net",
        "JIRA_USERNAME": "your.email@company.com",
        "JIRA_API_TOKEN": "your_jira_api_token"
      }
    }
  }
}
```

#### For MCP Server with Local Python
```json
{
  "mcpServers": {
    "mcp-atlassian": {
      "command": "uv",
      "args": [
        "run", 
        "mcp-atlassian", 
        "--env-file", 
        "/absolute/path/to/mcp-server--atlassian/.env",
        "--verbose"
      ],
      "cwd": "/absolute/path/to/mcp-server--atlassian"
    }
  }
}
```

### HTTP Transport Integration

#### Start HTTP Server
```bash
cd mcp-server--atlassian

# SSE transport
docker run --rm -p 9000:9000 --env-file .env \
  ghcr.io/sooperset/mcp-atlassian:latest \
  --transport sse --port 9000 --verbose

# OR streamable-http transport
docker run --rm -p 9000:9000 --env-file .env \
  ghcr.io/sooperset/mcp-atlassian:latest \
  --transport streamable-http --port 9000 --verbose
```

#### Configure IDE for HTTP
```json
{
  "mcpServers": {
    "mcp-atlassian-http": {
      "url": "http://localhost:9000/sse"
    }
  }
}
```

## 🧪 Testing the Integration

### Test MCP Server Tools

Once your IDE is configured, you can test these commands with your AI assistant:

#### Jira Operations
- "Show me urgent bugs in PROJ project from last week"
- "Create a new issue in project PROJ with title 'Test Issue'"
- "Get details for issue PROJ-123"
- "Search for issues assigned to me"

#### Confluence Operations
- "Search for 'API documentation' in Confluence"
- "Get the content of page 'Team Guidelines'"
- "Find all pages in the DEV space"
- "Create a new page titled 'Test Page' in space DEV"

### Test MCP Client

The MCP client is currently in foundation phase. Access http://localhost:3332 to verify it's running.

## 🔧 Configuration Options

### MCP Server Configuration

#### Tool Filtering
```bash
# Enable only specific tools
ENABLED_TOOLS=confluence_search,jira_get_issue,jira_search

# Enable read-only mode
READ_ONLY_MODE=true

# Filter by spaces/projects
CONFLUENCE_SPACES_FILTER=DEV,TEAM,DOC
JIRA_PROJECTS_FILTER=PROJ,DEV,SUPPORT
```

#### Logging and Debug
```bash
# Enable verbose logging
MCP_VERBOSE=true
MCP_VERY_VERBOSE=true
MCP_LOGGING_STDOUT=true
```

#### Proxy Configuration
```bash
# Standard proxy settings
HTTP_PROXY=http://proxy.company.com:8080
HTTPS_PROXY=http://proxy.company.com:8080
NO_PROXY=localhost,.company.com
```

### MCP Client Configuration

Edit `mcp-client/src/main/resources/application.properties`:
```properties
spring.application.name=mcpclient
server.port=3332

# Add MCP-specific configuration as development progresses
```

## 🐛 Troubleshooting

### Common MCP Server Issues

#### Authentication Errors
```bash
# Test API connectivity manually
curl -u "your.email@company.com:your_api_token" \
  "https://your-company.atlassian.net/rest/api/2/myself"
```

#### Import/Dependency Errors
```bash
# Reinstall dependencies
cd mcp-server--atlassian
python3 install_dependencies.py
```

#### SSL Certificate Issues (Server/Data Center)
```bash
# Add to .env
JIRA_SSL_VERIFY=false
CONFLUENCE_SSL_VERIFY=false
```

### Common MCP Client Issues

#### Java Version Issues
```bash
# Check Java version
java -version
# Should show 21.x.x

# Set JAVA_HOME if needed
export JAVA_HOME=/path/to/java21
```

#### Port Already in Use
```bash
# Find process using port 3332
lsof -i :3332

# Kill the process
kill -9 <PID>

# Or run on different port
./gradlew bootRun --args='--server.port=3333'
```

#### Build Failures
```bash
# Clean and rebuild
./gradlew clean build
```

### Debug Mode

#### MCP Server Debug
```bash
# Enable maximum logging
export MCP_VERY_VERBOSE=true
export MCP_LOGGING_STDOUT=true

# Run with debug
uv run mcp-atlassian --env-file .env --verbose
```

#### MCP Client Debug
```bash
# Enable debug logging
./gradlew bootRun --args='--logging.level.com.mcpclient=DEBUG'
```

## 📁 Project Structure

```
MCP/
├── mcp-client/                    # Java Spring Boot MCP Client
│   ├── src/main/java/            # Java source code
│   ├── src/main/resources/       # Configuration files
│   ├── build.gradle              # Build configuration
│   ├── RUN-PROJECT-GUIDE.md      # Client-specific guide
│   └── memory-bank/              # Client documentation
│
├── mcp-server--atlassian/         # Python MCP Server for Atlassian
│   ├── src/mcp_atlassian/        # Python source code
│   ├── .env.debug                # Environment template
│   ├── simple_test.py            # Testing script
│   ├── install_dependencies.py   # Setup script
│   ├── README.md                 # Server documentation
│   ├── TESTING_GUIDE.md          # Testing instructions
│   └── memory-bank/              # Server documentation
│
└── MCP-END2END.md                # This guide
```

## 🚀 Next Steps

### Development Workflow

1. **Start MCP Server**: Run the Atlassian MCP server first
2. **Test Server**: Verify it works with `simple_test.py`
3. **Configure IDE**: Set up your AI assistant to use the MCP server
4. **Start MCP Client**: Run the Spring Boot client for development
5. **Develop Integration**: Build features that connect the client to MCP servers

### Production Deployment

#### MCP Server
- Use Docker image: `ghcr.io/sooperset/mcp-atlassian:latest`
- Remove `READ_ONLY_MODE=true` for write operations
- Set up monitoring and logging
- Configure proper authentication and security

#### MCP Client
- Build production JAR: `./gradlew clean build -Pprod`
- Deploy to cloud platform or container
- Configure external MCP server connections
- Set up health checks and monitoring

## 📚 Additional Resources

### Documentation
- **MCP Client**: `mcp-client/memory-bank/` directory
- **MCP Server**: `mcp-server--atlassian/memory-bank/` directory
- **Spring AI MCP**: https://docs.spring.io/spring-ai/reference/
- **MCP Protocol**: https://modelcontextprotocol.io/

### Support
- **MCP Server Issues**: Check `mcp-server--atlassian/TESTING_GUIDE.md`
- **MCP Client Issues**: Check `mcp-client/RUN-PROJECT-GUIDE.md`
- **Integration Issues**: Review this guide's troubleshooting section

---

**Quick Command Summary:**
```bash
# Setup MCP Server
cd mcp-server--atlassian && python3 install_dependencies.py && cp .env.debug .env
# Edit .env with your credentials, then:
uv run python3 simple_test.py --verbose

# Setup MCP Client
cd ../mcp-client && ./gradlew bootRun
```

This guide provides everything needed to run both MCP components successfully. Follow the steps in order, and refer to the troubleshooting section if you encounter any issues.
