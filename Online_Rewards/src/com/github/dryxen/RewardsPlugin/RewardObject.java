package com.github.dryxen.RewardsPlugin;

public class RewardObject {
	private int rewardID;
	private String rewardName;
	private int amount;
	private int metaID;
	private boolean canRandom;
	
	public RewardObject(int id, String name, int quanity, int meta, boolean random){
		this.rewardID = id;
		this.rewardName = name;
		this.amount = quanity;
		this.metaID = meta;
		this.canRandom = random;
	}  
	
	public int getID() {
		return rewardID;
	}	
	public String getName() {
		return rewardName;
	}	
	public int getMeta() {
		return metaID;
	}	
	public boolean isRandom() {
		return canRandom;
	}
	
	public int getAmount() {
		return amount;
	}

}
