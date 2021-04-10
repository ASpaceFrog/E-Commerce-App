package store;

import java.util.Set;

public interface ProductStockContainer {

    /**
     * Gets the quantity of a given product
     *
     * @param myProduct Product, corresponding product
     * @return int, Returns stock of the current product. Return -1 if product does not exist.
     */
    int getProductQuantity(Product myProduct);

    /**
     * Add stock to a given product
     *
     * @param myProduct Product, corresponding product
     * @param amount    int, amount of stock to be added
     */
    void addProductQuantity(Product myProduct, int amount);

    /**
     * Remove stock from a given product
     *
     * @param myProduct Product, corresponding product
     * @param amount    int, amount of stock to be removed
     * @return Returns true if the desired amount of stock can be removed. Returns false otherwise.
     */
    boolean removeProductQuantity(Product myProduct, int amount);

    /**
     * Get the number of products contained in this class
     *
     * @return int, number of products
     */
    int getNumOfProducts();

    /**
     * Get all the products in a hashmap
     * @return Product[], return an array containing all products in a hashmap
     */
    Product[] getProducts();
}
