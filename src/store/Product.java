package store;

import java.util.Objects;

/**
 * A store.Product that is sold by the store
 *
 * @author Stefan Lukic - 101156711, Filip Lukic - 101156713
 * @version 1.1
 */
public class Product {
    private final String NAME;
    private final int ID;
    private final double price;

    /**
     * Constructor for a store.Product
     * @param NAME String, name of the product
     * @param ID int, store.Product id
     * @param PRICE double, store.Product Price
     */
    public Product(String NAME, int ID, double PRICE) {
        this.NAME = NAME;
        this.ID = ID;
        this.price = PRICE;
    }

    /**
     *
     * @return String, Returns store.Product name
     */
    public String getNAME() {
        return NAME;
    }

    /**
     *
     * @return int, Returns store.Product id
     */
    public int getID() {
        return ID;
    }

    /**
     *
     * @return double, Returns store.Product price
     */
    public double getPRICE() {
        return price;
    }

    /**
     * Check if two products are equal
     * @param o Object, object to check if equal
     * @return boolean, true if equal, false if not equal
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return ID == product.ID && Double.compare(product.price, price) == 0 && NAME.equals(product.NAME);
    }

    /**
     *
     * @return int, HashCode of a store.Product
     */
    @Override
    public int hashCode() {
        return Objects.hash(NAME, ID, price);
    }
}