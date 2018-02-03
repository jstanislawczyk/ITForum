package itforum.web;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import itforum.entities.ForumPost;
import itforum.exceptions.PageNotFoundException;
import itforum.repositories.ForumPostRepository;

@Controller
public class PostController {
	
	private ForumPostRepository forumPostRepository;
	
	@Autowired
	public PostController(ForumPostRepository forumPostRepository){
		this.forumPostRepository = forumPostRepository;
	}
	
	@RequestMapping(value = "/post/{postIdAsString}", method = GET)
	public String showForumPost(
			@PathVariable String postIdAsString, 
			Model model){
		
		ForumPost post = null;
		post = findPostByIdOrThrowErrorIfNotExists(post, postIdAsString);
				
		model.addAttribute("nick", post.getUser().getNick());
		model.addAttribute("post",post);
		return "forumPostPage";
	}
	
	private ForumPost findPostByIdOrThrowErrorIfNotExists(ForumPost post, String postIdAsString){
		try{
			post = forumPostRepository.findPostById(Long.parseLong(postIdAsString));
		}catch(NumberFormatException nfe){
			throw new PageNotFoundException();
		}
		
		if(post == null){
			throw new PageNotFoundException();
		}	
		return post;
	}
}
