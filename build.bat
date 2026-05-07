@echo off
REM BurpAI Pro - Build Script for Windows
REM This script requires Maven and Java to be installed

echo.
echo ============================================================
echo     BurpAI Pro - Extension Build Script (Windows)
echo ============================================================
echo.

REM Check if Java is installed
java -version >nul 2>&1
if %errorlevel% neq 0 (
    echo [ERROR] Java is not installed or not in PATH
    echo Please install Java JDK 11+ from: https://www.oracle.com/java/technologies/downloads/
    pause
    exit /b 1
)

echo [+] Java installation found
java -version

REM Check if Maven is installed
mvn --version >nul 2>&1
if %errorlevel% neq 0 (
    echo.
    echo [ERROR] Maven is not installed or not in PATH
    echo Please install Maven from: https://maven.apache.org/download.cgi
    echo And add it to your PATH environment variable
    pause
    exit /b 1
)

echo [+] Maven installation found
mvn --version

REM Check if Burp JAR exists
if not exist "lib\burpsuite_community_v2_0_5.jar" (
    echo.
    echo [WARNING] Burp Suite JAR not found in lib\ directory
    echo Download from: https://portswigger.net/burp/communitydownload
    echo Place the JAR in: lib\burpsuite_community_v2_0_5.jar
    echo.
    pause
    exit /b 1
)

echo [+] Burp Suite JAR found

echo.
echo [*] Building BurpAI Pro...
echo.

REM Clean and build
mvn clean package -DskipTests

if %errorlevel% equ 0 (
    echo.
    echo ============================================================
    echo [SUCCESS] Build completed successfully!
    echo ============================================================
    echo.
    echo The compiled extension is located at:
    echo   target\BurpAIPro.jar
    echo.
    echo Next steps:
    echo 1. Open Burp Suite
    echo 2. Go to Extender -^> Extensions -^> Installed
    echo 3. Click "Add"
    echo 4. Select "target\BurpAIPro.jar"
    echo 5. Click "Load"
    echo.
    pause
) else (
    echo.
    echo [ERROR] Build failed. Check errors above.
    echo.
    pause
    exit /b 1
)
