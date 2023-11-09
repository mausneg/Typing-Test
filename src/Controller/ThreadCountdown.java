package Controller;

import Views.Typing;

public class ThreadCountdown extends Thread {
    private int time;
    private Typing typing;
    private boolean isRunning = true;

    public ThreadCountdown(int time, Typing typing) {
        this.time = time;
        this.typing = typing;
    }

    @Override
    public void run() {
        while (time > 0 && isRunning) {
            typing.setJLabel1("00:" + String.valueOf(time));
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            time--;
        }

    }

    public void interrupt() {
        isRunning = false;
        typing.setJLabel1("01:00");
    }
}
