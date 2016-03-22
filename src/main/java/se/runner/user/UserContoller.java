package se.runner.user;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.constraints.Null;
import java.util.List;

@RestController
public class UserContoller {

    @Resource
    private UserRepository repository;

    @RequestMapping("/login")
    public String login(@RequestParam(value = "account") String account, @RequestParam(value = "passwd") String passwd) {
        List<User> uList = repository.findByAccount(account);
        if (uList.isEmpty()) {
            return "account not exist.";
        } else if (uList.get(0).verifypwd(passwd)) {
            User user = uList.get(0);
            user.login();
            repository.save(user);
            return "login success";
        } else
            return "passwd incorrect";
    }

    @RequestMapping("/register")
    public Object register(@RequestParam(value = "account") String account, @RequestParam(value = "passwd") String passwd) {
        if (repository.findByAccount(account).isEmpty()) {
            User newUser = new User(account, passwd, 0);
            repository.save(newUser);
            newUser.login();
            repository.save(newUser);
            return newUser;
        } else {
            return null;
        }
    }

    @RequestMapping("/logout")
    public String logout(@RequestParam(value = "account") String account) {
        User user = repository.findByAccount(account).get(0);
        user.logout();
        repository.save(user);
        return "logout success";
    }

    @RequestMapping("/deposit")
    public String deposit(@RequestParam(value = "account") String account, @RequestParam(value = "amount") String amount) {
        User user = repository.findByAccount(account).get(0);
        user.deposit(Integer.parseInt(amount));
        repository.save(user);
        return "deposit success";
    }

//    @RequestMapping("/pay")
//    public String deposit(@RequestParam(value = "account") String account, @RequestParam(value = "amount") String amount) {
//        User user = repository.findByAccount(account).get(0);
//        user.pay(Integer.parseInt(amount));
//        repository.save(user);
//        return "deposit success";
//    }
    
}
