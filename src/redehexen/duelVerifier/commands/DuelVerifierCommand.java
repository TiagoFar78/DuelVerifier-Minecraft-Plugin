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
		if (args.length != 1) {
			// TODO usage
			return false;
		}
		
		String subcommand = args[0];
		
		if (subcommand.equalsIgnoreCase(RELOAD_COMMAND)) {
			if (!sender.hasPermission(DuelVerifier.RELOAD_PERMISSION)) {
				// TODO not allowed
				return false;
			}
			
			ConfigManager.reload();
			// TODO reloaded successfully
			
			return false;
		}
		
		Player targetPlayer = Bukkit.getPlayer(subcommand);
		if (targetPlayer == null || !targetPlayer.isOnline()) {
			// TODO not online
			return false;
		}
		
		DuelVerifierMenu.open((Player)sender, targetPlayer.getName());
		
		return false;
	}

}
