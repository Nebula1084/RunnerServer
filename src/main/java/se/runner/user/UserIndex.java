package se.runner.user;
/**
 * Created by yangyuming on 16/3/18.
 */

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

@Controller
public class UserIndex {

    @RequestMapping("/user")
    public String indexPage() {
        return "index";
    }
}