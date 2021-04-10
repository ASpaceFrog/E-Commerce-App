package store;

import java.util.ArrayList;

/**
 * Manages a Store's inventory. Allows transactions for products to be made.
 *
 * @author Stefan Lukic - 101156711, Filip Lukic - 101156713
 * @version 1.1
 */
public class StoreManager {
    //init store.Inventory to some default values
    private Inventory myInventory;
    private ArrayList<ShoppingCart> userCarts = new ArrayList<>();

    public StoreManager() {
        Product[] products = new Product[]{new Product("apple", 1, 2.00),
                new Product("orange", 2, 2.5),
                new Product("banana", 3, 2.75),
                new Product("mango", 4, 3),
                new Product("grape", 5, 3),
                new Product("pineapple", 6, 5),
                new Product("kiwi", 7, 4),
                new Product("peach", 8, 3.5),
                new Product("pear", 9, 2.5),
                new Product("watermelon", 10, 6),
                new Product("water malone", 11, 20)
        };
        int[] stock = new int[]{10, 15, 20, 20, 30, 40, 20, 20, 20, 20, 20};
        myInventory = new Inventory(products, stock);
    }

    /**
     * Get the store's inventory
     *
     * @return store.Inventory, store's inventory
     */
    public Inventory getMyInventory() {
        return myInventory;
    }

    /**
     * @return ArrayList<store.ShoppingCart>, ArrayList of userCarts
     */
    public ArrayList<ShoppingCart> getUserCarts() {
        return userCarts;
    }

    /**
     * Check how much stock of a given store.Product is in the store.Inventory
     *
     * @param myProduct store.Product, store.Product to check
     * @return int, Returns amount of stock for given product. If product does not exist in inventory, returns -1.
     */
    public int checkStock(Product myProduct) {
        return myInventory.getProductQuantity(myProduct);
    }

    /**
     * Create a new Shopping Cart
     *
     * @return int, unique cart id
     */
    public int newShoppingCart() {
        userCarts.add(new ShoppingCart());
        return userCarts.size() - 1; //Index of new shopping cart
    }

    /**
     * Add an existing product to a shopping Cart
     * Removes stock from Store inventory and transfers stock to cart inventory
     *
     * @param cartID         int, Cart ID of the desired cart
     * @param myProduct  Product, store.Product ID of product to be added
     * @param quantity       int, amount of stock to add to the cart
     * @return return true if valid product ID's and stock levels are passed,
     * else return false
     */
    public boolean addToCart(int cartID, Product myProduct, int quantity) {
        if (myInventory.getProductQuantity(myProduct) == -1 || myInventory.getProductQuantity(myProduct) - quantity < 0) {
            return false;
        } else {
            userCarts.get(cartID).addProductQuantity(myProduct, quantity);
            myInventory.removeProductQuantity(myProduct, quantity);
            return true;
        }
    }

    /**
     * Remove an existing product from a shopping cart
     * Removes stock from cart inventory and transfers stock to store inventory
     *
     * @param cartID    int, Cart ID of the desired cart
     * @param myProduct Product, store.Product ID of product to be removed
     * @param quantity  int, amount of removed from the cart
     * @return return true if valid product ID's and stock levels are passed,
     * else return false
     */
    public boolean removeFromCart(int cartID, Product myProduct, int quantity) {
        if (userCarts.get(cartID).getProductQuantity(myProduct) - quantity < 0) {
            return false;
        } else {
            userCarts.get(cartID).removeProductQuantity(myProduct, quantity);
            myInventory.addProductQuantity(myProduct, quantity);
            return true;
        }

    }

    /**
     * Removes all products from a cart and returns them to the store inventory
     *
     * @param cartID int, Cart to empty
     */
    public void emptyCart(int cartID) {
        int amount;
        Product[] allProducts = myInventory.getProducts();

        for (Product p : allProducts) {
            amount = userCarts.get(cartID).getProductQuantity(p);
            removeFromCart(cartID, p, amount);
        }
    }
}
