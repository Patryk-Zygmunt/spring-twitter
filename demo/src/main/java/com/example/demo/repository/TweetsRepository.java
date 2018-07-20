package com.example.demo.repository;

import com.example.demo.model.Tweet;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface TweetsRepository extends ReactiveMongoRepository<Tweet, String> {
    

}
