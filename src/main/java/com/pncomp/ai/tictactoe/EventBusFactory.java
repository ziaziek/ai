package com.pncomp.ai.tictactoe;

import com.google.common.base.Strings;
import com.google.common.eventbus.EventBus;
import java.util.HashMap;
import java.util.Map;

public class EventBusFactory {

    private static EventBus eventBus;
    private static Map<String, EventBus> namedEventBus = new HashMap<>();

    public static EventBus getEventBus(){
        if(eventBus==null){
            eventBus=new EventBus();
        }
        return eventBus;
    }

    public static void buildEventBus(final String name){
        if(canBuildEventBus(name)){
            namedEventBus.put(name, new EventBus(name));
        } else {
            throw new IllegalStateException("There is an Event Bus with this name. ["+ name+"]");
        }
    }

    public static EventBus getEventBus(final String name){
        if(Strings.isNullOrEmpty(name)){
            return getEventBus();
        } else {
            return namedEventBus.get(name);
        }
    }

    private static boolean canBuildEventBus(final String name){
        return !namedEventBus.containsKey(name) || namedEventBus.get(name)==null;
    }

}
