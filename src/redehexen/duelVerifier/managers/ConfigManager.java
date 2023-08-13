package redehexen.duelVerifier.managers;

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

}
