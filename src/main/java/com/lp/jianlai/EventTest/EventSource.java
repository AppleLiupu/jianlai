package com.lp.jianlai.EventTest;

import java.util.Vector;

/**
 * @Description:
 * @Author: liupu
 * @Date: 2019/7/18
 */
public class EventSource {

    private String who;

    Vector listeners = new Vector();

    public EventSource(String who) {
        this.who = who;
    }

    public String getActioner() {
        return who;
    }

    public void addMyEventListener(EventListener listener) {
        listeners.add(listener);
    }

    /*设定say方法能被MyEventListener对象监听到*/
    public void say(String words) {
        System.out.println(this.getActioner() + "说：" + words);
        for (int i = 0; i < listeners.size(); i++) {
            EventListener listener = (EventListener) listeners.elementAt(i);
            /*发布事件。当然应该事先规划say方法事件能发布给哪些事件监听器。*/
            listener.onMyEvent(new EventClassOne(this));
        }
    }
}
