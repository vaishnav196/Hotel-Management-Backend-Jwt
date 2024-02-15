package com.HotelManagement.Controller;

import com.HotelManagement.Repository.CartRepo;
import com.HotelManagement.Repository.ProductRepo;
import com.HotelManagement.Repository.UserRepo;
import com.HotelManagement.model.Cart;
import com.HotelManagement.model.OfferCoupon;
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
    public String addToCart(@PathVariable("cartId") int cartId, @PathVariable("productId") int productId){
        double totalPrice=0;
        int quantity=0;
//        double discount=0;
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
        for(Product pr:cart.getProduct()){
            totalPrice= totalPrice +(pr.getPrice());
            quantity++;
        }


        cart.setTotalPrice(totalPrice);
      cart.setQuantity(quantity);
        cartRepo.save(cart);


        return "Successfully added to cart ";

    }

    @DeleteMapping("{cartId}/product/{productId}")
    public String  deleteFromCart(@PathVariable("productId") int productId,@PathVariable("cartId") int cartId){
        double totalPriceNew = 0;

        Cart cart =cartRepo.findById(cartId).orElse(null);


        Product product=productRepo.findById(productId).orElse(null);
        cart.getProduct().remove(product);
        int quantity=cart.getQuantity();
         quantity=quantity-1;
        double totalPrice=cart.getTotalPrice();

        cartRepo.save(cart);
         for(Product pr:cart.getProduct()){
             totalPrice=(totalPrice)-(pr.getPrice());

       }

        cart.setTotalPrice(totalPrice);
        cart.setQuantity(quantity);

        if(totalPrice < 0 || quantity < 0){
            cart.setTotalPrice(0);
            cart.setQuantity(0);
        }

        if(quantity==0 &&totalPrice!=0){
            cart.setTotalPrice(0);
        }
        cartRepo.save(cart);

        return "product Removed From Cart";
    }


//    @DeleteMapping("{cartId}/product/{productId}")
//    public String  deleteFromCart(@PathVariable("productId") int productId,@PathVariable("cartId") int cartId){
//        double totalPriceNew = 0;
//        Cart cart =cartRepo.findById(cartId).orElse(null);
//        double totalPrice=cart.getTotalPrice();
//        int quantity=cart.getQuantity();
//
//        Product product=productRepo.findById(productId).orElse(null);
//        cart.getProduct().remove(product);
//        cartRepo.save(cart);
//        for(Product pr:cart.getProduct()){
//            totalPrice=(totalPriceNew)+(pr.getPrice());
//            quantity=quantity-1;
//        }
//        cart.setTotalPrice(totalPrice);
//        cart.setQuantity(quantity);
//
//        if(totalPrice < 0 || quantity < 0){
//            cart.setTotalPrice(0);
//            cart.setQuantity(0);
//        }
//        cartRepo.save(cart);
//
//        return "product Removed From Cart";
//    }

}
