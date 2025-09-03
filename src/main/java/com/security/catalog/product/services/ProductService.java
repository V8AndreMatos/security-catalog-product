package com.security.catalog.product.services;

import com.security.catalog.product.dto.CategoryDTO;
import com.security.catalog.product.dto.ProductDTO;
import com.security.catalog.product.entities.Category;
import com.security.catalog.product.repositories.CategoryRepository;
import com.security.catalog.product.entities.Product;
import com.security.catalog.product.exceptions.DataBaseException;
import com.security.catalog.product.exceptions.ResourceNotFoundException;
import com.security.catalog.product.repositories.ProductRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository ;

    @Autowired
    private CategoryRepository categoryRepository;

    @Transactional(readOnly = true)
    public Page<ProductDTO> findAllPaged(PageRequest pageRequest){
        Page<Product> list = productRepository.findAll(pageRequest);
        return list.map(x -> new ProductDTO(x));
    }

    @Transactional(readOnly = true)
    public ProductDTO findById(Long id) {
        Optional<Product> obj = productRepository.findById(id);
        Product entity = obj.orElseThrow(() -> new ResourceNotFoundException("Entity Not Found"));
        return new ProductDTO(entity);
    }

    @Transactional
    public ProductDTO inserCategory(ProductDTO dto) {

        Product entity = new Product();
        copyDTOToEntity(dto , entity);
        entity = productRepository.save(entity);
        return new ProductDTO(entity);
    }

    @Transactional
    public ProductDTO updateCategory(Long id , ProductDTO dto){
        try {
            Product entity = productRepository.getReferenceById(id);
            copyDTOToEntity(dto , entity);
            entity = productRepository.save(entity);
            return new ProductDTO(entity);
        } catch (EntityNotFoundException e) {
            throw new ResourceNotFoundException("Id not found exception " +id);
        }
    }

    public void deleteCategoryById(Long id){

        try {
            productRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new ResourceNotFoundException("Id not found exception " +id);
        } catch (DataIntegrityViolationException e) {
            throw new DataBaseException(" Integrity Violation" );
        }

    }

    // Copia os dados do DTO para o produto
    private void copyDTOToEntity(ProductDTO dto , Product entity){

        entity.setName(dto.getName());
        entity.setDescription(dto.getDescription());
        entity.setPrice(dto.getPrice());

        // Limpa as categorias
        entity.getCategories().clear();
        for (CategoryDTO catDTO : dto.getCategories()){
            Category category = categoryRepository.getReferenceById(catDTO.getId());
            entity.getCategories().add(category);
        }
    }
}
