package com.thomasmarshall.scraping.model;

import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.ArrayList;

import static org.junit.Assert.*;

public class ShoppingListTest {

    ShoppingList shoppingList;
    ArrayList<Product> products;

    @Before
    public void setup() {
        shoppingList = new ShoppingList();
        products = new ArrayList<>();
        products.add(new Product("apple", new BigDecimal(5), 5, "bag of apples"));
        products.add(new Product("orange", new BigDecimal(6), 5, "creat of oranges"));
        products.add(new Product("pears", new BigDecimal(7), 5, "pair of pears"));
        shoppingList.setProducts(products);
        shoppingList.calculateTotal();
    }

    @Test
    public void calculateTotal() {
        assertEquals(new BigDecimal(18).setScale(2), shoppingList.getTotal().getGross());
    }

    @Test
    public void calculateVat() {
        assertEquals(new BigDecimal(3).setScale(2), shoppingList.getTotal().getVat());
    }

}