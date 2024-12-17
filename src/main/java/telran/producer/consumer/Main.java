package telran.producer.consumer;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Main {
    private static final int N_MESSAGES = 20;
    static final int N_RECEIVERS = 10;
    static final Long TIMEOUT = 1l;

    public static void main(String[] args) throws InterruptedException {
        MessageBox messageBox = new SimpleMessageBox();
        Sender sender = new Sender(N_MESSAGES, messageBox);
        ExecutorService executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

        for (int i = 0; i < N_RECEIVERS; i++) {
            Receiver receiver = new Receiver(messageBox);
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