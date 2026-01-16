# Rice's Tabby List
A standalone tab list plugin for Purpur/Paper 1.21.4 - 1.21.11 (WILL SUPPORT OLDER VERSIONS IN FUTURE RELEASES (should work from 1.21.1-1.21.3, i will not provide releases however.))
- A wiki is located at https://wiki.icicle1133.cc/rices-tabby-list

## What This Plugin Does

- Rice's Tabby List replaces your default tab list with a fully customizable one that displays real-time server information. It's designed to have a stupidly simple config that isn't confusing to read.

### Key Features
- Standalone tab list - No other plugins required (works independently)
- Real-time TPS tracking - Monitor server performance at a glance
- RAM usage display - Show memory consumption (used/max/percentage)
- OP count - Display how many staff members are online
- Domain display - Show your server IP/domain in the tab list
- AFK integration - Automatically detects AFK players (requires EssentialsX)
- Full color support - Use `&` color codes and hex colors (`&x&R&R&G&G&B&B` format)
- PlaceholderAPI support - Use any PAPI placeholder in your tab list (optional)
- Customizable header/footer - Multi-line support with placeholders
- Player name formatting - Custom tab list name formats
- Reloading without stopping - You can make the plugin load your newly edited config without stopping the server~~~~

## Why Download This Plugin?

- Lightweight & Fast - Minimal performance impact, no bloat  
- Easy Configuration - Simple YAML config with the varibles on my wiki
- Works Out of the Box - No dependencies required (PAPI & Essentials are optional)  
- Works on anything paper - (paper itself, pupur, pufferfish, etc) 
- Up-to-date development - Built for modern Minecraft versions (1.21.4+)

Perfect for server owners who want:
- A tab list that "just works" without complex setup
- Real-time server stats visible to all players
- Full customization without performance concerns
- A plugin that doesn't require any dependencies

## Critical Information Before Downloading

### Optional Dependencies
- **PlaceholderAPI** - For using external placeholders (player health, economy, etc.)
- **EssentialsX** - For AFK status detection

### Important Notes
⚠️ **This plugin replaces your tab list entirely.** It's incompatible with other tab list plugins (TAB, NametagEdit tab features, etc.). You must choose one or the other.

✅ **No configuration required to start** - Works immediately with default settings, though it is HIGHLY RECOMMENDED you edit the config after you start the server


## Build Instructions
All in one command:
```bash
git clone https://github.com/icicle1133/RiceTabbyList && cd RiceTabbyList && chmod +x gradlew && ./gradlew build
```

Seperated command:
```bash
git clone https://github.com/icicle1133/RiceTabbyList
cd RiceTabbyList
chmod +x gradlew
./gradlew build
```
_im not providing windows build instructions, figure it out youself since you like to use ai slop_

Output: `build/libs/RiceTabbyList.jar`

## Don't wanna build?
- Grab a .jar in releases
