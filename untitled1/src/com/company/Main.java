package com.company;


public class Main {
    public static void main(String[] args) {
        Manager manager = new Manager(2);
        Thread manager_thread = new Thread(manager,"Manager");
        Generator generator = new Generator();
        Thread gen_thread = new Thread(generator);
        manager_thread.start();
        gen_thread.start();
    }
}
