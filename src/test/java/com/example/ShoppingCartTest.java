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
        shoppingCart.addItem(new Item("Apple", 1.0, 2));
        assertEquals(1, shoppingCart.getItemCount());
    }

    @Test
    void removeAnItemFromTheShoppingCart() {
        shoppingCart.addItem(new Item("Orange", 1.0, 2));
        shoppingCart.removeItem("Orange");
        assertEquals(0, shoppingCart.getItemCount());
    }

    @Test
    void calculateTotalPriceOfTheItemsInTheShoppingCart() {
        shoppingCart.addItem(new Item("Banana", 2.0, 3));
        shoppingCart.addItem(new Item("Grapes", 3.0, 2));
        assertEquals(12.0, shoppingCart.calculateTotalPrice());
    }

    @Test
    void applyDiscountOnAnItem() {
        shoppingCart.addItem(new Item("Mango", 5.0, 2));
        shoppingCart.applyDiscount(0.1);
        assertEquals(9.0, shoppingCart.calculateTotalPrice());
    }

    @Test
    void updateQuantityOfAnItemInTheShoppingCart() {
        shoppingCart.addItem(new Item("Peach", 1.0, 2));
        shoppingCart.updateQuantity("Peach", 5);
        assertEquals(5.0, shoppingCart.calculateTotalPrice());
    }

    @Test
    void edgeCaseAddItemWithZeroOrNegativeQuantity() {
        shoppingCart.addItem(new Item("testItem", 2.0, 0));
        shoppingCart.addItem(new Item("testItem2", 2.0, -1));
        assertEquals(0, shoppingCart.getItemCount());
    }

    @Test
    void edgeCaseUpdatingWithNonExistingItem() {
        shoppingCart.updateQuantity("testItem", 3);
        assertEquals(0, shoppingCart.getItemCount());
    }

    @Test
    void edgeCaseRemoveItemThatDoesNotExist() {
        shoppingCart.removeItem("testItem");
        assertEquals(0, shoppingCart.getItemCount());
    }


    @Test
    void calculateTotalPrice_forEmptyCart_shouldReturnZero() {
        assertEquals(0.0, shoppingCart.calculateTotalPrice());
    }

}
