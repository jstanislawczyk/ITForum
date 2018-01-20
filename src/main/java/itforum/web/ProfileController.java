package itforum.web;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ProfileController {
	
	@RequestMapping(value = "/profile/{nick}", method = GET)
	public String showUserProfile(@PathVariable String nick, Model model) {
		
		return "profilePage";
	}
}
