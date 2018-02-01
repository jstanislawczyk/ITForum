package itforum.entities;

import java.sql.Timestamp;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
public class ForumPost extends Post {

	@NotNull
	@Size(min = 5, max = 70, message = "Title must conatin {min} to {max} letters")
	private String title;

	@ManyToOne
	@JoinColumn(name = "idUser")
	private User user;

	@ManyToOne
	@JoinColumn(name = "idCategory")
	private ForumCategory category;

	@OneToMany(mappedBy = "post")
	private List<PostComment> comments;

	public ForumCategory getCategory() {
		return category;
	}

	public void setCategory(ForumCategory category) {
		this.category = category;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public List<PostComment> getComments() {
		return comments;
	}

	public void setComments(List<PostComment> comments) {
		this.comments = comments;
	}

	@Override
	public String toString() {
		return "ForumPost [id=" + getId() + ", title=" + title + ", content=" + getContent() + ", date=" + getDate()
				+ ", user=" + user + ", category=" + category + ", comments=" + comments + "]";
	}

	public ForumPost(@NotNull @Size(min = 5, max = 70, message = "{title.size}") String title, User user,
			ForumCategory category, List<PostComment> comments) {
		super();
		this.title = title;
		this.user = user;
		this.category = category;
		this.comments = comments;
	}
	
	public ForumPost() {
		super();
	}

	public ForumPost(Long id, @NotNull @Size(min = 5, max = 70, message = "{title.size}") String title,  String content, Timestamp date) {
		super(id, content, date);
		this.title = title;
	}
}
