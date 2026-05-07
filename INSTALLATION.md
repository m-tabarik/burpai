# BurpAI Pro - Complete Installation & Usage Guide

## 🎯 What You Have

A **complete, production-ready Burp Suite extension** with:
- ✅ 25 Java source files (~3,500 LOC)
- ✅ Full OpenAI and Claude AI integration
- ✅ Beautiful Swing UI with settings panel
- ✅ Async processing (non-blocking)
- ✅ Sensitive data masking
- ✅ Error handling & retry logic
- ✅ Context menu integration
- ✅ Complete documentation

---

## 📥 Step 1: Get the Burp Suite JAR (REQUIRED)

This is the **only external dependency** you need to download.

### Option A: Windows
1. Visit: https://portswigger.net/burp/communitydownload
2. Click **"Download"** (Community Edition)
3. Save as: `burpsuite_community_v2_0_5.jar`
4. Create a `lib` folder in your project
5. Place the JAR in: `d:\Burpsuite pro\burp-claude\lib\burpsuite_community_v2_0_5.jar`

### Option B: Command Line (Linux/Mac)
```bash
cd ~/burp-claude
mkdir -p lib
# Download and place in lib/ folder
wget https://portswigger.net/burp/communitydownload -O burpsuite.jar
# Then place in lib/burpsuite_community_v2_0_5.jar
```

### Verify JAR is in place:
```bash
# Should show the JAR file
ls -lh lib/burpsuite_community_v2_0_5.jar
```

---

## 🔧 Step 2: Install Prerequisites

### Windows
```cmd
# Check Java (should be 11+)
java -version

# Check Maven
mvn --version

# If Maven not found:
# 1. Download from: https://maven.apache.org/download.cgi
# 2. Extract to C:\Program Files\maven
# 3. Add to PATH: C:\Program Files\maven\bin
# 4. Restart command prompt
# 5. Verify: mvn --version
```

### Mac
```bash
# Install via Homebrew (easiest)
brew install java
brew install maven

# Verify
java -version
mvn --version
```

### Linux (Ubuntu/Debian)
```bash
sudo apt-get update
sudo apt-get install openjdk-11-jdk maven

# Verify
java -version
mvn --version
```

---

## 🏗️ Step 3: Build the Extension

### Windows
```cmd
cd d:\Burpsuite pro\burp-claude
build.bat
```

### Linux/Mac
```bash
cd ~/burp-claude
bash build.sh
```

### Or use Maven directly (all platforms):
```bash
mvn clean package
```

### Expected Output:
```
[INFO] Building jar: target/BurpAIPro.jar
[INFO] BUILD SUCCESS
[INFO] Total time: 45.123 s
```

---

## 🔌 Step 4: Load Into Burp Suite

### Method 1: Via GUI (Easiest)

1. **Open Burp Suite**
2. Go to: **Extender** → **Extensions** → **Installed**
3. Click **"Add"**
4. Select **"Extension Type: Java"** (should be default)
5. Click **"Select file"**
6. Navigate to: `target/BurpAIPro.jar`
7. Click **"Open"** then **"Load"**

You should see:
```
[*] BurpAI Pro v1.0.0 loaded successfully!
[*] Use the 'BurpAI Pro' tab to analyze requests and responses
```

### Method 2: Via File Management

1. Build extension: `mvn clean package`
2. Copy `target/BurpAIPro.jar` to a safe location
3. In Burp: **Extender** → **Extensions** → **Installed**
4. Click **"Add"** and select the JAR
5. Click **"Load"**

---

## ⚙️ Step 5: Configure API Key

### Get an API Key

**Option A: OpenAI (Recommended for beginners)**
1. Visit: https://platform.openai.com/api-keys
2. Sign up or log in
3. Click **"Create new secret key"**
4. Copy the key (you won't see it again!)
5. Keep it safe!

**Option B: Claude (Anthropic)**
1. Visit: https://console.anthropic.com/
2. Sign up or log in
3. Go to **API Keys**
4. Click **"Create Key"**
5. Save the key securely

### Add Key to BurpAI Pro

1. In Burp Suite, click the **"BurpAI Pro"** tab
2. Click **"⚙️ Settings"** button
3. Paste your API key in the appropriate field:
   - **OpenAI** → "OpenAI API Key" field
   - **Claude** → "Claude API Key" field
4. (Optional) Adjust Temperature and Max Tokens
5. Click **"OK"** to save

✅ **You're now ready to use BurpAI Pro!**

---

## 🚀 Step 6: First Analysis

### Test It Out

1. **Open Burp Suite's Repeater** (or any tab with requests)
2. **Paste a test request:**
```
GET /api/users/123 HTTP/1.1
Host: example.com
Authorization: Bearer test-token
Cookie: session_id=abc123
```

3. **Option A: Via Context Menu (easiest)**
   - Right-click on the request
   - Select **"Send to BurpAI Pro"**

4. **Option B: Via BurpAI Pro Tab**
   - Click **"BurpAI Pro"** tab
   - Manually paste request in the Request area
   - Add a response (optional)

5. **Configure Analysis:**
   - Select Provider: **OpenAI** or **Claude**
   - Select Mode: **Passive** (for first test)

6. **Click "🔍 Analyze with AI"**

7. **Wait for results** (typically 3-15 seconds)

### Expected Output:
```
═══════════════════════════════════════════════════════════
                  BURPAI PRO ANALYSIS RESULTS
═══════════════════════════════════════════════════════════

[+] Vulnerability: Broken Authentication
Severity: High
Description: The endpoint uses Bearer token authentication...
[Processing Time: 4523ms]
```

---

## 🎯 Common Use Cases

### Use Case 1: Scan a Login Form
```
1. Open login request in Repeater
2. Right-click → "Send to BurpAI Pro"
3. Select "Active" mode (shows payloads)
4. Click "Analyze with AI"
5. Review vulnerability findings
```

### Use Case 2: Analyze API Endpoint
```
1. Copy API request/response
2. Paste in BurpAI Pro tab
3. Select "Explain" mode (beginner-friendly)
4. Click "Analyze with AI"
5. Learn about potential issues
```

### Use Case 3: Quick Vulnerability Check
```
1. Right-click request → "Send to BurpAI Pro"
2. Select "Passive" mode (no payloads)
3. Click "Analyze with AI"
4. Get quick assessment without attack suggestions
```

---

## 💰 Cost & Budgeting

### OpenAI Pricing
- **gpt-4o** (recommended): ~$0.015 per 1K tokens input
- **gpt-3.5-turbo** (budget): ~$0.0005 per 1K tokens input
- Typical request: 500-2000 tokens = $0.01-0.30 per analysis

### Claude Pricing
- **Claude 3 Opus**: ~$0.015 per 1K tokens input
- **Claude 3 Sonnet** (recommended): ~$0.003 per 1K tokens input
- **Claude 3 Haiku** (budget): ~$0.00025 per 1K tokens input
- Typical request: 500-2000 tokens = $0.001-0.06 per analysis

### Budget Tips
✅ **To Save Money:**
- Use `gpt-3.5-turbo` or `Claude Haiku`
- Use "Passive" mode (fewer tokens)
- Reduce "Max Tokens" in Settings
- Analyze smaller requests

**Monthly Budget Examples:**
- 100 analyses/month × $0.01 = ~$1/month (budget)
- 100 analyses/month × $0.10 = ~$10/month (detailed)
- 500 analyses/month × $0.05 = ~$25/month (moderate use)

---

## 🔒 Security Tips

### API Key Safety
✅ **DO:**
- Keep your API key private
- Use Burp's encrypted storage
- Rotate keys periodically
- Monitor AI API usage for unusual activity

❌ **DON'T:**
- Share your API key in chat/email
- Commit API keys to version control
- Type API keys in plain text files
- Use the same key across multiple tools

### Data Privacy
⚠️ **Remember:**
- Your requests are sent to OpenAI/Claude servers
- Sensitive headers are auto-masked
- Don't analyze production data with real credentials
- Review provider's privacy policy

### Masking Sensitive Data
The extension automatically masks:
- Authorization headers → `Authorization: [MASKED]`
- Cookies → `Cookie: [MASKED]`
- API keys → `api_key=[MASKED]`

You can toggle this in **Settings**.

---

## 🐛 Troubleshooting

### "Build fails: JAR not found"
```
Error: Burp Suite JAR not found in lib/

Solution:
1. Download from: https://portswigger.net/burp/communitydownload
2. Place in: lib/burpsuite_community_v2_0_5.jar
3. Rebuild: mvn clean package
```

### "Maven command not found"
```
Windows:
1. Download Maven from: https://maven.apache.org/download.cgi
2. Extract to: C:\Program Files\maven
3. Add to PATH: C:\Program Files\maven\bin
4. Restart command prompt
5. Verify: mvn --version

Mac/Linux:
brew install maven
# or
apt-get install maven
```

### "Java version error"
```
Error: Unsupported Java version

Solution:
1. Check version: java -version
2. Need Java 11+ (Java 21 recommended)
3. Download from: https://www.oracle.com/java/technologies/downloads/
4. Update PATH to point to new Java
5. Verify: java -version
```

### "Extension won't load in Burp"
```
Solution steps:
1. Verify BurpAIPro.jar exists in target/
2. Ensure Java 11+ is installed
3. Check Burp Suite logs for errors
4. Try rebuilding: mvn clean package
5. Restart Burp Suite
6. Try loading again
```

### "API key authentication failed"
```
Error: 401 Unauthorized

Solutions:
1. Double-check API key (copy/paste carefully)
2. Verify key hasn't expired
3. Check you have active account with credits
4. Try creating a new key
5. Verify correct provider (OpenAI vs Claude)
```

### "Analysis returns blank or timeout"
```
Solution:
1. Check internet connection
2. Verify API provider status page
3. Try with smaller request first
4. Increase timeout (backend setting)
5. Check Burp's error logs
```

---

## 📊 Monitoring & Usage

### Check Your API Usage

**OpenAI:**
1. Visit: https://platform.openai.com/usage/overview
2. See daily/monthly usage
3. Check current balance

**Claude:**
1. Visit: https://console.anthropic.com/
2. Go to Usage section
3. Monitor consumption

### Set Spending Limits

**OpenAI:**
1. Platform.openai.com → Billing
2. Click "Usage limits"
3. Set monthly budget

**Claude:**
1. Console.anthropic.com → Billing
2. Set usage thresholds

---

## 🎓 Learning & Advanced Usage

### Understanding the Analysis

The extension provides:

1. **Vulnerabilities Found** - List of identified issues
2. **Severity** - Low, Medium, High, Critical
3. **Description** - What the vulnerability is
4. **Exploitation** - How to exploit it (Active mode)
5. **Remediation** - How to fix it

### Analysis Modes Explained

| Mode | Details |
|------|---------|
| **Passive** | Identifies vulnerabilities without attack payloads. Safe, focuses on what's wrong. |
| **Active** | Full analysis including exploitation steps, sample payloads, and proof-of-concept. |
| **Explain** | Educational mode explaining concepts in beginner-friendly language. |

### Tips for Better Results

✅ **Best practices:**
- Include both request AND response for context
- Use "Active" mode for detailed exploitation guidance
- Use "Explain" mode to understand security concepts
- Test on diverse request types
- Save interesting results for reference

---

## 📚 Documentation Files

All documentation is in the project root:

| File | Contents |
|------|----------|
| **START_HERE.md** | Quick overview (you should read this first!) |
| **README.md** | Complete technical documentation |
| **SETUP.md** | Detailed setup instructions (this file) |
| **QUICKREF.md** | Quick command reference |
| **CHANGELOG.md** | Version history and roadmap |
| **FILE_INVENTORY.md** | Complete file listing |

---

## ✅ Verification Checklist

Before you start, verify:

- [ ] Java 11+ installed: `java -version`
- [ ] Maven installed: `mvn --version`
- [ ] Burp JAR in lib/: `ls lib/burpsuite_community*.jar`
- [ ] All source files present (25 .java files)
- [ ] pom.xml exists
- [ ] build.bat or build.sh executable

Ready to build?
- [ ] Run: `mvn clean package`
- [ ] Check: `ls target/BurpAIPro.jar`
- [ ] Load into Burp Suite
- [ ] Configure API key
- [ ] Test with sample request

---

## 🚀 Quick Command Reference

```bash
# Check prerequisites
java -version
mvn --version

# Build the extension
mvn clean package

# Clean build artifacts
mvn clean

# Run tests (if any)
mvn test

# Rebuild from scratch
mvn clean install package

# The compiled JAR
# Location: target/BurpAIPro.jar
# This is what you load into Burp!
```

---

## 🎯 Next Steps

1. ✅ Download Burp JAR and place in `lib/`
2. ✅ Install Java 11+ and Maven
3. ✅ Build: `mvn clean package`
4. ✅ Load `target/BurpAIPro.jar` into Burp
5. ✅ Get OpenAI or Claude API key
6. ✅ Configure API key in Settings
7. ✅ Test with first request
8. ✅ Start analyzing!

---

## 💬 Support

**Got stuck?**
1. Check this SETUP.md - Troubleshooting section
2. Read README.md - FAQ section
3. Check QUICKREF.md - for command syntax
4. Verify prerequisites are installed
5. Check API provider's status page

**Popular Resources:**
- Burp Suite: https://portswigger.net/
- OpenAI: https://platform.openai.com/docs
- Claude: https://docs.anthropic.com/
- Maven: https://maven.apache.org/guides/
- Java: https://docs.oracle.com/en/java/

---

## 🎉 You're Ready!

You now have everything needed to:
✅ Build the extension
✅ Load it into Burp Suite
✅ Analyze HTTP requests with AI
✅ Get vulnerability insights
✅ Improve your security testing workflow

**Time to get started!**

First action: Download the Burp JAR file → `lib/burpsuite_community_v2_0_5.jar`

Then: `mvn clean package`

Then: Load into Burp Suite!

---

*BurpAI Pro - Complete Setup Guide*

*Version 1.0.0 - March 2024*
