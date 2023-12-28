# MC-DragonLib

![Logo](https://github.com/MisterJulsen/MC-DragonLib/blob/1.18.2/src/main/resources/mod_logo.png)

DragonLib is a small and simple Mionecraft Forge Library Mod which contains code that is used by most of my mods. This mod adds nothing special on its own. New functionality my be added at any time when needed by my mods.

If you are developer and find the features useful, you can use it if you want. However, I cannot guarantee that everything will work as expected and will be supported in newer versions! Below there is a small tutorial on how to add this library as a dependency to your Forge Mod.

## **Download**
[![CurseForge](https://i.imgur.com/XZYlGVF.png)](https://www.curseforge.com/minecraft/mc-mods/dragonlib)

## **Discord**
[![Discord](https://i.imgur.com/YnDoeHs.png)](https://discord.gg/AeSbNgvc7f)

Feel free to join my Discord server for more information about my mods or to send bug reports, suggestions or to exchange with the community!

## **Setup a project using DragonLib**
#### 1. Add the following repository and dependency to your `build.gradle`:

```groovy
repositories {
    maven {
        url "https://cursemaven.com"
    }
}

dependencies {
    implementation fg.deobf("curse.maven:dragonlib-946163:<fileId>")
}
```
Replace `<fileId>` with the id of the file you can find on [CurseForge](https://www.curseforge.com/minecraft/mc-mods/dragonlib/files). Select a version and copy the file id from the url, which should look like `https://www.curseforge.com/minecraft/mc-mods/dragonlib/files/<fileId>`.

#### 2. Add the following lines to your `mods.toml`:
```toml
[[dependencies.dragonlib]]
    modId="dragonlib"
    mandatory=true
    versionRange="[<your_version>,<next_major_version>)"
    ordering="NONE"
    side="BOTH"
```
Replace `<your_version>` with the minecraft version you are modding, e.g. `1.18.2`, and `<next_major_version>` with the next major version, e.g. `1.19`.
