package com.example.gameofthroneshelper;

public class PlayableEvent {
	public Character character;
	public int secondsFromStart;
	public PlayableEvent(Character character, int secondsFromStart) {
		super();
		this.character = character;
		this.secondsFromStart = secondsFromStart;
	}
	
}
