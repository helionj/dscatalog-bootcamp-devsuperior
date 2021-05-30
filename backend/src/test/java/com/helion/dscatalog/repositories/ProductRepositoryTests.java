package com.helion.dscatalog.repositories;

import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.EmptyResultDataAccessException;

import com.helion.dscatalog.entities.Product;
import com.helion.dscatalog.tests.Factory;

@DataJpaTest
public class ProductRepositoryTests {
	
	private long existingId;
	private long nonExistingId;
	private long countTotalProducts;
	
	@Autowired
	private ProductRepository repository;
	
	@BeforeEach
	void setUp()throws Exception{
		existingId = 1L;
		nonExistingId = 100L;
		countTotalProducts = 25L;
	}
	
	@Test
	public void saveShouldPersistWithAutoincrementWhenIdisNull() {
		Product product = Factory.createProduct();
		product.setId(null);
		product = repository.save(product);
		
		Assertions.assertNotNull(product.getId());
		Assertions.assertEquals(product.getId(), countTotalProducts+1);
	}
	
	@Test
	public void deleteShouldDeleteObjectWhenIdExists() {
		
		
		repository.deleteById(existingId);
		
		Optional<Product> result = repository.findById(existingId);
		
		Assertions.assertFalse(result.isPresent());
		
	}
	
	@Test
	public void deleteShouldThrowEmptyResultDataAccessExceptionWhenIdNotExists() {
		
		Assertions.assertThrows(EmptyResultDataAccessException.class, () ->{
			
			repository.deleteById(nonExistingId);
		});
		
	}
	
	@Test
	public void findByIdShouldReturnNullOptionalWhenIdNotExists() {
		
		Optional<Product> result =repository.findById(countTotalProducts+10);
		Assertions.assertFalse(result.isPresent());
		
	}
	
	@Test
	public void findByIdShouldReturnNonNullOptionalWhenIdExists() {
		
		Optional<Product> result =repository.findById(countTotalProducts);
		Assertions.assertTrue(result.isPresent());
		
	}

}
