package org.wecancodeit.shoppingcart;

import java.util.Collection;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Product {

	@Id
	@GeneratedValue
	private long id;
	
	private String name;
	private String imageUrl;
	
	@Lob
	private String description;
	
	@ManyToOne
	private Category category;
	
	@JsonIgnore
	@OneToMany(mappedBy="product")
	private Collection<CartItem> cartItems;

	public long getId() {
		return id;
	}
	
	public String getName() {
		return name;
	}

	public String getImageUrl() {
		return imageUrl;
	}
	
	public Category getCategory() {
		return category;
	}
	
	public String getDescription() {
		return description;
	}
	
	protected Product() {
		
	}
	
	public Product(String name) {
		this.name = name;
	}

	public Product(String name, Category category) {
		this.name = name;
		this.setCategory(category);
		this.description = "";
	}

	public Product(String name, Category category, String description, String imageUrl) {
		this.name = name;
		this.setCategory(category);
		this.description = description;
		this.imageUrl = imageUrl;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (id ^ (id >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Product other = (Product) obj;
		if (id != other.id)
			return false;
		return true;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

}
