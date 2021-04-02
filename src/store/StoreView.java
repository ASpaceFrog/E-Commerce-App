package store;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.HashMap;
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
    private static final String WELCOMEPANELSTRING = "welcomePanel";
    private static final String COMMANDPANELSTRING = "commandPanel";
    private static final String CARTPANELSTRING = "cartPanel";
    private static final String STOREPANELSTRING = "storePanel";

    // {"browse", "viewCart", "add", "remove", "checkout", "help"};
    private static JFrame frame;
    private static JPanel mainPanel;
    private static CardLayout card = new CardLayout();

    private StoreManager myStoreManager;
    private int cartID;

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
     * Display the GUI
     */
    public static void displayGUI() {
        frame.setSize(1000, 1000);
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
     * Main Method for program execution
     *
     * @param args Unused: no parameters utilized in this version
     */
    public static void main(String[] args) {
        StoreManager sm = new StoreManager();
        StoreView sv = new StoreView(sm, sm.newShoppingCart()); //add one existing store.StoreView

        displayGUI();
    }

    /**
     * Override default to string method to return cart id
     *
     * @return String, cartID
     */
    public String toString() {
        return String.valueOf(cartID);
    }

    /**
     * Display's the Store's inventory
     */
    public void browse() {
        myStoreManager.getMyInventory().printInventory();
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
     * @param id     int, store.Product id
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
     * @param id     int, store.Product id
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

    private void createPanels() {
        JPanel welcomePanel = createWelcomePanel();

        JPanel commandPanel = createCommandPanel();

        JPanel storePanel = createStorePanel();

        JPanel cartPanel = new JPanel();


        /* Add Panels to mainPanel */
        mainPanel.add(welcomePanel, WELCOMEPANELSTRING);
        mainPanel.add(commandPanel, COMMANDPANELSTRING);
        mainPanel.add(storePanel,STOREPANELSTRING);
    }

    private JPanel createWelcomePanel() {
        JPanel welcomePanel = new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        JLabel welcome1 = new JLabel("Welcome to the meme store!");
        welcome1.setHorizontalAlignment(SwingConstants.CENTER);
        JLabel welcome2 = new JLabel("We offer the best prices on the spiciest memes available on the market.");
        welcome2.setHorizontalAlignment(SwingConstants.CENTER);

        c.gridx = 0;
        c.gridy = 0;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.anchor = GridBagConstraints.PAGE_START;
        welcomePanel.add(welcome1, c);

        c.anchor = GridBagConstraints.CENTER;
        c.gridy = 1;
        welcomePanel.add(welcome2, c);

        JButton enter = new JButton("Enter the Store");
        enter.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                card.show(mainPanel, COMMANDPANELSTRING); //goto commands
            }
        });
        c.gridy = 2;
        c.ipady = 5;
        c.insets = new Insets(20, 0, 0, 0);
        c.anchor = GridBagConstraints.PAGE_END;
        welcomePanel.add(enter, c);

        return welcomePanel;
    }

    private JPanel createCommandPanel() {
        JPanel commandPanel = new JPanel();

        JButton browseStore = new JButton("Browse the Store");
        browseStore.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                card.show(mainPanel, STOREPANELSTRING);
            }
        });

        JButton viewCart = new JButton("View Shopping Cart");
        viewCart.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                card.show(mainPanel, CARTPANELSTRING);
            }
        });
        commandPanel.add(browseStore);
        commandPanel.add(viewCart);

        return commandPanel;
    }

    //TODO: Figure out how to add an image/icon to a JPanel
    private JPanel createStorePanel() {
        GridLayout gl = new GridLayout(0,3);
        gl.setHgap(40);
        gl.setVgap(40);
        JPanel storePanel = new JPanel(gl);

        JPanel inventoryPanel = new JPanel();
        Integer[] IDs = myStoreManager.getMyInventory().getIDs();
        Inventory inv = myStoreManager.getMyInventory();

        int row=0;
        int col=0;

        GridBagConstraints iconC = new GridBagConstraints();
        iconC.gridx=0;
        iconC.gridy=0;
        iconC.weightx=0.6;
        iconC.weighty=0.6;
        iconC.gridheight=3;
        iconC.gridwidth=3;
        iconC.fill = GridBagConstraints.BOTH;

        GridBagConstraints nameC = new GridBagConstraints();
        nameC.gridx=0;
        nameC.gridy=3;
        nameC.weightx=0.1;
        nameC.weighty=0.1;
        nameC.gridheight=1;
        nameC.gridwidth=3;
        nameC.insets = new Insets(5,0,0,0);

        GridBagConstraints priceC = new GridBagConstraints();
        priceC.gridx=3;
        priceC.gridy=0;
        priceC.weightx=0.1;
        priceC.weighty=0.1;
        priceC.gridheight=1;
        priceC.gridwidth=3;
        priceC.anchor = GridBagConstraints.FIRST_LINE_END;

        GridBagConstraints stockC = new GridBagConstraints();
        stockC.gridx=3;
        stockC.gridy=1;
        stockC.weightx=0.1;
        stockC.weighty=0.1;
        stockC.gridheight=1;
        stockC.gridwidth=3;
        stockC.anchor = GridBagConstraints.LINE_END;

        for (Integer id : IDs) {
            if (col==3){
                row++;
                col=0;
            }
            JPanel jp = new JPanel(new GridBagLayout());

            JPanel icon = new JPanel();
            icon.setBackground(Color.RED);
            icon.setPreferredSize(new Dimension(1920, 1080));

            JLabel nameLabel = new JLabel(inv.getInfo(id).getNAME());
            nameLabel.setHorizontalAlignment(SwingConstants.CENTER);
            JLabel priceLabel = new JLabel("$"+String.valueOf(inv.getInfo(id).getPRICE()));
            priceLabel.setHorizontalAlignment(SwingConstants.RIGHT);
            JLabel stockLabel = new JLabel("Stock: "+String.valueOf(inv.getStock(id)));
            stockLabel.setHorizontalAlignment(SwingConstants.RIGHT);

            jp.add(icon, iconC);
            jp.add(nameLabel, nameC);
            jp.add(priceLabel, priceC);
            jp.add(stockLabel, stockC);
            storePanel.add(jp);

            col++;
        }

        return storePanel;
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
