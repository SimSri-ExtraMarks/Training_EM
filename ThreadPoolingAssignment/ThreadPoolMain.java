import java.io.*;
import java.util.*;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.LinkedBlockingQueue;
import java.lang.*;

class ThreadPool{
    private int noOfThreads;
    private WorkerThread[] threads;
    private LinkedBlockingQueue queue;

    public ThreadPool(int noOfThreads){
        this.noOfThreads = noOfThreads;
        this.queue = new LinkedBlockingQueue<>();
        this.threads = new WorkerThread[noOfThreads];

        for(int i=0;i<noOfThreads;++i){
            threads[i] = new WorkerThread();
            threads[i].start();
        }
    }

    public void execute(Runnable task) {
        synchronized (queue) {
            queue.add(task);
            queue.notify();
        }
    }

    public synchronized void stop(){
        for(WorkerThread thread : threads){
            thread.interrupt();
        }
    }

    public synchronized void waitTillAllTasksAreCompleted() {
        while(this.queue.size() > 0) {
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    

    private class WorkerThread extends Thread{
        public void run() {
            Runnable task;
            while (true) {
                synchronized (queue) {
                    while (queue.isEmpty()) {
                        try {
                            queue.wait();
                        } catch (InterruptedException e) {
                            System.out.println("Thread Interupted!");
                        }
                    }
                    task = (Runnable) queue.poll();
                }
                try {
                    task.run();
                } catch (RuntimeException e) {
                    System.out.println("Thread pool is interrupted due to an issue: " + e.getMessage());
                }
            }
        }
    }

}

class Task implements Runnable {
    private int num;
    public Task(int n) {
        num = n;
    }

    public void run() {
        System.out.println("Task " + num + " is running.");
    }
}

class ThreadPoolMain{
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter the no of tasks to execute: ");
        int noOfTasks = sc.nextInt();
        System.out.println("Enter size of threadPool: ");
        int threadPoolSize = sc.nextInt();

        ThreadPool pool = new ThreadPool(threadPoolSize);

        for (int i=0; i<noOfTasks; i++) {
            Task task = new Task(i);
            pool.execute(task);
        }
        pool.waitTillAllTasksAreCompleted();
        pool.stop();
    }
}