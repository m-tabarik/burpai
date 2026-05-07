# BurpAI Pro - Quick Maven Installation & Compilation Guide

## ⚠️ Maven Not Installed

Your system doesn't have Maven installed yet. Follow these quick steps to install it and compile the extension.

---

## 🔧 Installing Maven on Windows (5 Minutes)

### Option 1: Using Chocolatey (Fastest - if you have it)
```cmd
choco install maven
# Verify:
mvn --version
```

### Option 2: Manual Installation (Most Common)

#### Step 1: Download Maven
1. Visit: https://maven.apache.org/download.cgi
2. Download: **apache-maven-3.9.6-bin.zip** (or latest version)
3. Extract to: `C:\Program Files\maven`

#### Step 2: Add to PATH
1. Open **Environment Variables**:
   - Press `Win + X` → "System"
   - Click "Advanced system settings"
   - Click "Environment Variables"

2. Under "System variables", click "New":
   - Variable name: `MAVEN_HOME`
   - Variable value: `C:\Program Files\maven`
   - Click "OK"

3. Find variable `Path` and click "Edit":
   - Click "New"
   - Add: `C:\Program Files\maven\bin`
   - Click "OK" for all dialogs

4. **Restart Command Prompt** for changes to take effect

#### Step 3: Verify Installation
```cmd
mvn --version
# Should show: Apache Maven 3.9.6 (or similar)
```

---

## ✅ Compile BurpAI Pro

Once Maven is installed, open Command Prompt in your project folder and run:

```cmd
cd d:\Burpsuite pro\burp-claude
mvn clean package
```

### What Happens:
```
[INFO] Cleaning...
[INFO] Downloading Maven dependencies...
[INFO] Building...
[INFO] Compiling 25 Java files...
[INFO] Creating JAR: target/BurpAIPro.jar
[INFO] BUILD SUCCESS
```

### Expected Output:
```
[INFO] BUILD SUCCESS
[INFO] Total time: 45 s
[INFO] Finished at: 2024-03-27T14:30:45Z
```

### The Result:
```
target/BurpAIPro.jar  ← This is your compiled extension!
```

---

## ⚡ Quick Start Command

```cmd
# Copy this entire command and paste into Command Prompt:
cd d:\Burpsuite pro\burp-claude && mvn clean package
```

---

## 🐛 Troubleshooting

### If Maven still not found after restart:
1. Close and reopen Command Prompt
2. Verify: `mvn --version`
3. If still failing, restart your computer

### If build fails on first try:
```cmd
# Clear Maven cache and rebuild
mvn clean package -U
```

### If "Burp JAR not found":
1. Download: https://portswigger.net/burp/communitydownload
2. Create: `lib` folder in project root
3. Place JAR: `lib/burpsuite_community_v2_0_5.jar`
4. Rebuild: `mvn clean package`

---

## ✨ After Successful Compilation

You'll have:
```
target/BurpAIPro.jar  (2-3 MB)
```

Load this into Burp Suite:
1. **Extender** → **Extensions** → **Installed**
2. Click **"Add"**
3. Select **target/BurpAIPro.jar**
4. Click **"Load"**

Done! 🎉

---

**Next: Install Maven, then run `mvn clean package`**
