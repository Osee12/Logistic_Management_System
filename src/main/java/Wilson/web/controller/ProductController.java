package Wilson.web.controller;

import Wilson.web.model.Product;
import Wilson.web.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class ProductController {

	@Autowired
	private ProductService productService;

	// Display list of products
	@GetMapping("/")
	public String viewHomePage(Model model) {
		return findPaginated(1, "productName", "asc", model);
	}

	// Show form for creating a new product
	@GetMapping("/showNewProductForm")
	public String showNewProductForm(Model model) {
		// Create model attribute to bind form data
		Product product = new Product();
		model.addAttribute("product", product);
		return "new_product";
	}

	// Save a new product
	@PostMapping("/saveProduct")
	public String saveProduct(@ModelAttribute("product") Product product) {
		// Save product to the database
		productService.saveProduct(product);
		return "redirect:/";
	}

	// Show form for updating a product
	@GetMapping("/showFormForUpdate/{id}")
	public String showFormForUpdate(@PathVariable(value = "id") long id, Model model) {
		// Get product from the service
		Product product = productService.getProductById(id);
		// Set product as a model attribute to pre-populate the form
		model.addAttribute("product", product);
		return "update_product";
	}

	// Delete a product
	@GetMapping("/deleteProduct/{id}")
	public String deleteProduct(@PathVariable(value = "id") long id) {
		// Call delete product method
		this.productService.deleteProductById(id);
		return "redirect:/";
	}

	// Paginate and display the list of products
	@GetMapping("/page/{pageNo}")
	public String findPaginated(@PathVariable(value = "pageNo") int pageNo,
								@RequestParam("sortField") String sortField,
								@RequestParam("sortDir") String sortDir,
								Model model) {
		int pageSize = 5;

		Page<Product> page = productService.findPaginated(pageNo, pageSize, sortField, sortDir);
		List<Product> listProducts = page.getContent();

		model.addAttribute("currentPage", pageNo);
		model.addAttribute("totalPages", page.getTotalPages());
		model.addAttribute("totalItems", page.getTotalElements());

		model.addAttribute("sortField", sortField);
		model.addAttribute("sortDir", sortDir);
		model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");

		model.addAttribute("listProducts", listProducts);
		return "index";
	}
}
