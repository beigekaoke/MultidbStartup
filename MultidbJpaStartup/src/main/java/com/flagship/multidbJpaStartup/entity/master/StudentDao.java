package com.flagship.multidbJpaStartup.entity.master;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentDao extends JpaRepository<Student, Integer> {

}
