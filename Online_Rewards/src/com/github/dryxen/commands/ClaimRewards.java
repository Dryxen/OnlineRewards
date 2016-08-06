package com.github.dryxen.commands;

import org.slf4j.Logger;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.args.GenericArguments;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.command.spec.CommandSpec;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;

import com.github.dryxen.RewardsPlugin.OnlineRewards;
import com.github.dryxen.RewardsPlugin.RewardHandler;

public class ClaimRewards {
	
	private Logger logger;
	private CommandSpec commandspec;
	private RewardHandler rewardHandler = new RewardHandler();
	
	public ClaimRewards(OnlineRewards instance){		
		    logger = instance.getLogger();
	        this.commandspec = CommandSpec.builder()
	        .description(Text.of("Used to claim your Online Rewards."))
	        .permission("onlineRewards.command.claim")
	        .executor(new CommandExecutor() {	            
	            public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
	            	if(src instanceof Player){
	            		String name = src.getName();
	            		//rewardHandler.checkRewards(instance, name, );
	            		rewardHandler.loadRewards(instance);
	            		//todo finish claim logic
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
