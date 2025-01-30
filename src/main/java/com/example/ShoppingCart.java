package com.example;

import java.util.ArrayList;
import java.util.List;

public class ShoppingCart {
    private List<CartItems> items;
    private double discount = 0.0;

    public void addItem(CartItems item) {
        items.add(item);
    }


}
