package itforum.web;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import itforum.entities.User;
import itforum.exceptions.UserNotFoundException;
import itforum.repositories.UserRepository;

@Controller
public class ProfileController {
	
	private UserRepository userRepository;
	
	@Autowired
	public ProfileController(UserRepository userRepository) {
		this.userRepository = userRepository;
	}
	
	@RequestMapping(value = "/profile/{nick}", method = GET)
	public String showUserProfile(@PathVariable String nick, Model model) {
		User user = null;
		user = userRepository.getUserByNick(nick);
		if(user == null){
			throw new UserNotFoundException();
		}
		model.addAttribute(user);
		return "profilePage";
	}
}
