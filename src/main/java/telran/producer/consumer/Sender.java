package telran.producer.consumer;

public class Sender extends Thread {
    private int nMessages;
    private MessageBox messageBoxOdd;
    private MessageBox messageBoxEven;

    public Sender(int nMessages, MessageBox messageBoxOdd, MessageBox messageBoxEven) {
        this.nMessages = nMessages;
        this.messageBoxOdd = messageBoxOdd;
        this.messageBoxEven = messageBoxEven;
    }

    @Override
    public void run() {
        for (int i = 1; i <= nMessages; i++) {
            try {
                if (i % 2 == 0) {
                    messageBoxEven.put("Message: " + (i));
                } else {
                    messageBoxOdd.put("Message: " + (i));
                }
            } catch (InterruptedException e) {

            }

        }
    }

}
