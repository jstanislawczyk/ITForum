package itforum.entities;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
public class ForumCategory {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@NotNull
	@Size(min = 3, max = 70, message="Title is to short")
	private String title;
	
	@NotNull
	@Size(min = 5, max = 100, message="Description is to short")
	private String description;

	@OneToMany(mappedBy = "category")
	private List<ForumPost> posts;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public String toString() {
		return "ForumCategory [id=" + id + ", title=" + title + ", description=" + description + ", posts=" + posts
				+ "]";
	}

	public ForumCategory(Long id, @NotNull @Size(min = 3, max = 70, message = "Title is to short") String title,
			@NotNull @Size(min = 5, max = 100, message = "Description is to short") String description,
			List<ForumPost> posts) {
		this.id = id;
		this.title = title;
		this.description = description;
		this.posts = posts;
	}
	
	public ForumCategory(Long id, String title,String description) {
		this.id = id;
		this.title = title;
		this.description = description;
	}

	public ForumCategory() {}
}
