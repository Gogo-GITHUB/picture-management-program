package imageshowuiutil;


import java.util.Timer;
import java.util.TimerTask;

import ui.ImageShowUi;


public class AutoPlay {
   private int second;
  ImageShowUi new1;
    private final Timer timer = new Timer();

    public AutoPlay(ImageShowUi new1){
        this.new1 =new1;
        second = 2;
    }

      public void start(){
       timer.schedule(new TimerTask() {
           public void run() {//从指定时间开始，为重复的固定延迟执行安排指定的任务。 随后的执行大约定期进行，间隔指定的时间段。
                   new1.Forward();
                   new1.getJButton1().setEnabled(false);
                   new1.getJButton6().setEnabled(true);
            }
       },second * 1000 ,second * 1000);
    }
         public void stop(){
        timer.cancel();
    }
}
