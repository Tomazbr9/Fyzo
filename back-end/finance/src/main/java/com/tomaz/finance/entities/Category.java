package com.tomaz.finance.entities;

import java.io.Serializable;
import java.util.Objects;

import com.tomaz.finance.enums.TransactionType;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "tb_category")
public class Category implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private Integer type;
    private String color;
    
    
    @ManyToOne
    private User user;
    
    public Category() {
    	
    }

	public Category(Long id, String name, TransactionType type, String color, User user) {
		super();
		this.id = id;
		this.name = name;
		setType(type);
		this.color = color;
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

	public TransactionType getType() {
		return TransactionType.valueOf(type);
	}

	public void setType(TransactionType type) {
		if(type != null) {
			this.type = type.getCode();
		}
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@Override
	public int hashCode() {
		return Objects.hash(color, id, name, type, user);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Category other = (Category) obj;
		return Objects.equals(color, other.color) && Objects.equals(id, other.id) && Objects.equals(name, other.name)
				&& Objects.equals(type, other.type) && Objects.equals(user, other.user);
	}
    
    
    
}
