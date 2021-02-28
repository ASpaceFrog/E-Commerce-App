import java.util.ArrayList;

/**
 * Manages a Store's inventory. Allows transactions for products to be made.
 *
 * @author Stefan Lukic - 101156711, Filip Lukic - 101156713
 * @version 1.1
 */
public class StoreManager {
    private Inventory myInventory = new Inventory();
    private ArrayList<ShoppingCart> userCarts = new ArrayList<>();

    /**
     * Get the store's inventory
     *
     * @return Inventory, store's inventory
     */
    public Inventory getMyInventory() {
        return myInventory;
    }

    /**
     * Check how much stock of a given Product is in the Inventory
     *
     * @param myProduct Product, Product to check
     * @return int, Returns amount of stock for given product. If product does not exist in inventory, returns -1.
     */
    public int checkStock(Product myProduct) {
        return myInventory.getStock(myProduct.getId());
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

    public boolean addToCart(int cartID, int productID, int quantity) {
        if (myInventory.getStock(productID) == -1 || myInventory.getStock(productID) - quantity < 0) {
            return false;
        } else {
            userCarts.get(cartID).addItemToCart(myInventory.getInfo(productID), quantity);
            myInventory.removeStock(productID, quantity, false);
            return true;
        }
    }

    public boolean removeFromCart(int cartID, int productID, int quantity) {
        if (userCarts.get(cartID).getUserCart().getStock(productID) - quantity < 0) {
            return false;
        } else {
            userCarts.get(cartID).removeItemFromCart(productID, quantity);
            myInventory.addStock(myInventory.getInfo(productID), quantity);
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
        for (int i : userCarts.get(cartID).getUserCart().getProductQuantity().keySet()) {
            amount = userCarts.get(cartID).getUserCart().getStock(i);
            removeFromCart(cartID, i, amount); //removes stock and returns to inv
        }
    }

    /**
     * Removes items from a shopping cart and presents the total cost
     *
     * @param cartID int, Cart ID
     */
    public boolean processTransaction(int cartID) {
        String s;

        printCartInventory(cartID);
        System.out.println("Total Cost: " + userCarts.get(cartID).getTotalPrice());
        System.out.println("Would you like to checkout this cart (Y/N)?");

        String[] yn = new String[]{"Y", "N"};
        s = UserInput.getStringInput(yn);
        if (s.equals("Y")) {
            userCarts.get(cartID).getUserCart().getProductQuantity().clear();
            userCarts.get(cartID).getUserCart().getProductInfo().clear();
            return true;
        } else {
            return false;
        }
    }

    public void printInventory() {
        System.out.println("|--------------------THE COURSE STORE--------------------|");
        System.out.println("\\------------------------------------------------------- /");
        System.out.println("Type 'help' for a list of commands.\n");
        System.out.println(" ID | PRODUCT NAME | PRODUCT PRICE | STOCK");

        for (int i : myInventory.getProductQuantity().keySet()) {
            System.out.printf("%d | %s | %f | %d\n", i, myInventory.getInfo(i).getName(), myInventory.getInfo(i).getPrice(), myInventory.getStock(i));
        }
        System.out.print("\n\n");
    }

    public void printCartInventory(int cartID) {
        System.out.println("|--------------------------CART--------------------------|");
        System.out.println("\\------------------------------------------------------- /");
        System.out.println("Type 'help' for a list of commands.\n");
        System.out.println(" ID | PRODUCT NAME | PRODUCT PRICE | STOCK");

        for (int i : userCarts.get(cartID).getUserCart().getProductQuantity().keySet()) {
            System.out.printf("%d | %s | %f | %d\n", i, userCarts.get(cartID).getUserCart().getInfo(i).getName(),
                    userCarts.get(cartID).getUserCart().getInfo(i).getPrice(),
                    userCarts.get(cartID).getUserCart().getStock(i));
        }
        System.out.print("\n\n");
    }
}
