package com.demo.springsecurity.domain;

public class ResponseCookieDTO {

    public String username;
    public String session_id;

    public ResponseCookieDTO(String username, String session_id) {
        this.username = username;
        this.session_id = session_id;
    }
}