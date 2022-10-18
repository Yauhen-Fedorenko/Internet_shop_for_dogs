package com.spring.shop.repository;

import com.spring.shop.models.MyUser;
import org.springframework.data.jpa.repository.JpaRepository;



public interface UserRepository extends JpaRepository<MyUser, Long> {

    MyUser findByUsername(String username);

    MyUser findByActivationCode(String code);
}
