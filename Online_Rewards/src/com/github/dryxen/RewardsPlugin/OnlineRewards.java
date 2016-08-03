package com.github.dryxen.RewardsPlugin;

import java.io.File;
import java.nio.file.Path;
import java.util.Optional;

import org.slf4j.Logger;
import org.spongepowered.api.config.ConfigDir;
import org.spongepowered.api.config.DefaultConfig;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.game.state.GamePreInitializationEvent;
import org.spongepowered.api.event.game.state.GameStartedServerEvent;
import org.spongepowered.api.event.network.ClientConnectionEvent;
import org.spongepowered.api.plugin.Plugin;
import org.spongepowered.api.text.Text;

import com.google.inject.Inject;

import ninja.leaping.configurate.commented.CommentedConfigurationNode;
import ninja.leaping.configurate.loader.ConfigurationLoader;

@Plugin(id = "onlinerewards", name = "OnlineRewards", version = "0.1")
public class OnlineRewards {
	private PlayerHandler playerhandler = new PlayerHandler();
	
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
	private Player player;
	
	@Listener
	public void onPreInit(GamePreInitializationEvent event){	
		
	}
	
	@Listener
	public void onServerStart(GameStartedServerEvent event){
		
	}
	
	@Listener
	public void onLogin(ClientConnectionEvent.Login e){
		Optional<Player> player = e.getCause().last(Player.class).get().getPlayer();
		logger.info(player.get().getUniqueId().toString());
		playerhandler.checkPlayer(this, player.get().getUniqueId().toString());
		
	}
	
	@Listener
	public ClientConnectionEvent onLogout(ClientConnectionEvent.Disconnect e){
		return e;
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

}
