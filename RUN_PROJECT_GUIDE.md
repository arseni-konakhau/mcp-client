# MCP Client - Production Deployment Guide

## Prerequisites
- Java 21 JDK (OpenJDK or Oracle)
- Git

## Deployment Steps

1. Clone and build:
```bash
git clone https://github.com/arseni-konakhau/mcp-client.git
cd mcp-client
chmod +x gradlew
./gradlew clean build
```

2. Run in background:
```bash
mkdir -p logs
nohup java -jar build/libs/mcpclient-0.0.1-SNAPSHOT.jar > logs/mcpclient.log 2>&1 &
echo $! > mcpclient.pid
```

3. Verify:
```bash
ps aux | grep mcpclient
tail -f logs/mcpclient.log
curl http://localhost:3335/actuator/health
```

4. Stop when needed:
```bash
kill $(cat mcpclient.pid)
rm mcpclient.pid
```

## Key Details
- Runs on port 3335
- Logs to: logs/mcpclient.log
- Process ID in: mcpclient.pid
