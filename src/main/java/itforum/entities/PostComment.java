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

	public void setPost(ForumPost post) {
		this.post = post;
	}

	@Override
	public String toString() {
		return "PostComment [id=" + getId() + ", content=" + getContent() + ", date=" + getDate() + ", post="+post;
	}
	
	public PostComment() {
		super();
	}

	public PostComment(Long id, String content, Timestamp date) {
		super(id, content, date);
	}

	public PostComment(Long id,
			@NotNull @Size(min = 10, max = 300, message = "Content must contain {min} to {max} letters") String content,
			Timestamp date, ForumPost post) {
		super(id, content, date);
		this.post = post;
	}
}
