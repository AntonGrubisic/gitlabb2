package com.example;

import java.util.ArrayList;
import java.util.List;

public class ShoppingCart {
    private final List<Item> items;
    double discount = 0.0;

    public ShoppingCart() {
        items = new ArrayList<Item>();

    }

    public void addItem(Item item) {
        if (item.getQuantity() > 0) {
            items.add(item);
        }
    }


    public void removeItem(String itemName) {
        items.removeIf(item -> item.getItemName().equals(itemName));
    }
    public double calculateTotalPrice() {
        double total = 0.0;
        for (Item item : items) {
            total += item.getPrice() * item.getQuantity();
        }
        return total * (1 - discount);
    }
    public void applyDiscount(double discount) {
        if (discount >= 0.0 && discount <= 1.0) {
            this.discount = discount;
        }
    }

    public int getItemCount() {
        return items.size();
    }
}
