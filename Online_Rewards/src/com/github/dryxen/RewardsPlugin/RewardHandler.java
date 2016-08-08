package com.github.dryxen.RewardsPlugin;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

import org.slf4j.Logger;
import org.spongepowered.api.text.Text;

import ninja.leaping.configurate.ConfigurationNode;
import ninja.leaping.configurate.commented.CommentedConfigurationNode;
import ninja.leaping.configurate.hocon.HoconConfigurationLoader;
import ninja.leaping.configurate.loader.ConfigurationLoader;

public class RewardHandler {
	
	private DateFormat dFormatter = new SimpleDateFormat("dd-MM-yyyy'@'HH:mm:ss");	
	private Logger logger;
	private ConfigurationLoader<CommentedConfigurationNode> configLoader;	
	private ConfigurationNode rootNode;	
	private HashMap<Integer, RewardObject> rewards = new HashMap<Integer, RewardObject>();
	private RewardObject reward;
	
	
	
	public boolean checkRewards(OnlineRewards instance, String name){
		logger = instance.getLogger();
		configLoader = HoconConfigurationLoader.builder().setPath(instance.getdefaultConfig()).build();
		try {
			Calendar cal = Calendar.getInstance();
			rootNode = configLoader.load();
			Calendar resetTime = cal;
			Calendar claimedToCal = cal;
			int resetMonth = rootNode.getNode("PluginSettings:","Reset:","Month").getInt();
			 if(resetMonth != 0) resetTime.set(Calendar.MONTH, (resetMonth+1));
			String resetDay = rootNode.getNode("PluginSettings:","Reset:","Day").getString();
			 switch(resetDay.toUpperCase()){
			 case "SUNDAY":
				 resetTime.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
				 break;
			 case "MONDAY":
				 resetTime.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
			     break;
			 case "TUESDAY":
				 resetTime.set(Calendar.DAY_OF_WEEK, Calendar.TUESDAY);
			     break;
			 case "WEDNESDAY":
				 resetTime.set(Calendar.DAY_OF_WEEK, Calendar.WEDNESDAY);
			     break;
			 case "THURSDAY":
				 resetTime.set(Calendar.DAY_OF_WEEK, Calendar.THURSDAY);
			     break;
			 case "Friday":
				 resetTime.set(Calendar.DAY_OF_WEEK, Calendar.FRIDAY);
			     break;
			 case "SATURDAY":
				 resetTime.set(Calendar.DAY_OF_WEEK, Calendar.SATURDAY);
			     break;
			 default:
				 break;
			 }			
			int resetHour = rootNode.getNode("PluginSettings:","Reset:","Hour").getInt();
			 if(resetHour != 0){ 
				 resetTime.set(Calendar.HOUR_OF_DAY, resetHour);				 
			 }else{
				 resetTime.set(Calendar.HOUR_OF_DAY, 0);
			 }
			int resetMinute = rootNode.getNode("PluginSettings:","Reset:","Minute").getInt();
			 if(resetMinute != 0){
				 resetTime.set(Calendar.MINUTE, resetMinute);
			 }else{
				 resetTime.set(Calendar.MINUTE, 0);
			 }
			int cooldown = rootNode.getNode("PluginSettings:", "PickupCooldown:", "Hours").getInt();
			Date startReset = resetTime.getTime();
			logger.info(instance.getOnlinePlayers().get(instance.getUUID(name)));
			Date playerClaimed = dFormatter.parse(instance.getOnlinePlayers().get(instance.getUUID(name)));
			claimedToCal.setTime(playerClaimed);
			claimedToCal.add(Calendar.HOUR_OF_DAY, cooldown);
			Date isCooled = claimedToCal.getTime();		
			
			if(playerClaimed.compareTo(startReset) == -1 && isCooled.compareTo(startReset) == -1){
				return true;
			}
			
		} catch (ParseException | IOException e) {
			
			e.printStackTrace();
		}
		
		return false;
	}	
	public void loadRewards(OnlineRewards instance){
		 logger = instance.getLogger();
		 
		 configLoader = HoconConfigurationLoader.builder().setPath(instance.getdefaultConfig()).build();
		 try{                	
     		rootNode = configLoader.load();     		
     			if(!rootNode.getNode("SetRewards:").isVirtual()){     				
     				int size = rootNode.getNode("SetRewards:").getChildrenMap().size();     				
     				for(int i = 0; i<size; i++){     					
     					int id = (i+1);
     					logger.info(""+(i+1));
     					String name = rootNode.getNode("SetRewards:","Reward:"+(i+1),"Item").getValue().toString();
     					int amount = rootNode.getNode("SetRewards:","Reward:"+(i+1),"Amount").getInt();
     					int meta = rootNode.getNode("SetRewards:","Reward:"+(i+1),"MetaID").getInt();
     					boolean isRandom = rootNode.getNode("SetRewards:","Reward:"+(i+1),"RandomReward").getBoolean();
     					reward = new RewardObject(id, name, amount, meta, isRandom);
     					rewards.put(i+1, reward);
     				}
     				
     				instance.setRewards(rewards);	     				
     			}     		
     		
         }catch(IOException e){
        
         }
	}
	
	public void addReward(OnlineRewards instance, int id, String name, int amount, int meta, boolean random){
		
		reward = new RewardObject(id, name, amount, meta, random);
		rewards.put(id, reward);
		instance.setRewards(rewards);		
		
	}	

}
