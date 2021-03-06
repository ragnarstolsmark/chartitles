package com.example.gameofthroneshelper;

import android.graphics.Bitmap;

public class Character {
	private String[] names;
	private String fullName;
	private Bitmap image;
	
	public Character(String name){
		names = name.split(" ");
		fullName = name;
	}
	public String getFullName(){
		return fullName;
	}
	public String getFirstName(){
		return names[0];
	}
	
	public Bitmap getImage() {
		return image;
	}
	public void setImage(Bitmap image) {
		this.image = image;
	}
	//Note must handle persons with the same last name in the same series
	public boolean LineContainsCharacterName(String[] line){
		for(int i=0;i<line.length;i++){
			if(line[i].contains(getFirstName())){
				return true;
			}
		}
		return false;
	}
}
