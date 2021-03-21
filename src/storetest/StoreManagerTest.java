package storetest;

// JUnit imports
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

// Import classes from Store package
import store.Product;
import store.ShoppingCart;
import store.StoreManager;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Class used to test StoreManager Class
 *
 * @author Filip Lukic - 101156713
 * @version 1.0
 */
class StoreManagerTest{

    private static StoreManager sm;

    @BeforeEach
    public void init(){
        sm = new StoreManager();
    }

    @Test
    public void testGetMyInventory() {
        new StoreManager();

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

        assertEquals(pQuantity, sm.getMyInventory().getProductQuantity(), "Field init in StoreManager is not working");
        assertEquals(pInfo, sm.getMyInventory().getProductInfo(), "Field init in StoreManager is not working");
    }

    @Test
    public void testGetUserCarts() {
        ArrayList<ShoppingCart> uc = new ArrayList<>();
        assertEquals(uc, sm.getUserCarts(), "Getter not working properly");
    }

    @Test
    public void testCheckStock() {
        Product p1 = new Product("apple", 1, 2.00); // Same arguments as the Product in StoreManager
        Product p2 = new Product("orange",2,2.5); // Same arguments as the Product in StoreManager
        Product p3 = new Product("banana", 3, 1.99); // Product not in StoreManager

        assertEquals(10, sm.checkStock(p1), "Field init in StoreManager is not working");
        assertEquals(5, sm.checkStock(p2), "Field init in StoreManager is not working");
        assertEquals(-1, sm.checkStock(p3), "Field init in StoreManager is not working");
    }

    @Test
    public void testNewShoppingCart() {
        assertEquals(0, sm.newShoppingCart(), "newShoppingCart not working"); // First ShoppingCart will have id 0
        assertEquals(1, sm.newShoppingCart(), "newShoppingCart not working"); // Next ShoppingCart will have id 1
    }

    @Test
    public void testAddToCart() {
        sm.newShoppingCart(); // Create new cart, id will be 0

        assertTrue(sm.addToCart(0, 1, 1), "Field init in StoreManager is not working or addToCart is not working"); // Product created by StoreManager
        assertFalse(sm.addToCart(0, 5, 5), "Field init in StoreManager is not working or addToCart is not working");
    }

    @Test
    public void testRemoveFromCart() {
        sm.newShoppingCart();  // Create new cart, id will be 0

        sm.addToCart(0, 1, 1);
        sm.addToCart(0, 2, 3);

        assertTrue(sm.removeFromCart(0, 1 , 1), "removeFromCart is not working or addToCart is not working");
        assertFalse(sm.removeFromCart(0, 1, 1), "removeFromCart is not working or addToCart is not working");
        assertTrue(sm.removeFromCart(0, 2, 2), "removeFromCart is not working or addToCart is not working");

    }

    @Test
    public void testEmptyCart() {
        sm.newShoppingCart();  // Create new cart, id will be 0

        sm.addToCart(0, 1, 1);
        sm.addToCart(0, 2, 3);

        // ShoppingCart's Inventory will contain empty HashMaps once sm.emptyCart() is called
        // We will compare HashMaps again as Inventory does not have a proper .equals method

        sm.emptyCart(0);

        HashMap<Integer, Integer> pQuantity = new HashMap<>(); // Empty HashMap
        HashMap<Integer, Product> pInfo = new HashMap<>(); // Empty HashMap

        assertEquals(pQuantity, sm.getUserCarts().get(0).getUserCart().getProductQuantity(), "Field init in StoreManager is not working or emptyCrt is not working");
        assertEquals(pInfo, sm.getUserCarts().get(0).getUserCart().getProductInfo(), "Field init in StoreManager is not working or emptyCrt is not working");
    }
}