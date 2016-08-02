package com.github.dryxen.commands;

import java.io.File;
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
		        .description(Text.of("Used to claim your Online Rewards."))
		        .permission("onlineRewards.command.claim")
		        .arguments(GenericArguments.seq(
		        		   GenericArguments.integer(Text.of("Hours")),
		        		   GenericArguments.string(Text.of("Item")),
		        		   GenericArguments.integer(Text.of("Amount"))		        		
		        		))
		        .executor(new CommandExecutor() {	            
		            public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
		              	                
		                configLoader = HoconConfigurationLoader.builder().setFile(instance.getdefaultConfig()).build();
		                int hours = args.<Integer>getOne("Hours").get();
		                String item = args.<String>getOne("Item").get();
		                int amount = args.<Integer>getOne("Amount").get();
		                try{                	
		            		rootNode = configLoader.load();	            	    
		            		rootNode.getNode("Hours:"+hours,"Item").setValue(item);
		            		rootNode.getNode("Hours:"+hours,"Amount").setValue("Amount:"+amount);
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
