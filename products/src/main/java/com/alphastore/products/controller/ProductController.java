package com.alphastore.products.controller;

import com.alphastore.utils.pojo.APIResponse;
import com.alphastore.utils.service.APIResponseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    @Autowired
    private APIResponseService apiResponseService;

    @GetMapping("/ping")
    public APIResponse ping() {
        return apiResponseService.buildAPIResponse(HttpStatus.OK, "products module is up");
    }

    @GetMapping("/sample")
    public APIResponse sample() {
        List<Map<String, Object>> products = List.of(
                Map.of("id", 1, "name", "Laptop", "price", 55000),
                Map.of("id", 2, "name", "Mouse", "price", 499)
        );
        return apiResponseService.buildAPIResponse(HttpStatus.OK, (Object) products);
    }
}
