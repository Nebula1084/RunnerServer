package se.runner.user;


import javax.annotation.Resource;
import javax.persistence.Entity;
import javax.persistence.Id;
@Entity

public class User {

    @Id
    private String account;
    private String passwd;
    private int balance;
    private int login;

    protected User() {
    }

    public User(String account, String passwd, int balance) {
        this.account = account;
        this.passwd = passwd;
        this.balance = balance;
        this.login = 1;
    }

    public boolean verifypwd(String passwd) {
        return this.passwd.equals(passwd);
    }

    public void login() {
        login = 1;
    }

    public void logout() {
        login = 0;
    }

    public boolean pay(int amount) {
        if (balance < amount)
            return false;
        else {
            balance -= amount;
            return true;
        }
    }

    public void deposit(int m) {
        balance += m;
    }

    public int getBalance() {
        return balance;
    }

    @Override
    public String toString() {
        return String.format(
                "User[account=%s, passwd='%s', balance='%d']",
                account, passwd, balance);
    }
}
