package org.wecancodeit.shoppingcart;

import java.util.Collection;
import java.util.Optional;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
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
	
	// This route is an example of how to mock role-based authorization
	// To experience the page as an admin, add "?role=admin" to the end of the URL
	// To experience the page as a regular user, add any other role
	// The idea is that only admins can use this page to perform administrative tasks
	// This might include adding new products or marking products on sale or out of stock
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
		
		Iterable<Category> categories = categoryRepo.findAll();
		model.addAttribute("categories", categories);

		return "admin";
	}
	
	@PostMapping("/admin/addCategory")
	public String addCategory(@RequestParam(name = "name") String newCategoryName) throws CategoryExistsException {
		
		Optional<Category> existingCategory = categoryRepo.findByName(newCategoryName);
		
		if (existingCategory.isPresent()) {
			throw new CategoryExistsException();
		}
		
		categoryRepo.save(new Category(newCategoryName, ""));
		
		return "redirect:/admin?role=admin";
	}
}
