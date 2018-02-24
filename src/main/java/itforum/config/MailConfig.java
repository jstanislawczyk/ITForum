package itforum.config;

import java.util.Properties;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

@Configuration
public class MailConfig{
	
	private static final String USERNAME = "janitforum@gmail.com";
	private static final String PASSWORD = "janittest";
	
	@Bean
	public JavaMailSender javaMailService() {
		
	    JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
	    mailSender.setHost("smtp.gmail.com");
	    mailSender.setPort(587);
	    mailSender.setUsername(USERNAME);
	    mailSender.setPassword(PASSWORD);
	     
	    Properties props = mailSender.getJavaMailProperties();
	    props.put("mail.transport.protocol", "smtp");
	    props.put("mail.smtp.ssl.trust", "smtp.gmail.com");
	    props.put("mail.smtp.auth", "true");
	    props.put("mail.smtp.starttls.enable", "true");
	    props.put("mail.debug", "false");
	     
	    return mailSender;
	}
}
