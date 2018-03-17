package itforum.web;


import static org.springframework.web.bind.annotation.RequestMethod.GET;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import itforum.entities.User;
import itforum.repositories.PostCommentRepository;
import itforum.repositories.UserRepository;


@Controller
@RequestMapping()
public class AdminActionsController {

	private UserRepository userRepository;
	private PostCommentRepository postCommentRepository;
	
	@Autowired
	public AdminActionsController(UserRepository userRepository, PostCommentRepository postCommentRepository) {
		this.userRepository = userRepository;
		this.postCommentRepository = postCommentRepository;
	}
	
	@RequestMapping(value = "/admin/deleteComment/{postId}/{commentId}", method = GET)
	public String deleteComment(@PathVariable Long postId, @PathVariable Long commentId){
		postCommentRepository.deletePostById(commentId);
		return "redirect:/post/"+postId;
	}
	
	@RequestMapping(value = "/admin/listOfUsers")
	public String showListOfUsers(Model model){
		model.addAttribute("users", getListOfActiveUsers());
		return "listOfUsersPage";
	}
	
	@RequestMapping(value = "/admin/ban/{userNick}", method = GET)
	public String banUser(@PathVariable String userNick){
		disableUser(userNick);
		return "redirect:/admin/listOfUsers";
	}
	
	private List<User> getListOfActiveUsers(){
		return userRepository.getAllActiveUsers();
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
}