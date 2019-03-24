package com.amazonaws.lambda.demo.repo;

import org.group7.wc.persistence.model.Car;
import org.springframework.data.repository.CrudRepository;

public interface CarRepository extends CrudRepository<Car, String> {
}
