package redehexen.duelVerifier;

import java.io.File;
import java.io.IOException;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import redehexen.duelVerifier.commands.DuelVerifierCommand;
import redehexen.duelVerifier.menus.DuelVerifierMenu;

public class DuelVerifier extends JavaPlugin {
	
	public static final String START_DUEL_PERMISSION = "TF_DuelVerifier.StartDuel";
	public static final String RELOAD_PERMISSION = "TF_DuelVerifier.Reload";
	
	@Override
	public void onEnable() {		
		if (!new File(getDataFolder(), "config.yml").exists()) {
			saveDefaultConfig();
		}
		
		getServer().getPluginManager().registerEvents(new DuelVerifierMenu(), this);
		
		getCommand("desafiar").setExecutor(new DuelVerifierCommand());
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
