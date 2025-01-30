package com.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ShoppingCartTest {
    private ShoppingCart shoppingCart;

    @BeforeEach
    void setUp() {
        shoppingCart = new ShoppingCart();
    }

    @Test
    void testToAddAnItemToTheShoppingCart() {
        shoppingCart.addItem(new Item("Apple", 1, 2));
        assertEquals(1, shoppingCart.getItemCount());
    }

    @Test
    void removeAnItemFromTheShoppingCart() {
        shoppingCart.addItem(new Item("Orange", 1, 2));
        shoppingCart.removeItem("Orange");
        assertEquals(0, shoppingCart.getItemCount());
    }


}
