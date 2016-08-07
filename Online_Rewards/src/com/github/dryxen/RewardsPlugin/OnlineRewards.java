package com.github.dryxen.RewardsPlugin;

import java.nio.file.Path;
import java.util.HashMap;
import java.util.Optional;

import org.slf4j.Logger;
import org.spongepowered.api.Game;
import org.spongepowered.api.config.ConfigDir;
import org.spongepowered.api.config.DefaultConfig;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.game.state.GamePreInitializationEvent;
import org.spongepowered.api.event.game.state.GameStartedServerEvent;
import org.spongepowered.api.event.network.ClientConnectionEvent;
import org.spongepowered.api.plugin.Plugin;

import com.google.inject.Inject;

import ninja.leaping.configurate.commented.CommentedConfigurationNode;
import ninja.leaping.configurate.loader.ConfigurationLoader;

@Plugin(id = "onlinerewards", name = "OnlineRewards", version = "0.1")
public class OnlineRewards {
	private PlayerHandler playerhandler = new PlayerHandler();
	private SetDefaultConfig setDefaultConfig = new SetDefaultConfig();
	private HashMap<String, String> onlinePlayers = new HashMap<String, String>();
	private HashMap<Integer, Object> rewards = new HashMap<Integer, Object>();
	@SuppressWarnings("unused")
	private RegisterCommands register;
	
	@Inject
	private Game game; 
	
	@Inject
	@DefaultConfig(sharedRoot = false)	
	private Path defaultConfig;
	
	@Inject
	@DefaultConfig(sharedRoot = false)
	private ConfigurationLoader<CommentedConfigurationNode> configManager;
	
	@Inject
	@ConfigDir(sharedRoot = false)
	private Path ConfigDir;	
	
	@Inject
	private Logger logger;
	@SuppressWarnings("unused")
	private Player player;
	
	@Listener
	public void onPreInit(GamePreInitializationEvent event){	
		
	}
	
	@Listener
	public void onServerStart(GameStartedServerEvent event){
		setDefaultConfig.configCheck(this);
		register = new RegisterCommands(this, game);
	}
	
	@Listener
	public void onLogin(ClientConnectionEvent.Login e){
		Optional<Player> player = e.getCause().last(Player.class).get().getPlayer();
		String uuid = player.get().getUniqueId().toString();		
		playerhandler.checkPlayer(this, uuid);
		playerhandler.importPlayerConfig(this, uuid);		
	}
	
	@Listener
	public void onLogout(ClientConnectionEvent.Disconnect e){
		Optional<Player> player = e.getCause().last(Player.class).get().getPlayer();
		String uuid = player.get().getUniqueId().toString();
		playerhandler.exportPlayerConfig(this, uuid, onlinePlayers.get(uuid));
		onlinePlayers.remove(uuid);		
		
	}
	
	public Logger getLogger(){
		return logger;
	}
	
	public Path getConfigDir() {		
		return ConfigDir;
	}
	public Path getdefaultConfig(){
		return defaultConfig;
	}
	public String getUUID(String name){
		Optional<Player> player = game.getServer().getPlayer(name);
		String uuid = player.get().getUniqueId().toString();
		return uuid;
	}
	public HashMap<String, String> getOnlinePlayers(){
		return onlinePlayers;
	}
	public HashMap<Integer, Object> getRewards(){
		return rewards;
	}
	public void setRewards(HashMap<Integer, Object> rewards){
		rewards.putAll(rewards);
		
	}

}
