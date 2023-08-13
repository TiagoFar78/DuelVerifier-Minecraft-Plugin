package redehexen.duelVerifier.managers;

import java.util.List;
import java.util.stream.Collectors;

import org.bukkit.configuration.file.YamlConfiguration;

import redehexen.duelVerifier.DuelVerifier;

public class ConfigManager {
	
	private static ConfigManager instance = new ConfigManager();
	
	public static ConfigManager getInstance() {
		return instance;
	}
	
	public static void reload() {
		instance = new ConfigManager();
	}
	
	private String _duelVerifierMenuName;
	private String _duelVerifierMenuSwordName;
	private String _duelVerifierMenuHeadName;
	
	private String _startDuelCommand;
	private String _seeInventoryCommand;
	
	private String _configReloadedMessage;
	
	private String _notAllowedMessage;
	private String _playerOfflineMessage;
	private String _differentInventoriesMessage;
	
	private List<String> _generalUsageMessage;
	
	
	private ConfigManager() {
		YamlConfiguration config = DuelVerifier.getYamlConfiguration();
		
		_duelVerifierMenuName = config.getString("Menus.DuelVerifier.Name").replace("&", "§");
		_duelVerifierMenuSwordName = config.getString("Menus.DuelVerifier.Sword.Name").replace("&", "§");
		_duelVerifierMenuHeadName = config.getString("Menus.DuelVerifier.Head.Name").replace("&", "§");
		
		_startDuelCommand = config.getString("Commands.StartDuel");
		_seeInventoryCommand = config.getString("Commands.SeeInventory");
		
		_configReloadedMessage = config.getString("Messages.Warnings.ConfigReloaded").replace("&", "§");
		
		_notAllowedMessage = config.getString("Messages.Errors.NotAllowed").replace("&", "§");
		_playerOfflineMessage = config.getString("Messages.Errors.PlayerOffline").replace("&", "§");
		_differentInventoriesMessage = config.getString("Messages.Errors.DifferentInventories").replace("&", "§");
		
		_generalUsageMessage = config.getStringList("Messages.Usages.GeneralUsage").stream()
				.map(line -> line.replace("&", "§")).collect(Collectors.toList());
	}
		
	
	public String getDuelVerifierMenuName() {
		return _duelVerifierMenuName;
	}
	
	public String getDuelVerifierMenuSwordName() {
		return _duelVerifierMenuSwordName;
	}

	public String getDuelVerifierMenuHeadName() {
		return _duelVerifierMenuHeadName;
	}
	
	public String getStartDuelCommand() {
        return _startDuelCommand;
    }

    public String getSeeInventoryCommand() {
        return _seeInventoryCommand;
    }
    
    public String getConfigReloadedMessage() {
        return _configReloadedMessage;
    }

    public String getNotAllowedMessage() {
        return _notAllowedMessage;
    }

    public String getPlayerOfflineMessage() {
        return _playerOfflineMessage;
    }

    public String getDifferentInventoriesMessage() {
        return _differentInventoriesMessage;
    }

    public List<String> getGeneralUsageMessage() {
        return _generalUsageMessage;
    }

}
