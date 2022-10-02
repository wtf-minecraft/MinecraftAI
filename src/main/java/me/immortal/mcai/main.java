package me.immortal.mcai;

import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public final class main extends JavaPlugin {

    public static main getInstance() {
        return getPlugin(main.class);
    }

    @Override
    public void onEnable() {
        // Plugin startup logic
        getCommand("npcspawn").setExecutor(new CreateCommandNPC());

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
