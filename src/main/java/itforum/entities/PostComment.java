package itforum.entities;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class PostComment extends Post{
	
	@ManyToOne
	@JoinColumn(name = "idPost")
	private ForumPost post;

	public ForumPost getPost() {
		return post;
	}

	public void setPost(ForumPost post) {
		this.post = post;
	}
}
