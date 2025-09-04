package com.security.catalog.product.controller;

import com.security.catalog.product.dto.ProductDTO;
import com.security.catalog.product.dto.UserDTO;
import com.security.catalog.product.dto.UserInsertDTO;
import com.security.catalog.product.entities.User;
import com.security.catalog.product.services.ProductService;
import com.security.catalog.product.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.liquibase.LiquibaseProperties;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping(value = "/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<Page<UserDTO>> findAll(
            @RequestParam(value = "page" ,defaultValue = "0") Integer page,
            @RequestParam(value = "linesPerPage" ,defaultValue = "12") Integer linesPerPage,
            @RequestParam(value = "direction" ,defaultValue = "ASC") String direction,
            @RequestParam(value = "orderBy" ,defaultValue = "name") String orderBy

    ){

        PageRequest pageRequest = PageRequest.of(page , linesPerPage , Sort.Direction.valueOf(direction) , orderBy);

        Page<UserDTO> list = userService.findAllPaged(pageRequest);
        return ResponseEntity.ok().body(list);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<UserDTO> findById(@PathVariable Long id){
        UserDTO dto = userService.findById(id);
        return ResponseEntity.ok().body(dto);
    }

    @PostMapping
    public ResponseEntity<UserDTO> insertProduct(@Valid @RequestBody UserInsertDTO dto){
       UserDTO  newUser = userService.insertnewUser(dto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(newUser.getId()).toUri();
        return ResponseEntity.created(uri).body(newUser);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<UserDTO> updateCategory(@PathVariable Long id , @Valid @RequestBody UserDTO dto){
        dto = userService.updateUser(id , dto);
        return ResponseEntity.ok().body(dto);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deleteProductById(@PathVariable Long id){
        userService.deleteUserById(id);
        return ResponseEntity.noContent().build();
    }


}

