package itforum.web;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
		User loggedUser = getLoggedUser();
		
		if(isLoggedUserAccountPage(user, loggedUser)){
			model.addAttribute(user);
			return "loggedUserProfilePage";
		}else{
			model.addAttribute(user);
			return "profilePage";
		}
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
	
	private User getLoggedUser(){
		try{
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			String loggedUserNick = authentication.getName();
			return getUserByNick(loggedUserNick);
		}catch(NullPointerException npe){
			return null;
		}
	}
	
	private boolean isLoggedUserAccountPage(User user, User loggedUser){
		if(loggedUser!=null && user.getNick().equals(loggedUser.getNick())){
			return true;
		}else{
			return false;
		}
	}
}
