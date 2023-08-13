package redehexen.duelVerifier.menus;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
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
		meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
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
		
		Inventory inv = e.getClickedInventory();
		InventoryView view = e.getView();
		int slot = e.getSlot();
		
		if (inv == null || slot < 0 || slot > 35) {
			return;
		}
		
		String invName = inv.getTitle();
		if (invName == null || invName != view.getTitle()) {
			return;
		}
		
		boolean isCorrectInventory = invName.contains(defaultMenuName);
		if (!isCorrectInventory) {
			return;
		}
		e.setCancelled(true);
		
		ItemStack item = inv.getItem(e.getSlot());
		if (item == null) {
			return;
		}
		
		Player player = (Player) e.getWhoClicked();
		String targetPlayerName = invName.substring(defaultMenuName.length());
		Player targetPlayer = Bukkit.getPlayer(targetPlayerName);
		
		Material type = item.getType();
		if (type == SWORD_MATERIAL) {
			if (!areInventoriesEqual(player, targetPlayer)) {
				player.sendMessage(configManager.getDifferentInventoriesMessage());
				return;
			}
			
			String command = configManager.getStartDuelCommand();
			executeCommand(player, command, targetPlayerName);
			return;
		}
		
		if (type == HEAD_MATERIAL) {
			String command = configManager.getSeeInventoryCommand();
			executeCommand(player, command, targetPlayerName);
		}
	}
	
	private void executeCommand(Player player, String command, String targetPlayerName) {
		command = command.replace("{PLAYER}", targetPlayerName);
		Bukkit.dispatchCommand(player, command);
	}
	
	private boolean areInventoriesEqual(Player player, Player targetPlayer) {
		PlayerInventory playerInventory = player.getInventory();
		PlayerInventory targetPlayerInventory = targetPlayer.getInventory();
		
		List<ItemStack> inventoryContents = buildInventoryContentsList(playerInventory.getContents());
		
		for (ItemStack item : targetPlayerInventory.getContents()) {
			int index = findItemInInventory(inventoryContents, item);
			if (index != -1) {
				inventoryContents.remove(findItemInInventory(inventoryContents, item));
			}
		}
		
		return inventoryContents.isEmpty() && 
				areSameItens(playerInventory.getHelmet(), targetPlayerInventory.getHelmet()) &&
				areSameItens(playerInventory.getChestplate(), targetPlayerInventory.getChestplate()) &&
				areSameItens(playerInventory.getLeggings(), targetPlayerInventory.getLeggings()) &&
				areSameItens(playerInventory.getBoots(), targetPlayerInventory.getBoots());
	}
	
	private int findItemInInventory(List<ItemStack> inventoryContents, ItemStack item) {
		for (int i = 0; i < inventoryContents.size(); i++) {
			if (areSameItens(inventoryContents.get(i), item)) {
				return i;
			}
		}
		
		return -1;
	}
	
	private boolean areSameItens(ItemStack item1, ItemStack item2) {		
		return (item1 == null && item2 == null) || (item1 != null && item2 != null && 
				item1.getType() == item2.getType() && item1.getAmount() == item2.getAmount() && 
				haveSameEnchantments(item1, item2));
	}
	
	private boolean haveSameEnchantments(ItemStack item1, ItemStack item2) {
		Map<Enchantment, Integer> enchantments1 = item1.getEnchantments();
		Map<Enchantment, Integer> enchantments2 = item2.getEnchantments();
		if (enchantments1.size() != enchantments2.size()) {
			return false;
		}
		
		for (Enchantment enchantment : enchantments1.keySet()) {
			if (!item2.containsEnchantment(enchantment) || 
					item1.getEnchantmentLevel(enchantment) != item2.getEnchantmentLevel(enchantment)) {
				return false;
			}
		}
		
		return true;
	}
	
	private List<ItemStack> buildInventoryContentsList(ItemStack[] inventoryContentsArray) {
		List<ItemStack> inventoryContentsList = new ArrayList<ItemStack>();
		
		for (ItemStack item : inventoryContentsArray) {
			inventoryContentsList.add(item);
		}
		
		return inventoryContentsList;
	}

}
