package com.pncomp.ai.tictactoe.events;

import com.google.common.base.Strings;
import com.google.common.eventbus.EventBus;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class EventBusFactory {

    private static EventBus eventBus;
    private static Map<String, EventBus> namedEventBus = new ConcurrentHashMap<>();

    public static EventBus getEventBus(){
        if(eventBus==null){
            eventBus=new EventBus();
        }
        return eventBus;
    }

    public static EventBus buildEventBus(final String name){
        if(canBuildEventBus(name)){
            EventBus bus = new EventBus(name);
            namedEventBus.put(name, bus);
            System.out.println("Registered new event bus "+ name);
            return bus;
        } else {
            throw new IllegalStateException("There is an Event Bus with this name. ["+ name+"]");
        }
    }

    public static EventBus getEventBus(final String name){
        if(Strings.isNullOrEmpty(name) || "default".equals(name)){
            return getEventBus();
        } else {
            return namedEventBus.get(name);
        }
    }

    private static boolean canBuildEventBus(final String name){
        return !namedEventBus.containsKey(name) || namedEventBus.get(name)==null;
    }

}
