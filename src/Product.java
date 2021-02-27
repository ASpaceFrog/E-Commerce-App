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

}