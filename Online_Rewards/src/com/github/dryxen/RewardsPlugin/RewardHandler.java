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
	private HashMap<Integer, String[][]> rewards = new HashMap<Integer, String[][]>();
	private String[][] items;
	
	
	public boolean checkRewards(OnlineRewards instance, String name){
		logger = instance.getLogger();
		configLoader = HoconConfigurationLoader.builder().setPath(instance.getdefaultConfig()).build();
		try {
			Calendar cal = Calendar.getInstance();
			rootNode = configLoader.load();
			Calendar resetTime = cal;
			Calendar claimedToCal = cal;
			int resetMonth = rootNode.getNode("PluginSettings:","Reset:","Month").getInt();
			 if(resetMonth != 0) resetTime.set(Calendar.MONTH, resetMonth);
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
			 if(resetHour != 0) resetTime.set(Calendar.HOUR, resetHour);
			int resetMinute = rootNode.getNode("PluginSettings:","Reset:","Minute").getInt();
			 if(resetMinute != 0) resetTime.set(Calendar.MINUTE, resetMinute);
			int cooldown = rootNode.getNode("PluginSettings:", "PickupCooldown:", "Hours").getInt();
			Date startReset = resetTime.getTime();		
			Date playerClaimed = dFormatter.parse(instance.getOnlinePlayers().get(instance.getUUID(name)));
			claimedToCal.setTime(playerClaimed);
			claimedToCal.add(Calendar.HOUR, cooldown);
			Date isCooled = claimedToCal.getTime();
			
			
			
			
			
			
			
			if(playerClaimed.compareTo(startReset) == 1 && playerClaimed.compareTo(isCooled) == 1){
				return true;
			}
			logger.info(Text.of("").toString());
		} catch (ParseException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return false;
	}	
	public void loadRewards(OnlineRewards instance){
		 logger = instance.getLogger();
		 
		 configLoader = HoconConfigurationLoader.builder().setPath(instance.getdefaultConfig()).build();
		 try{                	
     		rootNode = configLoader.load();
     		for(int i = 0; i<24; i++){
     			if(!rootNode.getNode("Hours:"+i).isVirtual()){     				
     				int size = rootNode.getNode("Hours:"+i).getChildrenMap().size();     				
     				for(int j = 0; j<size; j++){     					
     					items = new String[size][3];
     					items[j][0]= rootNode.getNode("Hours:"+i,"Reward Number:"+(j+1),"Item").getValue().toString();
     					items[j][1]= rootNode.getNode("Hours:"+i,"Reward Number:"+(j+1),"Amount").getValue().toString();
     					items[j][2]= rootNode.getNode("Hours:"+i,"Reward Number:"+(j+1),"MetaID").getValue().toString();    					
     				}
     				rewards.put(i, items);
     				instance.setRewards(rewards);
     				logger.info(""+items.length);
     				//todo need to rework with for config changes
     				
     			}
     		}
     		
     		
         }catch(IOException e){
        
         }
	}
	public void exportRewards(OnlineRewards instance){
		
	}

}
