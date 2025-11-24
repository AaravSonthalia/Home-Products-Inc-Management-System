import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import javax.swing.border.*;
import java.util.List;
import java.util.HashMap;
import java.util.ArrayList; 

/**
 * A presentation class for viewing customer details in a graphical interface.
 * This class provides a comprehensive view of customer information including
 * personal details, contact information, orders, and payment history.
 * @author Ethan C and Aarav S
 */
public class CustomerViewPresentation extends JFrame {
    // Color scheme constants for UI consistency
    private static final Color PRIMARY_COLOR = new Color(41, 128, 185);    // Professional blue
    private static final Color SECONDARY_COLOR = new Color(236, 240, 241); // Light gray
    private static final Color ACCENT_COLOR = new Color(52, 152, 219);     // Bright blue
    private static final Color TEXT_COLOR = new Color(44, 62, 80);         // Dark blue-gray
    private static final Color HEADER_COLOR = new Color(52, 73, 94);       // Dark slate
    private static final Color BACKGROUND_COLOR = new Color(75, 101, 132); // Blue-grey
    private static final Color BUTTON_BG_COLOR = Color.WHITE;
    private static final Color BUTTON_TEXT_COLOR = HEADER_COLOR;

    // Form fields for customer information
    private JTextField firstNameField, lastNameField, streetField, cityField, stateField, zipCodeField, creditField;
    private JTextField companyField, websiteField, emailField, businessNumberField, cellNumberField, titleField, statusField;
    private JTextField remainingCreditField, lifetimeOrdersTotalField;
    private JTextArea notesField;
    private JTable ordersTable, paymentsTable;
    private JButton backButton;
    private JTable customersTable;

    /**
     * Constructs a new CustomerViewPresentation window.
     * Initializes the UI components and sets up the layout.
     */
    public CustomerViewPresentation() {
        // Basic window setup
        setTitle("View Customer");
        setSize(800, 1000);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Create main panel with gradient background
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

        // Add components to main panel
        mainPanel.add(createHeaderPanel(), BorderLayout.NORTH);
        
        // Create scrollable content area
        JScrollPane scrollPane = new JScrollPane(createGroupedFormPanel());
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        scrollPane.getViewport().setOpaque(false);
        scrollPane.setOpaque(false);
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        // Add button panel at bottom
        mainPanel.add(createButtonPanel(), BorderLayout.SOUTH);
    }

    /**
     * Creates and returns the header panel with title.
     * @return JPanel containing the header elements
     */
    private JPanel createHeaderPanel() {
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(HEADER_COLOR);
        headerPanel.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));
        
        JLabel headerLabel = new JLabel("Customer View", JLabel.CENTER);
        headerLabel.setFont(new Font("Segoe UI Light", Font.BOLD, 28));
        headerLabel.setForeground(Color.WHITE);
        headerPanel.add(headerLabel, BorderLayout.CENTER);
        
        return headerPanel;
    }


    /**
     * Creates the button panel containing navigation controls.
     * @return JPanel containing the styled buttons
     */
    private JPanel createButtonPanel() {
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.setOpaque(false);
        
        // Create and style the back button
        backButton = createStyledButton("Back to Main Menu", new Color(231, 76, 60));
        buttonPanel.add(backButton);
        backButton.addActionListener(e -> backToMainMenu());
        return buttonPanel;
    }

    /**
     * Creates a styled button with consistent appearance.
     * @param text The button label text
     * @param baseColor The base color for the button
     * @return JButton styled according to the application's theme
     */
    private JButton createStyledButton(String text, Color baseColor) {
        JButton button = new JButton(text);
        button.setFont(new Font("Segoe UI", Font.BOLD, 14));
        button.setForeground(BUTTON_TEXT_COLOR);
        button.setBackground(BUTTON_BG_COLOR);
        button.setFocusPainted(false);
        
        // Add compound border for better visual appearance
        button.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(BUTTON_TEXT_COLOR, 2),
            BorderFactory.createEmptyBorder(8, 15, 8, 15)
        ));
        button.setPreferredSize(new Dimension(160, 40));
        
        // Add hover effects
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
     * Creates the main form panel containing all customer information groups.
     * @return JPanel containing all grouped form elements
     */
    private JPanel createGroupedFormPanel() {
        JPanel groupedPanel = new JPanel(new GridBagLayout());
        groupedPanel.setOpaque(false);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;

        // Add instruction header
        JLabel headerLabel = new JLabel("<html><div style='text-align: center;'>"
                + "Search for a customer using their Customer ID.</div></html>");
        headerLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        headerLabel.setForeground(Color.WHITE);
        headerLabel.setHorizontalAlignment(SwingConstants.CENTER);
        
        // Layout components using GridBagConstraints
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.weightx = 1.0;
        groupedPanel.add(headerLabel, gbc);
        gbc.gridwidth = 1;

        // Add all information panels in sequence
        addGroupPanel(groupedPanel, createCustomersTablePanel(), "Customers", ++gbc.gridy);
        addGroupPanel(groupedPanel, createSearchPanel(), "Search Customer", ++gbc.gridy);
        addGroupPanel(groupedPanel, createPersonalInfoPanel(), "Personal Information", ++gbc.gridy);
        addGroupPanel(groupedPanel, createAddressInfoPanel(), "Address Information", ++gbc.gridy);
        addGroupPanel(groupedPanel, createContactInfoPanel(), "Contact Information", ++gbc.gridy);
        addGroupPanel(groupedPanel, createJobInfoPanel(), "Job Information", ++gbc.gridy);
        addGroupPanel(groupedPanel, createOtherInfoPanel(), "Other Information", ++gbc.gridy);
        addGroupPanel(groupedPanel, createOrdersPanel(), "Orders", ++gbc.gridy);
        addGroupPanel(groupedPanel, createPaymentsPanel(), "Payments", ++gbc.gridy);

        return groupedPanel;
    }

        /**
     * Adds a titled panel to the parent container with consistent styling.
     * @param parent The parent panel to add to
     * @param panel The panel to be added
     * @param title The title for the panel section
     * @param gridy The vertical position in the grid
     */
    private void addGroupPanel(JPanel parent, JPanel panel, String title, int gridy) {
        panel.setOpaque(false);
        TitledBorder titledBorder = BorderFactory.createTitledBorder(title);
        titledBorder.setTitleColor(Color.WHITE);
        panel.setBorder(titledBorder);
        
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
     * Creates the search panel with customer ID search functionality.
     * @return JPanel containing search components
     */
    private JPanel createSearchPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setOpaque(false);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Create and configure search components
        JTextField searchField = createStyledTextField();
        JButton searchButton = createStyledButton("Search", ACCENT_COLOR);
        
        addFormField(panel, "Customer ID:", searchField, gbc);
        
        gbc.gridx = 2;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.NONE;
        panel.add(searchButton, gbc);
        
        // Add search functionality
        searchButton.addActionListener(e -> searchCustomer(searchField.getText()));
        return panel;
    }

    /**
     * Creates the customers table panel displaying all customer records.
     * @return JPanel containing the customers table
     */
    private JPanel createCustomersTablePanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setOpaque(false);
        
        // Define table structure
        String[] columnNames = {"Customer ID", "First Name", "Last Name", "Company", "Email", "Status"};
        DefaultTableModel model = new DefaultTableModel(columnNames, 0);
        customersTable = new JTable(model);
        
        // Style the table
        customersTable.setForeground(TEXT_COLOR);
        customersTable.setBackground(SECONDARY_COLOR);
        customersTable.setGridColor(ACCENT_COLOR);
        customersTable.getTableHeader().setBackground(HEADER_COLOR);
        customersTable.getTableHeader().setForeground(Color.WHITE);
        
        // Populate table with customer data
        List<Customer> customers = CustomerService.getAllCustomers();
        for (Customer customer : customers) {
            model.addRow(new Object[]{
                customer.getCustomerID(),
                customer.getFirstName(),
                customer.getLastName(),
                customer.getCompany(),
                customer.getEmail(),
                customer.getStatus()
            });
        }
        
        // Add table to scrollpane
        JScrollPane scrollPane = new JScrollPane(customersTable);
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);
        panel.add(scrollPane, BorderLayout.CENTER);
        return panel;
    }

        /**
     * Creates the personal information panel containing name fields.
     * @return JPanel containing personal information fields
     */
    private JPanel createPersonalInfoPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setOpaque(false);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        // Initialize and configure name fields
        firstNameField = createStyledTextField();
        lastNameField = createStyledTextField();
        firstNameField.setEditable(false);
        lastNameField.setEditable(false);

        // Add fields to panel
        addFormField(panel, "First Name:", firstNameField, gbc);
        addFormField(panel, "Last Name:", lastNameField, gbc);
        return panel;
    }

    /**
     * Creates the address information panel containing location fields.
     * @return JPanel containing address information fields
     */
    private JPanel createAddressInfoPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setOpaque(false);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        // Initialize address fields
        streetField = createStyledTextField();
        cityField = createStyledTextField();
        stateField = createStyledTextField();
        zipCodeField = createStyledTextField();
        
        // Set fields as read-only
        streetField.setEditable(false);
        cityField.setEditable(false);
        stateField.setEditable(false);
        zipCodeField.setEditable(false);

        // Add fields to panel
        addFormField(panel, "Street:", streetField, gbc);
        addFormField(panel, "City:", cityField, gbc);
        addFormField(panel, "State:", stateField, gbc);
        addFormField(panel, "Zip Code:", zipCodeField, gbc);
        return panel;
    }

    /**
     * Creates the contact information panel containing communication fields.
     * @return JPanel containing contact information fields
     */
    private JPanel createContactInfoPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setOpaque(false);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        // Initialize contact fields
        companyField = createStyledTextField();
        businessNumberField = createStyledTextField();
        cellNumberField = createStyledTextField();
        emailField = createStyledTextField();
        websiteField = createStyledTextField();
        
        // Set fields as read-only
        companyField.setEditable(false);
        businessNumberField.setEditable(false);
        cellNumberField.setEditable(false);
        emailField.setEditable(false);
        websiteField.setEditable(false);

        // Add fields to panel
        addFormField(panel, "Company:", companyField, gbc);
        addFormField(panel, "Business Number:", businessNumberField, gbc);
        addFormField(panel, "Cell Number:", cellNumberField, gbc);
        addFormField(panel, "Email:", emailField, gbc);
        addFormField(panel, "Website:", websiteField, gbc);
        return panel;
    }

        /**
     * Creates the job information panel containing employment-related fields.
     * @return JPanel containing job information fields
     */
    private JPanel createJobInfoPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setOpaque(false);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        // Initialize job-related fields
        titleField = createStyledTextField();
        statusField = createStyledTextField();
        
        // Set fields as read-only
        titleField.setEditable(false);
        statusField.setEditable(false);

        // Add fields to panel
        addFormField(panel, "Title:", titleField, gbc);
        addFormField(panel, "Status:", statusField, gbc);
        return panel;
    }

    /**
     * Creates the other information panel containing miscellaneous customer data.
     * @return JPanel containing additional customer information fields
     */
    private JPanel createOtherInfoPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setOpaque(false);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        // Initialize additional information fields
        creditField = createStyledTextField();
        remainingCreditField = createStyledTextField();
        lifetimeOrdersTotalField = createStyledTextField();
        notesField = createStyledTextArea(5, 20);
        
        // Set fields as read-only
        creditField.setEditable(false);
        remainingCreditField.setEditable(false);
        lifetimeOrdersTotalField.setEditable(false);
        notesField.setEditable(false);

        // Add fields to panel
        addFormField(panel, "Credit ($):", creditField, gbc);
        addFormField(panel, "Remaining Credit ($):", remainingCreditField, gbc);
        addFormField(panel, "Lifetime Orders Total ($):", lifetimeOrdersTotalField, gbc);
        addAreaField(panel, "Notes:", notesField, gbc);
        return panel;
    }

    /**
     * Creates the orders panel containing the customer's order history table.
     * @return JPanel containing the orders table
     */
    private JPanel createOrdersPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setOpaque(false);
        
        // Initialize orders table
        String[] columnNames = {"Order ID", "Date", "Total", "Status"};
        DefaultTableModel model = new DefaultTableModel(columnNames, 0);
        ordersTable = new JTable(model);

        // Style the table
        ordersTable.setForeground(TEXT_COLOR);
        ordersTable.setBackground(SECONDARY_COLOR);
        ordersTable.setGridColor(ACCENT_COLOR);
        ordersTable.getTableHeader().setBackground(HEADER_COLOR);
        ordersTable.getTableHeader().setForeground(Color.WHITE);
        
        // Add table to scrollpane
        JScrollPane scrollPane = new JScrollPane(ordersTable);
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);
        panel.add(scrollPane, BorderLayout.CENTER);
        return panel;
    }

        /**
     * Creates the payments panel containing the customer's payment history table.
     * @return JPanel containing the payments table
     */
    private JPanel createPaymentsPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setOpaque(false);
        
        // Initialize payments table
        String[] columnNames = {"Payment ID", "Date", "Amount", "Method"};
        DefaultTableModel model = new DefaultTableModel(columnNames, 0);
        paymentsTable = new JTable(model);

        // Style the table
        paymentsTable.setForeground(TEXT_COLOR);
        paymentsTable.setBackground(SECONDARY_COLOR);
        paymentsTable.setGridColor(ACCENT_COLOR);
        paymentsTable.getTableHeader().setBackground(HEADER_COLOR);
        paymentsTable.getTableHeader().setForeground(Color.WHITE);
        
        // Add table to scrollpane
        JScrollPane scrollPane = new JScrollPane(paymentsTable);
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);
        panel.add(scrollPane, BorderLayout.CENTER);
        return panel;
    }

    /**
     * Creates a styled text field with consistent appearance.
     * @return JTextField configured with the application's styling
     */
    private JTextField createStyledTextField() {
        JTextField field = new JTextField();
        field.setBackground(SECONDARY_COLOR);
        field.setForeground(TEXT_COLOR);
        field.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(ACCENT_COLOR),
            BorderFactory.createEmptyBorder(5, 5, 5, 5)
        ));
        field.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        
        // Set consistent field size
        field.setPreferredSize(new Dimension(300, 30));
        return field;
    }

    /**
     * Creates a styled text area with consistent appearance.
     * @param rows Number of rows in the text area
     * @param columns Number of columns in the text area
     * @return JTextArea configured with the application's styling
     */
    private JTextArea createStyledTextArea(int rows, int columns) {
        JTextArea area = new JTextArea(rows, columns);
        area.setBackground(SECONDARY_COLOR);
        area.setForeground(TEXT_COLOR);
        area.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(ACCENT_COLOR),
            BorderFactory.createEmptyBorder(5, 5, 5, 5)
        ));
        area.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        area.setLineWrap(true);
        area.setWrapStyleWord(true);
        return area;
    }

        /**
     * Adds a labeled form field to a panel.
     * @param panel The panel to add the field to
     * @param label The label text for the field
     * @param field The component to add
     * @param gbc The GridBagConstraints for layout
     */
    private void addFormField(JPanel panel, String label, JComponent field, GridBagConstraints gbc) {
        gbc.gridx = 0;
        gbc.gridy++;
        gbc.gridwidth = 1;
        gbc.weightx = 0.3;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.WEST;
        
        // Create and style the label
        JLabel labelComponent = new JLabel(label, SwingConstants.LEFT);
        labelComponent.setForeground(Color.WHITE);
        labelComponent.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        panel.add(labelComponent, gbc);
        
        // Add the field component
        gbc.gridx = 1;
        gbc.weightx = 0.7;
        gbc.anchor = GridBagConstraints.WEST;
        panel.add(field, gbc);
    }

    /**
     * Adds a labeled text area to a panel.
     * @param panel The panel to add the text area to
     * @param label The label text for the text area
     * @param textArea The text area component to add
     * @param gbc The GridBagConstraints for layout
     */
    private void addAreaField(JPanel panel, String label, JTextArea textArea, GridBagConstraints gbc) {
        gbc.gridx = 0;
        gbc.gridy++;
        gbc.gridwidth = 1;
        gbc.weightx = 0.3;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.WEST;
        
        // Create and style the label
        JLabel labelComponent = new JLabel(label, SwingConstants.LEFT);
        labelComponent.setForeground(Color.WHITE);
        labelComponent.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        panel.add(labelComponent, gbc);

        // Add the text area in a scroll pane
        gbc.gridx = 1;
        gbc.weightx = 0.7;
        gbc.anchor = GridBagConstraints.WEST;
        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setPreferredSize(new Dimension(300, 100));
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);
        panel.add(scrollPane, gbc);
    }

    /**
     * Populates the payments table with customer payment data.
     * @param customerId The ID of the customer whose payments to display
     */
    private void populatePaymentsTable(int customerId) {
        DefaultTableModel model = (DefaultTableModel) paymentsTable.getModel();
        model.setRowCount(0); // Clear existing rows
        
        // Get detailed payment information
        ArrayList<ArrayList<Object>> payments = CustomerService.getAllCustomerPaymentsDetailed(customerId);
        
        // Add each payment to the table
        for (ArrayList<Object> payment : payments) {
            model.addRow(new Object[]{
                payment.get(0),  // Payment ID
                payment.get(2),  // Date
                String.format("$%.2f", payment.get(3)),  // Amount formatted as currency
                payment.get(4)   // Method
            });
        }
    }

    /**
     * Populates the orders table with customer order data.
     * @param customerId The ID of the customer whose orders to display
     */
    private void populateOrdersTable(int customerId) {
        DefaultTableModel model = (DefaultTableModel) ordersTable.getModel();
        model.setRowCount(0); // Clear existing rows
        
        // Get detailed product/order information
        ArrayList<ArrayList<Object>> products = CustomerService.getAllCustomerProducts(customerId);
        
        // Create a map to store order information
        java.util.Map<Integer, java.sql.Date> orderDates = new java.util.HashMap<>();
        java.util.Map<Integer, Double> orderTotals = new java.util.HashMap<>();
        
        // Calculate totals for each order
        for (ArrayList<Object> product : products) {
            int orderId = (Integer) product.get(4);
            java.sql.Date orderDate = (java.sql.Date) product.get(5);
            double itemTotal = (Integer) product.get(2) * (Double) product.get(3); // quantity * price
            
            // Store the order date
            orderDates.putIfAbsent(orderId, orderDate);
            
            // Add to the order total
            orderTotals.merge(orderId, itemTotal, Double::sum);
        }
        
        // Add each order to the table
        for (Integer orderId : orderDates.keySet()) {
            model.addRow(new Object[]{
                orderId,  // Order ID
                orderDates.get(orderId),  // Date
                String.format("$%.2f", orderTotals.get(orderId)),  // Total formatted as currency
                "Completed"  // Status (assuming all historical orders are completed)
            });
        }
    }

    // Update the searchCustomer method to include populating tables
    private void searchCustomer(String searchQuery) {
        try {
            int customerId = Integer.parseInt(searchQuery);
            int maxCustomerId = CustomerService.getCustomerCount();
            
            // Validate customer ID
            if (maxCustomerId == 0) {
                JOptionPane.showMessageDialog(this,
                    "Please load a customer first",
                    "Invalid Input",
                    JOptionPane.WARNING_MESSAGE);
                return;
            }
            else if (customerId < 1 || customerId > maxCustomerId) {
                JOptionPane.showMessageDialog(this,
                    "Customer ID must be between 1 and " + maxCustomerId,
                    "Invalid Input",
                    JOptionPane.WARNING_MESSAGE);
                return;
            }

            // Retrieve and display customer data
            Customer customer = CustomerService.getCustomerByID(customerId);
            if (customer != null) {
                populateCustomerFields(customer);
                populatePaymentsTable(customerId);  // Add this line
                populateOrdersTable(customerId);    // Add this line
            } else {
                JOptionPane.showMessageDialog(this,
                    "Customer not found!", "Error",
                    JOptionPane.ERROR_MESSAGE);
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this,
                "Invalid Customer ID! Please enter a number.",
                "Error",
                JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this,
                "An error occurred while retrieving the customer.",
                "Error",
                JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Populates all form fields with customer data.
     * @param customer The customer object containing the data
     */
    private void populateCustomerFields(Customer customer) {
        firstNameField.setText(customer.getFirstName());
        lastNameField.setText(customer.getLastName());
        streetField.setText(customer.getStreet());
        cityField.setText(customer.getCity());
        stateField.setText(customer.getState());
        zipCodeField.setText(customer.getZipCode());
        creditField.setText(String.valueOf(customer.getCredit()));
        companyField.setText(customer.getCompany());
        websiteField.setText(customer.getWebsite());
        emailField.setText(customer.getEmail());
        businessNumberField.setText(customer.getBusinessNumber());
        cellNumberField.setText(customer.getCellNumber());
        titleField.setText(customer.getTitle());
        statusField.setText(customer.getStatus());
        remainingCreditField.setText(String.valueOf(customer.getRemainingCredit()));
        lifetimeOrdersTotalField.setText(String.valueOf(customer.getLifetimeOrderTotal()));
        notesField.setText(customer.getNotes());
    }

    /**
     * Closes the customer view window.
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
            new CustomerViewPresentation().setVisible(true);
        });
    }
}