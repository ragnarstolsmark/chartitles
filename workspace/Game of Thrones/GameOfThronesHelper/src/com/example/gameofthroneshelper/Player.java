package com.example.gameofthroneshelper;

import java.io.*;
import java.util.*;

import android.net.Uri;
import android.os.*;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.res.AssetFileDescriptor;
import android.view.Menu;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.Chronometer.OnChronometerTickListener;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.VideoView;

public class Player extends Activity implements OnChronometerTickListener {
	public boolean isPlaying = false;
	public boolean firstStart = true;
	public boolean animationStarted = false;
	public boolean revAnimationStarted = false;
    public Episode _episode;
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);
		
        Chronometer crm = null;
        crm = (Chronometer)findViewById(R.id.chronometer1);
        crm.setOnChronometerTickListener(this);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.activity_player, menu);
        return true;
    }
    /** Called when the user touches the button */
    public void startTimer(View view) {
    	if(_episode == null){
    		SubtitleParser stp = new SubtitleParser(this, "Gameofthrones0301.srt");
    		stp.start();     
    	}else{
    		StartPlaying(_episode);
    	}
    }

	private long getSecondsFromStart(Chronometer crm) {
		String text = crm.getText().toString();
		String[] minsec = text.split(":");
		int min = Integer.parseInt(minsec[0]);
		int sec = Integer.parseInt(minsec[1]);
		return min*60+sec;
	}

	@Override
	public void onChronometerTick(Chronometer chronometer) {
		long seconds = getSecondsFromStart(chronometer);
		List<PlayableEvent> events = _episode.getEventsAtTime(seconds);
		if(events.isEmpty()){
			ImageView imgv = (ImageView)findViewById(R.id.imageView1);
			imgv.setVisibility(View.INVISIBLE);
			TextView tv1 = (TextView)findViewById(R.id.textView1);
			tv1.setVisibility(View.INVISIBLE);
			imgv = (ImageView)findViewById(R.id.imageView2);
			imgv.setVisibility(View.INVISIBLE);
			tv1 = (TextView)findViewById(R.id.textView2);
			tv1.setVisibility(View.INVISIBLE);
		}else{
			Iterator<PlayableEvent> itr = events.iterator();
			while(itr.hasNext()){
				PlayableEvent pe = itr.next();
				if(pe.character.getFirstName().equalsIgnoreCase("Tyrion")){
					ImageView imgv = (ImageView)findViewById(R.id.imageView1);
					imgv.setImageResource(R.drawable.tyrion);
					imgv.setVisibility(View.VISIBLE);
					TextView tv1 = (TextView)findViewById(R.id.textView1);
					tv1.setVisibility(View.VISIBLE);
					tv1.setText("Tyrion:");
				}else if(pe.character.getFirstName().equalsIgnoreCase("Arya")){
					ImageView imgv = (ImageView)findViewById(R.id.imageView2);
					imgv.setImageResource(R.drawable.arya);
					imgv.setVisibility(View.VISIBLE);
					TextView tv2 = (TextView)findViewById(R.id.textView2);
					tv2.setText("Arya:");
				}			
			}
		}
	}

	public void StartPlaying(Episode episode) {
		// TODO Auto-generated method stub
		_episode = episode;
		// Do something in response to button click
    	if(!isPlaying){
	    	Chronometer crm = null;
	        crm = (Chronometer)findViewById(R.id.chronometer1);
	        long seconds = 790;//getSecondsFromStart(crm);
	        crm.setBase(android.os.SystemClock.elapsedRealtime()-(seconds*1000));
	    	crm.start();
	    	Button button = null;
	        button = (Button)findViewById(R.id.playButton);
	        button.setText("Pause");
	    	isPlaying=true;
    	}else{
    		Chronometer crm = null;
	        crm = (Chronometer)findViewById(R.id.chronometer1);
	    	crm.stop();
	    	Button button = null;
	        button = (Button)findViewById(R.id.playButton);
	        button.setText("Play");
	    	isPlaying=false;
    	}
	}
}
