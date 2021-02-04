import java.util.HashMap;

public class Inventory {

    private HashMap<String, ProductStock> productInventory = new HashMap<>();

    public HashMap<String, ProductStock> getProducts() {
        return productInventory;
    }

    public void setProducts(HashMap<String, ProductStock> productStock) {
        this.productInventory = productStock;
    }

    public int getStock(String id) {
        return productInventory.get(id).getQuantity();
    }

    public void addStock(String id, int amount) {
        productInventory.get(id).setQuantity( productInventory.get(id).getQuantity() + amount);
    }

    public void removeStock(String id, int amount) {
        if (productInventory.get(id) == null || productInventory.get(id).getQuantity() == 0) {
            return;
        }
        else if (productInventory.get(id).getQuantity() - amount < 0) {
            productInventory.get(id).setQuantity(0);
        }
        else {
            productInventory.get(id).setQuantity( productInventory.get(id).getQuantity() - amount);
        }
    }

    public Product getInfo(String id){
        return productInventory.get(id).getProduct();

    }

}
