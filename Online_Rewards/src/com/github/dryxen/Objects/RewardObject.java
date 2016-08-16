package com.github.dryxen.Objects;

public class RewardObject {
	private int rewardID;
	private String rewardName;
	private int amount;
	private int metaID;
	private int streak;
	private boolean canRandom;
	
	public RewardObject(int id, String name, int quanity, int meta, int streak, boolean random){
		this.rewardID = id;
		this.rewardName = name;
		this.amount = quanity;
		this.metaID = meta;
		this.streak = streak;
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
	public int getStreak(){
		return streak;
	}
	public boolean isRandom() {
		return canRandom;
	}
	
	public int getAmount() {
		return amount;
	}

}
