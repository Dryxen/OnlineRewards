package com.github.dryxen.commands;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Random;
import org.slf4j.Logger;
import org.spongepowered.api.Game;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.command.spec.CommandSpec;
import org.spongepowered.api.data.DataContainer;
import org.spongepowered.api.data.DataQuery;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.item.ItemType;
import org.spongepowered.api.item.inventory.Inventory;
import org.spongepowered.api.item.inventory.ItemStack;
import org.spongepowered.api.item.inventory.Slot;
import org.spongepowered.api.text.Text;

import com.github.dryxen.RewardsPlugin.OnlineRewards;
import com.github.dryxen.RewardsPlugin.PlayerHandler;
import com.github.dryxen.RewardsPlugin.RewardHandler;
import com.github.dryxen.RewardsPlugin.RewardObject;

import ninja.leaping.configurate.ConfigurationNode;
import ninja.leaping.configurate.commented.CommentedConfigurationNode;
import ninja.leaping.configurate.hocon.HoconConfigurationLoader;
import ninja.leaping.configurate.loader.ConfigurationLoader;

public class ClaimRewards {
	
	private PlayerHandler handler = new PlayerHandler();	
	private Logger logger;
	private CommandSpec commandspec;
	private RewardObject reward; 
	private Random random = new Random();
	private ConfigurationLoader<CommentedConfigurationNode> configLoader;	
	private ConfigurationNode rootNode;	
	private RewardHandler rewardHandler = new RewardHandler();
	private HashMap<Integer, RewardObject> rewards;
	private int randomTimes = 0;
	private int randomNumber = 0;
	
	public ClaimRewards(OnlineRewards instance, Game game){		
		    logger = instance.getLogger();
	        this.commandspec = CommandSpec.builder()
	        .description(Text.of("Used to claim your Online Rewards."))
	        .permission("onlineRewards.command.claim")
	        .executor(new CommandExecutor() {	            
	            public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
	            	if(src instanceof Player){
	            		configLoader = HoconConfigurationLoader.builder().setPath(instance.getdefaultConfig()).build();
	            		String name = ((Player)src).getName();
	            		logger.info(""+rewardHandler.checkRewards(instance, name));
	            		if(rewardHandler.checkRewards(instance, name)){
	            			try{
	            				rootNode = configLoader.load();
	            				randomTimes = rootNode.getNode("PluginSettings:", "RandomAmount").getInt();
	            			}catch(IOException e){
	            				logger.error("config for randomAmount couldn't be found.");
	            			}	            			
	            			rewards = instance.getRewards();
	            			Inventory inventory = ((Player) src).getInventory();
	            			randomNumber = (random.nextInt(rewards.size())+1);            			
	            			if(inventory.size() < (randomNumber+35)){
	            			  for(int i=0; i<randomTimes; i++){              				
	            				reward = rewards.get(randomNumber);
	            				if(reward.isRandom()){	            				
	            				  ItemType type = game.getRegistry().getType(ItemType.class, reward.getName()).get();	            				 
	            				  ItemStack item = ItemStack.builder().itemType(type).quantity(reward.getAmount()).build();
	            				  DataQuery.of();
	            				  DataContainer container = item.toContainer();	            				  
	            				  
	            				  if(reward.getMeta() != 0){
	            					 container.set(DataQuery.of("UnsafeDamage"), reward.getMeta());
	            				  }	
	            				  ItemStack items = ItemStack.builder().fromContainer(container).build();
	            				  inventory.offer(items);
	            				}else{
	            					i = i-1;
	            				}
	            				Date date = Calendar.getInstance().getTime();
	            				handler.setPlayerClaimed(instance, ((Player) src).getUniqueId().toString(),
	            						date);
	            			}            			
	            		   }else{
	            			  src.sendMessage(Text.of("you need atleast "+randomNumber+" empty Slots"));
	            		   }
	            		}else{
	            			src.sendMessage(Text.of("You can't claim for"));
	            		}	            		
	            	}else{
	            		logger.info("This Command can only be used by a Player!");
	            	}
	                
	                return CommandResult.success();
	            }
	        })
	        .build();
	 }	
	
	public CommandSpec getCommandSpec(){
		return commandspec;
	}

	
	
		
	

}
