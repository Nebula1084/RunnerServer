package se.runner.user;


import javax.annotation.Resource;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class User {

    @Id
    private String account;
    private String passwd;
    private String nickname;
    private String icon;
    private int balance;
    private String address;
    private float averagerate;
    private int login;


    protected User() {
    }

    public User(String account, String passwd) {
        this.account = account;
        this.passwd = passwd;
        this.nickname = "null";
        this.icon = "default.jpg";
        this.balance = 0;
        this.address = "null";
        this.login = 0;
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

    public String getAccount() {
        return account;
    }

    public String getPasswd() {
        return passwd;
    }

    public String getNickname() {
        return nickname;
    }

    public String getAddress() {
        return address;
    }

    public String getIcon() {
        return icon;
    }

    public int getBalance() {
        return balance;
    }

    public float getAveragerate() {
        return averagerate;
    }

    public void setPasswd(String passwd) {
        this.passwd = passwd;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setAveragerate(float averagerate){
        this.averagerate = averagerate;
    }

    @Override
    public String toString() {
        return String.format(
                "User[account=%s, passwd='%s', balance='%d']",
                account, passwd, balance);
    }
}
