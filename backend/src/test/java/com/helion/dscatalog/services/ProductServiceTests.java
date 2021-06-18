package com.helion.dscatalog.services;

import static org.mockito.ArgumentMatchers.any;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.helion.dscatalog.dto.ProductDTO;
import com.helion.dscatalog.entities.Category;
import com.helion.dscatalog.entities.Product;
import com.helion.dscatalog.repositories.CategoryRepository;
import com.helion.dscatalog.repositories.ProductRepository;
import com.helion.dscatalog.services.exceptions.DataBaseException;
import com.helion.dscatalog.services.exceptions.ResourceNotFoundException;
import com.helion.dscatalog.tests.Factory;

@ExtendWith(SpringExtension.class)
public class ProductServiceTests {
	
	private long existingId;
	private long nonExistingId;
	private long dependentId;
	Product product;
	Category category;
	ProductDTO productDTO;
	
	
	PageImpl<Product> page;
	
	
	@InjectMocks
	private ProductService service;
	
	@Mock
	private ProductRepository repository;
	
	@Mock 
	private CategoryRepository categoryRepository;
	
	
	@BeforeEach
	void setup() throws Exception{
		
		existingId = 1L;
		nonExistingId = 2L;
		dependentId = 3L;
		product = Factory.createProduct();
		productDTO = Factory.createProdutDTO();
		category = Factory.createCategory();
		
		page = new PageImpl<>(List.of(product));
		
		
		Mockito.doThrow(EntityNotFoundException.class).when(repository).getOne(nonExistingId);
		Mockito.when(repository.getOne(existingId)).thenReturn(product);
		Mockito.when(categoryRepository.getOne(existingId)).thenReturn(category);
		
		Mockito.when(repository.findAll((Pageable) any())).thenReturn(page);
		Mockito.when(repository.save(any())).thenReturn(product);
		Mockito.when(repository.findById(existingId)).thenReturn(Optional.of(product));
		Mockito.when(repository.findById(nonExistingId)).thenReturn(Optional.empty());
		Mockito.when(repository.find(any(), any(), any())).thenReturn(page);
		
		Mockito.doNothing().when(repository).deleteById(existingId);
		Mockito.doThrow(EmptyResultDataAccessException.class).when(repository).deleteById(nonExistingId);
		Mockito.doThrow(DataIntegrityViolationException.class).when(repository).deleteById(dependentId);
	}
	
	@Test
	public void updateShouldThrowResourceNotFoundExceptionWhenIdNonExists() {
		Assertions.assertThrows(ResourceNotFoundException.class, ()->{
			service.update(nonExistingId, productDTO);
		});
		
		Mockito.verify(repository).getOne(nonExistingId);
		
		
	}
	
	
	@Test
	public void updateShouldReturnProductDTOwhenIdExists() {
		ProductDTO productDTO = service.update(existingId, this.productDTO);
		Assertions.assertNotNull(productDTO);
		Mockito.verify(repository).save(this.product);
		Mockito.verify(categoryRepository).getOne(category.getId());
	}
	
	@Test
	public void findByIdShouldThrowResourceNotFoundExceptionWhenIdNonExists() {

		Assertions.assertThrows(ResourceNotFoundException.class, ()->{
			service.findById(nonExistingId);
		});
		Mockito.verify(repository).findById(nonExistingId);
	}
	
	@Test
	public void findByIdShouldReturnProductDTOWhenIdExists() {
		
		ProductDTO productDTO = service.findById(existingId);
		Assertions.assertNotNull(productDTO);
		Assertions.assertEquals(productDTO.getId(), existingId);
		Mockito.verify(repository).findById(existingId);
	}
	
	@Test
	public void findAllPagedShouldReturnPage() {
		
		Pageable pageable = PageRequest.of(0, 5);
		Page<ProductDTO> page = service.findAllPaged(0L,"", pageable);
		Assertions.assertNotNull(page);
		
	}
	
	
	@Test
	public void deleteShouldDoNothingWhenIdExists() {
		
		Assertions.assertDoesNotThrow(()-> {
			service.delete(existingId);
		});
		
		Mockito.verify(repository, Mockito.times(1)).deleteById(existingId);
	}
	
	
	@Test
	public void deleteShouldThrowsResourceNotFoundExceptionWhenIdNonExists() {
		
		Assertions.assertThrows(ResourceNotFoundException.class, ()->{
			service.delete(nonExistingId);
		});
		
		Mockito.verify(repository, Mockito.times(1)).deleteById(nonExistingId);
	}
	
	@Test
	public void deleteShouldThrowsDataBaseExceptionWhenIdNonExists() {
		
		Assertions.assertThrows(DataBaseException.class, ()->{
			service.delete(dependentId);
		});
		
		Mockito.verify(repository, Mockito.times(1)).deleteById(dependentId);
	}
	

}

