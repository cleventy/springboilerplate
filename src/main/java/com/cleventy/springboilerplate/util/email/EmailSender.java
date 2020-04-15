package com.cleventy.springboilerplate.util.email;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashSet;
import java.util.Locale;
import java.util.Properties;
import java.util.Set;

import javax.activation.DataHandler;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.util.ByteArrayDataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import com.cleventy.springboilerplate.util.date.DateUtil;

import lombok.extern.slf4j.Slf4j;


@Component
@Slf4j
public class EmailSender {
	
	@Value("${spring.mail.from}")
	private String from;
	@Value("${spring.mail.fromname}")
	private String fromName;
	@Value("${spring.mail.username}")
	private String username;
	@Value("${spring.mail.password}")
	private String password;
	@Value("${spring.mail.host}")
	private String host;
	@Value("${spring.mail.port}")
	private int port;
	@Value("${mail.enabled}")
	private Boolean mailingEnabled;
	
	@Autowired
	private TemplateEngine templateEngine;
	
	@Autowired
	private MessageSource messageSource;

    public void sendWelcomeEmail(String to, String usernameParam, Calendar registrationDate, Locale locale) throws IOException, MessagingException {
    	final Context ctx = new Context(locale);
    	ctx.setVariable("username", usernameParam);
    	ctx.setVariable("registerDate", registrationDate.getTime());
    	String subject = this.messageSource.getMessage("email.welcome.subject", null, locale);
    	final String htmlContent = this.templateEngine.process("emails/welcome.html", ctx);
    	this.sendTemplateEmail(to, null, null, subject, htmlContent, null);
    }
    public void sendEmail(String to, Set<String> ccs, Set<String> ccos, String subject, String content, Set<FileAttachment> attachments) throws IOException, MessagingException {
    	this.sendEmailIfEnabled(to, ccs, ccos, subject, content, attachments);
    }
    	
    private void sendTemplateEmail(String to, Set<String> ccs, Set<String> ccos, String subject, String content, Set<FileAttachment> attachments) throws IOException, MessagingException {
    	this.sendEmailIfEnabled(to, ccs, ccos, subject, content, attachments);
    }
    private void sendEmailIfEnabled(String to, Set<String> ccs, Set<String> ccos, String subject, String content, Set<FileAttachment> attachments) throws IOException, MessagingException {
    	if (!this.mailingEnabled) {
    		logEmail(to, ccs, ccos, subject, content, attachments);
    	} else {
    		this.sendEmailAux(to, ccs, ccos, subject, content, attachments);
    	}
    }
    private static void logEmail(String to, Set<String> ccs, Set<String> ccos, String subject, String content, Set<FileAttachment> attachments) throws IOException, MessagingException {
		StringBuffer mailLogSB = new StringBuffer("Fake mail");
		mailLogSB.append("\n\t" + "subject" + ": '" + subject + "'");
		mailLogSB.append("\n\t" + "to" + ": '" + to + "'");
		mailLogSB.append("\n\t" + "ccs" + ": '" + ccs + "'");
		mailLogSB.append("\n\t" + "ccos" + ": '" + ccos + "'");
		mailLogSB.append("\n\t" + "content" + ": '" + content + "'");
        if (attachments!=null) {
        	for (FileAttachment fileAttachment : attachments) {
        		mailLogSB.append("\n\t" + "attachment" + ": '" + fileAttachment.getName() + "'");
        	}
        }
        log.debug(mailLogSB.toString());
    }
    
    // based on https://docs.aws.amazon.com/ses/latest/DeveloperGuide/send-using-smtp-java.html
    private void sendEmailAux(String to, Set<String> ccs, Set<String> ccos, String subject, String content, Set<FileAttachment> attachments) throws IOException, MessagingException {
        // Create a Properties object to contain connection configuration information.
    	Properties props = System.getProperties();
    	props.put("mail.transport.protocol", "smtp");
    	props.put("mail.smtp.port", this.port); 
    	props.put("mail.smtp.starttls.enable", "true");
    	props.put("mail.smtp.auth", "true");

        // Create a Session object to represent a mail session with the specified properties. 
    	Session session = Session.getDefaultInstance(props);

        // Create a message with the specified information. 
        MimeMessage message = new MimeMessage(session);
        message.setFrom(new InternetAddress(this.from, this.fromName));
        message.setRecipient(Message.RecipientType.TO, new InternetAddress(to));
        if (ccs!=null) {
	        for (String cc:ccs) {
		        message.addRecipient(Message.RecipientType.CC, new InternetAddress(cc));
	        }
        }
        if (ccos!=null) {
	        for (String cco:ccos) {
		        message.addRecipient(Message.RecipientType.BCC, new InternetAddress(cco));
	        }
        }
        message.setSubject(subject);
        
        
        MimeMultipart multipart = new MimeMultipart("related");
		
        BodyPart messageBodyPart = new MimeBodyPart();
        messageBodyPart.setContent(content, "text/html; charset=UTF-8");
        multipart.addBodyPart(messageBodyPart, 0);
        
        if (attachments!=null) {
        	for (FileAttachment fileAttachment : attachments) {
	        	BufferedInputStream in = new BufferedInputStream(new URL(fileAttachment.getUrl()).openStream());
		        MimeBodyPart attachment= new MimeBodyPart();
		        ByteArrayDataSource ds = new ByteArrayDataSource(in, "application/pdf"); 
		        attachment.setDataHandler(new DataHandler(ds));
		        attachment.setFileName(fileAttachment.getName());
		        multipart.addBodyPart(attachment, 1);
        	}
        }
        
        
        message.setContent(multipart,"text/html");
        
        // Create a transport.
        Transport transport = session.getTransport();
                    
        // Send the message.
        try {
            // Connect to Amazon SES using the SMTP username and password you specified above.
            transport.connect(this.host, this.username, this.password);
        	
            // Send the email.
            transport.sendMessage(message, message.getAllRecipients());
        }
        catch (Exception e) {
        	throw e;
        }
        finally {
            // Close and terminate the connection.
            transport.close();
        }
    }
    
    public void sendEmailTest() {
    	this.sendEmailWelcome();
    }
    public void sendEmailWelcome() {
		try {
			String to = "user@domain.com";
			this.sendWelcomeEmail(to, to, DateUtil.getNow(), Locale.getDefault());
		} catch (IOException e) {
			e.printStackTrace();
		} catch (MessagingException e) {
			e.printStackTrace();
		}
    }
    public void sendEmailHello() {
		try {
			String to = "user@domain.com";
			FileAttachment fa = new FileAttachment("https://application.com/example.pdf", "example.pdf");
			Set<String> ccs = new HashSet<>(Arrays.asList("example@domain.com"));
			Set<String> ccos = new HashSet<>(Arrays.asList("example2@domain.com", "example3@domain.com"));
			this.sendEmail(to, ccs, ccos, "subject", "content<strong>HTML</strong>", new HashSet<>(Arrays.asList(fa)));
		} catch (IOException e) {
			e.printStackTrace();
		} catch (MessagingException e) {
			e.printStackTrace();
		}
    }
    
    
}
