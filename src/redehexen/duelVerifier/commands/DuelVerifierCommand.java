package redehexen.duelVerifier.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import redehexen.duelVerifier.DuelVerifier;
import redehexen.duelVerifier.managers.ConfigManager;
import redehexen.duelVerifier.menus.DuelVerifierMenu;

public class DuelVerifierCommand implements CommandExecutor {
	
	private static final String RELOAD_COMMAND = "reload";

	@Override
	public boolean onCommand(CommandSender sender, Command label, String cmd, String[] args) {
		ConfigManager configManager = ConfigManager.getInstance();
		
		if (args.length != 1) {
			sender.sendMessage(configManager.getGeneralUsageMessage());
			return false;
		}
		
		String subcommand = args[0];
		
		if (subcommand.equalsIgnoreCase(RELOAD_COMMAND)) {
			if (!sender.hasPermission(DuelVerifier.RELOAD_PERMISSION)) {
				sender.sendMessage(configManager.getNotAllowedMessage());
				return false;
			}
			
			ConfigManager.reload();
			sender.sendMessage(configManager.getConfigReloadedMessage());
			
			return false;
		}
		
		if (!sender.hasPermission(DuelVerifier.START_DUEL_PERMISSION)) {
			sender.sendMessage(configManager.getNotAllowedMessage());
			return false;
		}
		
		Player targetPlayer = Bukkit.getPlayer(subcommand);
		if (targetPlayer == null || !targetPlayer.isOnline()) {
			sender.sendMessage(configManager.getPlayerOfflineMessage());
			return false;
		}
		
		DuelVerifierMenu.open((Player)sender, targetPlayer.getName());
		
		return false;
	}

}
