package com.helion.dscatalog.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.helion.dscatalog.dto.CategoryDTO;
import com.helion.dscatalog.entities.Category;
import com.helion.dscatalog.repositories.CategoryRepository;
import com.helion.dscatalog.services.exceptions.ResourceNotFoundException;

@Service
public class CategoryService {
	
	@Autowired
	private CategoryRepository repo;
	
	@Transactional(readOnly = true)
	public List<CategoryDTO> findAll(){
		List<Category> list = repo.findAll();
		return list.stream().map(x -> new CategoryDTO(x)).collect(Collectors.toList());
		
	}
	
	@Transactional(readOnly = true)
	public CategoryDTO findById(Long id){
		Optional<Category> obj  = repo.findById(id);
		Category entity = obj.orElseThrow(() -> new ResourceNotFoundException("Objeto não encontrado"));
		return new CategoryDTO(entity);
	}

	@Transactional
	public CategoryDTO insert(CategoryDTO dto) {
		Category entity = new Category();
		entity.setName(dto.getName());
		entity = repo.save(entity);
		return new CategoryDTO(entity);
	}

	@Transactional
	public CategoryDTO update(Long id, CategoryDTO dto) {
		try {
			Category entity = repo.getOne(id);
			entity.setName(dto.getName());
			entity = repo.save(entity);
			return new CategoryDTO(entity);
		}
		catch(EntityNotFoundException e) {
			throw new ResourceNotFoundException("Id não encontrado na base de dados : "+id);
		}
		
	}
	
	
	
	

}
