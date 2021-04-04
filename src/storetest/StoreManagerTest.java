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

/**
 * Class used to test StoreManager Class
 *
 * @author Filip Lukic - 101156713
 * @version 1.0
 */
class StoreManagerTest{

    private static StoreManager sm;

    /**
     * Reset StoreManager sm to its default state before each test
     */
    @BeforeEach
    public void init(){
        sm = new StoreManager();
    }

    /**
     * Test getUserCarts method in the StoreManager Class
     */
    @Test
    public void testGetUserCarts() {
        ArrayList<ShoppingCart> uc = new ArrayList<>();
        assertEquals(uc, sm.getUserCarts(), "Getter not working properly");
    }

    /**
     * Test method to check the stock of a product in the StoreManager Class
     */
    @Test
    public void testCheckStock() {
        Product p1 = new Product("apple", 1, 2.00); // Same arguments as the Product in StoreManager
        Product p2 = new Product("orange",2,2.5); // Same arguments as the Product in StoreManager
        Product p3 = new Product("banana", 3, 1.99); // Product not in StoreManager

        assertEquals(10, sm.checkStock(p1), "Field init in StoreManager is not working");
        assertEquals(15, sm.checkStock(p2), "Field init in StoreManager is not working");
        assertEquals(20, sm.checkStock(p3), "Field init in StoreManager is not working");
    }

    /**
     * Test creating a new shopping cart in the StoreManager Class
     */
    @Test
    public void testNewShoppingCart() {
        assertEquals(0, sm.newShoppingCart(), "newShoppingCart not working"); // First ShoppingCart will have id 0
        assertEquals(1, sm.newShoppingCart(), "newShoppingCart not working"); // Next ShoppingCart will have id 1
    }

    /**
     * Test adding a specified amount of a product to a cart in the StoreManager Class
     */
    @Test
    public void testAddToCart() {
        sm.newShoppingCart(); // Create new cart, id will be 0

        assertTrue(sm.addToCart(0, 1, 1), "Field init in StoreManager is not working or addToCart is not working"); // Product created by StoreManager
        assertFalse(sm.addToCart(0, 20, 5), "Field init in StoreManager is not working or addToCart is not working");
    }

    /**
     * Test removing a specified amount of a product to a cart in the StoreManager Class
     */
    @Test
    public void testRemoveFromCart() {
        sm.newShoppingCart();  // Create new cart, id will be 0

        sm.addToCart(0, 1, 1);
        sm.addToCart(0, 2, 3);

        assertTrue(sm.removeFromCart(0, 1 , 1), "removeFromCart is not working or addToCart is not working");
        assertFalse(sm.removeFromCart(0, 1, 1), "removeFromCart is not working or addToCart is not working");
        assertTrue(sm.removeFromCart(0, 2, 2), "removeFromCart is not working or addToCart is not working");

    }

    /**
     * Test emptying a cart in the StoreManager Class
     */

    @Test
    public void testEmptyCart() {
        sm.newShoppingCart();  // Create new cart, id will be 0

        sm.addToCart(0, 1, 1);
        sm.addToCart(0, 2, 3);

        sm.emptyCart(0);
        Integer[] stock = new Integer[]{10, 15};
        for (int i = 0; i < 2; i++){
            assertEquals(stock[i], sm.getMyInventory().getStock(i+1), "EmptyCart does not work");
        }

    }

}