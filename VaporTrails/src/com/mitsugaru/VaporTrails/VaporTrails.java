package com.mitsugaru.VaporTrails;

import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import com.sk89q.worldguard.bukkit.WorldGuardPlugin;

public class VaporTrails extends JavaPlugin {
	private Commander commander;
	private Config config;
	private PermCheck perm;
	public static final String prefix = "[VaporTrails]";

	@Override
	public void onDisable() {
		// Save config
		this.saveConfig();
	}

	@Override
	public void onEnable() {
		// Config
		config = new Config(this);
		//Create permissions
		perm = new PermCheck(this);
		//Create commander
		commander = new Commander(this);
		getCommand("trail").setExecutor(commander);

		PluginManager pm = this.getServer().getPluginManager();
		//Create listener
		VTPlayerListener playerListener = new VTPlayerListener(this);
		pm.registerEvents(playerListener, this);
		//Check for worldguard?
		if(config.worldGuard)
		{
			try
			{
				WorldGuardPlugin test = getWorldGuard();
				if(test == null)
				{
					getLogger().warning("Could not attach to WorldGuard. Ignoring WorldGuard regions.");
					config.worldGuard = false;
				}
			}
			catch (Exception e)
			{
				getLogger().warning("Could not attach to WorldGuard. Ignoring WorldGuard regions.");
				config.worldGuard = false;
			}
		}
	}

	public Config getPluginConfig()
	{
		return config;
	}

	public PermCheck getPerm() {
		return perm;
	}

	public Commander getCommander()
	{
		return commander;
	}
	
	public WorldGuardPlugin getWorldGuard() {
	    Plugin plugin = getServer().getPluginManager().getPlugin("WorldGuard");
	 
	    // WorldGuard may not be loaded
	    if (plugin == null || !(plugin instanceof WorldGuardPlugin)) {
	        return null; // Maybe you want throw an exception instead
	    }
	 
	    return (WorldGuardPlugin) plugin;
	}
}
