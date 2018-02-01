package itforum.web;

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
	
	@RequestMapping("/post/{postId}")
	public String showForumPost(@PathVariable Long postId, Model model){
		
		ForumPost post = forumPostRepository.findPostById(postId);
		
		if(post == null){
			throw new PageNotFoundException();
		}else{
			//todo
		}
		
		return "forumPostPage";
	}
}
