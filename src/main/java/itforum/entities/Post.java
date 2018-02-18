package itforum.entities;

import java.sql.Timestamp;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@MappedSuperclass
public abstract class Post {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@NotNull
	@Size(min = 5, max = 300, message = "Content must contain {min} to {max} letters")
	private String content;

	private Timestamp date;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Timestamp getDate() {
		return date;
	}

	public void setDate(Timestamp date) {
		this.date = date;
	}

	public Post(Long id,
			@NotNull @Size(min = 10, max = 300, message = "Content must contain {min} to {max} letters") String content,
			Timestamp date) {
		this.id = id;
		this.content = content;
		this.date = date;
	}

	public Post() {
		super();
	}
}
