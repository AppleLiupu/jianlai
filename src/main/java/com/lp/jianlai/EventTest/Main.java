package com.lp.jianlai.EventTest;

/**
 * @Description:
 * @Author: liupu
 * @Date: 2019/7/18
 */
public class Main{
    public static void main(String[] args){

        EventListener listener = new MyEventListener();
        EventSource event = new EventSource("小白");
        event.addMyEventListener(new EventListener() {
            @Override
            public void onMyEvent(EventObject e) {
                System.out.println("hhuu");
            }
        });
        event.addMyEventListener(listener);
        event.say("今天天气不错");
    }
}
