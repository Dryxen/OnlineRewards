package com.github.dryxen.commands;

import java.util.Optional;

import org.slf4j.Logger;
import org.spongepowered.api.Game;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.command.spec.CommandSpec;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.cause.Cause;
import org.spongepowered.api.event.cause.NamedCause;
import org.spongepowered.api.item.inventory.custom.CustomInventory;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.translation.FixedTranslation;

import com.github.dryxen.RewardsPlugin.OnlineRewards;

public class RewardGui {
	private Logger logger;
	private CommandSpec commandspec;	
	public RewardGui(OnlineRewards instance, Game game){
		logger = instance.getLogger();		
		this.commandspec = CommandSpec.builder()
		        .description(Text.of("Create a time based Reward"))
		        .permission("onlineRewards.command.create")
		        .arguments(
		        		)
		        .executor(new CommandExecutor() {	            
		            public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
		            //Waiting on implementation of custom inventories.
		            if(src instanceof Player){
		            Optional<Player> player = ((Player) src).getPlayer();
		            CustomInventory inventory = CustomInventory.builder().name(new FixedTranslation("Create Reward")).size(5).build();
		            player.get().openInventory(inventory, Cause.of(NamedCause.simulated(player)));
		            }else{
		            	logger.info("You need to be a Player to use this Command!");
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
