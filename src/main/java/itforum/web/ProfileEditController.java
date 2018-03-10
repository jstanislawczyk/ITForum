package itforum.web;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import itforum.entities.User;
import itforum.repositories.UserRepository;

@Controller
public class ProfileEditController {
	
	private UserRepository userRepository;
	
	@Autowired
	public ProfileEditController(UserRepository userRepository) {
		this.userRepository = userRepository;
	}
	
	@RequestMapping(value = "/profile/delete", method = GET)
	public String showDeleteProfileConfirmation(Model model) {	
		return "deleteProfilePage";
	}
	
	@RequestMapping(value = "/profile/delete", method = POST)
	public String disableUser(Model model) {
		disableUser();
		logOffDisabledUser();
		return "redirect:/";
	}
	
	private void disableUser(){
		userRepository.disableUser(getLoggedUser());
	}
	
	private void logOffDisabledUser(){
		SecurityContextHolder.clearContext();
	}
	
	private User getLoggedUser(){
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String loggedUserNick = authentication.getName();
		
		return getUserByNick(loggedUserNick);
	}
	
	private User getUserByNick(String nick){
		return userRepository.getUserByNick(nick);
	}
}
