package Wilson.web.service;

import Wilson.web.model.Product;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ProductService {
	List<Product> getAllProducts();
	void saveProduct(Product product);
	Product getProductById(long id);
	void deleteProductById(long id);
	Page<Product> findPaginated(int pageNo, int pageSize, String sortField, String sortDirection);
}
