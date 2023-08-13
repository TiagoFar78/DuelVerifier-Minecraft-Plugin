package redehexen.duelVerifier;

import java.io.File;
import java.io.IOException;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import redehexen.duelVerifier.menus.DuelVerifierMenu;

public class DuelVerifier extends JavaPlugin {
	
	@Override
	public void onEnable() {		
		if (!new File(getDataFolder(), "config.yml").exists()) {
			saveDefaultConfig();
		}
		
		getServer().getPluginManager().registerEvents(new DuelVerifierMenu(), this);
		
		getCommand("KillOnDisconnect").setExecutor(new KillOnDisconnectCommand());
	}
	
	public static YamlConfiguration getYamlConfiguration() {
		return YamlConfiguration.loadConfiguration(configFile());
	}
	
	private static File configFile() {
		return new File(getDuelVerifier().getDataFolder(), "config.yml");
	}
	
	public static DuelVerifier getDuelVerifier() {
		return (DuelVerifier)Bukkit.getServer().getPluginManager().getPlugin("TF_DuelVerifier");
	}
	
	public static void saveConfiguration(YamlConfiguration config) {
		File configFile = configFile();
		
		try {
			config.save(configFile);
		} catch (IOException e) {
			e.printStackTrace();
		}		
	}

}
