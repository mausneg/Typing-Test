package Controller;

import Views.Typing;

public class ThreadCountdown extends Thread {
    private int time;
    private Typing typing;
    private boolean isRunning = true;
    private TypingController typingController;

    public ThreadCountdown(int time, Typing typing, TypingController typingController) {
        this.time = time;
        this.typing = typing;
        this.typingController = typingController;
    }

    @Override
    public void run() {
        while (time >= 0 && isRunning) {
            if (time < 10) {
                typing.setJLabel1("00:0" + String.valueOf(time));
            } else
                typing.setJLabel1("00:" + String.valueOf(time));
            typingController.checkWord(typing.getJTextField1().getText());
            try {
                ThreadCountdown.sleep(1000);
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
