package com.github.dryxen.RewardsPlugin;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.UUID;

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
	String claimed;
	HashMap<String, String> players = new HashMap<String, String>();
	 private DateFormat dFormatter = new SimpleDateFormat("dd-MM-yyyy'@'HH:mm:ss");
	
	 
	
	
	public void exportPlayerConfig(OnlineRewards instance, String uuid, String claimed){
		
		logger = instance.getLogger();
		playerConfig = new File(instance.getConfigDir().toFile(), "Players.conf");
		configLoader = HoconConfigurationLoader.builder().setFile(playerConfig).build();
		try{                	
			rootNode = configLoader.load();	            	    
			rootNode.getNode(uuid);
			rootNode.getNode(uuid,"Last Claimed Date").setValue(claimed);			
			
			configLoader.save(rootNode);           		
		
		}catch(IOException e){
    	 
		}
	}
	
	public HashMap<String, String> importPlayerConfig(OnlineRewards instance, String uuid){
		
		logger = instance.getLogger();
		playerConfig = new File(instance.getConfigDir().toFile(), "Players.conf");
		configLoader = HoconConfigurationLoader.builder().setFile(playerConfig).build();		
		try{                	
			rootNode = configLoader.load();		
			claimed = Text.of(rootNode.getNode(uuid,"Last Claimed Date").getValue()).toString();			
			players.put(uuid, claimed);          		
		
		}catch(IOException e){
    	 logger.error("The hamsters couldn't find "+ uuid, e);
		}
		
		return players;
	}
	public void checkPlayer(OnlineRewards instance, String uuid){
		
		logger = instance.getLogger();
		playerConfig = new File(instance.getConfigDir().toFile(), "Players.conf");
		configLoader = HoconConfigurationLoader.builder().setFile(playerConfig).build();		 
		
		try{
			logger.info("I made it to the check player try catch");
			Date date = Calendar.getInstance().getTime();
			String dateTime = dFormatter.format(date);
			
			rootNode = configLoader.load();
			rootNode = rootNode.getNode();			
			if(rootNode.getNode(uuid).isVirtual()){
				logger.info("this is the if statement");				
				rootNode.getNode(uuid,"Last Claimed Date").setValue(dateTime);				;
				configLoader.save(rootNode);
			}
			
		}catch(IOException e){
			
		}
		
	}

}
