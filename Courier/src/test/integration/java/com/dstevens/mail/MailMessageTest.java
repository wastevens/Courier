package com.dstevens.mail;

import java.util.function.Supplier;

import javax.mail.Session;
import javax.net.ssl.SSLSocketFactory;

import org.junit.Test;

import com.dstevens.mail.MailMessage;
import com.dstevens.mail.MailSessionBuilder;


public class MailMessageTest {

	@Test
	public void testSendMailMessageViaGMail() {
		Supplier<Session> supplier = 
				new MailSessionBuilder().withSMTP("smtp.gmail.com").
								 withPort(465).
								 withSocketFactoryClass(SSLSocketFactory.class).
								 withUsername("").
								 withPassword("").
								 build();
		MailMessage message = new MailMessage(supplier.get());
		
		message.from("int-test@address.com", "Integration Test From name");
		message.to("wastevens@gmail.com");
		message.subject("Integration test email subject");
		message.body("Integration test email body");
		message.send();
	}
	
}
