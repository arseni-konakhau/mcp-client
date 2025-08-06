# MCP Client - Step-by-Step Running Guide

## Prerequisites

### Required Software
- **Java 21 JDK** (OpenJDK or Oracle JDK) - ✅ You have: OpenJDK 21.0.5
- **Git** (for cloning the repository)
- **Terminal/Command Prompt** access

### Verify Your Environment
```bash
# Verify Java version (should be 21.x.x)
java -version
# Expected: OpenJDK 21.0.5 2024-10-15 LTS

# Check Java compiler version
javac -version

# Check Git installation
git --version
```



------------



## 1. Foreground Execution (Local Development)

### Step 1: Clone and Setup Project
```bash
# Clone the repository
git clone https://github.com/arseni-konakhau/mcp-client.git
cd mcp-client

# Make gradlew executable (Unix/Linux/macOS only)
chmod +x gradlew
```

### Step 2: Verify Project Structure
```bash
# Check project files
ls -la
# Should see: build.gradle, gradlew, src/, memory-bank/, etc.

# Verify Gradle wrapper
./gradlew --version
```

### Step 3: Build and Run in Foreground
```bash
# Option A: Run directly with Gradle (Recommended for development)
./gradlew bootRun

# Option B: Build JAR first, then run
./gradlew clean build
java -jar build/libs/mcpclient-0.0.1-SNAPSHOT.jar

# Option C: Run with specific profile
./gradlew bootRun --args='--spring.profiles.active=dev'
```

### Step 4: Verify Application is Running
```bash
# Check if application is responding
curl http://localhost:3335
# Expected: 404 error with JSON response (this is normal - app is running!)

# Check application health
curl http://localhost:3335/actuator/health
# Expected: JSON health status

# Check application info
curl http://localhost:3335/actuator/info
# Expected: Application information
```

### Step 5: Access Application
- **Application URL**: http://localhost:3335
- **Port**: 3335 (customized)
- **Application Name**: mcpclient

### Development Commands
```bash
# Run with auto-reload (DevTools)
./gradlew bootRun --continuous

# Run tests
./gradlew test

# Clean and rebuild
./gradlew clean build

# Check for dependency updates
./gradlew dependencyUpdates
```

---

## 2. Background Execution

### Option A: Using nohup (Unix/Linux/macOS)

#### Step 1: Build the Application
```bash
cd mcp-client
./gradlew clean build
```

#### Step 2: Run in Background with nohup
```bash
# Create logs directory
mkdir -p logs

# Run with nohup (redirects output to log file)
nohup java -jar build/libs/mcpclient-0.0.1-SNAPSHOT.jar > logs/mcpclient.log 2>&1 &

# Get the process ID
echo $! > mcpclient.pid
```

#### Step 3: Verify Background Process
```bash
# Check if process is running
ps aux | grep mcpclient

# Check the log file
tail -f logs/mcpclient.log

# Check application status
curl http://localhost:3335/actuator/health
```

#### Step 4: Stop Background Process
```bash
# Stop using PID file
kill $(cat mcpclient.pid)
rm mcpclient.pid

# Or find and kill manually
ps aux | grep mcpclient
kill -9 <PID>
```

### Option B: Using systemd (Linux)

#### Step 1: Create systemd Service File
```bash
sudo nano /etc/systemd/system/mcpclient.service
```

Add the following content:
```ini
[Unit]
Description=MCP Client Application
After=network.target

[Service]
Type=simple
User=your-username
WorkingDirectory=/path/to/mcp-client
ExecStart=/usr/bin/java -jar build/libs/mcpclient-0.0.1-SNAPSHOT.jar
Restart=on-failure
RestartSec=10

[Install]
WantedBy=multi-user.target
```

#### Step 2: Enable and Start Service
```bash
# Reload systemd
sudo systemctl daemon-reload

# Enable service (start on boot)
sudo systemctl enable mcpclient

# Start service
sudo systemctl start mcpclient

# Check status
sudo systemctl status mcpclient
```

#### Step 3: Manage Service
```bash
# Stop service
sudo systemctl stop mcpclient

# Restart service
sudo systemctl restart mcpclient

# View logs
sudo journalctl -u mcpclient -f
```

### Option C: Using screen (Unix/Linux/macOS)

#### Step 1: Install screen (if not available)
```bash
# On macOS
brew install screen

# On Ubuntu/Debian
sudo apt install screen
```

#### Step 2: Run in screen session
```bash
# Create new screen session
screen -S mcpclient

# Inside screen session, run the application
cd mcp-client
./gradlew bootRun

# Detach from screen session: Ctrl+A, then D
```

#### Step 3: Manage screen session
```bash
# List screen sessions
screen -ls

# Reattach to session
screen -r mcpclient

# Kill session
screen -S mcpclient -X quit
```

---

## 3. Docker Execution

### Option A: Using Dockerfile

#### Step 1: Create Dockerfile
```bash
# Create Dockerfile in project root
cat > Dockerfile << 'EOF'
FROM openjdk:21-jre-slim

# Create app directory
WORKDIR /app

# Copy the JAR file
COPY build/libs/mcpclient-0.0.1-SNAPSHOT.jar app.jar

# Expose port
EXPOSE 3335

# Run the application
ENTRYPOINT ["java", "-jar", "/app/app.jar"]
EOF
```

#### Step 2: Build and Run Docker Container
```bash
# Build the application first
./gradlew clean build

# Build Docker image
docker build -t mcpclient:latest .

# Run container
docker run -d \
  --name mcpclient \
  -p 3335:3335 \
  -e SPRING_PROFILES_ACTIVE=prod \
  mcpclient:latest

# Check container status
docker ps

# View logs
docker logs -f mcpclient
```

#### Step 3: Manage Docker Container
```bash
# Stop container
docker stop mcpclient

# Start container
docker start mcpclient

# Remove container
docker rm mcpclient

# Remove image
docker rmi mcpclient:latest
```

### Option B: Using Docker Compose

#### Step 1: Create docker-compose.yml
```bash
cat > docker-compose.yml << 'EOF'
version: '3.8'

services:
  mcpclient:
    build: .
    container_name: mcpclient
    ports:
      - "3335:3335"
    environment:
      - SPRING_PROFILES_ACTIVE=prod
    restart: unless-stopped
    volumes:
      - ./logs:/app/logs
EOF
```

#### Step 2: Build and Run with Docker Compose
```bash
# Build the application first
./gradlew clean build

# Build and run with docker-compose
docker-compose up -d

# Check status
docker-compose ps

# View logs
docker-compose logs -f mcpclient
```

#### Step 3: Manage with Docker Compose
```bash
# Stop services
docker-compose down

# Restart services
docker-compose restart

# Rebuild and restart
docker-compose up -d --build
```

---

## Troubleshooting

### Common Issues

#### Port 3335 Already in Use
```bash
# Find process using port 3335
lsof -i :3335

# Kill the process
kill -9 <PID>

# Or run on different port
./gradlew bootRun --args='--server.port=3336'
```

#### Java Version Issues
```bash
# Check Java version
java -version

# Set JAVA_HOME if needed
export JAVA_HOME=/path/to/java21
export PATH=$JAVA_HOME/bin:$PATH
```

#### Permission Issues
```bash
# Make gradlew executable
chmod +x gradlew

# Fix file permissions
chmod +x gradlew.bat
```

#### Build Failures
```bash
# Clean and rebuild
./gradlew clean build

# Check for dependency issues
./gradlew dependencies

# Update Gradle wrapper if needed
./gradlew wrapper --gradle-version=8.5
```

### Logs and Debugging

#### View Application Logs
```bash
# Foreground mode - logs appear in terminal
./gradlew bootRun

# Background mode - check log files
tail -f logs/mcpclient.log

# Docker mode - check container logs
docker logs -f mcpclient
```

#### Enable Debug Logging
```bash
# Run with debug logging
./gradlew bootRun --args='--logging.level.com.mcpclient=DEBUG'

# Or add to application.properties
echo "logging.level.com.mcpclient=DEBUG" >> src/main/resources/application.properties
```

---

## Verification Checklist

After running the application, verify:

- [ ] Application starts without errors
- [ ] Port 3335 is accessible
- [ ] Health endpoint responds: http://localhost:3335/actuator/health
- [ ] Info endpoint responds: http://localhost:3335/actuator/info
- [ ] Logs show no critical errors
- [ ] MCP client configuration is loaded correctly

---

## Next Steps

Once the application is running successfully:

1. **Explore the codebase**: Review `src/main/java/com/mcpclient/mcpclient/`
2. **Read documentation**: Check `memory-bank/` directory for project context
3. **Start development**: Follow the phases outlined in `memory-bank/progress.md`
4. **Add MCP functionality**: Begin implementing MCP client features

---

## Support Resources

- **Project Documentation**: `memory-bank/` directory
- **Technical Context**: `memory-bank/techContext.md`
- **Development Progress**: `memory-bank/progress.md`
- **Spring Boot Documentation**: https://spring.io/projects/spring-boot
- **Spring AI Documentation**: https://docs.spring.io/spring-ai/reference/ 