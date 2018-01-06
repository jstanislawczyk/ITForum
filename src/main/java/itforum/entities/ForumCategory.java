package itforum.entities;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
public class ForumCategory {
	
	@Id
	private Long id;
	
	@NotNull
	@Size(min = 5, max = 70, message="{title.size}")
	private String title;
	
	@NotNull
	@Size(min = 5, max = 100, message="{description.size}")
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

}
