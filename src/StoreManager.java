public class StoreManager {
    private Inventory myInventory = new Inventory();

    public Inventory getMyInventory() {
        return myInventory;
    }

    /**
     * Check how much stock of a given Product is in the Inventory
     * @param myProduct Product to check
     * @return Returns amount of stock for given product. If product does not exist in inventory, returns -1.
     */
    public int checkStock(Product myProduct) {
        return myInventory.getStock(myProduct.getId());
    }

    /**
     * Takes an Array of Product information and subtracts quantities from the Inventory
     * @param transaction  Array of Product information  (eg: “[[productID1, quantity], [productID2, quantity], [productID3, quantity]])”.
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
            total += myInventory.getInfo(ints[0]).getPrice()  * ints[1];
            myInventory.removeStock(ints[0], ints[1]);
        }

        return total;
    }

}
