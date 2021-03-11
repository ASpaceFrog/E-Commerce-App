package storetest;

// JUnit imports
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

// Import classes from Store package
import store.Inventory;
import store.Product;
import store.ShoppingCart;
import store.StoreManager;


import java.util.ArrayList;
import java.util.HashMap;

/**
 * Class used to test StoreManager Class
 *
 * @author Stefan Lukic - 101156711, Filip Lukic - 101156713
 * @version 1.0
 */
class StoreManagerTest{

    @Test
    void testGetMyInventory() {
        StoreManager sm = new StoreManager();

        Product p1 = new Product("apple", 1, 2.00); // Same arguments as the Product in StoreManager
        Product p2 = new Product("orange",2,2.5); // Same arguments as the Product in StoreManager

        // Inventory's .equals method compares the Inventory objects, not the contents of the inventory objects
        // To test getMyInventory, we will check if the returned Inventory has the correct HashMaps as the hashMap .equals compares contents

        // Init two empty HashMap
        HashMap<Integer, Integer> pQuantity = new HashMap<>();
        HashMap<Integer, Product> pInfo = new HashMap<>();

        // Add products and stock to HashMaps (same contents as myInventory)
        pQuantity.put(1, 10);
        pInfo.put(1, p1);
        pQuantity.put(2 , 5);
        pInfo.put(2 , p2);

        // Init myInventory with same values
        Inventory myInventory = new Inventory(new Product[]{p1, p2}, new int[]{10,5});

        assertEquals(pQuantity, sm.getMyInventory().getProductQuantity());
        assertEquals(pInfo, sm.getMyInventory().getProductInfo());
    }

    @Test
    void testGetUserCarts() {
        StoreManager sm = new StoreManager();
        ArrayList<ShoppingCart> uc = new ArrayList<>();
        assertEquals(uc, sm.getUserCarts());
    }

    @Test
    void testCheckStock() {
        StoreManager sm = new StoreManager();
        Product p1 = new Product("apple", 1, 2.00); // Same arguments as the Product in StoreManager
        Product p2 = new Product("orange",2,2.5); // Same arguments as the Product in StoreManager
        Product p3 = new Product("banana", 3, 1.99); // Product not in StoreManager

        assertEquals(10, sm.checkStock(p1));
        assertEquals(5, sm.checkStock(p2));
        assertEquals(-1, sm.checkStock(p3));
    }

    @Test
    void testNewShoppingCart() {
        StoreManager sm = new StoreManager();
        assertEquals(0, sm.newShoppingCart()); // First ShoppingCart will have id 0
        assertEquals(1, sm.newShoppingCart()); // Next ShoppingCart will have id 1
    }

    @Test
    void testAddToCart() {
        StoreManager sm = new StoreManager();
        sm.newShoppingCart(); // Create new cart, id will be 0

        assertEquals(true, sm.addToCart(0, 1, 1)); // Product created by StoreManager
        assertEquals(false, sm.addToCart(0, 5, 5));
    }

    @Test
    void testRemoveFromCart() {
        StoreManager sm = new StoreManager();
        sm.newShoppingCart();  // Create new cart, id will be 0

        sm.addToCart(0, 1, 1);
        sm.addToCart(0, 2, 3);

        assertEquals(true, sm.removeFromCart(0, 1 , 1));
        assertEquals(false, sm.removeFromCart(0, 1, 1));
        assertEquals(true, sm.removeFromCart(0, 2, 2));

    }

    @Test
    void testEmptyCart() {
        StoreManager sm = new StoreManager();
        sm.newShoppingCart();  // Create new cart, id will be 0

        sm.addToCart(0, 1, 1);
        sm.addToCart(0, 2, 3);

        // ShoppingCart's Inventory will contain empty HashMaps once sm.emptyCart() is called
        // We will compare HashMaps again as Inventory does not have a proper .equals method

        sm.emptyCart(0);

        HashMap<Integer, Integer> pQuantity = new HashMap<>(); // Empty HashMap
        HashMap<Integer, Product> pInfo = new HashMap<>(); // Empty HashMap

        assertEquals(pQuantity, sm.getUserCarts().get(0).getUserCart().getProductQuantity());
        assertEquals(pInfo, sm.getUserCarts().get(0).getUserCart().getProductInfo());
    }
}