package com.yong.spring.jpa.jms.repository;

import com.yong.spring.jpa.jms.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
}
