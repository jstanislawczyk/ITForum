package itforum.web;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

import java.sql.Timestamp;
import java.util.LinkedList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import itforum.entities.ForumPost;
import itforum.entities.PostComment;
import itforum.entities.User;
import itforum.exceptions.PageNotFoundException;
import itforum.repositories.ForumPostRepository;
import itforum.repositories.PostCommentRepository;
import itforum.repositories.UserRepository;

@Controller
public class PostController {
	
	private ForumPostRepository forumPostRepository;
	private PostCommentRepository postCommentRepository;
	private UserRepository userRepository;
	
	@Autowired
	public PostController(ForumPostRepository forumPostRepository, PostCommentRepository postCommentRepository, UserRepository userRepository){
		this.forumPostRepository = forumPostRepository;
		this.postCommentRepository = postCommentRepository;
		this.userRepository = userRepository;
	}
	
	@RequestMapping(value = "/post/{postId}", method = GET)
	public String showForumPost(@PathVariable Long postId, Model model){
		setPostProperties(postId, model);
		return "forumPostPage";
	}
	
	@RequestMapping(value = "/post/{postId}", method = POST)
	public String addCommentToPost(
				@PathVariable Long postId, 
				@Valid PostComment comment,
				BindingResult result,
				Model model,
				RedirectAttributes redirectAttributes){		
			
		if (result.hasErrors()) {
			validCommentErrors(redirectAttributes, postId);
		}
		
		saveCommentToDatabase(comment, postId, getCommentingUser());
		setPostProperties(postId, model);
		return "forumPostPage";
	}
	
	private void setPostProperties(Long postId, Model model){
		ForumPost post = null;
		List<PostComment> comments = null;
		List<String> usersNicknames = null;
		
		comments = findCommentsByPostId(postId);
		usersNicknames = findUsersNicknames(comments);
		
		post = findPostByIdOrThrowErrorIfNotExists(postId);
		model = setUserAndPostAttributesForModel(post, model, comments, usersNicknames);
	}
	
	private ForumPost findPostByIdOrThrowErrorIfNotExists(Long postId){
		ForumPost post = null;		
		try{
			post =  findPostById(postId);
		}catch(NumberFormatException nfe){
			throw new PageNotFoundException();
		}
				
		if(post == null){
			throw new PageNotFoundException();
		}	
		
		return post;
	}
	
	private ForumPost findPostById(Long postId){
		return forumPostRepository.findPostById(postId);
	}
	
	private List<PostComment> findCommentsByPostId(Long postId){
		List<PostComment> comments = postCommentRepository.getAllCommentsWithUserIdByPostId(postId);
		return comments;
	}
	
	private List<String> findUsersNicknames(List<PostComment> comments){
		List<String> nicks = new LinkedList<String>();
		String nick = null;
		User user = null;
		
		for(PostComment comment : comments){
			user = findUserById(comment.getUser().getId());
			nick = user.getNick();
			nicks.add(nick);
		}
		
		return nicks;
	}
	
	private User findUserById(Long userId){
		return userRepository.getUserById(userId);
	}
	
	private Model setUserAndPostAttributesForModel(ForumPost post, Model model, List<PostComment> comments , List<String> nicknames){
		model.addAttribute("nick", post.getUser().getNick());
		model.addAttribute("post",post);
		model.addAttribute("postComment", new PostComment());
		model.addAttribute("comments", comments);
		model.addAttribute("nicknames", nicknames);
		
		return model;
	}
	
	private Timestamp getCurrentDate(){
		return new Timestamp(System.currentTimeMillis());
	}
	
	private String validCommentErrors(RedirectAttributes redirectAttributes, Long postId){
		redirectAttributes.addFlashAttribute("postId", postId);
		return "redirect:/post/{postId}";
	}
	
	private User getCommentingUser(){
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	    String nick = auth.getName();
	    
		return userRepository.getUserByNick(nick);
	}
	
	private void saveCommentToDatabase(PostComment comment, Long postId, User user){
		comment = setPropertiesOfComment(comment, postId, user);
		postCommentRepository.saveComment(comment);
	}
	
	private PostComment setPropertiesOfComment(PostComment comment, Long postId, User user){
		comment.setDate(getCurrentDate());
		comment.setUser(user);
		comment.setPost(findPostById(postId));
		
		return comment;
	}
}
