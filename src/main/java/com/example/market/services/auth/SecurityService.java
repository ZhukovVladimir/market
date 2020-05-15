package com.example.market.services.auth;

public interface SecurityService {
    String FindLoggedInUserEmail();

    void autoLogin(String email, String password);
}
