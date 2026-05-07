#!/bin/bash

# BurpAI Pro - Build Script for Linux/Mac
# This script requires Maven and Java to be installed

echo ""
echo "============================================================"
echo "     BurpAI Pro - Extension Build Script (Linux/Mac)"
echo "============================================================"
echo ""

# Check if Java is installed
if ! command -v java &> /dev/null; then
    echo "[ERROR] Java is not installed or not in PATH"
    echo "Please install Java JDK 11+ from: https://www.oracle.com/java/technologies/downloads/"
    exit 1
fi

echo "[+] Java installation found"
java -version

# Check if Maven is installed
if ! command -v mvn &> /dev/null; then
    echo ""
    echo "[ERROR] Maven is not installed or not in PATH"
    echo "Please install Maven from: https://maven.apache.org/download.cgi"
    echo "And add it to your PATH environment variable"
    exit 1
fi

echo "[+] Maven installation found"
mvn --version

# Check if Burp JAR exists
if [ ! -f "lib/burpsuite_community_v2_0_5.jar" ]; then
    echo ""
    echo "[WARNING] Burp Suite JAR not found in lib/ directory"
    echo "Download from: https://portswigger.net/burp/communitydownload"
    echo "Place the JAR in: lib/burpsuite_community_v2_0_5.jar"
    echo ""
    exit 1
fi

echo "[+] Burp Suite JAR found"

echo ""
echo "[*] Building BurpAI Pro..."
echo ""

# Clean and build
mvn clean package -DskipTests

if [ $? -eq 0 ]; then
    echo ""
    echo "============================================================"
    echo "[SUCCESS] Build completed successfully!"
    echo "============================================================"
    echo ""
    echo "The compiled extension is located at:"
    echo "  target/BurpAIPro.jar"
    echo ""
    echo "Next steps:"
    echo "1. Open Burp Suite"
    echo "2. Go to Extender -> Extensions -> Installed"
    echo "3. Click 'Add'"
    echo "4. Select 'target/BurpAIPro.jar'"
    echo "5. Click 'Load'"
    echo ""
else
    echo ""
    echo "[ERROR] Build failed. Check errors above."
    echo ""
    exit 1
fi
