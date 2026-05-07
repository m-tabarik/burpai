# BurpAI Pro - Changelog

## Version 1.0.0 (Current)

### 🎉 Initial Release

#### Features
- ✅ Multi-provider AI support (OpenAI + Claude)
- ✅ Three analysis modes (Passive, Active, Explain)
- ✅ Burp Suite integration (tab + context menu)
- ✅ Settings panel for configuration
- ✅ Sensitive data masking
- ✅ Async processing (non-blocking UI)
- ✅ Multiple AI models support
- ✅ Configurable temperature and token limits
- ✅ Error handling with retry logic
- ✅ Professional prompt engineering

#### Supported Providers
- OpenAI: gpt-4, gpt-4-turbo, gpt-4o, gpt-3.5-turbo
- Claude: claude-3-opus, claude-3-sonnet, claude-3-haiku

#### Supported Analysis Modes
- Passive: Vulnerability identification without payloads
- Active: Full analysis with exploitation steps
- Explain: Educational mode for learning

#### Improvements
- Clean Maven build configuration
- Well-organized source code structure
- Comprehensive error handling
- Token limit enforcement
- API timeout (30 seconds) with retry logic

#### Bug Fixes
- None (initial release)

#### Known Limitations
- Single analysis at a time (no queuing)
- Large requests (>10KB) are truncated
- Subject to API provider rate limits
- Auto-analyze proxy traffic not yet implemented

### 🗺️ Roadmap

#### v1.1.0 (Planned)
- [ ] Caching to reduce API costs
- [ ] Batch analysis of multiple requests
- [ ] Export results to file/PDF
- [ ] Custom prompt templates
- [ ] Highlight vulnerabilities in response
- [ ] Copy/paste formatted results

#### v1.2.0 (Future)
- [ ] Auto-analyze proxy traffic toggle
- [ ] Integration with Burp's issue reporter
- [ ] Payload generation tool
- [ ] Attack workflow automation
- [ ] Response body analysis with syntax highlighting
- [ ] API usage statistics dashboard

#### v2.0.0 (Long-term)
- [ ] Support for additional AI providers (Gemini, Llama)
- [ ] Local AI model support (Ollama)
- [ ] Team collaboration features
- [ ] Report generation
- [ ] Webhook integrations
- [ ] Custom vulnerability rules

## Version History

### v1.0.0
- Initial public release
- Full feature set as described

---

## Installation Notes

For each new version:
1. Download the `.jar` from releases
2. Load into Burp Suite (Extender → Add)
3. No configuration required (settings persist)

## Upgrade Path

- All versions are backward compatible
- Settings are preserved during updates
- No database migrations required

## Support & Feedback

- Report bugs on GitHub
- Request features via GitHub issues
- Check documentation for FAQs

---

**Current Version: 1.0.0 (March 2024)**

**Last Updated: 2024-03-27**
