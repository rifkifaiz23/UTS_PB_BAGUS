package com.example.githubapp_bagus.data.response;

import com.google.gson.annotations.SerializedName;

import java.util.List;
public class GithubResponse {
    @SerializedName("items")
    private List<GithubUser> users;
    public List<GithubUser> getUsers() {
        return users;
    }
}

