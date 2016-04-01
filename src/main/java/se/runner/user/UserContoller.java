package se.runner.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.jws.soap.SOAPBinding;
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





    private boolean checkUser(String account){
        List<User> uList = repository.findByAccount(account);
        if (uList.isEmpty()) {
            return false;
        } else {
            return true;
        }
    }


    @RequestMapping(value = "/checkuser", method = RequestMethod.POST)
    public Object checkaccount(@RequestParam(value = "account") String account) {
        if (!checkUser(account)) return "no";
        else return "yes";
    }

    private User getUser(String account){
        return repository.findByAccount(account).get(0);
    }



    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public Object login(@RequestParam(value = "account") String account, @RequestParam(value = "passwd") String passwd) {
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
    public Object register(@RequestParam(value = "account") String account, @RequestParam(value = "passwd") String passwd) {
        if (repository.findByAccount(account).isEmpty()) {
            User newUser = new User(account, passwd);
            repository.save(newUser);
            newUser.login();
            repository.save(newUser);
            return newUser;
        } else {
            return "already exist";
        }
    }

    @RequestMapping(value = "/logout", method = RequestMethod.POST)
    public Object logout(@RequestParam(value = "account") String account) {
        if (!checkUser(account)) return "not exist";
        User user = getUser(account);
        user.logout();
        repository.save(user);
        return "logout success";
    }

    @RequestMapping(value = "/info", method = RequestMethod.POST)
    public Object info(@RequestParam(value = "account") String account) {
        if (!checkUser(account)) return "not exist";
        else return getUser(account);
    }

    @RequestMapping(value = "/setpasswd", method = RequestMethod.POST)
    public Object setpasswd(@RequestParam(value = "account") String account, @RequestParam(value = "passwd") String passwd) {
        if (!checkUser(account)) return "not exist";
        User user = getUser(account);
        user.setPasswd(passwd);
        repository.save(user);
        return user;
    }

    @RequestMapping(value = "/setnickname", method = RequestMethod.POST)
    public Object setnickname(@RequestParam(value = "account") String account, @RequestParam(value = "nickname") String nickname) {
        if (!checkUser(account)) return "not exist";
        User user = getUser(account);
        user.setNickname(nickname);
        repository.save(user);
        return user;
    }

    @RequestMapping(value = "/setaddress", method = RequestMethod.POST)
    public Object info(@RequestParam(value = "account") String account, @RequestParam(value = "address") String address) {
        if (!checkUser(account)) return "not exist";
        User user = getUser(account);
        user.setAddress(address);
        repository.save(user);
        return user;
    }

    @RequestMapping(value = "/setaveragerate", method = RequestMethod.POST)
    public Object setaveragerate(@RequestParam(value = "account") String account, @RequestParam(value = "averagerate") String averagerate) {
        if (!checkUser(account)) return "not exist";
        User user = getUser(account);
        user.setAvgrate(Double.parseDouble(averagerate));
        repository.save(user);
        return user;
    }

    @RequestMapping(value = "/deposit", method = RequestMethod.POST)
    public Object deposit(@RequestParam(value = "account") String account, @RequestParam(value = "amount") String amount) {
        if (!checkUser(account)) return "not exist";
        User user = getUser(account);
        user.deposit(Double.parseDouble(amount));
        repository.save(user);
        return user;
    }


    @RequestMapping(value = "/geticon", method = RequestMethod.POST)
    public void geticon(@RequestParam(value = "account") String account) throws IOException {
        User user = getUser(account);

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
    public Object uploadicon(@RequestParam(value = "account") String account, @RequestParam("iconfile") MultipartFile iconfile) throws IOException {

        String filePath = "src/main/resources/icon/" + iconfile.getOriginalFilename();
        BufferedOutputStream stream =
                new BufferedOutputStream(new FileOutputStream(new File(filePath)));
        stream.write(iconfile.getBytes());
        stream.close();

        User user = getUser(account);
        user.setIcon(iconfile.getOriginalFilename());
        repository.save(user);

        return iconfile.getOriginalFilename() + "has already upload";
    }

    @RequestMapping(value = "/pay", method = RequestMethod.POST)
    public Object pay(@RequestParam(value = "account1") String account1,  @RequestParam(value = "account2") String account2, @RequestParam(value = "amount") String amount) {
        User user1 = repository.findByAccount(account1).get(0);
        user1.pay(Double.parseDouble(amount));
        repository.save(user1);

        User user2 = repository.findByAccount(account2).get(0);
        user2.deposit(Double.parseDouble(amount));
        repository.save(user2);

        return user1;
    }

    @RequestMapping(value = "/drop", method = RequestMethod.POST)
    public Object drop() {
        repository.deleteAll();
        return "Drop All";
    }

    @RequestMapping(value = "/setlatitude", method = RequestMethod.POST)
    public Object setlatitude(@RequestParam(value = "account") String account, @RequestParam(value = "latitude") String latitude) {
        if (!checkUser(account)) return "not exist";
        User user = getUser(account);
        user.setLatitude(Double.parseDouble(latitude));
        repository.save(user);
        return user;
    }


    @RequestMapping(value = "/setlongtitude", method = RequestMethod.POST)
    public Object setlongtitude(@RequestParam(value = "account") String account, @RequestParam(value = "longtitude") String longtitude) {
        if (!checkUser(account)) return "not exist";
        User user = getUser(account);
        user.setLongtitude(Double.parseDouble(longtitude));
        repository.save(user);
        return user;
    }

    @RequestMapping(value = "/setlaunchtasknum", method = RequestMethod.POST)
    public Object setlauchtasknum(@RequestParam(value = "account") String account, @RequestParam(value = "launchtasknum") String launchtasknum) {
        if (!checkUser(account)) return "not exist";
        User user = getUser(account);
        user.setLaunchTaskNum(Integer.parseInt(launchtasknum));
        repository.save(user);
        return user;
    }


    @RequestMapping(value = "/settaketasknum", method = RequestMethod.POST)
    public Object settaketasknum(@RequestParam(value = "account") String account, @RequestParam(value = "taketasknum") String taketasknum) {
        if (!checkUser(account)) return "not exist";
        User user = getUser(account);
        user.setTakeTaskNum(Integer.parseInt(taketasknum));
        repository.save(user);
        return user;
    }
}
