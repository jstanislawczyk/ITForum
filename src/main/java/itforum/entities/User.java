package itforum.entities;

import java.sql.Timestamp;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
public class User {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@NotNull
	@Size(min = 5, max = 40, message="Nick must contain {min} to {max} letters")
	private String nick;
	
	@NotNull
	@Size(min = 7, message="Password must contain at least {min} letters")
	private String password;
	
	@Email(message = "Wrong email")
	@NotNull
	@NotBlank(message="Wrong email")
	private String email;
	
	private Timestamp date;
	private int points;
	private String role;
	private boolean enabled;
	
	@OneToMany(mappedBy = "user")
	private List<ForumPost> posts;
	
	@OneToMany(mappedBy = "user")
	private List<PostComment> comments;

	public User() {}

	public User(Long id) {
		this.id = id;
	}
	
	public User(Long id, String nick, String password){
		this.id = id;
		this.nick = nick;
		this.password = password;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", nick=" + nick + ", password=" + password + ", email=" + email + ", date=" + date
				+ ", points=" + points + ", role=" + role + ", enabled=" + enabled + "]";
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNick() {
		return nick;
	}

	public void setNick(String nick) {
		this.nick = nick;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Timestamp getDate() {
		return date;
	}

	public void setDate(Timestamp date) {
		this.date = date;
	}

	public int getPoints() {
		return points;
	}

	public void setPoints(int points) {
		this.points = points;
	}

	public List<ForumPost> getPosts() {
		return posts;
	}

	public void setPosts(List<ForumPost> posts) {
		this.posts = posts;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
}
