package org.wecancodeit.shoppingcart;

import java.util.Collection;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class Product {

	@Id
	@GeneratedValue
	private long id;
	
	private String name;
	
	@ManyToOne
	private Category category;
	
	@OneToMany(mappedBy="product")
	private Collection<CartItem> cartItems;

	public long getId() {
		return id;
	}
	
	public String getName() {
		return name;
	}
	
	public Category getCategory() {
		return category;
	}
	
	
	protected Product() {
		
	}
	
	public Product(String name) {
		this.name = name;
	}
	
	public Product(String name, Category category) {
		this.name = name;
		this.setCategory(category);
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
