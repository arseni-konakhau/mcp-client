# MCP Client - Deployment Guide

## Prerequisites
- Git
- Java 21 JDK (OpenJDK or Oracle) - Current: 21.0.5 (Temurin)
- Gradle 8.14.3 (included via wrapper)


## Detailed Deployment

1. Clone repository:
```bash
git clone https://github.com/arseni-konakhau/mcp-client.git
cd mcp-client
```



2. Environment Variables:
NOTE: make sure you are using ur specific environment config based on target environment
```bash
cp ./_config_/env.selectel ./.env # example for intermediate target on selectel cloud
```



3. Run with automated setup:
```bash
chmod +x start.sh
bash ./start.sh
```



4. Verify:
```bash
# Check if process is running:
ps aux | grep mcpclient

# Check application health:
curl http://localhost:3335/actuator/health
# Should return {"status":"UP"}
```



5. Stop Process:
```bash
pkill -f mcpclient
```



## Key Details
- Runs on port 3335
- Uses Java 21.0.5 (Temurin)
- Uses Gradle 8.14.3 via wrapper
- Logs to: logs/mcpclient.log
