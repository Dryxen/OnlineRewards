package com.github.dryxen.RewardsPlugin;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import org.slf4j.Logger;
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
	
	public void importPlayerConfig(OnlineRewards instance, String uuid){
		
		logger = instance.getLogger();
		playerConfig = new File(instance.getConfigDir().toFile(), "Players.conf");
		configLoader = HoconConfigurationLoader.builder().setFile(playerConfig).build();		
		try{                	
			rootNode = configLoader.load();		
			claimed = rootNode.getNode(uuid,"Last Claimed Date").getValue().toString();			
			instance.getOnlinePlayers().put(uuid, claimed);          		
		
		}catch(IOException e){
    	 logger.error("The hamsters couldn't find "+ uuid, e);
		}		
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
				//todo add a default first join reward
			}
			
		}catch(IOException e){
			
		}
		
	}

}
