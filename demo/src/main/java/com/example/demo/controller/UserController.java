package com.example.demo.controller;

import com.example.demo.doku.DokuService;
import com.example.demo.doku.TwitterDoku;
import com.example.demo.model.Tweet;
import com.example.demo.model.TweetResponse;
import com.example.demo.model.User;
import com.example.demo.repository.Repository;
import com.example.demo.repository.TweetsRepository;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.ResponseEntity;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Arrays;

import java.util.stream.Collectors;

// /actuator/refresh
@RefreshScope
@RestController
public class UserController {

    final Repository repository;
    final TweetsRepository tweetsRepository;
    final DokuService dokuService;
    
    
     @Value("${user.role}")
    private String role;

    @Autowired
    public UserController(Repository repository, TweetsRepository tweetsRepository, DokuService dokuService) {
        this.repository = repository;
        this.tweetsRepository = tweetsRepository;
        this.dokuService = dokuService;
    }

    @GetMapping("/up") 
    @TwitterDoku(description = "check the server is up")
    public Mono<String> test() {
        return Mono.just("Uzytkownik " + role);
    }

    @GetMapping("/all") @TwitterDoku
    public Flux<User> testAll() {
        return repository.findAll();
    }

    @DeleteMapping("/{id}") @TwitterDoku public Mono<Void> 
    deleteUser(@PathVariable("id") String id) {
        return repository.deleteById(id);

    }

    @PostMapping("/tweet/{id}") @TwitterDoku public Mono 
    addTweet(@PathVariable("id") String id,@RequestBody Tweet tweet) {
        User user = repository.findById(id).block();
        Tweet tweetE = tweetsRepository.save(tweet).block();
        if (user.getTweets() == null)
            user.setTweets(Arrays.asList(tweetE));
        else
            user.getTweets().add(tweetE);
        return repository.save(user);
    }

    @PutMapping("/follow/{id}") public Mono follow(@PathVariable("id") String id,
            @RequestParam("follow") String follow) {
        return repository.save(repository.findById(id).doOnSuccess(u -> u.getFollowing().add(follow)).block());
    }

    @DeleteMapping("/follow/{id}") public Mono unfollow(@PathVariable("id") String id,
            @RequestParam("unfollow") String follow) {
        return repository.save(repository.findById(id).doOnSuccess(u -> u.getFollowing().remove(follow)).block());
    }

    @GetMapping("/tweets/my/{id}") @TwitterDoku public Mono<ResponseEntity<?>> getUserTweets(
            @PathVariable("id") String id) {
        return repository.findById(id).map(u -> ResponseEntity.ok(u.getTweets()));

    }

    @GetMapping("/login/{login}") @TwitterDoku public Mono<?> getUserId(@PathVariable("login") String login) {

        return repository.findByName(login).map(u -> u.getId()).defaultIfEmpty(null);
    }

    @GetMapping("/following/{id}") @TwitterDoku public Flux<User> getFollowing(@PathVariable("id") String id) {
        return repository.findAllById(
                Flux.from(repository.findById(id).map(User::getFollowing).flatMapMany(Flux::fromIterable)));
    }

    @GetMapping("/tweets/{id}") @TwitterDoku public Flux<?> getTweets(@PathVariable("id") String id) {
        return repository
                .findAllById(Flux.from(repository.findById(id).map(User::getFollowing).flatMapMany(Flux::fromIterable)))
                .filter(u -> u.getTweets() != null)
                .map(u -> u.getTweets().stream().map(t -> new TweetResponse(t, u)).collect(Collectors.toList()))
                .flatMap(i -> Flux.fromIterable(i));

    }

    @GetMapping("/doku") public String getDoku(Model model) throws ClassNotFoundException {
        model.addAttribute("doku", dokuService.getDoku());
        return "doku";

    }

    @PostMapping("/new") @TwitterDoku public Mono<User> test(@RequestBody User user) {
        Mono<User> userDb = repository.insert(user);
        return userDb;
    }

}
