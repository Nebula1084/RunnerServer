package se.runner.user;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import se.runner.test.Greeting;

@RestController
public class UserContoller {

    @RequestMapping("/login")
    public String greeting(@RequestParam(value = "account") String account, @RequestParam(value = "passwd") String passwd) {
        return passwd;
    }
}
