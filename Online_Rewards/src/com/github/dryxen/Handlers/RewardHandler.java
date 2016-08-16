package com.github.dryxen.Handlers;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

import com.github.dryxen.Objects.RewardObject;
import com.github.dryxen.RewardsPlugin.OnlineRewards;

import ninja.leaping.configurate.ConfigurationNode;
import ninja.leaping.configurate.commented.CommentedConfigurationNode;
import ninja.leaping.configurate.hocon.HoconConfigurationLoader;
import ninja.leaping.configurate.loader.ConfigurationLoader;

public class RewardHandler {
	
	private DateFormat dFormatter = new SimpleDateFormat("dd-MM-yyyy'@'HH:mm:ss");
	private DateFormat tFormatter = new SimpleDateFormat("HH' Hours': mm' Mins': ss' Secs'");
	private ConfigurationLoader<CommentedConfigurationNode> configLoader;	
	private ConfigurationNode rootNode;	
	private HashMap<Integer, RewardObject> rewards = new HashMap<Integer, RewardObject>();
	private RewardObject reward;
	private ResetTime rTime = new ResetTime();
	
	
	
	public boolean checkRewards(OnlineRewards instance, String uuid){
		instance.getLogger();
		configLoader = HoconConfigurationLoader.builder().setPath(instance.getdefaultConfig()).build();
		try {
			Calendar cal = Calendar.getInstance();
			rootNode = configLoader.load();			
			Calendar resetTime = rTime.getResetTime(instance);
			Calendar claimedToCal = cal;			
			int cooldown = rootNode.getNode("PluginSettings:", "PickupCooldown:", "Hours").getInt();
			Date startReset = resetTime.getTime();			
			Date playerClaimed = dFormatter.parse(instance.getOnlinePlayers().get(uuid).getLastClaimed());
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
	public String getRemainingTime(OnlineRewards instance, String uuid){
		configLoader = HoconConfigurationLoader.builder().setPath(instance.getdefaultConfig()).build();
		Calendar cal = Calendar.getInstance();
		Calendar resetTime = rTime.getResetTime(instance);
		Calendar claimedToCal = cal;
		Calendar cooldown = cal;
		Calendar remain = cal;				
		String remainingTime = "Something went Wrong";
		try {
			rootNode = configLoader.load();
			int cool = rootNode.getNode("PluginSettings:", "PickupCooldown:", "Hours").getInt();
			Date playerClaimed = dFormatter.parse(instance.getOnlinePlayers().get(uuid).getLastClaimed());
			claimedToCal.setTime(playerClaimed);
			cooldown.setTime(playerClaimed);
			cooldown.add(Calendar.HOUR_OF_DAY, cool);
			Date reset = resetTime.getTime();
			Date coolTime = cooldown.getTime();
			if(playerClaimed.compareTo(reset) == -1 && coolTime.compareTo(reset) != -1){
				long millis = (resetTime.getTimeInMillis() - cooldown.getTimeInMillis()); 
				remain.setTimeInMillis(millis);
				Date remaining = remain.getTime();
				remainingTime = tFormatter.format(remaining);
			}else{
				long millis = (resetTime.getTimeInMillis() - claimedToCal.getTimeInMillis()); 
				remain.setTimeInMillis(millis);
				Date remaining = remain.getTime();
				remainingTime = tFormatter.format(remaining);
			}
			
		} catch (ParseException | IOException e) {			
			e.printStackTrace();
		}	
		
		return remainingTime;
	}
	public void loadRewards(OnlineRewards instance){
		 instance.getLogger();
		 
		 configLoader = HoconConfigurationLoader.builder().setPath(instance.getdefaultConfig()).build();
		 try{                	
     		rootNode = configLoader.load();     		
     			if(!rootNode.getNode("SetRewards:").isVirtual()){     				
     				int size = rootNode.getNode("SetRewards:").getChildrenMap().size();     				
     				for(int i = 0; i<size; i++){     					
     					int id = (i+1);     					
     					String name = rootNode.getNode("SetRewards:","Reward:"+(i+1),"Item").getValue().toString();
     					int amount = rootNode.getNode("SetRewards:","Reward:"+(i+1),"Amount").getInt();
     					int meta = rootNode.getNode("SetRewards:","Reward:"+(i+1),"MetaID").getInt();
     					int streak = rootNode.getNode("SetRewards:","Reward:"+(i+1),"Streak").getInt();     					
     					reward = new RewardObject(id, name, amount, meta, streak);
     					rewards.put(i+1, reward);
     				}
     				
     				instance.setRewards(rewards);	     				
     			}     		
     		
         }catch(IOException e){
        
         }
	}
	
	public void addReward(OnlineRewards instance, int id, String name, int amount, int meta, int streak){
		
		reward = new RewardObject(id, name, amount, meta, streak);
		rewards.put(id, reward);
		instance.setRewards(rewards);		
		
	}	

}
