package com.github.dryxen.commands;

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

public class CreateReward {
	
	private Logger logger;
	private CommandSpec commandspec;
	
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
		               //todo setup default config with these params
		                return CommandResult.success();
		            }
		        })
		        .build();
		
	 }
	
	public CommandSpec getCommandSpec(){
		return commandspec;
	}

}
