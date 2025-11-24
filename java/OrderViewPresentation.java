import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import javax.swing.border.*;
import java.util.ArrayList;  

/**
 * Class to view an order
 * @author Ethan C and Aarav S
 * @version 1.0.0
 */
public class OrderViewPresentation extends JFrame {
    // Modern color scheme (matching CustomerViewPresentation)
    private static final Color PRIMARY_COLOR = new Color(41, 128, 185);    // Professional blue
    private static final Color SECONDARY_COLOR = new Color(236, 240, 241); // Light gray
    private static final Color ACCENT_COLOR = new Color(52, 152, 219);     // Bright blue
    private static final Color TEXT_COLOR = new Color(44, 62, 80);         // Dark blue-gray
    private static final Color HEADER_COLOR = new Color(52, 73, 94);       // Dark slate
    private static final Color BACKGROUND_COLOR = new Color(75, 101, 132); // Blue-grey
    private static final Color BUTTON_BG_COLOR = Color.WHITE;
    private static final Color BUTTON_TEXT_COLOR = HEADER_COLOR;

    // styling things
    private JTextField orderIdField, orderDateField, shippingDateField, statusField, shippingMethodField;
    private JTextField customerNameField, salesTaxField, orderSubtotalField, orderTotalField, discountField;
    private JTable productsTable, paymentsTable;
    private JButton backButton;

    /**
     * Constructor initializes the form and components
     */
    public OrderViewPresentation() {
        setTitle("View Order");
        setSize(800, 1000);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Main panel with gradient background
        JPanel mainPanel = new JPanel(new BorderLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
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
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        scrollPane.getViewport().setOpaque(false);
        scrollPane.setOpaque(false);
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        // Button panel
        JPanel buttonPanel = createButtonPanel();
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);
    }

    /**
     * Header panel method
     * @return Configured header panel
     */
    private JPanel createHeaderPanel() {
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(HEADER_COLOR);
        headerPanel.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));
        
        JLabel headerLabel = new JLabel("Order View", JLabel.CENTER);
        headerLabel.setFont(new Font("Segoe UI Light", Font.BOLD, 28));
        headerLabel.setForeground(Color.WHITE);
        headerPanel.add(headerLabel, BorderLayout.CENTER);
        
        return headerPanel;
    }

    /**
     * Button panel method
     * @return Configured button panel
     */
    private JPanel createButtonPanel() {
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.setOpaque(false);
        
        backButton = createStyledButton("Back to Main Menu", new Color(231, 76, 60));
        buttonPanel.add(backButton);
        backButton.addActionListener(e -> backToMainMenu());
        return buttonPanel;
    }

    /**
     * Styled button method
     * @return Configured styled button
     */
    private JButton createStyledButton(String text, Color baseColor) {
        // font and text stuff
        JButton button = new JButton(text);
        button.setFont(new Font("Segoe UI", Font.BOLD, 14));
        button.setForeground(BUTTON_TEXT_COLOR);
        button.setBackground(BUTTON_BG_COLOR);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(BUTTON_TEXT_COLOR, 2),
            BorderFactory.createEmptyBorder(8, 15, 8, 15)
        ));
        button.setPreferredSize(new Dimension(160, 40));
        
        // super cool hover feature
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
     * Group panel method
     * @return Configured group panel
     */
    private JPanel createGroupedFormPanel() {
        JPanel groupedPanel = new JPanel(new GridBagLayout());
        groupedPanel.setOpaque(false);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);

        JLabel headerLabel = new JLabel("<html><div style='text-align: center;'>"
                + "Search for an order using the Order ID.</div></html>");
        headerLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        headerLabel.setForeground(Color.WHITE);
        headerLabel.setHorizontalAlignment(SwingConstants.CENTER);

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.weightx = 1.0;
        groupedPanel.add(headerLabel, gbc);
        gbc.gridwidth = 1;

        addGroupPanel(groupedPanel, createSearchPanel(), "Search Order", ++gbc.gridy);
        addGroupPanel(groupedPanel, createOrderDetailsPanel(), "Order Details", ++gbc.gridy);
        addGroupPanel(groupedPanel, createFinancialDetailsPanel(), "Financial Details", ++gbc.gridy);
        addGroupPanel(groupedPanel, createProductsPanel(), "Products", ++gbc.gridy);
        addGroupPanel(groupedPanel, createPaymentsPanel(), "Payments", ++gbc.gridy);

        return groupedPanel;
    }

    /**
     * Method to add a group panel
     * @param parent The parent j panel
     * @param panel A j panel to add
     * @param title The title of the group
     * @param gridy The grid y dimensions
     */
    private void addGroupPanel(JPanel parent, JPanel panel, String title, int gridy) {
        panel.setOpaque(false);
        TitledBorder titledBorder = BorderFactory.createTitledBorder(title);
        titledBorder.setTitleColor(Color.WHITE);
        panel.setBorder(titledBorder);
        
        // the dimensions
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
     * Creating search panel
     * @return Configured search panel
     */
    private JPanel createSearchPanel() {
        // creating panel style
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setOpaque(false);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JTextField searchField = createStyledTextField();
        JButton searchButton = createStyledButton("Search", ACCENT_COLOR);
        
        addFormField(panel, "Order ID:", searchField, gbc);
        
        // dimensions
        gbc.gridx = 2;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.NONE;
        panel.add(searchButton, gbc);
        searchButton.addActionListener(e -> searchOrder(searchField.getText()));
        return panel;
    }

    /**
     * Order details panel
     * @return Configured order details panel
     */
    private JPanel createOrderDetailsPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setOpaque(false);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        // creating text fields
        orderDateField = createStyledTextField();
        shippingDateField = createStyledTextField();
        statusField = createStyledTextField();
        shippingMethodField = createStyledTextField();
        customerNameField = createStyledTextField();

        orderDateField.setEditable(false);
        shippingDateField.setEditable(false);
        statusField.setEditable(false);
        shippingMethodField.setEditable(false);
        customerNameField.setEditable(false);

        // setting field names
        addFormField(panel, "Order Date:", orderDateField, gbc);
        addFormField(panel, "Shipping Date:", shippingDateField, gbc);
        addFormField(panel, "Status:", statusField, gbc);
        addFormField(panel, "Shipping Method:", shippingMethodField, gbc);
        addFormField(panel, "Customer Name:", customerNameField, gbc);

        return panel;
    }

    /**
     * Financial details panel
     * @return Configured financial details panel
     */
    private JPanel createFinancialDetailsPanel() {
        // panel dimensions
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setOpaque(false);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        // creating text fields
        salesTaxField = createStyledTextField();
        orderSubtotalField = createStyledTextField();
        orderTotalField = createStyledTextField();
        discountField = createStyledTextField();

        salesTaxField.setEditable(false);
        orderSubtotalField.setEditable(false);
        orderTotalField.setEditable(false);
        discountField.setEditable(false);

        // creating field names
        addFormField(panel, "Sales Tax ($):", salesTaxField, gbc);
        addFormField(panel, "Order Subtotal ($):", orderSubtotalField, gbc);
        addFormField(panel, "Order Total ($):", orderTotalField, gbc);
        addFormField(panel, "Discount ($):", discountField, gbc);

        return panel;
    }

    /**
     * Products panel
     * @return Configured products panel
     */
    private JPanel createProductsPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setOpaque(false);
        
        // configuring the UI
        String[] columnNames = {"Product ID", "Description", "Quantity", "Unit Price ($)", "Total ($)"};
        DefaultTableModel model = new DefaultTableModel(columnNames, 0);
        productsTable = new JTable(model);
        productsTable.setForeground(TEXT_COLOR);
        productsTable.setBackground(SECONDARY_COLOR);
        productsTable.setGridColor(ACCENT_COLOR);
        productsTable.getTableHeader().setBackground(HEADER_COLOR);
        productsTable.getTableHeader().setForeground(Color.WHITE);
        
        // adding a cool scroll pane
        JScrollPane scrollPane = new JScrollPane(productsTable);
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);
        panel.add(scrollPane, BorderLayout.CENTER);
        return panel;
    }

    /**
     * Payment panel
     * @return Configured payment panel
     */
    private JPanel createPaymentsPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setOpaque(false);
        
        // configuring the UI
        String[] columnNames = {"Payment ID", "Date", "Amount ($)", "Method"};
        DefaultTableModel model = new DefaultTableModel(columnNames, 0);
        paymentsTable = new JTable(model);
        paymentsTable.setForeground(TEXT_COLOR);
        paymentsTable.setBackground(SECONDARY_COLOR);
        paymentsTable.setGridColor(ACCENT_COLOR);
        paymentsTable.getTableHeader().setBackground(HEADER_COLOR);
        paymentsTable.getTableHeader().setForeground(Color.WHITE);
        
        // cool scroll pane
        JScrollPane scrollPane = new JScrollPane(paymentsTable);
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);
        panel.add(scrollPane, BorderLayout.CENTER);
        return panel;
    }

    /**
     * Create a stylized text field
     * @return The configured stylized text field
     */
    private JTextField createStyledTextField() {
        JTextField field = new JTextField();
        field.setBackground(SECONDARY_COLOR);
        field.setForeground(TEXT_COLOR);
        field.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(ACCENT_COLOR),
            BorderFactory.createEmptyBorder(5, 5, 5, 5)
        ));
        // font stuff
        field.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        field.setPreferredSize(new Dimension(300, 30));
        return field;
    }

    /**
     * Add a field to the form
     * @param panel The panel for the field
     * @param label The field label
     * @param field The field to configure
     * @param gbc The dimensions controller
     */
    private void addFormField(JPanel panel, String label, JComponent field, GridBagConstraints gbc) {
        gbc.gridx = 0;
        gbc.gridy++;
        gbc.gridwidth = 1;
        gbc.weightx = 0.3;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.WEST;
        
        JLabel labelComponent = new JLabel(label, SwingConstants.LEFT);
        labelComponent.setForeground(Color.WHITE);
        labelComponent.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        panel.add(labelComponent, gbc);
        
        gbc.gridx = 1;
        gbc.weightx = 0.7;
        gbc.anchor = GridBagConstraints.WEST;
        panel.add(field, gbc);
    }

    private void searchOrder(String searchQuery) {
        try {
            int orderId = Integer.parseInt(searchQuery);
            int maxOrderId = OrderService.getOrderCount();
            
            // if no orders do this
            if (maxOrderId == 0) {
                JOptionPane.showMessageDialog(this,
                    "Please load an order first",
                    "Invalid Input",
                    JOptionPane.WARNING_MESSAGE);
                return;
            }
            // if invalid order do this
            else if (orderId < 0 || orderId > maxOrderId) {
                JOptionPane.showMessageDialog(this,
                    "Order ID must be between 0 and " + maxOrderId,
                    "Invalid Input",
                    JOptionPane.WARNING_MESSAGE);
                return;
            }

            Order order = OrderService.getOrderByID(orderId);
            if (order != null) {
                // Update order details
                orderDateField.setText(String.valueOf(order.getDate()));
                shippingDateField.setText(String.valueOf(order.getShippingDate()));
                statusField.setText(order.getStatus());
                shippingMethodField.setText(order.getShippingMethod());
                customerNameField.setText(order.getCustomerName());
                salesTaxField.setText(String.format("%.2f", order.getSalesTax()));
                orderSubtotalField.setText(String.format("%.2f", order.getOrderSubtotal()));
                orderTotalField.setText(String.format("%.2f", order.getOrderTotal()));
                discountField.setText(String.format("%.2f", order.getDiscount()));

                // Update products table
                DefaultTableModel productsModel = (DefaultTableModel) productsTable.getModel();
                productsModel.setRowCount(0); // Clear existing rows
                ArrayList<ArrayList<Object>> products = OrderService.getOrderProducts(orderId);
                for (ArrayList<Object> product : products) {
                    Object[] row = {
                        product.get(0), // Product ID
                        product.get(1), // Description
                        product.get(2), // Quantity
                        String.format("%.2f", product.get(3)), // Unit Price
                        String.format("%.2f", product.get(4))  // Total
                    };
                    productsModel.addRow(row);
                }

                // Update payments table
                DefaultTableModel paymentsModel = (DefaultTableModel) paymentsTable.getModel();
                paymentsModel.setRowCount(0); // Clear existing rows
                ArrayList<ArrayList<Object>> payments = OrderService.getOrderPayments(orderId);
                for (ArrayList<Object> payment : payments) {
                    Object[] row = {
                        payment.get(0), // Payment ID
                        payment.get(1), // Date
                        String.format("%.2f", payment.get(2)), // Amount
                        payment.get(3)  // Method
                    };
                    paymentsModel.addRow(row);
                }
            } else {
                JOptionPane.showMessageDialog(this,
                    "Order not found!", "Error",
                    JOptionPane.ERROR_MESSAGE);
            }
        // showing user the errors
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this,
                "Invalid Order ID! Please enter a number.",
                "Error",
                JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this,
                "An error occurred while retrieving the order.",
                "Error",
                JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Back button functionality
     */
    private void backToMainMenu() {
        dispose();
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
            new OrderViewPresentation().setVisible(true);
        });
    }
}
