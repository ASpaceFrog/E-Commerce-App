public class Main {
    public static void main(String[] args) {

        /* Code below is for testing purposes */

        Product  p1 = new Product("Apple", 1, 2.00);
        StoreManager s1 = new StoreManager();

        s1.getMyInventory().addStock(p1, 5);

        s1.getMyInventory().removeStock(p1.getId(), 2);

        System.out.println(s1.checkStock(p1));  //3

        int[][] t = { {1,2} };

        System.out.println(s1.processTransaction(t)); //4.0
        System.out.println(s1.processTransaction(t)); //-1.0

    }
}
