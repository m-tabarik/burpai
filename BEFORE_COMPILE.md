# ⚠️ BEFORE COMPILATION: Download Burp Suite JAR

## Required File Missing: `burpsuite_community_v2_0_5.jar`

The compilation requires the **Burp Suite Extender API JAR file** to be in the `lib/` folder.

---

## 🚀 Quick Fix (2 Minutes)

### Step 1: Download Burp JAR
1. Go to: https://portswigger.net/burp/communitydownload
2. Click **"Download"** (Community Edition)
3. Save the file somewhere (e.g., Downloads)
4. The filename should be: `burpsuite_community_v2_0_5.jar` (or similar version)

### Step 2: Place in lib/ Folder
1. Create folder: `d:\Burpsuite pro\burp-claude\lib\`
2. Copy downloaded JAR into it
3. Result: `d:\Burpsuite pro\burp-claude\lib\burpsuite_community_v2_0_5.jar`

### Step 3: Verify
```cmd
# In Command Prompt, run:
dir "d:\Burpsuite pro\burp-claude\lib\"

# Should show:
# burpsuite_community_v2_0_5.jar
```

### Step 4: NOW You Can Compile
Once JAR is in place, run:

```cmd
"C:\Users\mtaba\Downloads\maven-mvnd-1.0.5-windows-amd64\maven-mvnd-1.0.5-windows-amd64\bin\mvnd.cmd" clean package
```

Or shorter:
```cmd
cd d:\Burpsuite pro\burp-claude
"C:\Users\mtaba\Downloads\maven-mvnd-1.0.5-windows-amd64\maven-mvnd-1.0.5-windows-amd64\bin\mvnd.cmd" clean package
```

---

## ✅ Expected Result After Compilation

```
[INFO] Building jar: target/BurpAIPro.jar
[INFO] BUILD SUCCESS
[INFO] Total time: 30-45 s
```

Your compiled extension:
```
target/BurpAIPro.jar  ✅
```

---

## 📋 Checklist Before Running Build

- [ ] Burp JAR downloaded
- [ ] Placed in: `lib/burpsuite_community_v2_0_5.jar`
- [ ] Verified with: `dir lib\burpsuite*`
- [ ] Ready to compile: `mvnd clean package`

---

**Once JAR is downloaded and placed, I can compile it for you!**
