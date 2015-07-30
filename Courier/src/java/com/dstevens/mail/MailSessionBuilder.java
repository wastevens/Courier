package com.dstevens.mail;

import java.util.Properties;
import java.util.function.Supplier;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;

public class MailSessionBuilder {

	private boolean enabled = false;
	private String smtp = "";
	private String username = "";
	private String password = "";
	private int port = -1;
	private boolean startTLS = false;
	private Class<?> socketFactoryClass;

	public MailSessionBuilder enabled(boolean enabled) {
		this.enabled = enabled;
		return this;
	}
	
	public MailSessionBuilder withSMTP(String smtp) {
		this.smtp = smtp;
		return this;
	}
	
	public MailSessionBuilder withUsername(String username) {
		this.username = username;
		return this;
	}
	
	public MailSessionBuilder withPassword(String password) {
		this.password = password;
		return this;
	}
	
	public MailSessionBuilder withPort(int port) {
		this.port = port;
		return this;
	}
	
	public MailSessionBuilder startTLS(boolean startTLS) {
		this.startTLS = startTLS;
		return this;
	}
	
	public MailSessionBuilder withSocketFactoryClass(Class<?> socketFactoryClass) {
		this.socketFactoryClass = socketFactoryClass;
		return this;
	}
	
	public Supplier<Session> build() {
		Properties properties = new Properties();
		properties.put("courier.enabled", enabled);
		properties.put("mail.smtp.auth", "true");
		properties.put("mail.smtp.starttls.enable", Boolean.toString(startTLS));
		properties.put("mail.smtp.socketFactory.class", socketFactoryClass.getName());
		properties.put("mail.smtp.host", smtp);
		properties.put("mail.smtp.port", port);
		
		Authenticator authenticator = new Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username, password);
			}
		};
		
		return new Supplier<Session>() {
			@Override
			public Session get() {
				return Session.getInstance(properties, authenticator);
			}
		};
	}
	
	
	
}
