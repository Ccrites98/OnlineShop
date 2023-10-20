package com.pluralsight;
import java.util.List;
import java.util.ArrayList;

public class ShoppingCart {
    private List<Product> items;
    private List<Product> checkoutItems;

    private List<Product> cartItems;


    public ShoppingCart() {
        items = new ArrayList<>();
    }

    public void addItem(Product product) {
        items.add(product);
    }

    public void removeItem(int index) {
        items.remove(index);
    }

    public List<Product> getItems() {
        return items;
    }

    public double getTotalPrice() {
        double totalPrice = 0.0;
        for (Product product : items) {
            totalPrice += product.getPrice();
        }
        return totalPrice;
    }

    public void removeItem(Product productToRemove) {
    }

    public List<Product> getCheckoutItems() {
        return checkoutItems;
    }
    public void checkoutCart() {
        checkoutItems.addAll(cartItems);
        cartItems.clear();
    }
}
