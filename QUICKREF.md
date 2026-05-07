# BurpAI Pro - Quick Reference

## Installation (TL;DR)

```bash
# 1. Download Burp JAR to lib/ folder
# 2. Ensure Java 11+ and Maven are installed
# 3. Build:
mvn clean package

# 4. Load target/BurpAIPro.jar into Burp Suite
# 5. Add API key in Settings
# 6. Start analyzing!
```

## Usage Quick Start

### In Burp Suite:

1. Open any HTTP request/response
2. **Option A**: Right-click → "Send to BurpAI Pro"
3. **Option B**: Copy/paste into BurpAI Pro tab
4. Click **⚙️ Settings** → Configure API key
5. Choose analysis mode: **Passive** | **Active** | **Explain**
6. Click **🔍 Analyze with AI**
7. View results

## Analysis Modes

| Mode | Use Case |
|------|----------|
| **Passive** | Find vulnerabilities without attack suggestions |
| **Active** | Full analysis with exploitation steps & payloads |
| **Explain** | Educational - learn about vulnerabilities |

## Configuration

### Settings Menu

- **API Keys**: OpenAI or Claude (required)
- **Model**: Choose specific AI model
- **Temperature**: 0-2 (lower = focused, higher = creative)
- **Max Tokens**: Response length limit (100-4000)
- **Mask Data**: Hide Auth/Cookie headers before API call

## API Providers

### OpenAI
- URL: https://platform.openai.com/api-keys
- Cost: $0.015 - $0.30 per 1K tokens
- Models: gpt-4, gpt-4o, gpt-3.5-turbo
- Best for: Detailed analysis

### Claude
- URL: https://console.anthropic.com/
- Cost: $0.003 - $0.30 per 1K tokens
- Models: opus, sonnet, haiku
- Best for: Well-reasoned analysis

## Troubleshooting

| Issue | Solution |
|-------|----------|
| "API key not configured" | Click Settings, add API key |
| "401 Unauthorized" | Verify API key is correct |
| "mvn not found" | Install Maven or add to PATH |
| "Build fails" | Place burpsuite JAR in lib/ folder |
| "Extension won't load" | Check Java version is 11+ |

## Tips & Tricks

### Save Money on API Costs
- Use `gpt-3.5-turbo` (2-10x cheaper)
- Use "Passive" mode (fewer tokens)
- Reduce "Max Tokens" setting

### Faster Analysis
- Reduce request size (truncate large responses)
- Use predefined models (avoid customization)
- Batch similar requests

### Better Results
- Include both request AND response (more context)
- Use "Active" mode for exploitation details
- Use "Explain" mode to learn concepts

## Keyboard Shortcuts
- **Clear** button to reset all fields
- **Tab** to switch between Request/Response tabs

## Common Vulnerabilities Detected

BurpAI Pro can identify:
- SQL Injection
- Cross-Site Scripting (XSS)
- Cross-Site Request Forgery (CSRF)
- Insecure Deserialization
- Broken Access Control (IDOR)
- Information Disclosure
- Broken Authentication
- Using Components with Known Vulnerabilities
- Insufficient Logging & Monitoring
- Server-Side Template Injection (SSTI)

## Privacy

✅ **What's NOT sent to APIs:**
- Authorization headers (masked)
- Cookies/Session tokens (masked)
- API keys/sensitive headers (masked)

❌ **What IS sent:**
- HTTP request/response content (masked)
- Custom prompt (no PII by default)

⚠️ **Best Practice:** Don't analyze real production data with credentials

## Performance Stats

- Avg analysis: 3-10 seconds
- API timeout: 30 seconds
- Max request size: ~10 KB
- Token limit: Based on selected model

## File Locations

```
target/BurpAIPro.jar          ← Load this into Burp
lib/burpsuite_community_v2_0_5.jar  ← Download & place here
src/main/java/...            ← Source code
```

## Next Steps

1. Setup.md - Detailed setup instructions
2. README.md - Full documentation
3. CHANGELOG - Version history

---

**Need help?** See SETUP.md for detailed guides!
