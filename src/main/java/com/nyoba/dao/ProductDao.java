package com.nyoba.dao;

import com.nyoba.model.Product;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * Created by Andre Luckyanto on 02/23/16.
 */
public interface ProductDao extends PagingAndSortingRepository<Product, String> {
}