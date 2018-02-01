package itforum.exceptions;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class AppExceptionHandler {
	
	@ExceptionHandler(PageNotFoundException.class)
	public String pageNotFoundHandler(){
		return "pageNotFoundError";
	}
}
