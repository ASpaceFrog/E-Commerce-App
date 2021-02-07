import java.util.HashMap;

public class Inventory {

    private HashMap<Integer, Integer> productQuantity = new HashMap<>(); // hashmap mapping id to quantities
    private HashMap<Integer, Product> productInfo = new HashMap<>();  // hashmap mapping id to Product

    /* no constructor needed, Hashmap's constructor is sufficient */

    /**
     * Get stock of given product id
     * @param id Product ID
     * @return Returns quantity of product. Returns -1 if no id entry exists
     */
    public int getStock(int id) {
        return productQuantity.getOrDefault(id, -1); //return -1 if no id entry exists
    }

    /**
     * Add stock of a given product, new products are allowed
     * @param myProduct Product of which stock is to be added
     * @param amount Amount of stock to add
     */
    public void addStock(Product myProduct, int amount) {
        if (productQuantity.getOrDefault(myProduct.getId(), -1) < 0){ //product does not already exist
            productInfo.put(myProduct.getId(), myProduct);
        }
        productQuantity.put(myProduct.getId(), productQuantity.getOrDefault(myProduct.getId(), 0) + amount); //add amount

    }

    /**
     * Remove stock of a given product id
     * @param id ID of stock to remove
     * @param amount Amount of stock to remove
     */
    public void removeStock(int id, int amount) {
        if (productQuantity.get(id) != null || productQuantity.get(id) != 0) {
            if (productQuantity.get(id) - amount < 0) {
                productQuantity.put(id, 0);
            } else {
                productQuantity.put(id, productQuantity.getOrDefault(id, 0) - amount);
            }
        }
    }

    /**
     * Return information of a given product id
     * @param id Product ID
     * @return  Returns Product containing product information
     */
    public Product getInfo(int id) {
        return productInfo.get(id);

    }

}
