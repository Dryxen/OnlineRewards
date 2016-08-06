package com.github.dryxen.RewardsPlugin;

import java.io.IOException;

import ninja.leaping.configurate.ConfigurationNode;
import ninja.leaping.configurate.commented.CommentedConfigurationNode;
import ninja.leaping.configurate.hocon.HoconConfigurationLoader;
import ninja.leaping.configurate.loader.ConfigurationLoader;

public class SetDefaultConfig {
	
	private ConfigurationLoader<CommentedConfigurationNode> configLoader;	
	private ConfigurationNode rootNode;
	
	public void configCheck(OnlineRewards instance){
		
		configLoader = HoconConfigurationLoader.builder().setPath(instance.getdefaultConfig()).build();		                
      	if(!instance.getdefaultConfig().toFile().exists()){      	
      	  try{
      		  rootNode = configLoader.load();
      		  rootNode.getNode("PluginSettings:","ResetInterval").setValue("days:1");
      		  rootNode.getNode("PluginSettings:","Reset:","Month").setValue(0);
      		  rootNode.getNode("PluginSettings:","Reset:","Day").setValue("default");
      		  rootNode.getNode("PluginSettings:","Reset:","Hour").setValue(0);
      		  rootNode.getNode("PluginSettings:","Reset:","Minute").setValue(0);
      		  rootNode.getNode("PluginSettings:","PickupCooldown:","Hours").setValue(4);
      		  rootNode.getNode("PluginSettings:","RandomAmount").setValue(1);
      		  configLoader.save(rootNode);
      	  }catch(IOException e){
      	       
      	  }
      			 
        }
		
	}

}
