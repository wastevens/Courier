package com.dstevens.mail;

import java.io.UnsupportedEncodingException;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class MailMessage {

	private Session session;
	private String fromAddress;
	private String fromName;
	private String toAddress;
	private String subject;
	private String body;

	public MailMessage(Session session) {
		this.session = session;
	}
	
	public MailMessage from(String fromAddress, String fromName) {
		this.fromAddress = fromAddress;
		this.fromName = fromName;
		return this;
	}
	
	public MailMessage to(String toAddress) {
		this.toAddress = toAddress;
		return this;
	}
	
	public MailMessage subject(String subject) {
		this.subject = subject;
		return this;
	}
	
	public MailMessage body(String body) {
		this.body = body;
		return this;
	}
	
	public void send() {
        try {
        	if(session.getProperties().get("courier.enabled").equals(Boolean.TRUE)) {
        		Message msg = new MimeMessage(session);
        		msg.setFrom(new InternetAddress(fromAddress, fromName));
        		msg.addRecipient(Message.RecipientType.TO, new InternetAddress(toAddress));
        		msg.setSubject(subject);
        		msg.setText(body);
        		Transport.send(msg);
        	} else {
        		System.out.println("Email not enabled");
        	}
        } catch (MessagingException | UnsupportedEncodingException e) {
        	throw new IllegalStateException("Failed to send message to " + toAddress, e);
        }		
	}
	
	@Override
	public boolean equals(Object that) {
		return EqualsBuilder.reflectionEquals(this, that);
	}
	
	@Override
	public int hashCode() {
		return HashCodeBuilder.reflectionHashCode(this);
	}
	
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}
