package store;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Scanner;
import java.util.ArrayList;

/**
 * Manages User Interface for the system
 * Text UI for this version
 *
 * @author Stefan Lukic - 101156711, Filip Lukic - 101156713
 * @version 1.0
 */
public class StoreView {
    private StoreManager myStoreManager;
    private int cartID;


    private static JFrame frame;
    private static JPanel mainPanel;
    private static CardLayout card = new CardLayout();

    private static final String WELCOMEPANELSTRING = "welcomePanel";
    private static final String COMMANDPANELSTRING = "commandPanel";
    private static final String CARTPANELSTRING = "cartPanel";
    private static final String STOREPANELSTRING = "storePanel";

    public StoreView(StoreManager myStoreManager, int cartID) {
        this.myStoreManager = myStoreManager;
        this.cartID = cartID;

        frame = new JFrame();
        mainPanel = new JPanel(card);
        createPanels(); //create JPanels that will be utilized in the mainPanel

        frame.setTitle("Store GUI");
        frame.add(mainPanel);
        frame.pack();
    }

    /**
     * Display the PaletteGenerator GUI
     */
    /**
     * Display the PaletteGenerator GUI
     */
    public static void displayGUI() {
        frame.setSize(500, 150);
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent we) {
                if (JOptionPane.showConfirmDialog(frame, "Are you sure you want to quit?")
                        == JOptionPane.OK_OPTION) {
                    frame.setVisible(false);
                    frame.dispose();
                }
            }
        });
        frame.setVisible(true);
    }

    /**
     * Available commands for user to input when connected to a store
     */
    private static final String[] COMMANDS = {"browse", "viewCart", "add", "remove", "checkout", "help"};

    /**
     * Override default to string method to return cart id
     * @return String, cartID
     */
    public String toString() {
        return String.valueOf(cartID);
    }

    /**
     * Display's the Store's inventory
     */
    public void browse() {
        myStoreManager.printInventory();
    }

    /**
     * Display's the User's cart
     */
    public void viewCart() {
        myStoreManager.getUserCarts().get(cartID).printCartInventory();
    }

    /**
     * Add a store.Product to the User's cart
     *
     * @param id int, store.Product id
     * @param amount int, amount to add to user
     */
    public void addToUser(int id, int amount) {
        if (!myStoreManager.addToCart(cartID, id, amount)) {
            System.out.println("Error: Please enter a valid amount of stock to add to your cart!");
        }
    }

    /**
     * Remove a store.Product from the user'sCart
     *
     * @param id int, store.Product id
     * @param amount int, amount to remove from user
     */
    public void removeFromUser(int id, int amount) {
        if (!myStoreManager.removeFromCart(cartID, id, amount)) {
            System.out.println("Error: Please enter a valid amount of stock to remove from your cart!");
        }
    }

    /**
     * Checkout User's cart
     *
     * @return boolean, false if user aborts checkout
     */
    public boolean checkout() {
        return myStoreManager.processTransaction(cartID);
    }

    /**
     * Print available commands for UI
     */
    public static void help() {
        System.out.print("Valid Commands: ");
        for (int i = 0; i < COMMANDS.length - 1; i++) {
            System.out.print(COMMANDS[i] + ", ");
        }
        System.out.println(COMMANDS[COMMANDS.length - 1]);
    }

    private void createPanels(){
        JPanel welcomePanel = createWelcomePanel();

        JPanel commandPanel = new JPanel();

        JPanel cartPanel = new JPanel();

        JPanel storePanel = new JPanel();


        /* Add Panels to mainPanel */
        mainPanel.add(welcomePanel, WELCOMEPANELSTRING);
    }

    private JPanel createWelcomePanel(){
        JPanel welcomePanel = new JPanel(new FlowLayout());

        JLabel welcome1 = new JLabel("Welcome to the meme store!");
        JLabel welcome2 = new JLabel("We offer the best prices on the spiciest memes available on the market.");

        welcomePanel.add(welcome1);
        welcomePanel.add(welcome2);

        JButton enter = new JButton("Enter the Store");
        enter.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                card.show(mainPanel, COMMANDPANELSTRING); //goto commands
            }
        });
        welcomePanel.add(enter);

        return welcomePanel;
    }


    /**
     * Main Method for program execution
     * @param args Unused: no parameters utilized in this version
     */
    public static void main(String[] args) {
        StoreManager sm = new StoreManager();
        StoreView sv = new StoreView(sm, sm.newShoppingCart()); //add one existing store.StoreView

        displayGUI();
    }

    /*
    public static void main2(String[] args) {
        StoreManager sm = new StoreManager();
        Scanner sc = new Scanner(System.in);
        boolean exit = false;
        boolean exitCart;
        int i, id, amount;
        String s;


        storeViews.add(new StoreView(sm, sm.newShoppingCart())); //add one existing store.StoreView

        while (!exit) {
            System.out.println("Please select a command:");
            System.out.println("1: Select an existing StoreView");
            System.out.println("2: Create a new StoreView");
            System.out.println("-1: Exit \n");


            i = UserInput.getIntInput(-1, 2);

            if (i == 1) { //choose existing
                System.out.println("Please select a StoreView or choose -1 to exit.");
                System.out.println("Existing Store Views:");
                UserInput.printArray(storeViews);
                System.out.print("\n");
                i = UserInput.getIntInput(-1, storeViews.size() - 1);

                if (i == -1) {
                    break;
                } else {  //the juice
                    exitCart = false;
                    while (!exitCart) {
                        System.out.println("Enter a command or -1 to exit");
                        System.out.print(">>> ");
                        s = sc.nextLine();
                        switch (s) {
                            case "browse" -> storeViews.get(i).browse();
                            case "viewCart" -> storeViews.get(i).viewCart();
                            case "add" -> {
                                storeViews.get(i).browse();
                                System.out.println("Please enter the id of the Product you wish to add to your cart.");
                                id = UserInput.getIntInput();
                                System.out.println("Please enter the amount of the Product you wish to add to your cart.");
                                amount = UserInput.getIntInput();
                                storeViews.get(i).addToUser(id, amount);
                            }
                            case "remove" -> {
                                storeViews.get(i).viewCart();
                                System.out.println("Please enter the id of the Product you wish to remove from your cart.");
                                id = UserInput.getIntInput();
                                System.out.println("Please enter the amount of the Product you wish to remove from your cart.");
                                amount = UserInput.getIntInput();
                                storeViews.get(i).removeFromUser(id, amount);
                            }
                            case "checkout" -> storeViews.get(i).checkout();
                            case "-1" -> exitCart = true;
                            default -> help();
                        }
                    }

                }

            } else if (i == 2) { //add new
                storeViews.add(new StoreView(sm, sm.newShoppingCart()));
                System.out.println("New StoreView Created. \n");
            } else {
                exit = true;
            }
        }

    }
    */

}
