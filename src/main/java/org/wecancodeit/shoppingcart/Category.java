package org.wecancodeit.shoppingcart;

import java.util.Collection;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Category {

	@Id
	@GeneratedValue
	private long id;
	
	private String name;
	
	@JsonIgnore
	@OneToMany (mappedBy = "category")
	private Collection<Product> products;

	public long getId() {
		return id;
	}
	
	public String getName() {
		return name;
	}
	
	public Collection<Product> getProducts() {
		return products;
	}
	
	public int getProductCount() {
		return this.getProducts().size();
	}
	
	protected Category() {
		
	}
	
	public Category(String name) {
		this.name = name;
	}
	
	

}
