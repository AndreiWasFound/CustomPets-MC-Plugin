package me.andreiwasfound.custompets;

import org.bukkit.ChatColor;
import org.bukkit.Particle;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.v1_15_R1.CraftWorld;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.plugin.java.JavaPlugin;

import net.minecraft.server.v1_15_R1.ChatComponentText;
import net.minecraft.server.v1_15_R1.WorldServer;

public class Main extends JavaPlugin implements Listener {
	
	@Override
	public void onEnable() {
		this.getServer().getPluginManager().registerEvents(this, this);
	}
	
	@Override
	public void onDisable() {
		
	}
	
	public boolean onCommand(CommandSender sender, Command cmd, String label, String args[]) {
		if (label.equalsIgnoreCase("pet")) {
			if (!(sender instanceof Player)) {
				sender.sendMessage("Console cannot run this command");
				return true;
			}
			Player player = (Player) sender;
			CustomPet pet = new CustomPet(player.getLocation(), player);
			pet.setCustomName(new ChatComponentText(ChatColor.LIGHT_PURPLE + player.getDisplayName() + "'s Pet"));
			WorldServer world = ((CraftWorld) player.getWorld()).getHandle();
			world.addEntity(pet);
			player.sendMessage(ChatColor.RED + "Hey youtube.");
		}
		return false;
	}
	
	//Right click on mob to make them explode
	@EventHandler
	public void onClick(PlayerInteractEntityEvent event) {
		if (event.getRightClicked().getCustomName() == null)
			return;
		if (event.getRightClicked().getCustomName().contains(event.getPlayer().getName() + "'s Pet")) {
			event.getRightClicked().remove();
			event.getPlayer().getWorld().spawnParticle(Particle.EXPLOSION_HUGE, 
					event.getPlayer().getLocation().getX(), 
					event.getPlayer().getLocation().getY(), 
					event.getPlayer().getLocation().getZ(), 0);
		}
	}

}
