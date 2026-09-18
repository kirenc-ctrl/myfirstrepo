package com.datajpa.jpademo.jpa_mappings;

import java.time.LocalDate;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "user_post")
//@AllArgsConstructor
//@NoArgsConstructor
//@Getter
//@Setter
public class Post {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private String message;
	private LocalDate postDate;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public LocalDate getPostDate() {
		return postDate;
	}
	public void setPostDate(LocalDate postDate) {
		this.postDate = postDate;
	}
	public Post(Integer id, String message, LocalDate postDate) {
		this.id = id;
		this.message = message;
		this.postDate = postDate;
	}
	public Post() {
	}
}
