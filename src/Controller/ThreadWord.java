package Controller;

import Views.Typing;

public class ThreadWord extends Thread {
    private Typing typing;
    private TypingController typingController;

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
        while (true) {
            typingController.checkWord(typing.getJTextField1().getText());
        }
    }

    public void interrupt() {
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
        }
    }
}
