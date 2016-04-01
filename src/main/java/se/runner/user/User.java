package se.runner.user;


import javax.annotation.Nullable;
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
    private double balance;
    private String address;
    private int login;

    private double longtitude;
    private double latitude;

    private int launchTaskNum;
    private int takeTaskNum;

    private double avgrate;

    protected User() {
    }

    public User(String account, String passwd) {
        this.account = account;
        this.passwd = passwd;
        this.nickname = "null";
        this.icon = "default.jpg";
        this.balance = 0.0;
        this.address = "null";
        this.login = 0;
        this.avgrate = 1.0;
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

    public boolean pay(double amount) {
        if (balance < amount)
            return false;
        else {
            balance -= amount;
            return true;
        }
    }

    public void deposit(double m) {
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

    public double getBalance() {
        return balance;
    }

    public double getAvgrate() {
        return avgrate;
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


    public void setLongtitude(double longtitude) {
        this.longtitude = longtitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public void setLaunchTaskNum(int launchTaskNum) {
        this.launchTaskNum = launchTaskNum;
    }

    public void setTakeTaskNum(int takeTaskNum) {
        this.takeTaskNum = takeTaskNum;
    }

    public int getLaunchTaskNum() {
        return launchTaskNum;
    }

    public int getTakeTaskNum() {
        return takeTaskNum;
    }

    public double getLongtitude() {
        return longtitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setAddress(String address) {
        this.address = address;
    }


    public int getLogin() {
        return login;
    }

    public void setAvgrate(double avgrate) {
        this.avgrate = avgrate;
    }


}
