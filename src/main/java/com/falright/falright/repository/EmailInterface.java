package com.falright.falright.repository;

public interface EmailInterface {

    void sendEmail(String to, String subject, String content);
}
