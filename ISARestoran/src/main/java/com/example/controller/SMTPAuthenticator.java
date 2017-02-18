package com.example.controller;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;

public class SMTPAuthenticator extends Authenticator {
	public PasswordAuthentication getPasswordAuthentication()
    {
        String username = "zlatanprecanica@gmail.com";
        String password = "zl@t@n0fk1ng";
        return new PasswordAuthentication(username, password);
    }
}
