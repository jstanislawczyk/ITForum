package itforum.web;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import itforum.entities.User;
import itforum.exceptions.PageNotFoundException;
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
		User user = findUserOrThrowPageNotFoundError(nick);
		
		model.addAttribute(user);
		return "profilePage";
	}
	
	private User findUserOrThrowPageNotFoundError(String nick){		
		User user = getUserByNick(nick);
		
		if(user == null){
			throw new PageNotFoundException();
		}else{
			return user;
		}
	}
	
	private User getUserByNick(String nick){
		return userRepository.getUserByNick(nick);
	}
}
