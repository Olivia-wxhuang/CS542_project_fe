package com.amazonaws.lambda.demo.repo;


import org.group7.wc.persistence.model.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, String> {
}

