package store;

import java.util.HashMap;
import java.util.Set;

/**
 * A User's shopping cart
 *
 * @author Stefan Lukic - 101156711, Filip Lukic - 101156713
 * @version 2.0
 */
public class ShoppingCart implements ProductStockContainer {
    private final HashMap<Product, Integer> products; // hashmap mapping products to stock levels
    private double totalPrice;

    /**
     * Default Constructor for Shipping Cart
     */
    public ShoppingCart() {
        this.products = new HashMap<>();
        this.totalPrice = 0.00;
    }

    /**
     * Gets the quantity of a given product
     *
     * @param myProduct Product, corresponding product
     * @return int, Returns stock of the current product. Return -1 if product does not exist.
     */
    public int getProductQuantity(Product myProduct) {
        if (products.get(myProduct) == null) { //product does not already exist
            return -1;
        } else {
            return products.get(myProduct);
        }
    }

    /**
     * Returns the total price of the shopping cart
     * @return double, total price of the shopping cart
     */
    public double getTotalPrice() {
        return totalPrice;
    }

    /**
     * Add stock to a given product
     *
     * @param myProduct Product, corresponding product
     * @param amount    int, amount of stock to be added
     */
    public void addProductQuantity(Product myProduct, int amount) {
        products.put(myProduct, products.getOrDefault(myProduct, 0) + amount);
        this.totalPrice += myProduct.getPRICE() * amount;
    }

    /**
     * Remove stock from a given product
     *
     * @param myProduct Product, corresponding product
     * @param amount    int, amount of stock to be removed
     * @return Returns true if the desired amount of stock can be removed. Returns false otherwise.
     */
    public boolean removeProductQuantity(Product myProduct, int amount) {
        if (products.get(myProduct) != null) {
            if (products.get(myProduct) - amount >= 0) {
                products.put(myProduct, products.get(myProduct) - amount);
                this.totalPrice -= myProduct.getPRICE() * amount;
                return true;
            }
        }
        return false;
    }

    /**
     * Get the number of products contained in this class
     *
     * @return int, number of products
     */
    public int getNumOfProducts() {
        return products.size();
    }

    /**
     * Removes (deletes) all items from this cart
     */
    public void clearCart(){
        products.clear();
    }

    /**
     * Get all the products in a hashmap
     * @return Product[], return an array containing all products in a hashmap
     */
    public Product[] getProducts() {
        Set<Product> hashSet = products.keySet();
        Product[] allProducts = new Product[hashSet.size()];
        hashSet.toArray(allProducts);
        return allProducts;
    }
}
