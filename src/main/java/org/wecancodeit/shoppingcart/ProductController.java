package org.wecancodeit.shoppingcart;

import java.util.Optional;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ProductController {

	@Resource
	CategoryRepository categoryRepo;
	
	@Resource
	ProductRepository productRepo;
	
	@RequestMapping("/")
	public String homePage() {
		return "index";
	}
	
	@RequestMapping("/category") 
	public String findOneCategory(@RequestParam(value="id")Long categoryId, Model model) throws CategoryNotFoundException {
		Optional<Category> category = categoryRepo.findById(categoryId);
		
		if(category.isPresent()) {
			model.addAttribute("category", category.get());
			return "category";
		}
		throw new CategoryNotFoundException();
	}
	
	@RequestMapping("/product") 
	public String findOneProduct(@RequestParam(value="id")Long productId, Model model) throws ProductNotFoundException {
		Optional<Product> product = productRepo.findById(productId);
		
		if(product.isPresent()) {
			model.addAttribute("product", product.get());
			return "product";
		}
		throw new ProductNotFoundException();
	}
}
