package se.runner.task;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Calendar;
import java.util.Random;

@Entity
public class Task {

    @Id
    private int tid;

    private String publisher;
    private String shipper;
    private String consignee;
    private String category;
    private long timestamp;
    private float pay;
    private int emergency;
    private long delivery_time;
    private long recieving_time;
    private String delivery_address;
    private String recieving_address;
    private int status;
    private int rate;
    private long gain_time;
    private long arrive_time;
    private String comment;

    static final int STATUS_PUBLISHED = 0;
    static final int STATUS_ACCEPTED = 1;
    private static final int STATUS_GAINED = 2;
    private static final int STATUS_DELIVERED = 3;
    private static final int STATUS_FINISHED = 4;

    protected Task() {}

    public Task(String publisher, String consignee, String category,
                float pay, int emergency, long delivery_time, long recieving_time,
                String delivery_address, String recieving_address) {
        Random random = new Random();
        tid = random.nextInt(1000000000);
        this.publisher = publisher;
        this.shipper = "";
        this.consignee = consignee;
        this.category = category;
        Calendar now = Calendar.getInstance();
        timestamp = now.getTimeInMillis();
        this.pay = pay;
        this.emergency = emergency;
        this.delivery_time = delivery_time;
        this.recieving_time = recieving_time;
        this.delivery_address = delivery_address;
        this.recieving_address = recieving_address;
        status = STATUS_PUBLISHED;
        rate = 0;
        gain_time = 0;
        arrive_time = 0;
        comment = "";
    }

    public void accepted(String shipper) {
        this.shipper = shipper;
        status = STATUS_ACCEPTED;
    }

    public void gainCargo(){
        status = STATUS_GAINED;
        Calendar now = Calendar.getInstance();
        gain_time = now.getTimeInMillis();
    }

    public void deliverCargo(){
        status = STATUS_DELIVERED;
        Calendar now = Calendar.getInstance();
        arrive_time = now.getTimeInMillis();
    }

    public void finish(){
        status = STATUS_FINISHED;
    }

    public void setRate(int rate){
        this.rate = rate;
    }

    public void setComment(String comment){
        this.comment = comment;
    }

    public int getTid() {return tid; }

    public String getPublisher() {return publisher; }

    public String getShipper() {return shipper; }

    public String getConsignee() {return consignee; }

    public String getCategory() {return category; }

    public long getTimestamp() {return timestamp; }

    public float getPay() {return pay; }

    public int getEmergency() {return emergency; }

    public long getDelivery_time() {return delivery_time; }

    public long getRecieving_time() {return recieving_time; }

    public String getDelivery_address() {return delivery_address; }

    public String getRecieving_address() {return recieving_address; }

    public int getStatus() {return status; }

    public int getRate(){
        return rate;
    }

    public long getGain_time() {return gain_time; }

    public long getArrive_time() {return arrive_time; }

    public String getComment(){
        return comment;
    }
}
