package com.nyoba.controller;

import com.nyoba.dao.ProductDao;
import com.nyoba.exception.DataNotFoundException;
import com.nyoba.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

/**
 * Created by Andre Luckyanto on 02/23/16.
 */
@RestController
@RequestMapping("/api/product")
@Transactional(readOnly = true)
public class ProductController {
    @Autowired
    private ProductDao productDao;

    @RequestMapping(value = "/", method = RequestMethod.POST)
    @Transactional(readOnly = false)
    public ResponseEntity<Void> create(@RequestBody @Valid Product p, UriComponentsBuilder uriBuilder) {
        productDao.save(p);
        URI location = uriBuilder.path("/api/product/{id}")
                .buildAndExpand(p.getId()).toUri();
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(location);
        return new ResponseEntity<>(headers, HttpStatus.CREATED);
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public Page<Product> findAll(Pageable page) {
        return productDao.findAll(page);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Product findById(@PathVariable("id") Product p) {
        if (p == null) {
            throw new DataNotFoundException("No data with the specified id");
        }

        return p;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.OK)
    @Transactional(readOnly = false)
    public void update(@PathVariable("id") String id, @RequestBody @Valid Product p) {
        if (!productDao.exists(id)) {
            throw new DataNotFoundException("No data with the specified id");
        }
        p.setId(id);
        productDao.save(p);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.OK)
    @Transactional(readOnly = false)
    public void delete(@PathVariable("id") String id) {
        if (!productDao.exists(id)) {
            throw new DataNotFoundException("No data with the specified id");
        }
        productDao.delete(id);
    }
}
