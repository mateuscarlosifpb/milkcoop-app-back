package com.milkcoop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.milkcoop.data.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

}
