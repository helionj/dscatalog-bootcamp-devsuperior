package com.helion.dscatalog.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.helion.dscatalog.dto.CategoryDTO;
import com.helion.dscatalog.entities.Category;
import com.helion.dscatalog.repositories.CategoryRepository;

@Service
public class CategoryService {
	
	@Autowired
	private CategoryRepository repo;
	
	@Transactional(readOnly = true)
	public List<CategoryDTO> findAll(){
		List<Category> list = repo.findAll();
		return list.stream().map(x -> new CategoryDTO(x)).collect(Collectors.toList());
		
	}

}
