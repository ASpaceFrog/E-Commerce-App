/**
 * A User's shopping cart
 *
 * @author Stefan Lukic - 101156711, Filip Lukic - 101156713
 * @version 1.0
 */
public class ShoppingCart {
    private Inventory userCart;
    private double totalPrice;

    /**
     * Default Constructor for ShoppingCart
     */
    public ShoppingCart(){
        userCart = new Inventory(true);
        totalPrice = 0;
    }

    /**
     * Get a shopping carts inventory
     * @return Inventory, ShoppingCart's inventory
     */
    public Inventory getUserCart() {
        return userCart;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    /**
     * Add a product to the user's cart
     *
     * @param myProduct Product, Product to add
     * @param amount int, amount of product to add to cart
     */
    public void addItemToCart(Product myProduct, int amount){
        userCart.addStock(myProduct, amount);
        totalPrice += myProduct.getPrice() * amount;
    }

    /**
     * Remove a product from the user's cart
     *
     * @param id int, ID of product to remove
     * @param amount int, amount of Product to remove
     */
    public void removeItemFromCart(int id, int amount){
        if (userCart.getInfo(id) != null){
            userCart.removeStock(id, amount, true);
            totalPrice -= userCart.getInfo(id).getPrice() * amount;
        }
    }
}
