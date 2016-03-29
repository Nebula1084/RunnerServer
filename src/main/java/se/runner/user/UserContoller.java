package se.runner.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.constraints.Null;
import java.io.*;
import java.util.List;

@RestController
public class UserContoller {

    @Resource
    private UserRepository repository;

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private HttpServletResponse response;

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String login(@RequestParam(value = "account") String account, @RequestParam(value = "passwd") String passwd) {
        List<User> uList = repository.findByAccount(account);
        if (uList.isEmpty()) {
            return "account not exist.";
        } else if (uList.get(0).verifypwd(passwd)) {
            User user = uList.get(0);
            user.login();
            //Session
            //HttpSession s = request.getSession();
            repository.save(user);
            return "login success";
        } else
            return "passwd incorrect";
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public User register(@RequestParam(value = "account") String account, @RequestParam(value = "passwd") String passwd) {
        if (repository.findByAccount(account).isEmpty()) {
            User newUser = new User(account, passwd);
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

    @RequestMapping("/info")
    public User info(@RequestParam(value = "account") String account) {
        User user = repository.findByAccount(account).get(0);
        return user;
    }

    @RequestMapping("/setnickname")
    public User setnickname(@RequestParam(value = "account") String account, @RequestParam(value = "nickname") String nickname) {
        User user = repository.findByAccount(account).get(0);
        user.setNickname(nickname);
        repository.save(user);
        return user;
    }

    @RequestMapping("/setaddress")
    public User info(@RequestParam(value = "account") String account, @RequestParam(value = "address") String address) {
        User user = repository.findByAccount(account).get(0);
        user.setAddress(address);
        repository.save(user);
        return user;
    }

    @RequestMapping("/setaveragerate")
    public User setaveragerate(@RequestParam(value = "account") String account, @RequestParam(value = "averagerate") String averagerate) {
        User user = repository.findByAccount(account).get(0);
        user.setAvgrate(Double.parseDouble(averagerate));
        repository.save(user);
        return user;
    }

    @RequestMapping(value = "/deposit", method = RequestMethod.POST)
    public String deposit(@RequestParam(value = "account") String account, @RequestParam(value = "amount") String amount) {
        User user = repository.findByAccount(account).get(0);
        user.deposit(Integer.parseInt(amount));
        repository.save(user);
        return "deposit success";
    }


    @RequestMapping("/geticon")
    public void geticon(@RequestParam(value = "account") String account) throws IOException {

        User user = repository.findByAccount(account).get(0);
        File file = new File("src/main/resources/icon/" + user.getIcon());

        FileInputStream inputStream = new FileInputStream(file);
        byte[] data = new byte[(int)file.length()];
        int length = inputStream.read(data);
        inputStream.close();

        response.setContentType("image/jpg");

        OutputStream stream = response.getOutputStream();
        stream.write(data);
        stream.flush();
        stream.close();

    }


    @RequestMapping(value = "/uploadicon", method = RequestMethod.POST)
    public String uploadicon(@RequestParam(value = "account") String account, @RequestParam("iconfile") MultipartFile iconfile) throws IOException {

        String filePath = "src/main/resources/icon/" + iconfile.getOriginalFilename();
        BufferedOutputStream stream =
                new BufferedOutputStream(new FileOutputStream(new File(filePath)));
        stream.write(iconfile.getBytes());
        stream.close();

        User user = repository.findByAccount(account).get(0);
        user.setIcon(iconfile.getOriginalFilename());
        repository.save(user);

        return iconfile.getOriginalFilename() + "has already upload";
    }

    @RequestMapping(value = "/pay", method = RequestMethod.POST)
    public String pay(@RequestParam(value = "account1") String account1,  @RequestParam(value = "account2") String account2, @RequestParam(value = "amount") String amount) {
        User user1 = repository.findByAccount(account1).get(0);
        user1.pay(Integer.parseInt(amount));
        repository.save(user1);

        User user2 = repository.findByAccount(account2).get(0);
        user2.deposit(Integer.parseInt(amount));
        repository.save(user2);

        return "paying";
    }

    @RequestMapping(value = "/drop", method = RequestMethod.POST)
    public String drop() {
        repository.deleteAll();
        return "Drop All";
    }
    
}
