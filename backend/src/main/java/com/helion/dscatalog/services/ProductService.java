package com.helion.dscatalog.services;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.helion.dscatalog.dto.CategoryDTO;
import com.helion.dscatalog.dto.ProductDTO;
import com.helion.dscatalog.entities.Category;
import com.helion.dscatalog.entities.Product;
import com.helion.dscatalog.repositories.CategoryRepository;
import com.helion.dscatalog.repositories.ProductRepository;
import com.helion.dscatalog.services.exceptions.DataBaseException;
import com.helion.dscatalog.services.exceptions.ResourceNotFoundException;

@Service
public class ProductService {
	
	@Autowired
	private ProductRepository repo;
	
	@Autowired
	private CategoryRepository categoryRepository;
	
	@Transactional(readOnly = true)
	public Page<ProductDTO> findAllPaged(Long categoryId, String name, Pageable pageable){
		
		List<Category> categories = (categoryId == 0) ? null :Arrays.asList(categoryRepository.getOne(categoryId));
		Page<Product> list = repo.find(categories, name, pageable);
		return list.map(x -> new ProductDTO(x));
		
	}
	
	@Transactional(readOnly = true)
	public ProductDTO findById(Long id){
		Optional<Product> obj  = repo.findById(id);
		Product entity = obj.orElseThrow(() -> new ResourceNotFoundException("Objeto não encontrado"));
		return new ProductDTO(entity, entity.getCategories());
	}

	@Transactional
	public ProductDTO insert(ProductDTO dto) {
		Product entity = new Product();
		copyDtoToEntity(entity, dto);
		entity = repo.save(entity);
		return new ProductDTO(entity);
	}

	

	@Transactional
	public ProductDTO update(Long id, ProductDTO dto) {
		try {
			Product entity = repo.getOne(id);
			copyDtoToEntity(entity, dto);
			entity = repo.save(entity);
			return new ProductDTO(entity);
		}
		catch(EntityNotFoundException e) {
			throw new ResourceNotFoundException("Id não encontrado na base de dados : "+id);
		}
		
	}

	public void delete(Long id) {
		try {
			repo.deleteById(id);
		}
		catch(EmptyResultDataAccessException e){
			throw new ResourceNotFoundException("Id não encontrado na base de dados : "+id);
		}
		catch (DataIntegrityViolationException e) {
			throw new DataBaseException("Violação de integridade");
		}
		
		
	}
	
	private void copyDtoToEntity(Product entity, ProductDTO dto) {
		entity.setName(dto.getName());
		entity.setDate(dto.getDate());
		entity.setDescription(dto.getDescription());
		entity.setPrice(dto.getPrice());
		entity.setImgUrl(dto.getImgUrl());
		entity.getCategories().clear();
		
		for(CategoryDTO catDTO: dto.getCategories()) {
			Category category = categoryRepository.getOne(catDTO.getId());
			entity.getCategories().add(category);
		}
	
	}
	
	
	
	

}
