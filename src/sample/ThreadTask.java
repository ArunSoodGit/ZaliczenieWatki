package sample;

import javafx.concurrent.Task;

public class ThreadTask extends Task {
    private static int MAX = 100; // maksymalna wartość postępu
    private static int SLEEP_TIME = 300; // czas wstrzymania wątku w ms
    private String threadName;    // nazwa wątku


    public String getName() {
        return threadName;
    }




    @Override
    protected Object call() throws Exception {
        threadName = Thread.currentThread().getName();

        for (int i=1; i <= MAX; i++) {  // pętla od 1 do 100
            updateProgress(i, MAX);   // aktualizacja postępu pracy wątku
            Thread.sleep(SLEEP_TIME); // wstrzymanie czasu pracy wątku
            
        }
        return null;
    }
}
