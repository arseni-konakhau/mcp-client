# MCP Client - Deployment Guide

## Prerequisites
- Java 21 JDK (OpenJDK or Oracle) - Current: 21.0.5 (Temurin)
- Git
- Gradle 8.14.3 (included via wrapper)


## Detailed Deployment

1. Clone repository:
```bash
git clone https://github.com/arseni-konakhau/mcp-client.git
cd mcp-client
```

2. Run with automated setup:
```bash
chmod +x start.sh
bash ./start.sh
```

3. Verify:
```bash
# Check if process is running:
ps aux | grep mcpclient

# Check application health:
curl http://localhost:3335/actuator/health
# Should return {"status":"UP"}
```

4. Stop Process:
```bash
pkill -f mcpclient
```


## Key Details
- Runs on port 3335
- Uses Java 21.0.5 (Temurin)
- Uses Gradle 8.14.3 via wrapper
- Logs to: logs/mcpclient.log
