package itforum.web;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

import java.sql.Timestamp;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import itforum.entities.ForumPost;
import itforum.entities.PostComment;
import itforum.exceptions.PageNotFoundException;
import itforum.repositories.ForumPostRepository;
import itforum.repositories.PostCommentRepository;

@Controller
public class PostController {
	
	private ForumPostRepository forumPostRepository;
	private PostCommentRepository postCommentRepository;
	
	@Autowired
	public PostController(ForumPostRepository forumPostRepository, PostCommentRepository postCommentRepository){
		this.forumPostRepository = forumPostRepository;
		this.postCommentRepository = postCommentRepository;
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
		
		saveCommentToDatabase(comment, postId);
		setPostProperties(postId, model);
		return "forumPostPage";
	}
	
	private void setPostProperties(Long postId, Model model){
		ForumPost post = null;
		List<PostComment> comments = null;
		
		comments = findCommentsByPostId(postId);
		post = findPostByIdOrThrowErrorIfNotExists(postId);
		model = setUserAndPostAttributesForModel(post, model, comments);
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
		return postCommentRepository.getAllCommentsByPostId(postId);
	}
	
	private Model setUserAndPostAttributesForModel(ForumPost post, Model model, List<PostComment> comments){
		model.addAttribute("nick", post.getUser().getNick());
		model.addAttribute("post",post);
		model.addAttribute("postComment", new PostComment());
		model.addAttribute("comments", comments);
		
		return model;
	}
	
	private Timestamp getCurrentDate(){
		return new Timestamp(System.currentTimeMillis());
	}
	
	private String validCommentErrors(RedirectAttributes redirectAttributes, Long postId){
		redirectAttributes.addFlashAttribute("postId", postId);
		return "redirect:/post/{postId}";
	}
	
	private void saveCommentToDatabase(PostComment comment, Long postId){
		comment = setPropertiesOfComment(comment, postId);
		postCommentRepository.saveComment(comment);
	}
	
	private PostComment setPropertiesOfComment(PostComment comment, Long postId){
		comment.setDate(getCurrentDate());
		comment.setPost(findPostById(postId));
		
		return comment;
	}
}
