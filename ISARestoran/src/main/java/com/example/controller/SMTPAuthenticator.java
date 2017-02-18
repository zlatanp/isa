package com.example.controller;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;

public class SMTPAuthenticator extends Authenticator {
	public PasswordAuthentication getPasswordAuthentication()
    {
        String username = "zlatanprecanica@gmail.com";
        String password = "*";
        return new PasswordAuthentication(username, password);
    }
}
