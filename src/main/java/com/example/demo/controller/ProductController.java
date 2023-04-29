package com.example.demo.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entity.Product;


@RestController
@RequestMapping ("api/v1/product")
public class ProductController {

    private List<Product> products;
    private int sequencialId=0;

    public ProductController(){
        products = new ArrayList<Product>();
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Product> get(@PathVariable int id){
        Product result = products.stream().filter(i->i.getId()==id).findFirst().orElse(null);
        if (result==null) return new ResponseEntity<Product>(HttpStatus.NOT_FOUND);
        return new ResponseEntity<Product>(result,  HttpStatus.OK);
    }
    @PostMapping ("/")
    public Product create (@RequestBody Product product){
        sequencialId++;
        product.setId(sequencialId);
        products.add(product);
        return product;
    }
    @GetMapping("/")
    public List<Product> getAll(){
        return products;
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable int id){
        Product result = products.stream().filter(i->i.getId()==id).findFirst().orElse(null);
        if (result==null) return ResponseEntity.notFound().build();
        products.remove(result);
        return ResponseEntity.ok().build();
    }
}
