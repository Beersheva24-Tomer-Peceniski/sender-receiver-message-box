package telran.producer.consumer;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Main {
    private static final int N_MESSAGES = 21;
    static final int N_RECEIVERS = 10;
    static final Long TIMEOUT = 1l;

    public static void main(String[] args) throws InterruptedException {
        MessageBox messageBoxOdd = new SimpleMessageBox();
        MessageBox messageBoxEven = new SimpleMessageBox();
        Sender sender = new Sender(N_MESSAGES, messageBoxOdd, messageBoxEven);
        ExecutorService executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

        for (int i = 1; i <= N_RECEIVERS; i++) {
            Receiver receiver;
            if(i % 2 == 0) {
                receiver = new Receiver(messageBoxEven);
            } else {
                receiver = new Receiver(messageBoxOdd);
            }
            executor.submit(receiver);
        }
        sender.start();
        sender.join();
        executor.shutdown();
        if (!executor.awaitTermination(TIMEOUT, TimeUnit.SECONDS)) {
            executor.shutdownNow();
        }
    }
}