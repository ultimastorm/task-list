package org.example;

import com.google.gson.Gson;
import org.example.model.TaskItem;
import org.example.model.TaskStatus;
import org.example.service.TaskItemService;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {

    public static final int PORT = 9001;


    public static void main(String[] args) {

        // Thread-pool that creates new threads as needed
        ExecutorService exSrv = Executors.newCachedThreadPool();

        try {
            // Create the Runnable class and submit it to the executor service
            CommandService server = new CommandService(PORT);
            exSrv.submit(server);
        } catch (Exception e) {
            e.printStackTrace();
        }



//        TaskItem ti = new TaskItem();
//        System.out.println(ti.getId());
//
//
//        TaskItemService ts = new TaskItemService();

//        TaskItem t = new TaskItem();
//        t.setName("second task");
//        t.setDescription("some description");
//        t.setStatus(TaskStatus.DONE);
//        System.out.println(t);
//        ts.create(t);
//        System.out.println(t);

//        TaskItem t1 = ts.find(1);
//        t1.setName(t1.getName() + " (updated)");
//        ts.update(t1);

//        ts.delete(6);
//
//        List<TaskItem> items = ts.findAll();
//        System.out.println(items);
//        System.out.println(new Gson().toJson(items));
    }
}