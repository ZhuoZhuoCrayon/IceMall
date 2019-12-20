package com.crayon.pojo;

public class ShoppingCart {
    private Integer cartId;
    private Integer userId;
    private Integer proListId;

    public ShoppingCart(){};

    public ShoppingCart(Integer userId, Integer proListId){
        this.proListId = proListId;
        this.userId = userId;
    }
    public Integer getProListId() {
        return proListId;
    }

    public Integer getUserId() {
        return userId;
    }

    public Integer getCartId() {
        return cartId;
    }

    public void setProListId(Integer proListId) {
        this.proListId = proListId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public void setCartId(Integer cartId) {
        this.cartId = cartId;
    }
}
