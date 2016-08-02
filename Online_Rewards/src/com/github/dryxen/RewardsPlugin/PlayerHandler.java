package com.github.dryxen.RewardsPlugin;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import org.slf4j.Logger;
import org.spongepowered.api.text.Text;

import ninja.leaping.configurate.ConfigurationNode;
import ninja.leaping.configurate.commented.CommentedConfigurationNode;
import ninja.leaping.configurate.hocon.HoconConfigurationLoader;
import ninja.leaping.configurate.loader.ConfigurationLoader;

public class PlayerHandler {
	private ConfigurationLoader<CommentedConfigurationNode> configLoader;	
	private ConfigurationNode rootNode;	
	private File playerConfig;
	Logger logger;
	HashMap<String, String> players = new HashMap<String, String>();
	
	public void exportPlayerConfig(OnlineRewards instance, String name, String claimed){
		
		logger = instance.getLogger();
		playerConfig = new File(instance.getConfigDir().toFile(), "Players.conf");
		configLoader = HoconConfigurationLoader.builder().setFile(playerConfig).build();
		try{                	
			rootNode = configLoader.load();	            	    
			rootNode.getNode(name);
			rootNode.getNode(name,"Last Claimed").setValue(claimed);
			configLoader.save(rootNode);           		
		
		}catch(IOException e){
    	 
		}
	}
	
	public HashMap<String, String> importPlayerConfig(OnlineRewards instance, String name){
		
		logger = instance.getLogger();
		playerConfig = new File(instance.getConfigDir().toFile(), "Players.conf");
		configLoader = HoconConfigurationLoader.builder().setFile(playerConfig).build();
		String claimed;
		try{                	
			rootNode = configLoader.load();		
			claimed = Text.of(rootNode.getNode(name,"Last Claimed").getValue()).toString();
			 players.put(name, claimed);          		
		
		}catch(IOException e){
    	 logger.error("The hamsters couldn't find "+ name, e);
		}
		
		return players;
	}

}
