import java.util.ArrayList;

/**
 * Manages a Store's inventory. Allows transactions for prodcuts to be made.
 *
 * @author Stefan Lukic - 101156711, Filip Lukic - 101156713
 * @version 1.1
 */
public class StoreManager {
    private Inventory myInventory = new Inventory();
    private ArrayList<ShoppingCart> userCarts = new ArrayList<>();

    /**
     * Get the store's inventory
     *
     * @return Inventory, store's inventory
     */
    public Inventory getMyInventory() {
        return myInventory;
    }

    /**
     * Check how much stock of a given Product is in the Inventory
     *
     * @param myProduct  Product, Product to check
     * @return int, Returns amount of stock for given product. If product does not exist in inventory, returns -1.
     */
    public int checkStock(Product myProduct) {
        return myInventory.getStock(myProduct.getId());
    }

    /**
     * Takes an Array of Product information and subtracts quantities from the Inventory
     *
     * @param transaction Array of Product information  (eg: “[[productID1, quantity], [productID2, quantity], [productID3, quantity]])”.
     * @return Returns the total cost of the transaction. Returns -1.0 if there is insufficient quantity of any of the products
     */
    public double processTransaction(int[][] transaction) {
        double total = 0.0;
        for (int[] ints : transaction) { //check if desired quantity is available for all products
            if (myInventory.getStock(ints[0]) - ints[1] < 0) {
                return -1;  //unsuccessful transaction
            }
        }

        for (int[] ints : transaction) {
            total += myInventory.getInfo(ints[0]).getPrice() * ints[1];
            myInventory.removeStock(ints[0], ints[1]);
        }

        return total;
    }

    public void printInventory(){
        System.out.println("|--------------------THE COURSE STORE--------------------|");
        System.out.println("\\------------------------------------------------------- /");
        System.out.println("Type 'help' for a list of commands.\n");
        System.out.println(" ID | PRODUCT NAME | PRODUCT PRICE | STOCK");

        for (int i : myInventory.getProductQuantity().keySet()){
            System.out.printf("%d | %s | %f | %d\n",i, myInventory.getInfo(i).getName(), myInventory.getInfo(i).getPrice(), myInventory.getStock(i));
        }
        System.out.printf("\n\n");
    }

    public void printCartInventory(int cartID){
        System.out.println("|--------------------------CART--------------------------|");
        System.out.println("\\------------------------------------------------------- /");
        System.out.println("Type 'help' for a list of commands.\n");
        System.out.println(" ID | PRODUCT NAME | PRODUCT PRICE | STOCK");

        for (int i : userCarts.get(cartID).getUserCart().getProductQuantity().keySet()) {
            System.out.printf("%d | %s | %f | %d\n",i, userCarts.get(cartID).getUserCart().getInfo(i).getName(),
                                                       userCarts.get(cartID).getUserCart().getInfo(i).getPrice(),
                                                       userCarts.get(cartID).getUserCart().getStock(i));
        }
        System.out.printf("\n\n");
    }
}
