package org.wecancodeit.shoppingcart;

public enum Status {
	WIP,
	PROCESSING,
	INVOICE
}

//@Entity
//public class Status {
//
//	@Id
//	@GeneratedValue
//	private long id;
//	
//	@OneToMany(mappedBy = "status")
//	private Collection<CartItem> cartItems;
//
//	private String name;
//
//	public long getId() {
//		return id;
//	}
//
//	public String getName() {
//		return name;
//	}
//	
//	protected Status() {
//	}
//
//	public Status(String name) {
//		this.name = name;
//	}
//}
