package com.yong.spring.data.jpa.repository;

import com.yong.spring.data.jpa.domain.User;
import com.yong.spring.data.jpa.repository.custom.CustomizedUserRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long>, CustomizedUserRepository {
    List<User> queryByCreatedTimeBetween(LocalDateTime start, LocalDateTime end, Pageable pageable);

    long countByAgeBetweenAndFirstName(Integer start, Integer end, String firstName);

    long removeByFirstName(String firstName);
}
