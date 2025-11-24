import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.text.DecimalFormat;
import javax.swing.border.*;
import java.sql.Date;
import java.text.SimpleDateFormat;

/**
 * Class to edit an order
 * @author Ethan C and Aarav S
 * @version 1.0.0
 */
public class OrderEditPresentation extends JFrame {
    // Modern color scheme (matching SalesRepEditPresentation)
    private static final Color PRIMARY_COLOR = new Color(41, 128, 185);    // Professional blue
    private static final Color SECONDARY_COLOR = new Color(236, 240, 241); // Light gray
    private static final Color ACCENT_COLOR = new Color(52, 152, 219);     // Bright blue
    private static final Color TEXT_COLOR = new Color(44, 62, 80);         // Dark blue-gray
    private static final Color HEADER_COLOR = new Color(52, 73, 94);       // Dark slate
    private static final Color BACKGROUND_COLOR = new Color(75, 101, 132); // Blue-grey
    private static final Color BUTTON_BG_COLOR = Color.WHITE;
    private static final Color BUTTON_TEXT_COLOR = HEADER_COLOR;

    // stuff for styling
    private JTextField customerIdField, orderDateField, shippingDateField, salesTaxField;
    private JComboBox<String> statusComboBox, shippingMethodComboBox;
    private JTable productsTable;
    private JTextField subtotalField, taxField, totalField, discountField;
    private JButton addProductButton, calculateTotalButton, saveOrderButton, cancelButton;

    /**
     * Constructor initializes the form and components
     */
    public OrderEditPresentation() {
        setTitle("New Order");
        setSize(800, 900);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Main panel with gradient background
        JPanel mainPanel = new JPanel(new BorderLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
                
                // Solid blue-grey background
                g2d.setColor(BACKGROUND_COLOR);
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        mainPanel.setBorder(new EmptyBorder(20, 20, 20, 20));
        setContentPane(mainPanel);

        // Header panel
        JPanel headerPanel = createHeaderPanel();
        mainPanel.add(headerPanel, BorderLayout.NORTH);

        // Content panel
        JScrollPane scrollPane = new JScrollPane(createGroupedFormPanel());
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.getViewport().setOpaque(false);
        scrollPane.setOpaque(false);
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        // Button panel
        JPanel buttonPanel = createButtonPanel();
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);
    }

    /**
     * Creates header panel
     * @return Configured header panel
     */
    private JPanel createHeaderPanel() {
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(HEADER_COLOR);
        headerPanel.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));
        
        // adding the text
        JLabel headerLabel = new JLabel("Order Management", JLabel.CENTER);
        headerLabel.setFont(new Font("Segoe UI Light", Font.BOLD, 28));
        headerLabel.setForeground(Color.WHITE);
        headerPanel.add(headerLabel, BorderLayout.CENTER);
        
        return headerPanel;
    }

    /**
     * Creates button panel
     * @return Configured button panel
     */
    private JPanel createButtonPanel() {
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.setOpaque(false);
        
        // save and cancel at bottom of form
        saveOrderButton = createStyledButton("Save", ACCENT_COLOR);
        cancelButton = createStyledButton("Cancel", new Color(231, 76, 60));
        
        buttonPanel.add(saveOrderButton);
        buttonPanel.add(cancelButton);

        // initialize order date defaults date to today
        initializeOrderDate();
        saveOrderButton.addActionListener(e -> saveOrder());
        cancelButton.addActionListener(e -> dispose());

        return buttonPanel;
    }

    /**
     * Sets order date to today
     */
    private void initializeOrderDate() {
        Date today = new Date(System.currentTimeMillis());
        orderDateField.setText(today.toString());
    }

    /**
     * Styled button for formatting
     * @param text The text on the button
     * @param baseColor The base color of button
     * @return Configured stylish button
     */
    private JButton createStyledButton(String text, Color baseColor) {
        // configuring button ui
        JButton button = new JButton(text);
        button.setFont(new Font("Segoe UI", Font.BOLD, 14));
        button.setForeground(BUTTON_TEXT_COLOR);
        button.setBackground(BUTTON_BG_COLOR);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(BUTTON_TEXT_COLOR, 2),
            BorderFactory.createEmptyBorder(8, 15, 8, 15)
        ));
        button.setPreferredSize(new Dimension(120, 40));
        
        // the hover feature
        button.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent evt) {
                button.setBackground(BUTTON_BG_COLOR.darker());
            }
            public void mouseExited(MouseEvent evt) {
                button.setBackground(BUTTON_BG_COLOR);
            }
        });
        
        return button;
    }

    /**
     * Creating group form layout
     * @return Configured group form
     */
    private JPanel createGroupedFormPanel() {
        JPanel groupedPanel = new JPanel(new GridBagLayout());
        groupedPanel.setOpaque(false);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;

        // Header label styling
        JLabel headerLabel = new JLabel("<html><div style='text-align: center;'>"
                + "Use this form to create a new order.<br>"
                + "Fields marked with an asterisk (*) are required.</div></html>");
        headerLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        headerLabel.setForeground(Color.WHITE);
        headerLabel.setHorizontalAlignment(SwingConstants.CENTER);
        
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.weightx = 1.0;
        groupedPanel.add(headerLabel, gbc);
        gbc.gridwidth = 1;

        // adding these groups
        addGroupPanel(groupedPanel, createOrderDetailsPanel(), "Order Details", ++gbc.gridy);
        addGroupPanel(groupedPanel, createProductsPanel(), "Products", ++gbc.gridy);
        addGroupPanel(groupedPanel, createOrderTotalsPanel(), "Order Totals", ++gbc.gridy);

        return groupedPanel;
    }

    /**
     * Adding a panel to the group
     */
    private void addGroupPanel(JPanel parent, JPanel panel, String title, int gridy) {
        panel.setOpaque(false);
        TitledBorder titledBorder = BorderFactory.createTitledBorder(title);
        titledBorder.setTitleColor(Color.WHITE);
        panel.setBorder(titledBorder);
        
        // setting dimensions to make it nice
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = gridy;
        gbc.gridwidth = 2;
        gbc.weightx = 1.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);
        parent.add(panel, gbc);
    }

    /**
     * Order details panel
     * @return Configured panel
     */
    private JPanel createOrderDetailsPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setOpaque(false);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // making the text fields
        customerIdField = createStyledTextField(10);
        orderDateField = createStyledTextField(20);
        shippingDateField = createStyledTextField(20);
        salesTaxField = createStyledTextField(20);
        salesTaxField.setText("0.07"); // Default value

        statusComboBox = createStyledComboBox(new String[]{"Pending", "Shipped", "Delivered"});
        shippingMethodComboBox = createStyledComboBox(new String[]{
            "Federal Express", "UPS Ground", "UPS Second Day", "US Certified Mail", "US Mail Overnight"
        });

        // all these fields are added
        addFormField(panel, "Customer ID*:", customerIdField, gbc);
        addFormField(panel, "Order Date (YYYY-MM-DD)*:", orderDateField, gbc);
        addFormField(panel, "Shipping Date (YYYY-MM-DD)*:", shippingDateField, gbc);
        addFormField(panel, "Status*:", statusComboBox, gbc);
        addFormField(panel, "Shipping Method*:", shippingMethodComboBox, gbc);
        addFormField(panel, "Sales Tax (0-0.1):", salesTaxField, gbc);

        return panel;
    }

    /**
     * Creating products panel
     * @return Configured products panel
     */
    private JPanel createProductsPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setOpaque(false);

        // configuring the panel
        String[] columnNames = {"Product ID", "Product Class", "Quantity", "Unit Price ($)"};
        DefaultTableModel model = new DefaultTableModel(columnNames, 0);
        productsTable = new JTable(model);
        productsTable.setForeground(TEXT_COLOR);
        productsTable.setBackground(SECONDARY_COLOR);
        productsTable.setGridColor(ACCENT_COLOR);
        productsTable.getTableHeader().setBackground(HEADER_COLOR);
        productsTable.getTableHeader().setForeground(Color.WHITE);

        JScrollPane scrollPane = new JScrollPane(productsTable);
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);
        panel.add(scrollPane, BorderLayout.CENTER);

        // add product functionality
        addProductButton = createStyledButton("Add Product", ACCENT_COLOR);
        addProductButton.addActionListener(e -> addProduct(model));
        
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.setOpaque(false);
        buttonPanel.add(addProductButton);
        panel.add(buttonPanel, BorderLayout.SOUTH);

        return panel;
    }

    /**
     * Creating total orders panel
     * @return Configured total orders panel
     */
    private JPanel createOrderTotalsPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setOpaque(false);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // text fields
        subtotalField = createStyledTextField(30);
        taxField = createStyledTextField(30);
        totalField = createStyledTextField(30);
        discountField = createStyledTextField(30);

        subtotalField.setEditable(false);
        taxField.setEditable(false);
        totalField.setEditable(false);
        discountField.setEditable(false);

        // field names
        addFormField(panel, "Subtotal ($):", subtotalField, gbc);
        addFormField(panel, "Tax ($):", taxField, gbc);
        addFormField(panel, "Total ($):", totalField, gbc);
        addFormField(panel, "Discount ($):", discountField, gbc);

        calculateTotalButton = createStyledButton("Calculate Total", ACCENT_COLOR);
        calculateTotalButton.addActionListener(e -> calculateTotal());
        
        gbc.gridx = 0;
        gbc.gridy++;
        gbc.gridwidth = 2;
        panel.add(calculateTotalButton, gbc);

        return panel;
    }

    /**
     * Creating a styled text field
     * @param columns The column names
     * @return Configured styled text field
     */
    private JTextField createStyledTextField(int columns) {
        JTextField field = new JTextField(columns);
        field.setBackground(SECONDARY_COLOR);
        field.setForeground(TEXT_COLOR);
        field.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(ACCENT_COLOR),
            BorderFactory.createEmptyBorder(5, 5, 5, 5)
        ));
        field.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        return field;
    }

    /**
     * Creating a styled combo box
     * @param items The items in the drop down
     * @return Configured styled combo box
     */
    private JComboBox<String> createStyledComboBox(String[] items) {
        JComboBox<String> comboBox = new JComboBox<>(items);
        comboBox.setBackground(SECONDARY_COLOR);
        comboBox.setForeground(TEXT_COLOR);
        comboBox.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        return comboBox;
    }

    /**
     * Adding a form field
     * @param panel the panel in the box
     * @param label The label in the box
     * @param field The component field
     * @param gbc Used for dimensions
     */
    private void addFormField(JPanel panel, String label, JComponent field, GridBagConstraints gbc) {
        // setting dimensions
        gbc.gridx = 0;
        gbc.gridy++;
        gbc.gridwidth = 1;
        gbc.weightx = 0.3;
        
        // setting font
        JLabel labelComponent = new JLabel(label);
        labelComponent.setForeground(Color.WHITE);
        labelComponent.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        panel.add(labelComponent, gbc);
        
        gbc.gridx = 1;
        gbc.weightx = 0.7;
        panel.add(field, gbc);
    }

    /**
     * Adding a product
     * @param model The table for the products
     */
    private void addProduct(DefaultTableModel model) {
        // product id error if no products
        String productId = JOptionPane.showInputDialog(this, "Enter Product ID:");
        if (ProductService.getProductCount() == 0) {
            JOptionPane.showMessageDialog(this,
                "Please load a product first",
                "Input Error",
                JOptionPane.ERROR_MESSAGE);
            return;
        }  
        
        // if invalid product id
        Product product = ProductService.getProductByID(productId);
        if (product == null) {
            JOptionPane.showMessageDialog(this,
                "Invalid input. Please enter a valid product ID.",
                "Input Error",
                JOptionPane.ERROR_MESSAGE);
            return;
        }

        // if invalid quantity
        String quantityStr = JOptionPane.showInputDialog(this, "Enter Quantity:");
        try {
            int quantity = Integer.parseInt(quantityStr);
            if (!isValidQuantity(productId, quantity)) {
                JOptionPane.showMessageDialog(this,
                    "Quantity must be between 1 and " + ProductService.getTotalUnitsOnHand(),
                    "Input Error",
                    JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            model.addRow(new Object[]{productId, product.getProductClass(), quantityStr, product.getUnitPrice()});
        
        // sending error message to user
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, 
                "Invalid input. Quantity must be a positive integer.", 
                "Input Error", 
                JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Calculating total
     */
    private void calculateTotal() {
        // adding subtotal of each product
        double subtotal = 0.0;
        for (int i = 0; i < productsTable.getRowCount(); i++) {
            int quantity = Integer.parseInt((String) productsTable.getValueAt(i, 2));
            double unitPrice = (double) productsTable.getValueAt(i, 3);
            subtotal += quantity * unitPrice;
        }

        // adding the tax
        double taxRate;
        try {
            taxRate = Double.parseDouble(salesTaxField.getText());
            if (taxRate < 0 || taxRate > 0.1) {
                throw new NumberFormatException();
            }
        // showing error to user
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, 
                "Invalid sales tax. Enter a value between 0 and 0.1.", 
                "Input Error", 
                JOptionPane.ERROR_MESSAGE);
            return;
        }

        // tax discount and total
        double tax = subtotal * taxRate;
        double discount = subtotal > 100 ? 10 : 0;
        double total = subtotal + tax - discount;

        DecimalFormat df = new DecimalFormat("#.##");
        subtotalField.setText(df.format(subtotal));
        taxField.setText(df.format(tax));
        discountField.setText(df.format(discount));
        totalField.setText(df.format(total));
    }

    /**
     * Validating customer id
     * @param customerId The ID of the customer
     * @return if its valid or not
     */
    private boolean isValidCustomerId(int customerId) {
        return customerId > 0 && customerId <= CustomerService.getCustomerCount();
    }

    /**
     * Validating order date
     * @param orderDate The date of the order
     * @return if its valid or not
     */
    private boolean isValidOrderDate(Date orderDate) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String today = sdf.format(new java.util.Date());
        return sdf.format(orderDate).equals(today);
    }

    /**
     * Validating shipping date
     * @param shippingDate The shipping date
     * @return if its valid or not
     */
    private boolean isValidShippingDate(Date shippingDate) {
        return !shippingDate.before(new java.util.Date());
    }

    /**
     * Validating product id
     * @param productId The ID of the product
     * @return if its valid or not
     */
    private boolean isValidProductId(String productId) {
        try {
            int id = Integer.parseInt(productId);
            return id > 0 && id <= ProductService.getProductCount();
        } catch (NumberFormatException e) {
            return false;
        }
    }

    /**
     * Validating quantity
     * @param productId The productId
     * @param quantity The quantity
     * @return if its valid or not
     */
    private boolean isValidQuantity(String productId, int quantity) {
        return quantity > 0 && quantity <= ProductService.getTotalUnitsOnHand();
    }

    /**
     * Save an order
     */
    private void saveOrder() {
        // error if no products
        if (productsTable.getRowCount() == 0) {
            JOptionPane.showMessageDialog(this, 
                "Please add at least one product.", 
                "Input Error", 
                JOptionPane.ERROR_MESSAGE);
            return;
        }

        // validating customer ID
        try {
            // Validate Customer ID
            int customerId = Integer.parseInt(customerIdField.getText());
            
            // if no customers loaded
            if (CustomerService.getCustomerCount() == 0) {
                JOptionPane.showMessageDialog(this,
                    "Please load a customer first",
                    "Input Error",
                    JOptionPane.ERROR_MESSAGE);
                return;
            }     
            // if the id isnt valid     
            else if (!isValidCustomerId(customerId)) {
                JOptionPane.showMessageDialog(this,
                    "Customer ID must be between 1 and " + CustomerService.getCustomerCount(),
                    "Input Error",
                    JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Validate dates
            Date orderDate = Date.valueOf(orderDateField.getText());
            if (!isValidOrderDate(orderDate)) {
                JOptionPane.showMessageDialog(this,
                    "Order date must be today's date",
                    "Input Error",
                    JOptionPane.ERROR_MESSAGE);
                return;
            }

            // validating shipping date
            Date shippingDate = Date.valueOf(shippingDateField.getText());
            if (!isValidShippingDate(shippingDate)) {
                JOptionPane.showMessageDialog(this,
                    "Shipping date cannot be in the past",
                    "Input Error",
                    JOptionPane.ERROR_MESSAGE);
                return;
            }

            // creating the new order
            Order newOrder = new Order();
            newOrder.setCustomerID(customerId);
            newOrder.setDate(orderDate);
            newOrder.setShippingDate(shippingDate);
            newOrder.setStatus((String)statusComboBox.getSelectedItem());
            newOrder.setShippingMethod((String)shippingMethodComboBox.getSelectedItem());
            newOrder.setSalesTax(Double.parseDouble(salesTaxField.getText()));

            // success or fail
            boolean result = OrderService.addOrder(newOrder);
            if (result) {
                JOptionPane.showMessageDialog(this, 
                    "Order data saved.", 
                    "Success", 
                    JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, 
                    "Order data not saved.", 
                    "Failed", 
                    JOptionPane.INFORMATION_MESSAGE);
            }
            dispose();

        // showing user errors
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, 
                "Customer ID must be an integer and Sales Tax must be a valid number.", 
                "Input Error", 
                JOptionPane.ERROR_MESSAGE);
        } catch (IllegalArgumentException e) {
            JOptionPane.showMessageDialog(this, 
                "Invalid date format. Please use YYYY-MM-DD.", 
                "Input Error", 
                JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Main method to launch the application.
     * @param args Command line arguments (not used)
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception e) {
                e.printStackTrace();
            }
            new OrderEditPresentation().setVisible(true);
        });
    }
}
