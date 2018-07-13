package org.wecancodeit.shoppingcart;

import java.util.Collection;
import java.util.Optional;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.view.RedirectView;

@Controller
public class ProductController {

	@Resource
	CategoryRepository categoryRepo;

	@Resource
	ProductRepository productRepo;

	@Resource
	CartItemRepository cartRepo;

	@RequestMapping("/")
	public String homePage(Model model) {
		Iterable<Category> categories = categoryRepo.findAll();
		model.addAttribute("categories", categories);
		return "categories";
	}

	@RequestMapping("/cart")
	public String shoppingCart() {
		return "cart";
	}
	
	@RequestMapping("/category") 
	public String findOneCategory(@RequestParam(value="id")Long categoryId, Model model) throws CategoryNotFoundException {
		Optional<Category> category = categoryRepo.findById(categoryId);
		
		if (category.isPresent()) {
			model.addAttribute("category", category.get());
			return "category";
		}
		throw new CategoryNotFoundException();
	}
	
	@RequestMapping("/product") 
	public String findOneProduct(@RequestParam(value="id")Long productId, Model model) throws ProductNotFoundException {
		Optional<Product> product = productRepo.findById(productId);
		
		if (product.isPresent()) {
			model.addAttribute("product", product.get());
			
			Optional<CartItem> cartItem = cartRepo.findByProduct(product.get());
			if (cartItem.isPresent()) {
				model.addAttribute("quantity", cartItem.get().getQuantity());
			}
			else {
				model.addAttribute("quantity", 0);
			}
			
			return "product";
		}
		throw new ProductNotFoundException();
	}
	
	@PostMapping("/addItemToCart")
	public RedirectView addItemToCart(
			@RequestParam(value="id") Long productId,
			@RequestParam(value= "quantity") int quantity
	) throws ProductNotFoundException {
		
		Optional<Product> product = productRepo.findById(productId);
		
		if (product.isPresent()) {
			cartRepo.addItemInQuantity(product.get(), quantity);
			return new RedirectView("cart");
		}
		
		throw new ProductNotFoundException();
	}
}
