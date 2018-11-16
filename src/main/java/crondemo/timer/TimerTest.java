package crondemo.timer;

import java.util.Timer;
import java.util.TimerTask;

public class TimerTest  extends TimerTask {
    private String jobName="";
    public long start = System.currentTimeMillis();
    private Timer timer = new Timer();

    public TimerTest(String jobName) {
        super();
        this.jobName = jobName;
    }

    public void run() {
        System.out.println("running "+jobName);
    }

    public void timerOne() {
        timer.schedule(new TimerTask() {
            public void run() {
                System.out.println("timerOne invoked ,the time:" + (System.currentTimeMillis() - start));
                try {
                    Thread.sleep(5000);    //线程休眠4s
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("end");
            }

        }, 1000);
    }
    int i =3;
    int j =20;
    public void timerTwo() {
        timer.schedule(new TimerTask() {
            public void run() {
                System.out.println("timerTwo invoked ,the time:" + (System.currentTimeMillis() - start));
                int k=j/i;
                --i;
            }
        }, 3000,1000);
    }

    public void timerT() {
        timer.schedule(new TimerTask() {
            public void run() {
                System.out.println("timerT invoked ,the time:" + (System.currentTimeMillis() - start));
            }
        }, 4000);
    }
    public static void main(String[] args) {
        Timer timer = new Timer();
        long delay1 = 1*1000;
        long period = 1000;
       // timer.schedule(new TimerTest("man1"),period,delay1);
        long delay2 = 2*1000;
      //  timer.schedule(new TimerTest("man2"),period,delay2);

        new TimerTest("0").timerOne();
        new TimerTest("1").timerTwo();
        new TimerTest("2").timerT();



    }
}
