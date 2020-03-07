package sample;

import javafx.concurrent.Service;
import javafx.concurrent.Task;

public class ThreadService extends Service<Void> {

    private ThreadTask task;

    public String getName() {
        return "[Service] " + task.getName();
    }

    @Override
    protected Task createTask() {
        task = new ThreadTask();
        return task;
    }

}
