package itforum.exceptions;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class AppExceptionHandler {
	
	@ExceptionHandler(UserNotFoundException.class)
	public String spitterNotFoundHandler(){
		return "errorUserNotFoundPage";
	}
}
