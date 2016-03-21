package se.runner.user;

import se.runner.task.Task;

import javax.persistence.Entity;
import java.util.ArrayList;

@Entity
public class Runner extends User {
//    private ArrayList<Task> tasklist;

    private int test;

    public Runner(String account, String passwd, int balance) {
        super(account, passwd, balance);
    }

    public Runner() {}



    public void accept(Task task){

    }

    public void abort(Task task){

    }
}
