package com.example.gameofthroneshelper;

import java.util.*;

public class Episode {
	public List<PlayableEvent> events;
	public Episode(){
		events = new LinkedList<PlayableEvent>();
	}
	public void AddEvent(PlayableEvent event){
		events.add(event);
	}
	public List<PlayableEvent> getEventsAtTime(long seconds) {
		// TODO Auto-generated method stub
		List<PlayableEvent> ret = new ArrayList<PlayableEvent>();
		Iterator<PlayableEvent> itr = events.iterator();
		while(itr.hasNext()){
			PlayableEvent pe = itr.next();
			if(pe.secondsFromStart <= seconds && (pe.secondsFromStart >= (seconds - 10))){
				ret.add(pe);
			}
		}
		return ret;
	}
}
