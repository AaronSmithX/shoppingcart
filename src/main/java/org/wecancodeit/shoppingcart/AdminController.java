package org.wecancodeit.shoppingcart;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.util.Optional;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class AdminController {

	@Resource
	CategoryRepository categoryRepo;

	@Resource
	ProductRepository productRepo;

	@Resource
	CartItemRepository cartRepo;
	
	@Resource
	ImageUploadService uploader;
	
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
	public String addCategory(
		@RequestParam("name") String newCategoryName,
		@RequestParam("imageFile") MultipartFile imageFile
	) throws Exception {
		
		Optional<Category> existingCategory = categoryRepo.findByName(newCategoryName);
		
		if (existingCategory.isPresent()) {
			throw new CategoryExistsException();
		}
		
		String virtualFileUrl = uploader.uploadMultipartFile(imageFile);	
		
		categoryRepo.save(new Category(
			newCategoryName,
			"/uploads/" + virtualFileUrl
		));
		
		return "redirect:/admin?role=admin";
	}

	@PostMapping("/admin/addProduct")
	public String addProduct(
		@RequestParam("name") String productName,
		@RequestParam("price") float productPrice,
		@RequestParam("description") String productDescription,
		@RequestParam("categoryId") Long categoryId,
		@RequestParam("imageFile") MultipartFile imageFile
	) throws CategoryNotFoundException, IllegalStateException, IOException, Exception {
		
		Optional<Category> category = categoryRepo.findById(categoryId);
		if (!category.isPresent()) {
			throw new CategoryNotFoundException();
		}
		
		String virtualFileUrl = uploader.uploadMultipartFile(imageFile);

		// Create and save product
		productRepo.save(new Product(
			productName,
			category.get(),
			productDescription,
			"/uploads/" + virtualFileUrl,
			new BigDecimal(productPrice)
		));
		
		return "redirect:/admin?role=admin";
	}
	
	@GetMapping("/uploads/{file:.+}") // Special pattern to allow "." to be part of PathVariable instead of truncating it
	public void serveImage(
		HttpServletRequest request,
		HttpServletResponse response,
		@PathVariable("file") String fileName
	) throws Exception {
		
		// Resolve path of requested file
		File requestedFile = uploader.getUploadedFile(fileName);
		
		// Ensure requested item exists and is a file
		if (!requestedFile.exists() || !requestedFile.isFile()) {
			throw new Exception();
		}
		
		// Determine and set correct content type of response
		String fileContentType= Files.probeContentType(requestedFile.toPath());
	    response.setContentType(fileContentType);
		
		// Serve file by streaming it directly to the response
		InputStream in = new FileInputStream(requestedFile);		
	    IOUtils.copy(in, response.getOutputStream());
	}
}
