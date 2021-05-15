package com.helion.dscatalog.services;

import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.helion.dscatalog.dto.ProductDTO;
import com.helion.dscatalog.entities.Product;
import com.helion.dscatalog.repositories.ProductRepository;
import com.helion.dscatalog.services.exceptions.DataBaseException;
import com.helion.dscatalog.services.exceptions.ResourceNotFoundException;

@Service
public class ProductService {
	
	@Autowired
	private ProductRepository repo;
	
	@Transactional(readOnly = true)
	public Page<ProductDTO> findAllPaged(PageRequest pageRequest){
		Page<Product> list = repo.findAll(pageRequest);
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
		//entity.setName(dto.getName());
		entity = repo.save(entity);
		return new ProductDTO(entity);
	}

	@Transactional
	public ProductDTO update(Long id, ProductDTO dto) {
		try {
			Product entity = repo.getOne(id);
			//entity.setName(dto.getName());
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
	
	
	
	

}
