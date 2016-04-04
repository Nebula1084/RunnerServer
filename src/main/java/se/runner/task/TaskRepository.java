package se.runner.task;

import org.springframework.data.jpa.repository.JpaRepository;
import se.runner.user.User;

import java.util.List;

/**
 * Created by Sea on 3/19/2016.
 */
public interface TaskRepository extends JpaRepository<Task, Long> {
    List<Task> findByTid(int tid);
    List<Task> findByStatus(int status);
    List<Task> findByShipper(String shipper);
    List<Task> findByPublisher(String publisher);
}
