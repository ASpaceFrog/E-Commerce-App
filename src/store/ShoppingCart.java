package store;

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
     * Default Constructor for store.ShoppingCart
     */
    public ShoppingCart() {
        userCart = new Inventory();
        totalPrice = 0;
    }

    /**
     * Get a shopping carts inventory
     *
     * @return store.Inventory, store.ShoppingCart's inventory
     */
    public Inventory getUserCart() {
        return userCart;
    }

    /**
     * Get the total price of the User's cart
     *
     * @return double, total price of the User's cart
     */
    public double getTotalPrice() {
        return totalPrice;
    }

    /**
     * Add a product to the user's cart
     *
     * @param myProduct store.Product, store.Product to add
     * @param amount    int, amount of product to add to cart
     */
    public void addItemToCart(Product myProduct, int amount) {
        userCart.addStock(myProduct, amount);
        totalPrice += myProduct.getPRICE() * amount;
    }

    /**
     * Remove a product from the user's cart
     *
     * @param id     int, ID of product to remove
     * @param amount int, amount of store.Product to remove
     */
    public void removeItemFromCart(int id, int amount) {
        if (userCart.getInfo(id) != null) {
            totalPrice -= userCart.getInfo(id).getPRICE() * amount;
            userCart.removeStock(id, amount, true);
        }
    }

    /**
     * Print the User's cart
     */
    public void printCartInventory() {
        userCart.printInventory();
    }

    /**
     * Clear the carts inventory
     */
    public void clearCart(){
        userCart.clearInventory();
    }
}
