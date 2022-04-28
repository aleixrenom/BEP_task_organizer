package fi.haagahelia.course.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Task {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;
    private String name;
    private String description;
    private String deadline; // localDate would be the type, but better not try this time
		private boolean completed;
    
    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "categoryid")
    private Category category;

		@ManyToOne
    @JoinColumn(name = "id")
		private User user;

    public Task() {}

	public Task(String name, String description, String deadline, Category category, User user) {
		super();
		this.name = name;
		this.description = description;
		this.deadline = deadline;
		this.category = category;
		this.completed = false;
		this.user = user;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getDeadline() {
		return deadline;
	}

	public void setDeadline(String deadline) {
		this.deadline = deadline;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public boolean getCompleted() {
		return completed;
	}

	public void setCompleted(Boolean completed) {
		this.completed = completed;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@Override
	public String toString() {
		if (this.category != null)
			return "Task [id=" + id + ", name=" + name + ", description=" + description + ", deadline=" + deadline + ", completed=" + completed + " category =" + this.getCategory() + "]";		
		else
			return "Task [id=" + id + ", name=" + name + ", description=" + description + ", deadline=" + deadline + ", completed=" + completed + "]";
	}
}
