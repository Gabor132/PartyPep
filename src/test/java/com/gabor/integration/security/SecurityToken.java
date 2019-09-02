package com.gabor.integration.security;

public class SecurityToken {

    public String access_token;

    public String token_type;

    public String refresh_token;

    public String expires_in;

    public String scope;

    public String jti;

    @Override
    public String toString() {
        return "SecurityToken{" +
                "access_token='" + access_token + '\'' +
                ", token_type='" + token_type + '\'' +
                ", refresh_token='" + refresh_token + '\'' +
                ", expires_in='" + expires_in + '\'' +
                ", scope='" + scope + '\'' +
                ", jti='" + jti + '\'' +
                '}';
    }
}
