import java.util.Objects;

/**
 * A Product that is sold by the store
 *
 * @author Stefan Lukic - 101156711, Filip Lukic - 101156713
 * @version 1.1
 */
public class Product {
    private final String name;
    private final int id;
    private final double price;

    /**
     * Constructor for a Product
     * @param name String, name of the product
     * @param id int, Product id
     * @param price double, Product Price
     */
    public Product(String name, int id, double price) {
        this.name = name;
        this.id = id;
        this.price = price;
    }

    /**
     *
     * @return String, Returns Product name
     */
    public String getName() {
        return name;
    }

    /**
     *
     * @return int, Returns Product id
     */
    public int getId() {
        return id;
    }

    /**
     *
     * @return double, Returns Product price
     */
    public double getPrice() {
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
        return id == product.id && Double.compare(product.price, price) == 0 && name.equals(product.name);
    }

    /**
     *
     * @return int, HashCode of a Product
     */
    @Override
    public int hashCode() {
        return Objects.hash(name, id, price);
    }
}