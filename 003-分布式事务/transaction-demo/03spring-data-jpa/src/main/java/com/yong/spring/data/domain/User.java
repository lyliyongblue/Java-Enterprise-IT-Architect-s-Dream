package com.yong.spring.data.domain;

import com.yong.spring.data.repository.aggregate.TestEvent;
import org.springframework.data.domain.AfterDomainEventPublication;
import org.springframework.data.domain.DomainEvents;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Entity(name = "USER")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;
    private String firstName;
    private String lastName;
    private Integer age;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public LocalDateTime getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(LocalDateTime createdTime) {
        this.createdTime = createdTime;
    }

    public LocalDateTime getUpdatedTime() {
        return updatedTime;
    }

    public void setUpdatedTime(LocalDateTime updatedTime) {
        this.updatedTime = updatedTime;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    /** 领域驱动模式时，发送驱动事件 */
    @DomainEvents
    public Collection<Object> domainEvents() {
        return Stream.of(new TestEvent(this)).collect(Collectors.toList());
    }

    @AfterDomainEventPublication
    public void callbackMethod() {
        System.err.println("领域驱动事件处理完成， user: " + getUserId());
    }

    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", age=" + age +
                ", createdTime=" + createdTime +
                ", updatedTime=" + updatedTime +
                '}';
    }
}
