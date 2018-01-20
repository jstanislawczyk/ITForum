package itforum.web;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

import java.sql.Timestamp;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import itforum.entities.User;
import itforum.repositories.UserRepository;

@Controller
public class RegistrationController{
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	private final UserRepository userRepository;
	
	@Autowired
	public RegistrationController(UserRepository userRepository) {
		this.userRepository = userRepository;
	}
	
	@RequestMapping(path = "/register", method=GET)
	public String register(User user){
		return "registrationPage";
	}
	
	@RequestMapping(path = "/register", method=POST)
	public String processRegistration(
				RedirectAttributes model,
				@Valid User user,
				BindingResult result
			){
		if (result.hasErrors()) {
			return "registrationPage";
		}else if(!userRepository.isEmailAvailable(user.getEmail())){
			result.addError(new ObjectError("email","An account already exists for this email"));
			return "registrationPage";
		}else if(!userRepository.isNickAvailable(user.getNick())){
			result.addError(new ObjectError("nick","An account already exists for this nick"));
			return "registrationPage";
		}else{
			user = setStartValuesForUser(user);
			user.setPassword(passwordEncoder.encode(user.getPassword()));
			userRepository.saveUser(user);
			
			model.addAttribute("nick", user.getNick());
			model.addFlashAttribute("user", user);
			return "redirect:/profile/{nick}";
		}
	}
	
	private User setStartValuesForUser(User user){
		user.setDate(new Timestamp(System.currentTimeMillis()));
		user.setPoints(0);
		user.setRole("USER");
		user.setEnabled(true);
		return user;
	}
}
