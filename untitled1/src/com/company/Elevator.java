package com.company;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.TimeUnit;

public class Elevator  extends Thread{
    private static int count = 0;

    enum State{
        UP,
        DOWN,
        STOP
    }

    public PriorityBlockingQueue<Integer> queue;
    int[] waiters = new int[10];
    int[] inside_waiters = new int[10];

    public int max_size = 6;
    private State current = State.STOP;
    public int size = 0;
    private int floor = 0;
    private int id;

    public Elevator() {
        this.id = count;
        count++;
        this.queue = new PriorityBlockingQueue<>(1, (floor1, floor2) -> {
            return Math.abs(floor - floor1) - Math.abs(floor - floor2);});
    }

    @Override
    public void run() {
        while (true){
            if(queue.peek() != null){
                try {
                    move();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            else {
                try {
                    stay();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void move() throws InterruptedException {
        if(floor < queue.peek()){
            current = State.UP;
            floor++;
            System.out.println("Elevator " + id + " going on " + floor + " floor");
            TimeUnit.SECONDS.sleep(5);
            stop_on_floor();

        }
        else if(floor > queue.peek()){
            current = State.DOWN;
            floor--;
            System.out.println("Elevator " + id + " going on " + floor + " floor");
            TimeUnit.SECONDS.sleep(5);
            stop_on_floor();
        }
        else {
            queue.poll();
        }
    }

    private void stay() throws InterruptedException {
        current = State.STOP;
        System.out.println("Elevator " + id + " stay on " + floor + " floor");
        TimeUnit.SECONDS.sleep(10);
    }

    public State get_state(){
        return current;
    }


    private void stop_on_floor() throws InterruptedException {
        if(inside_waiters[floor] > 0){
            go_out();
        }
        else if(waiters[floor] > 0){
            go_in();
        }
    }

    private void go_in() throws InterruptedException {
        System.out.println(waiters[floor] + " go in elevator " + id + " on " + floor + " floor ");
        size += waiters[floor];
        for(int i = 0; i < waiters[floor]; i++){
            int btn = (int) (Math.random() * 9);
            queue.offer(btn);
            System.out.println("Someone press the button on " + btn + " floor in elevator number " + id);
            inside_waiters[btn]++;
        }
        waiters[floor] = 0;
        TimeUnit.SECONDS.sleep(5);
    }

    private void go_out() throws InterruptedException {
        System.out.println(inside_waiters[floor] + " go out from elevator " + id + " on " + floor + " floor");
        size -= inside_waiters[floor];
        inside_waiters[floor] = 0;
        TimeUnit.SECONDS.sleep(5);
    }
}
