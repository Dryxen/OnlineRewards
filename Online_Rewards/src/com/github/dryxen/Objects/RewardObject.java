package com.github.dryxen.Objects;

public class RewardObject {
	private int rewardID;
	private String rewardName;
	private int amount;
	private int metaID;
	private int streak;
	
	
	public RewardObject(int id, String name, int quanity, int meta, int streak){
		this.rewardID = id;
		this.rewardName = name;
		this.amount = quanity;
		this.metaID = meta;
		this.streak = streak;		
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
	
	public int getAmount() {
		return amount;
	}

}
