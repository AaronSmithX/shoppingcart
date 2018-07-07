package org.wecancodeit.shoppingcart;

import java.util.Collection;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Status {

	@Id
	@GeneratedValue
	private long id;
	
	@OneToMany(mappedBy = "status")
	private Collection<CartItem> cartItems;

	private String name;

	public long getId() {
		return id;
	}

	public String getName() {
		return name;
	}
	
	protected Status() {
	}

	public Status(String name) {
		this.name = name;
	}
}
