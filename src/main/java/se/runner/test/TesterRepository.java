package se.runner.test;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface TesterRepository extends CrudRepository<Tester, Long> {

    List<Tester> findByLastName(String lastName);
}