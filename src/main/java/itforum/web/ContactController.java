package itforum.web;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;

import itforum.config.MailConfig;
import itforum.entities.Email;

@Controller
public class ContactController {
	
	@Autowired
	private JavaMailSender mailSender;

	@RequestMapping(value="/contact", method=GET)
	public String contactForm(Email email){
		return "contactFormPage";
	}
	
	@RequestMapping(value="/contact", method=POST)
	public String processContactForm(
			Model model,
			@Valid Email email,
			BindingResult result){
		
		if(result.hasErrors()){
			model = setErrorAttribute(model);
			return "contactFormPage";
		}else{
			sendEmail(email.getEmailAddress(), email.getSubject(), email.getContent());	
			model = setSuccessAttributes(model);
			return "contactFormPage";
		}
	}
	
	private Model setErrorAttribute(Model model){
		model.addAttribute("failureMessage","Error, subject or content is to short/long");
		return model;
	}
	
	private Model setSuccessAttributes(Model model){
		model.addAttribute(new Email());
		model.addAttribute("successMessage","Email has been sent");
		
		return model;
	}
	
	private void sendEmail(String emailAddress, String subject, String content){
		SimpleMailMessage message = new SimpleMailMessage();
		message.setFrom(emailAddress);
		message.setTo(MailConfig.USERNAME);
		message.setSubject(subject);
		message.setText("From: "+emailAddress+"\nMessage: "+content);
		
		mailSender.send(message);
	}
}
