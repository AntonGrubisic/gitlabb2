package com.example;

import java.util.ArrayList;
import java.util.List;

public class ShoppingCart {
    private final List<Item> items;

    public ShoppingCart() {
        items = new ArrayList<Item>();
        double discount = 0.0;
    }

    public void addItem(Item item) {
        if (item.getQuantity() > 0) {
            items.add(item);
        }
    }

    public void removeItem(String itemName) {
        items.removeIf(item -> item.getItemName().equals(itemName));
    }


    public int getItemCount() {
        return items.size();
    }
}
