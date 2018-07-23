package org.wecancodeit.shoppingcart;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.util.Optional;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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

	@RequestMapping("/login")
	public String adminLoginPage() {
		
		// Serves the login page
		return "login";
	}
	
	@RequestMapping("/admin/login")
	public String adminLogin(
		HttpServletResponse response // We need the response so we can add cookies,
	) {
		
		// Handle the login form... for this demo, pretend the credentials were correct
		// Set the cookie that declares the current user an administrator
		
		// NOTE: this is a MOCK UP of authentication, and is in NO WAY secure ON ANY LEVEL
		// In production, the user's credentials would be checked against a database of users
		
		// First, if an account with the given username exists, that account's password hash
		// would be compared against the password sent with the login form
		
		// Instead of a simple "admin" cookie, the user would receive a session cookie
		// The session cookie has a long random string that matches a session on the server
		
		// Servers keep a list of currently logged-in users (sessions) and their roles
		// On each request, the server will check the user's cookie, then the user's session,
		// and after confirming the user's identity the server will grant permissions
		
		Cookie adminRoleCookie = new Cookie("role", "admin");
		adminRoleCookie.setHttpOnly(true); // Only server can modify the cookie
		adminRoleCookie.setMaxAge(300); // Expires after 300 seconds (5 min)
		response.addCookie(adminRoleCookie);
		
		// Redirect the user back to the admin page once login is complete
		// The new cookie will allow the user to access the page
		return "redirect:/admin";
	}

	@RequestMapping("/admin/logout")
	public String adminLogin(
		HttpServletRequest request,
		HttpServletResponse response
	) {
		
		// Find "role" cookie, set it to immediately expire, and send that update to the browser
		Cookie[] cookies = request.getCookies();
		for (Cookie cookie : cookies) {
			if (cookie.getName().equals("role")) {
				cookie.setMaxAge(0);
				response.addCookie(cookie);
				break;
			}
		}
		
		return "redirect:/admin";
	}
	
	// This route is an example of how to mock role-based authorization
	// To experience the page as an admin, add "?role=admin" to the end of the URL
	// To experience the page as a regular user, add any other role
	// The idea is that only admins can use this page to perform administrative tasks
	// This might include adding new products or marking products on sale or out of stock
	@RequestMapping("/admin")
	public String adminPanel(
		@CookieValue(name = "role", defaultValue = "") String role,
		Model model
	) {
		
		System.out.println("ROLE: " + role);
		
		if (role == null || !role.equals("admin")) {
			return "redirect:/login";
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
			"/uploads/" + virtualFileUrl // TODO: manual concatenation of "/uploads/" is BAD MAGIC
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
			"/uploads/" + virtualFileUrl, // TODO: manual concatenation of "/uploads/" is BAD MAGIC
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
