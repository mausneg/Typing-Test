package Controller;

import Views.Typing;

public class ThreadWord extends Thread {
    private Typing typing;
    private TypingController typingController;
    private boolean isRunning = true;

    public ThreadWord(Typing typing, TypingController typingController) {
        this.typing = typing;
        this.typingController = typingController;
    }

    @Override
    public void run() {
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
        }
        while (isRunning) {
            typingController.checkWord(typing.getJTextField1().getText());
        }
    }

    public void interrupt() {
        isRunning = false;
    }
}
