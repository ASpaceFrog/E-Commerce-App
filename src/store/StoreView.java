package store;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.HashMap;

/**
 * Manages User Interface for the system
 * Text UI for this version
 *
 * @author Stefan Lukic - 101156711, Filip Lukic - 101156713
 * @version 2.0
 */
public class StoreView {
    private static final CardLayout card = new CardLayout();
    private static final String WELCOMEPANELSTRING = "welcomePanel";
    private static final String STOREUISTRING = "storeUI";
    private static final int FRAMEHEIGHT = 788;
    private static final int FRAMEWIDTH = 1400;
    private static final int ICONHEIGHT = 150;
    private static final int ICONWIDTH = 150;
    private static JFrame frame;
    private static JPanel mainPanel;
    private final HashMap<Product, JPanel> productPanels = new HashMap<>();
    private final StoreManager myStoreManager;
    private final int cartID;

    public StoreView(StoreManager myStoreManager, int cartID) {
        this.myStoreManager = myStoreManager;
        this.cartID = cartID;

        frame = new JFrame();
        mainPanel = new JPanel(card);
        createPanels(); //create JPanels that will be utilized in the mainPanel

        frame.setTitle("Store GUI");
        frame.add(mainPanel);
        frame.pack();
        frame.setResizable(false);

    }

    /**
     * Display the GUI
     */
    public static void displayGUI() {
        frame.setSize(FRAMEWIDTH, FRAMEHEIGHT);
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
     * Create an icon using an image
     *
     * @param path   String, relative path to the image
     * @param height int, icon height
     * @param width  int, image width
     * @return returns an ImageIcon, or null if the path was invalid.
     */
    private ImageIcon createImageIcon(String path, int width, int height) {
        try {
            Image img = ImageIO.read(getClass().getResource(path));
            return new ImageIcon(img.getScaledInstance(width, height, Image.SCALE_DEFAULT));
        } catch (Exception e) {
            System.err.println("Couldn't find file: " + path);
            return null;
        }
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
     * Checkout User's cart
     */
    private void checkout() {
        myStoreManager.getUserCarts().get(cartID).clearCart();
    }

    /**
     * Create panels for each section and combine them into a mainPanel
     */
    private void createPanels() {
        JPanel welcomePanel = createWelcomePanel();
        JPanel inventoryPanel = createInvPanel();
        JScrollPane scrollPane = new JScrollPane(inventoryPanel, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setPreferredSize(new Dimension(FRAMEWIDTH - 200, FRAMEHEIGHT - 100));
        scrollPane.getVerticalScrollBar().setUnitIncrement(10);
        JPanel cartButtons = createCartButtons();

        JPanel storeUI = new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        c.gridy = 0;
        c.gridx = 0;
        c.weightx = 1;
        c.weighty = 1;
        c.gridwidth = 16;
        c.gridheight = 9;
        storeUI.add(scrollPane, c);

        c.gridx = 16;
        c.weightx = 0;
        c.weighty = 0;
        c.gridwidth = 4;
        c.gridheight = 4;
        c.insets = new Insets(0, 10, 0, 0);
        storeUI.add(cartButtons, c);

        /* Add Panels to mainPanel */
        mainPanel.add(welcomePanel, WELCOMEPANELSTRING);
        mainPanel.add(storeUI, STOREUISTRING);
    }

    /**
     * Creates the welcome panel that greets the user
     *
     * @return JPanel, created welcome panel is returned
     */
    private JPanel createWelcomePanel() {
        JPanel welcomePanel = new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        JLabel welcome1 = new JLabel("Welcome to the produce store!");
        welcome1.setHorizontalAlignment(SwingConstants.CENTER);
        JLabel welcome2 = new JLabel("We offer the best prices on fruits and vegetables!");
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
        enter.addActionListener(e -> {
            card.show(mainPanel, STOREUISTRING); //goto commands
        });

        JButton quit = new JButton("Quit");
        quit.addActionListener(e -> {
            if (JOptionPane.showConfirmDialog(frame, "Are you sure you want to quit?")
                    == JOptionPane.OK_OPTION) {
                frame.setVisible(false);
                frame.dispose();
            }
        });

        c.gridy = 2;
        c.ipady = 5;
        c.insets = new Insets(20, 0, 0, 0);
        c.anchor = GridBagConstraints.PAGE_END;
        welcomePanel.add(enter, c);

        c.gridy = 3;
        welcomePanel.add(quit, c);

        return welcomePanel;
    }

    /**
     * Create panel to display the store's inventory with
     * plus and minus buttons to add and remove from cart
     *
     * @return JPanel, return the created JPanel
     */
    private JPanel createInvPanel() {
        GridLayout gl = new GridLayout(0, 3);
        gl.setHgap(40);
        gl.setVgap(40);
        JPanel invPanel = new JPanel(gl);

        Product[] allProducts = myStoreManager.getMyInventory().getProducts();
        Inventory inv = myStoreManager.getMyInventory();

        GridBagConstraints iconC = new GridBagConstraints();
        iconC.gridx = 0;
        iconC.gridy = 0;
        iconC.weightx = 0.7;
        iconC.weighty = 0.7;
        iconC.gridheight = 3;
        iconC.gridwidth = 3;
        iconC.anchor = GridBagConstraints.FIRST_LINE_START;
        iconC.fill = GridBagConstraints.BOTH;

        GridBagConstraints nameC = new GridBagConstraints();
        nameC.gridx = 0;
        nameC.gridy = 3;
        nameC.weightx = 0.5;
        nameC.weighty = 0.5;
        nameC.gridheight = 1;
        nameC.gridwidth = 3;
        nameC.insets = new Insets(5, 0, 0, 0);

        GridBagConstraints textC = new GridBagConstraints();
        textC.gridx = 3;
        textC.gridy = 0;
        textC.weightx = 0.5;
        textC.weighty = 0.5;
        textC.gridheight = 2;
        textC.gridwidth = 3;
        textC.anchor = GridBagConstraints.FIRST_LINE_END;


        GridBagConstraints buttonC = new GridBagConstraints();
        buttonC.gridx = 3;
        buttonC.gridy = 2;
        buttonC.weightx = 0.5;
        buttonC.weighty = 0.5;
        buttonC.gridheight = 1;
        buttonC.gridwidth = 3;
        buttonC.anchor = GridBagConstraints.LAST_LINE_END;

        for (Product p : allProducts) {
            JPanel productPanel = new JPanel(new GridBagLayout());
            productPanel.setBorder(BorderFactory.createLineBorder(Color.black));


            ImageIcon iconImage = createImageIcon("images/" + p.getNAME() + ".png", ICONWIDTH, ICONHEIGHT);
            JLabel icon = new JLabel();
            icon.setIcon(iconImage);
            icon.setPreferredSize(new Dimension(ICONWIDTH, ICONHEIGHT));

            JLabel nameLabel = new JLabel(p.getNAME());
            nameLabel.setHorizontalAlignment(SwingConstants.CENTER);

            JPanel textPanel = new JPanel();

            JLabel priceLabel = new JLabel("$" + p.getPRICE());
            priceLabel.setHorizontalAlignment(SwingConstants.RIGHT);

            JLabel stockLabel = new JLabel("Stock: " + inv.getProductQuantity(p));
            stockLabel.setHorizontalAlignment(SwingConstants.RIGHT);

            textPanel.add(priceLabel);
            textPanel.add(stockLabel);

            JPanel buttonPanel = new JPanel();
            JButton plus = createPlusButton(p);
            JButton minus = createMinusButton(p);
            buttonPanel.add(plus);
            buttonPanel.add(minus);

            productPanel.add(icon, iconC);
            productPanel.add(nameLabel, nameC);
            productPanel.add(textPanel, textC);
            productPanel.add(buttonPanel, buttonC);
            productPanel.setPreferredSize(new Dimension(2 * ICONWIDTH, 2 * ICONHEIGHT));
            invPanel.add(productPanel);
            productPanels.put(p, productPanel);
        }
        return invPanel;
    }

    /**
     * Displays the user's shopping cart as a popup message
     */
    private void showCart() {
        Product[] allProducts = myStoreManager.getUserCarts().get(cartID).getProducts();
        String name;
        int cartStock;
        double productPrice;
        StringBuilder sb = new StringBuilder();

        if (allProducts.length == 0) {
            sb.append("Empty");
        } else {
            for (Product p : allProducts) {
                name = p.getNAME();
                cartStock = myStoreManager.getUserCarts().get(cartID).getProductQuantity(p);
                productPrice = p.getPRICE();
                sb.append(name).append(": ").append(cartStock).append(" $").append(productPrice).append("\n");
            }
            sb.append("Total Price: $").append(myStoreManager.getUserCarts().get(cartID).getTotalPrice());
        }

        JOptionPane.showMessageDialog(frame,
                sb.toString(),
                "Cart",
                JOptionPane.PLAIN_MESSAGE);
    }

    /**
     * Create the view cart, checkout, and quit buttons
     *
     * @return JPanel, return a JPanel containing the required buttons
     */
    private JPanel createCartButtons() {
        JPanel cartButtons = new JPanel(new BorderLayout());

        JButton viewCart = new JButton("View Cart");
        viewCart.addActionListener(e -> {
            showCart();
        });

        JButton checkout = new JButton("Checkout Cart");
        checkout.addActionListener(e -> {
            if (JOptionPane.showConfirmDialog(frame, "Are you sure you want to checkout?")
                    == JOptionPane.OK_OPTION) {
                //save a copy of the product IDs in the user's cart
                Product[] allProducts = myStoreManager.getUserCarts().get(cartID).getProducts();

                checkout();
                //update buttons so the user cannot remove stock from products that were previously in the cart
                for (Product p : allProducts) {
                    updateButtons(p);
                }
            }
        });

        JButton quit = new JButton("Exit Store");
        quit.addActionListener(e -> {
            if (JOptionPane.showConfirmDialog(frame, "Are you sure you want return to the menu?")
                    == JOptionPane.OK_OPTION) {
                card.show(mainPanel, WELCOMEPANELSTRING);
                Product[] allProducts = myStoreManager.getUserCarts().get(cartID).getProducts();

                myStoreManager.emptyCart(cartID);
                //update buttons so the user cannot remove stock from products that were previously in the cart
                for (Product p : allProducts) {
                    updateButtons(p);
                }
            }
        });

        cartButtons.add(viewCart, BorderLayout.PAGE_START);
        cartButtons.add(checkout, BorderLayout.CENTER);
        cartButtons.add(quit, BorderLayout.PAGE_END);
        return cartButtons;
    }

    /**
     * Create a plus button to add a product to the user's cart
     *
     * @param myProduct Product, ID of product to add to cart
     * @return JButton, return the competed plus button
     */
    private JButton createPlusButton(Product myProduct) {
        JButton plus = new JButton("+");
        plus.addActionListener(e -> {
            myStoreManager.addToCart(cartID, myProduct, 1);
            updateButtons(myProduct);
        });
        return plus;
    }

    /**
     * Create a minus button to remove a product from the user's cart
     *
     * @param myProduct Product, ID of product to add to cart
     * @return JButton, return the competed minus button
     */
    private JButton createMinusButton(Product myProduct) {
        JButton minus = new JButton("-");
        minus.addActionListener(e -> {
            myStoreManager.removeFromCart(cartID, myProduct, 1);
            updateButtons(myProduct);
        });
        minus.setEnabled(false); //button is disabled by default
        return minus;
    }

    /**
     * Updates the availability of the plus and minus button
     * depending on the Store's stock and the quantity of a stock in the user's cart
     *
     * @param myProduct Product, id of a Product
     */
    private void updateButtons(Product myProduct) {
        //update store stock value
        JPanel textPanel = (JPanel) productPanels.get(myProduct).getComponents()[2];
        JLabel stockLabel = (JLabel) textPanel.getComponents()[1];
        stockLabel.setText(String.valueOf(myStoreManager.getMyInventory().getProductQuantity(myProduct)));

        //update button states
        JPanel buttonPanel = (JPanel) productPanels.get(myProduct).getComponents()[3];
        JButton plus = (JButton) buttonPanel.getComponents()[0];
        JButton minus = (JButton) buttonPanel.getComponents()[1];

        //store still has stock
        plus.setEnabled(myStoreManager.getMyInventory().getProductQuantity(myProduct) > 0);
        //more than 0 of product in cart
        minus.setEnabled(myStoreManager.getUserCarts().get(cartID).getProductQuantity(myProduct) > 0);
    }
}
