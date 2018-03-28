package itforum.entities;

import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
public class PostComment extends Post{
	
	@ManyToOne
	@JoinColumn(name = "idPost")
	private ForumPost post;

	public ForumPost getPost() {
		return post;
	}
	
	@ManyToOne
	@JoinColumn(name = "idUser")
	private User user;

	public void setPost(ForumPost post) {
		this.post = post;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@Override
	public String toString() {
		return "PostComment [id=" + getId() + ", content=" + getContent() + ", date=" + getDate() + ", post="+post+", user="+user;
	}
	
	public PostComment() {
		super();
	}

	public PostComment(Long id, String content, Timestamp date, Long userId) {
		super(id, content, date);
		this.user= new User
					.Builder()
					.id(userId)
					.build();
	}

	public PostComment(Long id,
			@NotNull @Size(min = 10, max = 300, message = "Content must contain {min} to {max} letters") String content,
			Timestamp date, ForumPost post, User user) {
		super(id, content, date);
		this.post = post;
		this.user = user;
	}
}
