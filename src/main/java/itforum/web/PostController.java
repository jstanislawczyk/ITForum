package itforum.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class PostController {
	
	@RequestMapping("/post/{postId}")
	public String showForumPost(@PathVariable String postId, Model model){
		
		return "forumPostPage";
	}
}
