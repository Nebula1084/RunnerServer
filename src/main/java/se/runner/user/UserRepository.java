package se.runner.user;


import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by yangyuming on 16/3/20.
 */


public interface UserRepository extends JpaRepository<User, Long> {

    List<User> findByAccount(String account);

}