package com.lp.jianlai.EventTest;

/**
 * @Description:
 * @Author: liupu
 * @Date: 2019/7/18
 */
public class MyEventListener implements EventListener {

    public void onMyEvent(EventObject e){
        /*如果该类与EventObject实例处于同一个类中，可以直接使用==判断事件来源*/
        if(e.getSource() instanceof EventSource){
            /*事件来源于OtherSource时要处理的业务*/
            EventSource tempSrc=(EventSource)e.getSource();
            System.out.println("MyEventListener收到来自"+tempSrc.getActioner()+"的事件!");
        }
		/*else if(e.getSource() instanceof OtherSource){
			System.out.println("事件来源于OtherSource时要处理的业务");
		}*/
    }

}
