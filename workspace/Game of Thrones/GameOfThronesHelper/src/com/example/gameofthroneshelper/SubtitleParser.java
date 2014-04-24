package com.example.gameofthroneshelper;

import java.io.BufferedReader;
import java.util.*;
import java.io.IOException;
import java.io.InputStreamReader;

import android.app.Activity;
import android.os.AsyncTask;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class SubtitleParser extends Thread{
	
	private Player _activity;
	private String _filename;
	public SubtitleParser(Player activity, String filename){
		_activity = activity;
		_filename = filename;
	}
	@Override
    public void run()
    {
		//parse textfil lag characters og lag timer med meir n�yaktig tickers...
		try {
			long pre = System.currentTimeMillis();
			StringBuilder text = new StringBuilder();  
			BufferedReader br = new BufferedReader(new InputStreamReader(_activity.getAssets().open(_filename)));
	        String line, timeLine, textLine;
	        int linenumber = 0;
	        List<Character> cast = new ArrayList<Character>();//cast b�r vere p� episode
	        Episode episode = new Episode();
	        Character tyrion = new Character("Tyrion Lannister");
	        Character arya = new Character("Arya Stark");
	        cast.add(tyrion);
	        cast.add(arya);
	        line = br.readLine();
	        while (line != null) {// denne blir feil n�r linen allered er lest i indre l�kke
	        	try{
	        		linenumber = Integer.parseInt(line);
	        		timeLine = br.readLine();
	        		int secondsFromStart = parseTimeLine(timeLine);
	        		textLine = br.readLine();
	        		while(textLine != null && !textLine.isEmpty()){
	        			String[] textLineSplit = textLine.split(" ");
	        			Iterator<Character> itr = cast.iterator();
	        			while(itr.hasNext()){
	        				Character ctr = itr.next();
	        				if(ctr.LineContainsCharacterName(textLineSplit)){
	        					episode.AddEvent(new PlayableEvent(ctr, secondsFromStart));
	        				}
	        			}
	        			textLine = br.readLine();
	        		}
	        		line = br.readLine();	        		
	        	}catch(Exception e){
	        		//something whent wrong

	        		e.printStackTrace();
	        	}
	        }
	        long post = System.currentTimeMillis();
	        long res = post - pre;
	        publishProgress(episode);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    
    }
	private int parseTimeLine(String timeLine) {
		String[] words = timeLine.split(" ");
		String startTime = words[0];
		String[] splitStart = startTime.split(":");
		int hours = Integer.parseInt(splitStart[0]);
		int minutes = Integer.parseInt(splitStart[1]);
		String[] splitSeconds = splitStart[2].split(",");
		int seconds = Integer.parseInt(splitSeconds[0]);
		return hours * 3600 + minutes * 60 + seconds;
	}
	private void publishProgress(final Episode episode){
		 if(this.isInterrupted()){
             return;
         }
         _activity.runOnUiThread(new Runnable(){

             @Override
             public void run()
             {
            	_activity.StartPlaying(episode);     
             }
         });
	}
}
