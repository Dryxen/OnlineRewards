package com.github.dryxen.commands;

import java.io.IOException;

import org.slf4j.Logger;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.args.GenericArguments;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.command.spec.CommandSpec;
import org.spongepowered.api.text.Text;

import com.github.dryxen.RewardsPlugin.OnlineRewards;
import ninja.leaping.configurate.ConfigurationNode;
import ninja.leaping.configurate.commented.CommentedConfigurationNode;
import ninja.leaping.configurate.hocon.HoconConfigurationLoader;
import ninja.leaping.configurate.loader.ConfigurationLoader;

public class CreateReward {
	
	private Logger logger;
	private CommandSpec commandspec;
	private ConfigurationLoader<CommentedConfigurationNode> configLoader;	
	private ConfigurationNode rootNode;
	public CreateReward(OnlineRewards instance){
		logger = instance.getLogger();
		this.commandspec = CommandSpec.builder()
		        .description(Text.of("Create a time based Reward"))
		        .permission("onlineRewards.command.claim")
		        .arguments(GenericArguments.seq(		        		   
		        		   GenericArguments.integer(Text.of("RewardNumber")),
		        		   GenericArguments.string(Text.of("Item")),
		        		   GenericArguments.integer(Text.of("Amount")),
		        		   GenericArguments.optional(GenericArguments.onlyOne(GenericArguments.integer(Text.of("MetaID")))),
		        		   GenericArguments.bool(Text.of("RandomReward"))
		        		))
		        .executor(new CommandExecutor() {	            
		            public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
		            	
		            	configLoader = HoconConfigurationLoader.builder().setPath(instance.getdefaultConfig()).build();	                
		                int rewardNumber = args.<Integer>getOne("RewardNumber").get();		               
		                String item = args.<String>getOne("Item").get();
		                int amount = args.<Integer>getOne("Amount").get();
		                int defaultMeta = 0;
		                boolean canRandom = args.<Boolean>getOne("RandomReward").get();
		                
		                try{                	
		            		rootNode = configLoader.load();	            	    
		            		rootNode.getNode("SetRewards:","Reward:"+rewardNumber,"Item").setValue(item);
		            		rootNode.getNode("SetRewards:","Reward:"+rewardNumber,"Amount").setValue(amount);
		            		if(args.hasAny("MetaID")){
		            			int metaID = args.<Integer>getOne("MetaID").get();
		            			rootNode.getNode("SetRewards:","Reward:"+rewardNumber,"MetaID").setValue(metaID);
		            		}else{
		            			rootNode.getNode("SetRewards:","Reward:"+rewardNumber,"MetaID").setValue(defaultMeta);
		            		}
		            		rootNode.getNode("SetRewards:","Reward:"+rewardNumber,"RandomReward").setValue(canRandom);
		            		configLoader.save(rootNode);           		
		            		
		                }catch(IOException e){
		               
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
