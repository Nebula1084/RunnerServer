package se.runner.task;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Created by scn3 on 16/3/31.
 */

@RestController
public class TaskController {

    @Resource
    private TaskRepository repository;

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private HttpServletResponse response;

    @RequestMapping(value = "/publish", method = RequestMethod.POST)
    public Task publish(@RequestParam(value = "account") String account,
                          @RequestParam(value = "consignee") String consignee,
                          @RequestParam(value = "category") String category,
                          @RequestParam(value = "pay") String pay,
                          @RequestParam(value = "delivery_time") String delivery_time,
                          @RequestParam(value = "receiving_time") String receiving_time,
                          @RequestParam(value = "delivery_address") String delivery_address,
                          @RequestParam(value = "receiving_address") String receiving_address,
                          @RequestParam(value = "emergency") String emergency) {
        Task task = new Task(account, consignee, category, Float.parseFloat(pay),
                Integer.parseInt(emergency), Long.parseLong(delivery_time),
                Long.parseLong(receiving_time), delivery_address, receiving_address);
        repository.save(task);
        return task;
    }


    @RequestMapping(value = "/gettask", method = RequestMethod.POST)
    public Task getTask(@RequestParam(value = "tid") String tid) {
        List<Task> taskList = repository.findByTid(Integer.parseInt(tid));
        if (taskList.isEmpty())
            return null;
        return taskList.get(0);
    }

    @RequestMapping(value = "/availabletask", method = RequestMethod.POST)
    public List<Task> availabletask() {
        List<Task> taskList = repository.findByStatus(Task.STATUS_PUBLISHED);
        return taskList;
    }

    @RequestMapping(value = "/accept", method = RequestMethod.POST)
    public String accept(@RequestParam(value = "account") String account, @RequestParam(value = "tid") String tid) {
        List<Task> taskList = repository.findByTid(Integer.parseInt(tid));
        if (taskList.isEmpty())
            return "task not exist.";
        Task task = taskList.get(0);
        if (task.getStatus() != Task.STATUS_PUBLISHED)
            return "task is already accpeted.";
        task.accepted(account);
        repository.save(task);
        return "success";
    }

    @RequestMapping(value = "/runnerTask", method = RequestMethod.POST)
    public List<Task> runnerTask(@RequestParam(value = "account") String account) {
        List<Task> taskList = repository.findByShipper(account);
        return taskList;
    }


    @RequestMapping(value = "/publisherTask", method = RequestMethod.POST)
    public List<Task> publisherTask(@RequestParam(value = "account") String account) {
        List<Task> taskList = repository.findByPublisher(account);
        return taskList;
    }

    @RequestMapping(value = "/gaincargo", method = RequestMethod.POST)
    public String gaincargo(@RequestParam(value = "tid") int tid) {
        List<Task> taskList = repository.findByTid(tid);
        if (taskList.isEmpty())
            return "task not exist.";
        Task task = taskList.get(0);
        task.gainCargo();
        repository.save(task);
        return "success";
    }

    @RequestMapping(value = "/delivercargo", method = RequestMethod.POST)
    public String delivercargo(@RequestParam(value = "tid") int tid) {
        List<Task> taskList = repository.findByTid(tid);
        if (taskList.isEmpty())
            return "task not exist.";
        Task task = taskList.get(0);
        task.deliverCargo();
        repository.save(task);
        return "success";
    }

    @RequestMapping(value = "/finish", method = RequestMethod.POST)
    public String finish(@RequestParam(value = "tid") int tid) {
        List<Task> taskList = repository.findByTid(tid);
        if (taskList.isEmpty())
            return "task not exist.";
        Task task = taskList.get(0);
        task.finish();
        repository.save(task);
        return "success";
    }

    @RequestMapping(value = "/rate", method = RequestMethod.POST)
    public String rate(@RequestParam(value = "tid") int tid,
                       @RequestParam(value = "rate") double rate,
                       @RequestParam(value = "comment") String comment) {
        List<Task> taskList = repository.findByTid(tid);
        if (taskList.isEmpty())
            return "task not exist.";
        Task task = taskList.get(0);
        task.setRate(rate);
        task.setComment(comment);
        repository.save(task);
        return "success";
    }

    @RequestMapping(value = "/clear", method = RequestMethod.POST)
    public String clear() {
        repository.deleteAll();
        return "success";
    }
}
