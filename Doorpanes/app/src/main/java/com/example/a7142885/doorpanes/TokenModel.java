package com.example.a7142885.doorpanes;

/**
 * Created by 7142885 on 3/25/2017.
 */

public class TokenModel {
    public String access_token;
    public String token_type;
    public int expires_in;
    public String userName;
    public String issued;
    public String expires;

    public String getToken() {return access_token;}

    public String getTokenType() {return token_type;}

    public int getExpiresIn() {return expires_in;}

    public String getUser() {return userName;}

    public String getIssuedTime() {return issued;}

    public String getExpiredTime() {return expires;}

    public void setToken(String access_token) {
        this.access_token = access_token;
    }

    public void setTokenType(String token_type) {
        this.token_type = token_type;
    }

    public void setExpiresIn(int expires_in) {
        this.expires_in = expires_in;
    }

    public void setUser(String userName) {
        this.userName = userName;
    }

    public void setIssuedTime(String issued) {
        this.issued = issued;
    }

    public void setExpiredTime(String expires) {
        this.expires = expires;
    }









}
