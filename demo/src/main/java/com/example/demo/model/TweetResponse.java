package com.example.demo.model;

import com.example.demo.model.Tweet;
import com.example.demo.model.User;

public class TweetResponse {
    private Tweet tweet;
    private User user;

    public TweetResponse(Tweet tweet, User user) {
        this.tweet = tweet;
        this.user = user;
    }

    public Tweet getTweet() {
        return tweet;
    }

    public void setTweet(Tweet tweet) {
        this.tweet = tweet;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
