package com.github.dryxen.RewardsPlugin;

import org.spongepowered.api.Game;

import com.github.dryxen.commands.ClaimRewards;
import com.github.dryxen.commands.CreateReward;
import com.github.dryxen.commands.RewardGui;

public class RegisterCommands {
	
	public RegisterCommands(OnlineRewards instance, Game game){
		 game.getCommandManager().register(instance, new ClaimRewards(instance, game).getCommandSpec(), "claimRewards", "crs", "claimRewards");
		 game.getCommandManager().register(instance, new CreateReward(instance).getCommandSpec(), "createReward", "cr", "createReward");
		 game.getCommandManager().register(instance, new RewardGui(instance, game).getCommandSpec(), "RewardGui", "rgui", "rewardGui");
		
	}

}
