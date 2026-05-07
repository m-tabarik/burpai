# BurpAI Pro - AI-Powered Burp Suite Extension

A powerful Burp Suite extension that integrates OpenAI GPT and Anthropic Claude APIs to provide AI-assisted security analysis of HTTP requests and responses.

## Features

✅ **Multi-AI Provider Support**
- OpenAI (GPT-4, GPT-4o, GPT-3.5-turbo)
- Claude 3 (Opus, Sonnet, Haiku)

✅ **Multiple Analysis Modes**
- **Passive**: Identify vulnerabilities without attack suggestions
- **Active**: Detailed exploitation steps with sample payloads
- **Explain**: Educational mode explaining vulnerabilities for beginners

✅ **Security-Focused Features**
- Automatic sensitive data masking (Authorization, Cookies, Tokens)
- Configurable API rate limiting and timeout handling
- Secure API key storage in Burp preferences
- Async processing to keep Burp UI responsive

✅ **Burp Integration**
- New "BurpAI Pro" tab in Burp Suite
- Right-click context menu integration (Proxy, Repeater, Intruder)
- Structured analysis results with clear formatting

## Requirements

- **Java**: JDK 11 or higher
- **Maven**: For building the extension
- **Burp Suite**: Community or Professional version
- **API Keys**: OpenAI or Claude API key (get from respective providers)

## Installation & Setup

### Step 1: Download and Extract Burp Suite API JAR

Download `burpsuite_community_v2_0_5.jar` from the Burp Suite website and place it in the `lib/` directory:

```bash
mkdir -p lib
# Download from: https://portswigger.net/burp/communitydownload
# Place burpsuite_community_v2_0_5.jar in lib/ folder
```

### Step 2: Build the Extension

Navigate to the project directory and build with Maven:

```bash
mvn clean package
```

This creates `target/BurpAIPro.jar` - the compilable extension.

### Step 3: Load into Burp Suite

1. Open **Burp Suite**
2. Go to **Extender** → **Extensions** → **Installed**
3. Click **Add**
4. Select **BurpAIPro.jar** from `target/` folder
5. Click **Load**

The extension should now appear as a new tab called "BurpAI Pro".

## Usage

### Basic Usage

1. **Navigate to Request/Response**
   - Open any request in Repeater or select from Proxy history
   - Right-click → **Send to BurpAI Pro**
   - Or manually paste request/response in the BurpAI Pro tab

2. **Configure AI Provider**
   - Click **Settings** button
   - Enter your **OpenAI** or **Claude** API key
   - Select model and configure preferences
   - Click **OK**

3. **Run Analysis**
   - Choose analysis mode (Passive/Active/Explain)
   - Click **Analyze with AI**
   - Wait for results to appear in the Results panel

### Example Analysis Output

```
═══════════════════════════════════════════════════════════
                  BURPAI PRO ANALYSIS RESULTS
═══════════════════════════════════════════════════════════

[+] Vulnerability: SQL Injection
Severity: High
Description: The 'id' parameter in the GET request is vulnerable to SQL injection...

[+] Exploitation Steps:
1. Modify the 'id' parameter with: id=1' OR '1'='1
2. Observe if the response changes
3. Use time-based blind SQL injection: id=1' AND SLEEP(5)--

[+] Recommended Payloads:
- Union-based: id=1 UNION SELECT database(),user(),version()--
- Time-based: id=1' AND SLEEP(5)--
```

## Configuration

### Settings Panel

The Settings panel allows you to configure:

- **API Keys**: Secure storage for OpenAI and Claude API keys
- **Model Selection**: Choose specific models for each provider
- **Temperature**: Controls randomness in responses (0-2)
- **Max Tokens**: Maximum response length (100-4000)
- **Data Masking**: Automatically mask sensitive headers before sending to APIs
- **Auto-Analyze**: (Future feature) Automatically analyze interesting proxy traffic

## API Configuration

### OpenAI

1. Get your API key from: https://platform.openai.com/api-keys
2. Create a new API key (keep it private!)
3. Paste it in BurpAI Pro Settings → OpenAI section
4. Recommended model: `gpt-4o` (fast and capable)

### Claude (Anthropic)

1. Get your API key from: https://console.anthropic.com/
2. Create a new API key in your workspace
3. Paste it in BurpAI Pro Settings → Claude section
4. Recommended model: `claude-3-opus-20240229` (most capable)

## Architecture

### Core Components

```
BurpAIPro (Main Extension Entry Point)
├── UI Layer
│   ├── BurpAIPanel (Main UI tab)
│   ├── SettingsPanel (Configuration)
│   └── ContextMenuHandler (Right-click integration)
├── AI Layer
│   ├── OpenAIProvider (GPT integration)
│   ├── ClaudeProvider (Claude integration)
│   └── PromptBuilder (Prompt engineering)
├── API Layer
│   └── HttpClient (API communication)
├── Data Processing
│   ├── DataMasker (Sensitive data protection)
│   └── ResponseParser (Result formatting)
└── Workers
    └── AnalysisWorker (Async processing)
```

### Data Flow

```
User Input → Request/Response Captured
    ↓
Data Masking (sensitive headers removed)
    ↓
AI Provider Selected (OpenAI or Claude)
    ↓
Prompt Built (with analysis mode context)
    ↓
API Call (async, 30-second timeout)
    ↓
Response Parsed
    ↓
Results Formatted & Displayed
```

## Security Considerations

### Sensitive Data Masking

By default, BurpAI Pro automatically masks:
- Authorization headers
- Cookies and authentication tokens
- API keys in URLs
- Session tokens

This can be disabled in Settings if you trust the API provider.

### API Key Storage

- API keys are stored in Burp's encrypted preferences
- Keys are never logged or displayed in plain text
- Use read-only API keys if your provider supports it

### API Provider Trust

- OpenAI and Claude are enterprise-grade providers with strong privacy policies
- Review their terms of service regarding data handling
- Masked sensitive data further reduces exposure

## Troubleshooting

### "API key not configured"
- Go to Settings and enter your API key for the selected provider
- Ensure the key is copied exactly (no extra spaces)

### "API Error: 401 Unauthorized"
- Check that your API key is correct
- Verify the API key hasn't expired
- Ensure you have an active account with the provider

### "Connection timeout"
- Check your internet connection
- Try again (includes automatic retry with backoff)
- Verify the API endpoint is reachable

### Extension won't load
- Ensure Java 11+ is installed
- Verify `burpsuite_community_v2_0_5.jar` is in the `lib/` folder
- Rebuild: `mvn clean package`

### "Analysis is blank or incomplete"
- Check the status label for error messages
- Try with a smaller request/response
- Verify the AI model is working (test from API provider's webpage)

## Development

### Project Structure

```
burp-claude/
├── pom.xml                 Maven configuration
├── src/
│   ├── main/java/com/burpai/
│   │   ├── BurpAIPro.java         Extension entry point
│   │   ├── ui/                    UI components
│   │   ├── ai/                    AI providers
│   │   ├── api/                   HTTP communication
│   │   ├── models/                Data models
│   │   ├── utils/                 Utilities
│   │   └── workers/               Async workers
│   └── resources/
│       └── log4j.properties       Logging config
└── lib/
    └── burpsuite_community_v2_0_5.jar (required)
```

### Building from Source

```bash
# Clone/download the project
cd burp-claude

# Build
mvn clean package

# Output
# target/BurpAIPro.jar  ← Ready to load into Burp
```

### Key Classes

- **BurpAIPro.java**: Main extension, implements IBurpExtender
- **BurpAIPanel.java**: Main UI component, user interaction
- **OpenAIProvider.java**: GPT integration
- **ClaudeProvider.java**: Claude integration
- **AnalysisWorker.java**: Background processing
- **DataMasker.java**: Sensitive data protection
- **PromptBuilder.java**: Prompt engineering

## Advanced Features

### Prompt Engineering

The extension uses professional penetration testing prompts optimized for:
- Clear vulnerability identification
- Exploitation step-by-step guidance
- Payload suggestions
- Remediation recommendations

### Token Management

- Automatic truncation of large requests to fit API limits
- Efficient prompt construction to maximize token usage
- Configurable token limits per API provider

### Error Handling

- 30-second timeout per API call
- 2 automatic retries with exponential backoff
- Graceful error messages for user feedback
- Connection pooling and resource cleanup

## Limitations

- **Rate Limiting**: Subject to API provider rate limits
- **API Costs**: OpenAI and Claude API calls incur costs
- **Size Limits**: Very large requests (>10KB) are truncated
- **Async Only**: UI doesn't block, but only one analysis at a time

## Roadmap (Future Features)

- [ ] Auto-analyze interesting proxy traffic
- [ ] Caching to reduce API calls
- [ ] Batch analysis of multiple requests
- [ ] Custom prompt templates
- [ ] Export analysis results
- [ ] Integration with Burp's issue reporter
- [ ] Payload generation tool
- [ ] Attack workflow automation

## Contributing

Found a bug? Have a feature request? Feel free to improve the extension!

### How to Contribute

1. Fork or clone the repository
2. Create a feature branch
3. Make your improvements
4. Test thoroughly in Burp Suite
5. Submit a pull request

## License

This extension is provided as-is for educational and professional use.

## Disclaimer

This tool is designed for authorized security testing purposes only. Users must:
- Obtain proper authorization before testing any system
- Comply with all applicable laws and regulations
- Use the tool responsibly and ethically
- Review API provider terms of service

## Support

For issues or questions:
1. Check the Troubleshooting section above
2. Verify your API keys and configuration
3. Test API providers independently
4. Check Burp Suite's extension logs

## Acknowledgments

- Burp Suite Extender API documentation
- OpenAI and Anthropic for their powerful APIs
- The security research community

---

**Built with ❤️ for penetration testers**

*BurpAI Pro - Making security analysis smarter*
