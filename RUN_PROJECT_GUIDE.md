# MCP Client - Production Deployment Guide

## Prerequisites
- Java 21 JDK (OpenJDK or Oracle) - Current: 21.0.5 (Temurin)
- Git
- Gradle 8.14.3 (included via wrapper)

## Quick Start
```bash
git clone https://github.com/arseni-konakhau/mcp-client.git
cd mcp-client
chmod +x start.sh
./start.sh
```

## Detailed Deployment

1. Clone repository:
```bash
git clone https://github.com/arseni-konakhau/mcp-client.git
cd mcp-client
```

2. Run with automated setup:
```bash
./start.sh
```

3. Verify:
```bash
curl http://localhost:3335/actuator/health
# Should return {"status":"UP"}
```

4. Stop when needed:
```bash
kill $(cat mcpclient.pid)
rm mcpclient.pid
```

## Key Details
- Runs on port 3335
- Uses Java 21.0.5 (Temurin)
- Uses Gradle 8.14.3 via wrapper
- Logs to: logs/mcpclient.log
- Process ID in: mcpclient.pid
