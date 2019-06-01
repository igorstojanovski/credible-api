package com.credible.configuration.security;

public class SecurityConstants {

    public static final String SECRET = "SecretKeyToGenJWTs";
    public static final long EXPIRATION_TIME = 864_000_000; // 10 days
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String AUTH_HEADER = "Authorization";
    public static final String USER_CODE_HEADER = "Code";
    public static final String SIGN_UP_URL = "/users/sign-up";
}
