package storetest;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import store.Inventory;
import store.Product;

import java.util.HashMap;

/**
 * Test Class for Inventory
 *
 * @author Stefan Lukic - 101156711
 */
class InventoryTest {
    private static Product p1;
    private static Product p2;
    private static Product[] products;
    private static int[] quantities;
    private static Inventory inv;


    /**
     * Create 2 products that we will use for testing, an array containing the products, and an array containing the quantities
     */
    @BeforeAll
    public static void init() {
        p1 = new Product("book", 0, 10);
        p2 = new Product("coffee", 3, 500);
        products = new Product[]{p1, p2};
        quantities = new int[]{15, 100};
    }

    /**
     * Create an Inventory with products p1 and p2 from above before each test
     */
    @BeforeEach
    public void initInv() {
        inv = new Inventory(products, quantities);
    }

    /**
     * Test getProductQuantity by comparing it with an equivalent hashmap
     */
    @Test
    public void TestGetProductQuantity() {
        HashMap<Integer, Integer> h1 = new HashMap<>();

        h1.put(0, 15);
        h1.put(3, 100);

        assertEquals(h1, inv.getProductQuantity(), "inv constructor or inv.getProductQuantity() is not working!");
    }

    /**
     * Test getProductInfo by comparing it with an equivalent hashmap
     */
    @Test
    public void TestGetProductInfo() {
        HashMap<Integer, Product> h2 = new HashMap<>();

        h2.put(0, p1);
        h2.put(3, p2);

        assertEquals(h2, inv.getProductInfo(), "inv constructor or inv.getProductInfo() is not working!");
    }

    /**
     * Test getStock to see if it returns the correct value
     */
    @Test
    public void TestGetStock() {
        assertEquals(15, inv.getStock(0), "inv.getStock is not returning the correct value!");
        assertEquals(100, inv.getStock(3), "inv.getStock is not returning the correct value!");
        assertEquals(-1, inv.getStock(2), "inv.getStock is not returning the correct value!");
    }

    /**
     * Test addStock to see if it properly adds the right stock to an inventory
     */
    @Test
    public void TestAddStock() {
        Inventory inv2 = new Inventory();
        inv2.addStock(p1, 15);
        inv2.addStock(p2, 100);

        assertEquals(15, inv2.getStock(0), "inv.addStock() is not adding the correct amount of stock!");
        assertEquals(100, inv2.getStock(3), "inv.addStock() is not adding the correct amount of stock!!");
    }

    /**
     * Test removeStock to see if it properly removes the correct amount of stock and deletes the product if requested
     */
    @Test
    public void TestRemoveStock() {
        inv.removeStock(0, 3, false);
        assertEquals(12, inv.getStock(0), "inv.removeStock() is not removing the correct amount of stock!");

        inv.removeStock(0, 3, true);
        assertEquals(9, inv.getStock(0), "inv.removeStock() is not removing the correct amount of stock!");

        inv.removeStock(0, 9, true);
        assertEquals(-1, inv.getStock(0), "inv.removeStock() is not removing products when it should be!");

        inv.removeStock(3, 999, false);
        assertEquals(0, inv.getStock(3), "inv.removeStock() is removing products when it should not be doing so!");
    }

    /**
     * Check to see if remove product removes products from an inventory
     */
    @Test
    public void TestRemoveProduct() {
        inv.removeProduct(-42); //this should not crash the program

        inv.removeProduct(0);
        assertEquals(-1, inv.getStock(0), "inv.removeProduct() is not removing products or is returning the wrong value!");
    }

    /**
     * Check if getInfo is returning the correct information
     */
    @Test
    public void TestGetInfo() {
        assertNull(inv.getInfo(-42), "inv.getInfo() is not working!");
        assertEquals(p1, inv.getInfo(0), "inv.getInfo() is not working!");
    }
}