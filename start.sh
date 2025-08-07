#!/bin/bash

# Verify Java 21 is installed
echo "Verifying Java 21..."
JAVA_VERSION=$(java -version 2>&1 | awk -F '"' '/version/ {print $2}')
if [[ "$JAVA_VERSION" != "21"* ]]; then
  echo "ERROR: Java 21 is required. Found version: $JAVA_VERSION"
  exit 1
fi
echo "Java version OK: $JAVA_VERSION"

# Verify Gradle wrapper
echo "Verifying Gradle wrapper..."
if [ ! -f "./gradlew" ]; then
  echo "ERROR: gradlew wrapper not found"
  exit 1
fi

# Build project
echo "Building project..."
./gradlew clean build
if [ $? -ne 0 ]; then
  echo "ERROR: Build failed"
  exit 1
fi

# Run on port 3335
echo "Starting application on port 3335..."
mkdir -p logs
nohup java -jar build/libs/mcpclient-0.0.1-SNAPSHOT.jar > logs/mcpclient.log 2>&1 &
echo $! > mcpclient.pid

# Verify startup
echo "Validating startup..."
sleep 5
curl -s http://localhost:3335/actuator/health | grep -q '"status":"UP"'
if [ $? -eq 0 ]; then
  echo "Application is running successfully on port 3335"
  echo "Access: http://localhost:3335"
else
  echo "ERROR: Application failed to start"
  exit 1
fi
