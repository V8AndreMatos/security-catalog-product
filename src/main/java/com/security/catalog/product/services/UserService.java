package com.security.catalog.product.services;

//import com.security.catalog.product.dto.*;
import com.security.catalog.product.dto.UserDTO;
import com.security.catalog.product.dto.RoleDTO;
import com.security.catalog.product.dto.UserInsertDTO;
import com.security.catalog.product.entities.Category;
import com.security.catalog.product.entities.Product;
import com.security.catalog.product.entities.Role;
import com.security.catalog.product.entities.User;
import com.security.catalog.product.exceptions.DataBaseException;
import com.security.catalog.product.exceptions.ResourceNotFoundException;
import com.security.catalog.product.repositories.RoleRepository;
import com.security.catalog.product.repositories.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private UserRepository userRepository ;

    @Autowired
    private RoleRepository roleRepository;


    @Transactional(readOnly = true)
    public Page<UserDTO> findAllPaged(PageRequest pageRequest){
        Page<User> list = userRepository.findAll(pageRequest);
        return list.map(x -> new UserDTO(x));
    }

    @Transactional(readOnly = true)
    public UserDTO findById(Long id) {
        Optional<User> obj = userRepository.findById(id);
        User entity = obj.orElseThrow(() -> new ResourceNotFoundException("Entity Not Found"));
        return new UserDTO(entity);
    }

    @Transactional
    public UserDTO insernewUser(UserInsertDTO dto) {

        User entity = new User(); // Instancia um usuário vazio
        copyDTOToEntity(dto , entity); // Copia os dados do DTO para a entidade
        entity.setPassword(bCryptPasswordEncoder.encode (dto.getPassword()));
        entity = userRepository.save(entity); // SALVA
        return new UserDTO(entity);// Retorna o DTO SALVO
    }

    @Transactional
    public UserDTO updateUser(Long id , UserDTO dto){
        try {
            User entity = userRepository.getReferenceById(id);
            copyDTOToEntity(dto , entity);
            entity = userRepository.save(entity);
            return new UserDTO(entity);
        } catch (EntityNotFoundException e) {
            throw new ResourceNotFoundException("Id not found exception " +id);
        }
    }

    public void deleteUserById(Long id){

        try {
            userRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new ResourceNotFoundException("Id not found exception " +id);
        } catch (DataIntegrityViolationException e) {
            throw new DataBaseException(" Integrity Violation" );
        }

    }

    // Copia os dados do DTO para o produto
    private void copyDTOToEntity(UserDTO dto , User entity){

        entity.setFirstName(dto.getFirstName());
        entity.setLastName(dto.getLastName());
        entity.setEmail(dto.getEmail());


        // Limpa as categorias
        entity.getRoles().clear();
        for (RoleDTO roleDTO : dto.getRolesDTO()){
            Role role = roleRepository.getOne(roleDTO.getId());
            entity.getRoles().add(role);
        }
    }
}
