package com.github.dryxen.RewardsPlugin;

public class RewardObject {
	
	private int rewardID;
	private String rewardName;
	private int amount;
	private int metaID;
	private boolean canRandom;
	
	
	public int getID() {
		return rewardID;
	}
	public void setID(int rewardID) {
		this.rewardID = rewardID;
	}
	public String getName() {
		return rewardName;
	}
	public void setName(String rewardName) {
		this.rewardName = rewardName;
	}
	public int getMeta() {
		return metaID;
	}
	public void setMeta(int metaID) {
		this.metaID = metaID;
	}
	public boolean isRandom() {
		return canRandom;
	}
	public void setRandom(boolean canRandom) {
		this.canRandom = canRandom;
	}
	public int getAmount() {
		return amount;
	}
	public void setAmount(int amount) {
		this.amount = amount;
	}
	
	

}
