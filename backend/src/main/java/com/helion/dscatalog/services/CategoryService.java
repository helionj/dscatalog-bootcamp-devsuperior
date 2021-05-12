package com.helion.dscatalog.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.helion.dscatalog.entities.Category;
import com.helion.dscatalog.repositories.CategoryRepository;

@Service
public class CategoryService {
	
	@Autowired
	private CategoryRepository repo;
	
	public List<Category> findAll(){
		return repo.findAll();
		
	}

}
