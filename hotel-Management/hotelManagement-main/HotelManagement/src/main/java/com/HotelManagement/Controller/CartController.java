package com.HotelManagement.Controller;

import com.HotelManagement.Repository.CartRepo;
import com.HotelManagement.Repository.ProductRepo;
import com.HotelManagement.Repository.UserRepo;
import com.HotelManagement.model.Cart;
import com.HotelManagement.model.Product;
import com.HotelManagement.model.User;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@RestController
public class CartController {

@Autowired
private ProductRepo productRepo;

@Autowired
private UserRepo userRepo;

@Autowired
private  CartRepo cartRepo;

    @GetMapping("/cart")
    public List<Cart> getCartProduct(){
        List<Cart> allProduct=cartRepo.findAll();
        return allProduct;
    }

    @GetMapping("/cart/{cartId}")
    public Cart getCartProduct(@PathVariable("cartId") int cartId,Product product){
        Cart Cartproduct=cartRepo.findById(cartId).orElse(null);
        Cartproduct.getProduct();
        cartRepo.save(Cartproduct);
        return Cartproduct;
    }


    @PostMapping("/{cartId}/product/{productId}")

    public String addToCart(@PathVariable("cartId") int cartId,@PathVariable("productId") int productId){
        Cart cart= cartRepo.findById(cartId).orElse(null);
        if(cart==null){
            return "Nothing is there in Cart";
        }
        Product product=productRepo.findById(productId).orElse(null);
        if(product==null){
            return "Product not Found";
        }
        cart.getProduct().add(product);
        cartRepo.save(cart);



        return "Successfully added to cart ";

    }





    @DeleteMapping("{cartId}/product/{productId}")
    public String  deleteFromCart(@PathVariable("productId") int productId,@PathVariable("cartId") int cartId){
        Cart cart =cartRepo.findById(cartId).orElse(null);
        Product product=productRepo.findById(productId).orElse(null);
        cart.getProduct().remove(product);

        cartRepo.save(cart);

        return "product Removed From Cart";
    }














}
