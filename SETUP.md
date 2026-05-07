# BurpAI Pro - Setup Guide

## Quick Start (5 Minutes)

### Prerequisites
- **Java JDK 11+** - [Download](https://www.oracle.com/java/technologies/downloads/)
- **Maven 3.6+** - [Download](https://maven.apache.org/download.cgi)
- **Burp Suite** (Community or Pro) - [Download](https://portswigger.net/burp)
- **API Key** - OpenAI or Claude (optional, but needed for analysis)

### Step 1: Download Burp Suite API JAR

1. Visit https://portswigger.net/burp/communitydownload
2. Download **burpsuite_community_v2_0_5.jar** (or your Pro version)
3. Create `lib` folder in the project directory
4. Place the JAR file in: `lib/burpsuite_community_v2_0_5.jar`

```bash
mkdir lib
# Download and place JAR here
```

### Step 2: Install Prerequisites

#### On Windows:
1. Install Java JDK 21 (or 11+)
2. Download Maven from apache.org
3. Add both to your PATH environment variable

To verify:
```cmd
java -version
mvn --version
```

#### On macOS:
```bash
brew install java
brew install maven
```

#### On Linux (Ubuntu/Debian):
```bash
sudo apt-get update
sudo apt-get install openjdk-11-jdk maven
```

### Step 3: Build the Extension

#### Windows:
```cmd
cd "d:\Burpsuite pro\burp-claude"
build.bat
```

#### Linux/Mac:
```bash
cd ~/burp-claude
chmod +x build.sh
./build.sh
```

#### Or use Maven directly:
```bash
mvn clean package
```

**Expected Output:**
```
[INFO] BUILD SUCCESS
[INFO] Total time:  45.123 s
[INFO] Finished at: 2024-03-27T14:30:45Z
```

### Step 4: Load into Burp Suite

1. **Open Burp Suite**
2. Navigate to: **Extender** → **Extensions** → **Installed**
3. Click **Add**
4. Select **BurpAIPro.jar** from the `target/` folder
5. Click **Load**
6. Accept the extension terms if prompted

**You should see:**
```
[*] BurpAI Pro v1.0.0 loaded successfully!
[*] Use the 'BurpAI Pro' tab to analyze requests and responses
[*] Right-click on requests in Proxy/Repeater to send to BurpAI
```

### Step 5: Configure API Keys

**⚠️ Important:** You need at least one API key to use the extension!

#### Option A: OpenAI (easiest to start)

1. Go to https://platform.openai.com/api-keys
2. Create a new API key
3. In Burp, click the **BurpAI Pro** tab
4. Click **⚙️ Settings**
5. Paste your OpenAI API key in the "API Key" field
6. Click **OK**

#### Option B: Claude (Anthropic)

1. Go to https://console.anthropic.com/
2. Create a new API key in your workspace
3. In Burp, click the **BurpAI Pro** tab
4. Click **⚙️ Settings**
5. Switch to the "Claude Settings" section
6. Paste your Claude API key
7. Click **OK**

## Troubleshooting

### "mvn command not found"
- **Windows**: Add Maven `bin` folder to PATH environment variable
- **Linux/Mac**: Install Maven via package manager or add to PATH

### "Java not found"
- Install Java JDK 11 or higher
- Add Java `bin` folder to PATH
- Verify: `java -version`

### "Build fails: burpsuite_community_v2_0_5.jar not found"
1. Download the JAR from https://portswigger.net/burp/communitydownload
2. Create `lib` folder: `mkdir lib`
3. Place JAR file in: `lib/burpsuite_community_v2_0_5.jar`
4. Rebuild: `mvn clean package`

### "Extension won't load into Burp"
1. Verify Java version: `java -version` (should be 11+)
2. Check Burp's error logs for details
3. Rebuild: `mvn clean package`
4. Try loading again

### "404 Not Found on API calls"
- Verify your API key is correct (copy/paste carefully)
- Check API provider's status page
- Ensure you have an active account with credits

### "401 Unauthorized"
- Double-check your API key in Settings
- Verify API key hasn't expired
- Try copying/pasting again (not typing)

### "Analysis returns blank"
- Check the status label for error messages
- Try with a smaller request/response first
- Verify the selected AI provider is configured

## Performance Tips

### Optimize Token Usage
- Smaller requests analyze faster
- Use "Passive" mode for quick scanning
- Use "Explain" mode for learning

### Reduce API Costs
- Use `gpt-3.5-turbo` instead of `gpt-4` (faster, cheaper)
- Mask sensitive data to avoid unnecessary exposure
- Use "Passive" mode (requires fewer tokens)

### Speed Up Analysis
- Reduce "Max Tokens" if responses are too long
- Use available API credits for faster processing
- Consider batch analysis for multiple requests

## Security Best Practices

### API Key Safety
✅ **Do:**
- Store API keys in Burp's settings (encrypted)
- Use read-only API keys if provider supports it
- Rotate keys periodically
- Monitor API usage for suspicious activity

❌ **Don't:**
- Share API keys in chat, email, or version control
- Hardcode API keys in source code
- Leave API keys visible in logs
- Use the same key for multiple tools

### Data Privacy
- Sensitive headers are automatically masked
- Review what data is sent to APIs
- Check API provider's privacy policy
- Don't analyze sensitive production data without authorization

### Network Security
- Use VPN if analyzing sensitive requests
- Ensure HTTPS when transmitting API keys
- Monitor for suspicious API calls
- Use network filters to restrict API traffic

## Common Use Cases

### Case 1: Quick Vulnerability Scan
```
1. Open request in Repeater
2. Right-click → "Send to BurpAI Pro"
3. Select "Active" mode
4. Click "Analyze with AI"
5. Review results
```

### Case 2: Educational Learning
```
1. Copy any request/response
2. Paste in BurpAI Pro tab
3. Select "Explain" mode
4. Click "Analyze with AI"
5. Read beginner-friendly explanation
```

### Case 3: Detailed Analysis
```
1. Select request from Proxy history
2. Right-click → "Send to BurpAI Pro"
3. Click Settings → Configure options
4. Select "Active" mode
5. Analyze and store results
```

## Next Steps

1. ✅ Install Java and Maven
2. ✅ Download Burp Suite API JAR
3. ✅ Build the extension
4. ✅ Load into Burp Suite
5. ✅ Configure API keys
6. 🚀 Start analyzing!

## Support Resources

- **Burp Suite**: https://portswigger.net/
- **OpenAI API**: https://platform.openai.com/docs/
- **Claude API**: https://docs.anthropic.com/
- **Maven**: https://maven.apache.org/guides/

## FAQ

**Q: Do I need both OpenAI and Claude API keys?**
A: No, you only need one. Configure whichever provider you prefer.

**Q: Is there a cost?**
A: Yes, API calls incur costs with respective providers. Check their pricing pages.

**Q: Can I use the free tier?**
A: OpenAI and Claude both offer trial credits, but check their current terms.

**Q: What Java version do I need?**
A: Java 11 or later. Java 21 is recommended.

**Q: Can I use Burp Suite Community edition?**
A: Yes, the extension works with both Community and Professional editions.

**Q: Will my requests be logged?**
A: Requests are sent to OpenAI/Claude APIs. Check their privacy policies.

**Q: Can I disable data masking?**
A: Yes, but not recommended. You can disable it in Settings.

**Q: What if the API is down?**
A: The extension will show an error. Try again later.

**Q: Can I run multiple analyses?**
A: One analysis at a time. Queue is not supported.

---

**Ready to get started? Follow the Quick Start section above!**
