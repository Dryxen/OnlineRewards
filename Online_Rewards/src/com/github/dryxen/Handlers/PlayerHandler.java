package com.github.dryxen.Handlers;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import org.slf4j.Logger;

import com.github.dryxen.Objects.PlayerObject;
import com.github.dryxen.RewardsPlugin.OnlineRewards;

import ninja.leaping.configurate.ConfigurationNode;
import ninja.leaping.configurate.commented.CommentedConfigurationNode;
import ninja.leaping.configurate.hocon.HoconConfigurationLoader;
import ninja.leaping.configurate.loader.ConfigurationLoader;

public class PlayerHandler {
	private ConfigurationLoader<CommentedConfigurationNode> configLoader;	
	private ConfigurationNode rootNode;	
	private File playerConfig;
	private PlayerObject player;
	Logger logger;
	String claimed;	
	Calendar time = Calendar.getInstance();
	ResetTime resetTime = new ResetTime();
	private DateFormat dFormatter = new SimpleDateFormat("dd-MM-yyyy'@'HH:mm:ss");
	
	 
	
	
	public void exportPlayerConfig(OnlineRewards instance, String uuid){
		
		logger = instance.getLogger();
		playerConfig = new File(instance.getConfigDir().toFile(), "Players.conf");
		configLoader = HoconConfigurationLoader.builder().setFile(playerConfig).build();
		try{                	
			rootNode = configLoader.load();	            	    
			rootNode.getNode(uuid);
			rootNode.getNode(uuid,"Last Claimed Date").setValue(instance.getOnlinePlayers().get(uuid).getLastClaimed());			
			configLoader.save(rootNode);           		
		
		}catch(IOException e){
    	 
		}
	}
	
	public void importPlayerConfig(OnlineRewards instance, String uuid){
		
		logger = instance.getLogger();
		playerConfig = new File(instance.getConfigDir().toFile(), "Players.conf");
		configLoader = HoconConfigurationLoader.builder().setFile(playerConfig).build();
		Calendar cal = Calendar.getInstance();
		Calendar streakCal = cal;
		try{                	
			rootNode = configLoader.load();
			int cooldown = HoconConfigurationLoader.builder().setPath(instance.getdefaultConfig()).build().load()
			.getNode("PluginSettings:", "PickupCooldown:", "Hours").getInt();
			String streakString = rootNode.getNode(uuid,"Streak Date").getString();
			int currentStreak = rootNode.getNode(uuid, "Streak").getInt();
			Date today = resetTime.getResetTime(instance).getTime();
			Date now = cal.getTime();
			
			Calendar yesterdayCal = cal;
			yesterdayCal.setTime(today);
			yesterdayCal.add(Calendar.DAY_OF_MONTH, -1);
			Date yesterday = yesterdayCal.getTime();			
			Date streakDate = dFormatter.parse(streakString);		
			streakCal.setTime(streakDate);
			streakCal.add(Calendar.HOUR, cooldown);
			Date streakCool = streakCal.getTime();		
			
			if(streakDate.compareTo(today) == -1 && streakCool.compareTo(today) == -1){
				logger.info("during compare");
				if(streakDate.compareTo(yesterday) == -1){					
					rootNode.getNode(uuid,"Streak Date").setValue(dFormatter.format(now));
					rootNode.getNode(uuid,"Streak").setValue(0);
					configLoader.save(rootNode);
				}else{					
					currentStreak = currentStreak+1;
					rootNode.getNode(uuid,"Streak Date").setValue(dFormatter.format(now));
					rootNode.getNode(uuid,"Streak").setValue(currentStreak);
					configLoader.save(rootNode);
				}
			}			
			
			claimed = rootNode.getNode(uuid,"Last Claimed Date").getString();			
			player = new PlayerObject(uuid, claimed, currentStreak);
			instance.getOnlinePlayers().put(uuid, player);          		
		
		}catch(IOException | ParseException e){
    	 logger.error("The hamsters couldn't find "+ uuid, e);
		}		
	}
	public void checkPlayer(OnlineRewards instance, String uuid){
		
		logger = instance.getLogger();
		playerConfig = new File(instance.getConfigDir().toFile(), "Players.conf");
		configLoader = HoconConfigurationLoader.builder().setFile(playerConfig).build();		 
		
		try{			
			Date date = Calendar.getInstance().getTime();
			String dateTime = dFormatter.format(date);			
			rootNode = configLoader.load();
			rootNode = rootNode.getNode();			
			if(rootNode.getNode(uuid).isVirtual()){							
				rootNode.getNode(uuid,"Last Claimed Date").setValue(dateTime);
				rootNode.getNode(uuid,"Streak Date").setValue(dateTime);
				rootNode.getNode(uuid,"Streak").setValue(0);
				configLoader.save(rootNode);
				//todo add a default first join reward
			}
			
		}catch(IOException e){
			
		}
		
	}
	public void setPlayerClaimed(OnlineRewards instance, String uuid, Date date){
		
		playerConfig = new File(instance.getConfigDir().toFile(), "Players.conf");
		configLoader = HoconConfigurationLoader.builder().setFile(playerConfig).build();		 
		String dateTime = dFormatter.format(date);
		try{ 
			 rootNode = configLoader.load();
			 if(!rootNode.getNode(uuid).isVirtual()){
				 rootNode.getNode(uuid,"Last Claimed Date").setValue(dateTime);
				 configLoader.save(rootNode);
			 }
			 
		 }catch(IOException e){
			 
		 }		 	
		 instance.getOnlinePlayers().get(uuid).setLastClaimed(dateTime);
		
	}

}
