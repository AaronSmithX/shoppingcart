package org.wecancodeit.shoppingcart;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AdminController {

	@Resource
	CategoryRepository categoryRepo;

	@Resource
	ProductRepository productRepo;

	@Resource
	CartItemRepository cartRepo;
	
	@RequestMapping("/admin")
	public String adminPanel(
			@RequestParam(name = "role", required = false, defaultValue = "customer") String role,
			Model model
	) throws UnauthorizedRequestException {
		
		System.out.println("ROLE: " + role);
		
		if (role == null || !role.equals("admin")) {
			System.out.println("ERROR");
			throw new UnauthorizedRequestException();
		}
		System.out.println("SUCCESS");

		return "admin";
	}
}
