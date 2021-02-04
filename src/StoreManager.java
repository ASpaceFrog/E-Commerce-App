public class StoreManager {
    private Inventory myInventory;

    public StoreManager() {
        myInventory = new Inventory();
    }

    public Inventory getMyInventory() {
        return myInventory;
    }

    public void setMyInventory(Inventory myInventory) {
        this.myInventory = myInventory;
    }

    public int checkStock(String id) {
        return myInventory.getStock(id);
    }

    public int processTransaction(ProductRequest[] userProducts) {
        int total = 0;
        for (ProductRequest i : userProducts) {
            if (myInventory.getStock(i.getId()) - i.getAmount() < 0) {
                return -1;  //unsuccessful transaction
            }
        }
        for (ProductRequest i : userProducts) {  //remove items from inventory
            total += myInventory.getInfo(i.getId()).getPrice();
            myInventory.removeStock(i.getId(), i.getAmount());
        }
        return total;
    }

}
