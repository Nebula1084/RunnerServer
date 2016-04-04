package se.runner.task;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by scn3 on 16/4/4.
 */

@Controller
public class TaskIndex {
    @RequestMapping("/taskrepository")
    public String indexPage() {return "taskrepository"; }
}
