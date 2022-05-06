package com.company;
import java.util.LinkedList;
import java.util.concurrent.LinkedBlockingQueue;

public class Manager implements Runnable{
    static LinkedBlockingQueue<Integer> floorqueue = new LinkedBlockingQueue<>();
    private LinkedList<Elevator> elevators = new LinkedList<>();
    private int num_of_elev = 0;


    public Manager(int num){
        this.num_of_elev = num;
    }

    @Override
    public void run() {
        start_work(num_of_elev);
        while (true) {
            for (Elevator it : elevators) {
                if(!floorqueue.isEmpty()) {
                    if (it.get_state() == Elevator.State.STOP) {
                        it.queue.offer(floorqueue.peek());
                        it.waiters[floorqueue.poll()] += (int) (Math.random() * 2) + 1;
                    } else if (it.get_state() == Elevator.State.UP && (it.size + it.waiters[floorqueue.peek()] + 1) < it.max_size) {
                        it.queue.offer(floorqueue.peek());
                        it.waiters[floorqueue.poll()] += (int) (Math.random() * 2) + 1;
                    } else if (it.get_state() == Elevator.State.DOWN && (it.size + it.waiters[floorqueue.peek()] + 1) < it.max_size) {
                        it.queue.offer(floorqueue.peek());
                        it.waiters[floorqueue.poll()] += (int) (Math.random() * 2) + 1;
                    }
                }
            }
        }
    }

    public void start_work(int num){
        for(int i = 0; i < num; i++){
            elevators.add(new Elevator());
            elevators.get(i).start();
        }
    }
}

