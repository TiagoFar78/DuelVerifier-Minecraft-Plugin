package redehexen.duelVerifier.menus;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import redehexen.duelVerifier.managers.ConfigManager;

public class DuelVerifierMenu implements Listener {
	
	private static final int SWORD_INDEX = 9 + 3;
	private static final Material SWORD_MATERIAL = Material.DIAMOND_SWORD;
	private static final int HEAD_INDEX = 9 + 5;
	private static final Material HEAD_MATERIAL = Material.SKULL_ITEM;
	
	public static void open(Player player, String playerChallenged) {
		ConfigManager configManager = ConfigManager.getInstance();
		
		String menuName = configManager.getDuelVerifierMenuName() + playerChallenged;
		
		int lines = 3;
		Inventory inv = Bukkit.createInventory(null, lines*9, menuName);
		
		ItemStack sword = createSwordIcon();
		ItemStack head = createInvitedHead(playerChallenged);
		
		inv.setItem(SWORD_INDEX, sword);
		inv.setItem(HEAD_INDEX, head);
		
		player.openInventory(inv);
	}
	
	private static ItemStack createSwordIcon() {
		ConfigManager configManager = ConfigManager.getInstance();
		
		String itemName = configManager.getDuelVerifierMenuSwordName();
		
		ItemStack item = new ItemStack(SWORD_MATERIAL);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(itemName);
		item.setItemMeta(meta);
		
		return item;
	}
	
	private static ItemStack createInvitedHead(String playerChallenged) {
		ConfigManager configManager = ConfigManager.getInstance();
		
		String itemName = configManager.getDuelVerifierMenuHeadName();
		
		ItemStack item = new ItemStack(HEAD_MATERIAL, 1, (short)3);
		SkullMeta meta = (SkullMeta) item.getItemMeta();
		meta.setOwner(playerChallenged);
		meta.setDisplayName(itemName);
		item.setItemMeta(meta);
		
		return item;
	}
	
	@EventHandler
	public void click(InventoryClickEvent e) {
		ConfigManager configManager = ConfigManager.getInstance();
		
		String defaultMenuName = configManager.getDuelVerifierMenuName();
		
		Inventory inv = e.getInventory();
		InventoryView view = e.getView();
		
		if (inv == null) {
			return;
		}
		
		String invName = inv.getName();
		if (invName == null || invName != view.getTitle()) {
			return;
		}
		
		boolean isCorrectInventory = invName.contains(defaultMenuName);
		if (!isCorrectInventory) {
			return;
		}
		
		ItemStack item = inv.getItem(e.getSlot());
		if (item == null) {
			return;
		}
		
		String targetPlayerName = invName.substring(defaultMenuName.length());
		
		Material type = item.getType();
		if (type == SWORD_MATERIAL) {
			String command = configManager.getStartDuelCommand();
			executeCommand(e.getWhoClicked(), command, targetPlayerName);
			return;
		}
		
		if (type == HEAD_MATERIAL) {
			String command = configManager.getSeeInventoryCommand();
			executeCommand(e.getWhoClicked(), command, targetPlayerName);
		}
	}
	
	private void executeCommand(HumanEntity player, String command, String targetPlayerName) {
		command = command.replace("{PLAYER}", targetPlayerName);
		Bukkit.dispatchCommand(player, command);
	}

}
