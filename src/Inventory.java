import java.util.HashMap;

public class Inventory {

    private HashMap<String, Integer> productQuantity = new HashMap<>(); // hashmap mapping id to quantities
    private HashMap<String, Product> productInfo = new HashMap<>();

    /* no constructor needed, Hashmap's constructor is sufficient */

    public int getStock(String id) {
        return productQuantity.getOrDefault(id, -1); //return 0 if no id entry exists
    }

    public void addStock(String id, int amount) {
        productQuantity.put(id, productQuantity.getOrDefault(id, 0) + amount);
    }

    public void removeStock(String id, int amount) {
        if (productQuantity.get(id) != null || productQuantity.get(id) != 0) {
            if (productQuantity.get(id) - amount < 0) {
                productQuantity.put(id, 0);
            } else {
                productQuantity.put(id, productQuantity.getOrDefault(id, 0) - amount);
            }
        }
    }

    public Product getInfo(String id) {
        return productInfo.get(id);

    }

}
