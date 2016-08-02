package com.github.dryxen.RewardsPlugin;

import java.nio.file.Path;
import java.util.ArrayList;

import org.slf4j.Logger;
import org.spongepowered.api.config.ConfigDir;
import org.spongepowered.api.config.DefaultConfig;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.game.state.GamePreInitializationEvent;
import org.spongepowered.api.event.game.state.GameStartedServerEvent;
import org.spongepowered.api.event.network.ClientConnectionEvent;
import org.spongepowered.api.plugin.Plugin;

import com.google.inject.Inject;

@Plugin(id = "onlinerewards", name = "OnlineRewards", version = "0.1")
public class OnlineRewards {
	ArrayList player = new ArrayList();
	ArrayList players = new ArrayList();
	
	@ConfigDir(sharedRoot = false)
	private Path ConfigDir;
	
	@DefaultConfig(sharedRoot = false)	
	private Path defaultConfig;
	
	@Inject
	private Logger logger;
	
	@Listener
	public void onPreInit(GamePreInitializationEvent event){	
		
	}
	
	@Listener
	public void onServerStart(GameStartedServerEvent event){
		
	}
	
	@Listener
	public ClientConnectionEvent onLogin(ClientConnectionEvent.Login e){
		return e;
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

}
