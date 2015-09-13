package com.dstevens.mail;

import java.io.UnsupportedEncodingException;
import java.util.concurrent.ExecutorService;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.log4j.Logger;

public class MailMessage {

	private static final Logger logger = Logger.getLogger(MailMessage.class);
	
	private final ExecutorService executorService;
	private Session session;
	private String fromAddress;
	private String fromName;
	private String toAddress;
	private String subject;
	private String body;

	public MailMessage(Session session, ExecutorService executorService) {
		this.session = session;
		this.executorService = executorService;
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
		if(session.getProperties().get("courier.enabled").equals(Boolean.TRUE)) {
			executorService.execute(() -> {
				try {
					Message msg = new MimeMessage(session);
					msg.setFrom(new InternetAddress(fromAddress, fromName));
					msg.addRecipient(Message.RecipientType.TO, new InternetAddress(toAddress));
					msg.setSubject(subject);
					msg.setText(body);
					Transport.send(msg);
				} catch (MessagingException | UnsupportedEncodingException e) {
					logger.error("Failed to send message to " + toAddress, e);
				}		
			});
		} else {
			logger.info("Courier not enabled, and thus did not send " + this);
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
