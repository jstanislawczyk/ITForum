package itforum.web;


import static org.springframework.web.bind.annotation.RequestMethod.GET;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import itforum.entities.User;
import itforum.repositories.UserRepository;


@Controller
@RequestMapping()
public class AdminActionsController {

	private UserRepository userRepository;
	
	@Autowired
	public AdminActionsController(UserRepository userRepository) {
		this.userRepository = userRepository;
	}
	
	@RequestMapping(value = "/admin/ban/{userNick}", method = GET)
	public String banUser(@PathVariable String userNick){
		disableUser(userNick);
		logOffDisabledUser();
		return "redirect:/";
	}
	
	private void disableUser(String nick){
		if(!nick.equals(getLoggedUserNick())){
			userRepository.disableUser(getUserByNick(nick));
		}
	}
	
	private String getLoggedUserNick(){
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String loggedUserNick = authentication.getName();
		
		return loggedUserNick;
	}
	
	private User getUserByNick(String nick){
		return userRepository.getUserByNick(nick);
	}
	
	private void logOffDisabledUser(){
		SecurityContextHolder.clearContext();
	}
}