# Run Project Guide: MCP Client

## Prerequisites

### Required Software
- **Java 21 JDK** (OpenJDK or Oracle JDK)
- **Git** (for cloning the repository)
- **Terminal/Command Prompt** access

### Verify Prerequisites
```bash
# Check Java version (should be 21.x.x)
java -version

# Check Java compiler version
javac -version

# Check Git installation
git --version
```

## Quick Start

### 1. Clone the Repository
```bash
git clone https://github.com/arseni-konakhau/mcp-client.git
cd mcp-client
```

### 2. Run the Application
```bash
# On Unix/Linux/macOS
./gradlew bootRun

# On Windows
gradlew.bat bootRun
```

### 3. Access the Application
- **Application URL**: http://localhost:3335
- **Application Name**: mcpclient
- **Custom Port**: 3335

## Detailed Setup Instructions

### Step 1: Environment Setup

#### Install Java 21
**On macOS (using Homebrew):**
```bash
brew install openjdk@21
```

**On Ubuntu/Debian:**
```bash
sudo apt update
sudo apt install openjdk-21-jdk
```

**On Windows:**
- Download from [Oracle JDK 21](https://www.oracle.com/java/technologies/downloads/#java21) or [OpenJDK 21](https://adoptium.net/)
- Install and set JAVA_HOME environment variable

#### Verify Java Installation
```bash
java -version
# Expected output: openjdk version "21.x.x" or similar
```

### Step 2: Project Setup

#### Clone and Navigate
```bash
git clone https://github.com/arseni-konakhau/mcp-client.git
cd mcp-client
```

#### Verify Project Structure
```bash
ls -la
# Should see: build.gradle, gradlew, src/, memory-bank/, etc.
```

### Step 3: Build and Run

#### Option A: Run with Gradle (Recommended)
```bash
# Make gradlew executable (Unix/Linux/macOS only)
chmod +x gradlew

# Run the application
./gradlew bootRun
```

#### Option B: Build JAR and Run
```bash
# Build the project
./gradlew build

# Run the built JAR
java -jar build/libs/mcpclient-0.0.1-SNAPSHOT.jar
```

#### Option C: Development Mode with Auto-Reload
```bash
# Run with Spring Boot DevTools for auto-reload
./gradlew bootRun --continuous
```

## Application Access

### Default Configuration
- **URL**: http://localhost:3335
- **Port**: 3335 (customized)
- **Context Path**: / (root)
- **Application Name**: mcpclient

### Application Status Check
Once running, verify the application is running:
```bash
# Check if application is responding (will return 404 - this is expected)
curl http://localhost:3335
# Expected: 404 error with JSON response - this means the app is running correctly
```

**Important Note**: The 404 error is expected because:
- No controllers or endpoints are implemented yet
- No static content (index.html) exists
- Spring Boot Actuator endpoints are not enabled by default
- This is a minimal Spring Boot application in foundation phase

The 404 response with JSON error details confirms the application is running properly.

## Development Commands

### Essential Gradle Commands
```bash
# Run the application
./gradlew bootRun

# Build the project
./gradlew build

# Run tests
./gradlew test

# Clean build artifacts
./gradlew clean

# Clean and build
./gradlew clean build

# Check for dependency updates
./gradlew dependencyUpdates
```

### Development Workflow
```bash
# 1. Start development server
./gradlew bootRun

# 2. In another terminal, run tests
./gradlew test --continuous

# 3. Make code changes (DevTools will auto-reload)
```

## Configuration

### Application Properties
The application configuration is in `src/main/resources/application.properties`:
```properties
spring.application.name=mcpclient
server.port=3335
# Add additional configuration as needed
```

### Environment-Specific Configuration
Create additional property files for different environments:
- `application-dev.properties` (development)
- `application-prod.properties` (production)
- `application-test.properties` (testing)

Run with specific profile:
```bash
./gradlew bootRun --args='--spring.profiles.active=dev'
```

## Troubleshooting

### Common Issues

#### Issue: "Java version not supported"
**Solution**: Ensure Java 21 is installed and JAVA_HOME is set correctly
```bash
export JAVA_HOME=/path/to/java21
export PATH=$JAVA_HOME/bin:$PATH
```

#### Issue: "Permission denied: ./gradlew"
**Solution**: Make gradlew executable
```bash
chmod +x gradlew
```

#### Issue: "Port 3335 already in use"
**Solutions**:
1. Kill process using port 3335:
   ```bash
   # Find process using port 3335
   lsof -i :3335
   # Kill the process
   kill -9 <PID>
   ```
2. Or run on different port:
   ```bash
   ./gradlew bootRun --args='--server.port=3333'
   ```

#### Issue: "Build failed"
**Solutions**:
1. Clean and rebuild:
   ```bash
   ./gradlew clean build
   ```
2. Check Java version compatibility
3. Verify internet connection for dependency downloads

#### Issue: "Getting 404 error when accessing http://localhost:3335"
**This is EXPECTED behavior** - the application is working correctly!

**Why you see 404:**
- The project is in foundation phase with no controllers implemented
- No static content (like index.html) exists yet
- No REST endpoints are defined yet

**What the 404 response tells you:**
- ✅ Spring Boot application is running
- ✅ Tomcat server is responding
- ✅ Error handling is working (returns proper JSON error)
- ✅ Ready for development

**Next steps:**
- Follow `memory-bank/progress.md` to see what needs to be implemented
- Start with Phase 1: Core MCP Integration
- Add controllers and endpoints as development progresses

### Logs and Debugging

#### View Application Logs
Logs are displayed in the terminal when running with `./gradlew bootRun`

#### Enable Debug Logging
```bash
./gradlew bootRun --args='--logging.level.com.mcpclient=DEBUG'
```

#### Log Files
By default, logs are output to console. To enable file logging, add to `application.properties`:
```properties
logging.file.name=logs/mcpclient.log
logging.level.com.mcpclient=DEBUG
```

## IDE Setup

### IntelliJ IDEA
1. Open project: File → Open → Select `mcp-client` directory
2. Import as Gradle project
3. Set Project SDK to Java 21
4. Run configuration: Create Spring Boot run configuration

### VS Code
1. Install Java Extension Pack
2. Open project folder
3. VS Code will auto-detect Gradle project
4. Use Command Palette: "Java: Run Spring Boot App"

### Eclipse
1. Import → Existing Gradle Project
2. Select `mcp-client` directory
3. Set Java Build Path to Java 21
4. Run as Spring Boot App

## Production Deployment

### Build Production JAR
```bash
./gradlew clean build -Pprod
```

### Run Production JAR
```bash
java -jar build/libs/mcpclient-0.0.1-SNAPSHOT.jar \
  --spring.profiles.active=prod \
  --server.port=3335
```

### Docker (Future Enhancement)
```dockerfile
# Dockerfile example for future use
FROM openjdk:21-jre-slim
COPY build/libs/mcpclient-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 3335
ENTRYPOINT ["java", "-jar", "/app.jar"]
```

## Next Steps

After successfully running the application:

1. **Explore the codebase**: Review `src/main/java/com/mcpclient/mcpclient/`
2. **Read documentation**: Check `memory-bank/` directory for project context
3. **Start development**: Follow the phases outlined in `memory-bank/progress.md`
4. **Add MCP functionality**: Begin implementing MCP client features

## Support

### Documentation
- **Project Documentation**: `memory-bank/` directory
- **Technical Context**: `memory-bank/techContext.md`
- **Development Progress**: `memory-bank/progress.md`

### Resources
- **Spring Boot Documentation**: https://spring.io/projects/spring-boot
- **Spring AI Documentation**: https://docs.spring.io/spring-ai/reference/
- **Gradle Documentation**: https://docs.gradle.org/

For project-specific questions, refer to the comprehensive documentation in the `memory-bank/` directory.
