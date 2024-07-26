package com.ra.service;

import com.ra.model.dto.request.ShopingCartRequest;
import com.ra.model.dto.request.ShoppingCartItemRequest;
import com.ra.model.entity.Product;
import com.ra.model.entity.ShopingCart;

import java.util.List;

public interface ShopingCartService {
    List<ShopingCart> getAll(Long userId);
    ShopingCart add(ShoppingCartItemRequest shoppingCartItemRequest, Long userId);

    ShopingCart findById(int id);

    ShopingCart save(ShopingCart shopingCart);

    void delete(int id);

    ShopingCart findByProductId(Long userId, Long productId);


}
