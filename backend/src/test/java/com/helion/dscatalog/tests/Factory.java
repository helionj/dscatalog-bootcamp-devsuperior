package com.helion.dscatalog.tests;

import java.time.Instant;

import com.helion.dscatalog.dto.ProductDTO;
import com.helion.dscatalog.entities.Category;
import com.helion.dscatalog.entities.Product;

public class Factory {
	
	public static Product createProduct() {
		Product product = new  Product(1L, "Phone", "GoodPhone",Instant.parse("2020-07-13T20:50:07.12345Z"), 800.0, "https://images/image.png");
		product.getCategories().add(new Category(2L, "Electronics"));
		return product;
	}
	
	public static ProductDTO createProdutDTO() {
		Product product = createProduct();
		
		return new ProductDTO(product, product.getCategories());
	}
	
	public static Category createCategory() {
		Category category = new Category(2L,"Eletronics");
		return category;
	}

}
