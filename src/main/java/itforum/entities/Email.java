package itforum.entities;

import javax.validation.constraints.Size;

public class Email {
	@javax.validation.constraints.Email
	private String emailAddress;
	@Size(min = 3, max = 70, message = "Title should contain {min} to {max} letters")
	private String subject;
	@Size(min = 3, max = 300, message = "Content should contain {min} to {max} letters")
	private String content;
	
	public String getEmailAddress() {
		return emailAddress;
	}

	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getSubject() {
		return subject;
	}

	public String getContent() {
		return content;
	}
}
