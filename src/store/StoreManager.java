package store;

import java.util.ArrayList;
import java.util.Set;

/**
 * Manages a Store's inventory. Allows transactions for products to be made.
 *
 * @author Stefan Lukic - 101156711, Filip Lukic - 101156713
 * @version 1.1
 */
public class StoreManager {
    //init store.Inventory to some default values
    private Inventory myInventory = new Inventory(new Product[]{new Product("apple", 1, 2.00), new Product("orange",2,2.5)}, new int[]{10,5});
    private ArrayList<ShoppingCart> userCarts = new ArrayList<>();

    /**
     * Get the store's inventory
     *
     * @return store.Inventory, store's inventory
     */
    public Inventory getMyInventory() {
        return myInventory;
    }

    /**
     *
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
        return myInventory.getStock(myProduct.getID());
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
     * @param cartID int, Cart ID of the desired cart
     * @param productID int, store.Product ID of product to be added
     * @param quantity int, amount of stock to add to the cart
     * @return return true if valid product ID's and stock levels are passed,
     *         else return false
     */
    public boolean addToCart(int cartID, int productID, int quantity) {
        if (myInventory.getStock(productID) == -1 || myInventory.getStock(productID) - quantity < 0) {
            return false;
        } else {
            userCarts.get(cartID).addItemToCart(myInventory.getInfo(productID), quantity);
            myInventory.removeStock(productID, quantity, false);
            return true;
        }
    }

    /**
     * Remove an existing product from a shopping cart
     * Removes stock from cart inventory and transfers stock to store inventory
     *
     * @param cartID int, Cart ID of the desired cart
     * @param productID int, store.Product ID of product to be removed
     * @param quantity int, amount of removed from the cart
     * @return return true if valid product ID's and stock levels are passed,
     *         else return false
     */
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
        Set<Integer> hashSet = userCarts.get(cartID).getUserCart().getProductQuantity().keySet();
        Integer[] keys = new Integer[hashSet.size()];
        hashSet.toArray(keys);

        for (int i : keys) {
            amount = userCarts.get(cartID).getUserCart().getStock(i);
            removeFromCart(cartID, i, amount); //removes stock and returns to inv
        }
    }

    /**
     * Prompts the user the checkout their cart.
     * Removes items from a shopping cart and presents the total cost.
     *
     * @param cartID int, Cart ID
     *
     * @return boolean, false if user aborts checkout, else returns true.
     */
    public boolean processTransaction(int cartID) {
        String s;

        userCarts.get(cartID).printCartInventory();
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

    /**
     * Display the Store's store.Inventory
     */
    public void printInventory() {
        System.out.println("|--------------------THE COURSE STORE--------------------|");
        System.out.println("\\------------------------------------------------------- /");
        System.out.println("Type 'help' for a list of commands.\n");
        System.out.println(" ID | PRODUCT NAME | PRODUCT PRICE | STOCK");

        for (int i : myInventory.getProductQuantity().keySet()) {
            System.out.printf("%d | %s | %f | %d\n", i, myInventory.getInfo(i).getNAME(), myInventory.getInfo(i).getPRICE(), myInventory.getStock(i));
        }
        System.out.print("\n\n");
    }
}
