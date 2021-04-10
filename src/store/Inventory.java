package store;

import java.util.HashMap;
import java.util.Set;

public class Inventory implements ProductStockContainer {
    private HashMap<Product, Integer> products; // hashmap mapping products to stock levels

    /**
     * Default constructor - init to empty hashmap
     */
    public Inventory() {
        products = new HashMap<>();
    }

    /**
     * Init an inventory given an array of products and their corresponding quantities
     *
     * @param myProducts Product[], array of Products
     * @param quantities int[], array of Product stock
     */
    public Inventory(Product[] myProducts, int[] quantities) {
        products = new HashMap<>();

        if (myProducts != null && quantities != null && myProducts.length == quantities.length) {
            for (int i = 0; i < myProducts.length; i++) {
                products.put(myProducts[i], quantities[i]);
            }
        }
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
     * Add stock to a given product
     *
     * @param myProduct Product, corresponding product
     * @param amount    int, amount of stock to be added
     */
    public void addProductQuantity(Product myProduct, int amount) {
        products.put(myProduct, products.getOrDefault(myProduct, 0) + amount);
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