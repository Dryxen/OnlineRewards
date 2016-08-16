package com.github.dryxen.Objects;

public class PlayerObject {	
	
	private String uuid;
	private String lastClaimed;
	private int streak;
	
	public PlayerObject(String uuid, String lastClaimed, int streak){		
		this.uuid = uuid;
		this.lastClaimed = lastClaimed;
		this.streak = streak;
	}
	
	public String getUUID(){
		return uuid;
	}
	
	public void setLastClaimed(String time){
		this.lastClaimed = time; 
	}	
	public String getLastClaimed(){
		return lastClaimed;
	}
	
	public int getStreak(){
		return streak;
	}

}
