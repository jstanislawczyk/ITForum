package itforum.web;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
@RequestMapping("/login")
public class LoginController {
	
	@RequestMapping(method = GET)
	public String login(
			Model model,
			@RequestParam(value = "logout",	required = false) String logout
		){
		if(logout != null){
			model.addAttribute("logoutMessage", "You have been successfully logged out");
		}
		
		return "loginPage";
	}
}
