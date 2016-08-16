package com.github.dryxen.Handlers;

import java.io.IOException;
import java.util.Calendar;
import com.github.dryxen.RewardsPlugin.OnlineRewards;

import ninja.leaping.configurate.ConfigurationNode;
import ninja.leaping.configurate.commented.CommentedConfigurationNode;
import ninja.leaping.configurate.hocon.HoconConfigurationLoader;
import ninja.leaping.configurate.loader.ConfigurationLoader;

public class ResetTime {
	
	
	private ConfigurationLoader<CommentedConfigurationNode> configLoader;	
	private ConfigurationNode rootNode;	
	
	public Calendar getResetTime(OnlineRewards instance){		
		configLoader = HoconConfigurationLoader.builder().setPath(instance.getdefaultConfig()).build();
		Calendar cal = Calendar.getInstance();
		Calendar resetTime = cal;
		try {			
			rootNode = configLoader.load();		
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
			
		}catch(IOException e){
			
		}
		 return resetTime;
	}

}
