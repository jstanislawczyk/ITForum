package itforum.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import static org.springframework.web.bind.annotation.RequestMethod.GET;


@Controller
@RequestMapping("/login")
public class LoginController {
	
	@RequestMapping(method = GET)
	public String login(){
		return "loginPage";
	}
}
