package com.example.demo.repository;

import com.example.demo.model.User;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;

public interface Repository extends ReactiveMongoRepository<User, String> {
//@Query("select u.age from users u where u.id = :id    ") 
//Mono<Integer> findUserTweets(@Param(value = "id") String id); 
    Mono<User> findByName(String name);
    
    

}
