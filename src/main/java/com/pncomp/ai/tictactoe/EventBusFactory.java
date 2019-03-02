package com.pncomp.ai.tictactoe;

import com.google.common.eventbus.EventBus;

public class EventBusFactory {

    private static EventBus eventBus;

    public static EventBus getEventBus(){
        if(eventBus==null){
            eventBus=new EventBus();
        }
        return eventBus;
    }
}
