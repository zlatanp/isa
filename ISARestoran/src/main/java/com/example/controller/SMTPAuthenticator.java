package com.example.controller;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;

public class SMTPAuthenticator extends Authenticator {
	public PasswordAuthentication getPasswordAuthentication()
    {
        String username = "teamdev70@gmail.com";
        String password = "restoran94";
        return new PasswordAuthentication(username, password);
    }
}
