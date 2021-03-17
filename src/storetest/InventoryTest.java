package storetest;

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

    @BeforeEach
    public void init() {
        p1 = new Product("book", 0, 10);
        p2 = new Product("coffee", 3, 500);
        products = new Product[]{p1, p2};
        quantities = new int[]{15, 100};
    }

    @Test
    public void getProductQuantity() {
        HashMap<Integer, Integer> h1 = new HashMap<>();
        Inventory inv = new Inventory(products, quantities);

        h1.put(0, 15);
        h1.put(3, 100);

        assertEquals(h1, inv.getProductQuantity());
    }

    @Test
    public void getProductInfo() {
        HashMap<Integer, Product> h2 = new HashMap<>();
        Inventory inv = new Inventory(products, quantities);

        h2.put(0, p1);
        h2.put(3, p2);

        assertEquals(h2, inv.getProductInfo());
    }

    @Test
    public void getStock() {
        Inventory inv = new Inventory(products, quantities);

        assertEquals(15, inv.getStock(0));
        assertEquals(100, inv.getStock(3));
        assertEquals(-1, inv.getStock(2));
    }

    @Test
    public void addStock() {
        Inventory inv = new Inventory();
        inv.addStock(p1, 15);
        inv.addStock(p2, 100);

        assertEquals(15, inv.getStock(0));
        assertEquals(100, inv.getStock(3));
        assertEquals(-1, inv.getStock(2));
    }

    @Test
    public void removeStock() {
        Inventory inv = new Inventory(products, quantities);

        inv.removeStock(0, 3, false);
        assertEquals(12, inv.getStock(0));

        inv.removeStock(0, 3, true);
        assertEquals(9, inv.getStock(0));

        inv.removeStock(0, 9, true);
        assertEquals(-1, inv.getStock(0));

        inv.removeStock(3, 999, false);
        assertEquals(0, inv.getStock(3));
    }

    @Test
    public void removeProduct() {
        Inventory inv = new Inventory(products, quantities);

        inv.removeProduct(-42);

        inv.removeProduct(0);
        assertEquals(-1, inv.getStock(0));
    }

    @Test
    public void getInfo() {
        Inventory inv = new Inventory(products, quantities);

        assertNull(inv.getInfo(-42));
        assertEquals(p1, inv.getInfo(0));
    }
}