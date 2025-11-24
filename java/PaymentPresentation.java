import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;
import javax.swing.border.*;
import java.sql.Date;

/**
 * Class to edit a payment
 * @author Ethan C and Aarav S
 * @version 1.0.0
 */
public class PaymentPresentation extends JFrame {
    private JTextField customerIdField, orderIdField, amountField, cardNumberField, cardExpirationField, cardHolderField, paymentDateField;
    private JComboBox<String> paymentMethodComboBox;
    private JButton submitButton, cancelButton;

    // Modern color scheme (matching SalesRepEditPresentation)
    private static final Color PRIMARY_COLOR = new Color(41, 128, 185);
    private static final Color SECONDARY_COLOR = new Color(236, 240, 241);
    private static final Color ACCENT_COLOR = new Color(52, 152, 219);
    private static final Color TEXT_COLOR = new Color(44, 62, 80);
    private static final Color HEADER_COLOR = new Color(52, 73, 94);
    private static final Color BACKGROUND_COLOR = new Color(75, 101, 132);
    private static final Color BUTTON_BG_COLOR = Color.WHITE;
    private static final Color BUTTON_TEXT_COLOR = HEADER_COLOR;
    
    /**
     * Constructor initializes the form and components
     */
    public PaymentPresentation() {
        setTitle("New Payment");
        setSize(620, 600);
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

        // cool scroll pane
        JScrollPane scrollPane = new JScrollPane(createGroupedFormPanel());
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.getViewport().setOpaque(false);
        scrollPane.setOpaque(false);
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        JPanel buttonPanel = createButtonPanel();
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        // payment date is todays date
        initializePaymentDate();
        submitButton.addActionListener(e -> submitPayment());
        cancelButton.addActionListener(e -> dispose());
    }

    /**
     * Set the payment date to today
     */
    private void initializePaymentDate() {
        Date today = new Date(System.currentTimeMillis());
        paymentDateField.setText(today.toString());
    }

    /**
     * Creates the header panel
     * @return COnfigured UI
     */
    private JPanel createHeaderPanel() {
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(HEADER_COLOR);
        headerPanel.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));

        // font and text stuff
        JLabel headerLabel = new JLabel("Payment Processing", JLabel.CENTER);
        headerLabel.setFont(new Font("Segoe UI Light", Font.BOLD, 28));
        headerLabel.setForeground(Color.WHITE);
        headerPanel.add(headerLabel, BorderLayout.CENTER);

        return headerPanel;
    }

    /**
     * Creates the button panel
     * @return Configured button panel
     */
    private JPanel createButtonPanel() {
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.setOpaque(false);

        // cancel and submit buttons
        submitButton = createStyledButton("Submit Payment", ACCENT_COLOR);
        cancelButton = createStyledButton("Cancel", new Color(231, 76, 60));

        buttonPanel.add(submitButton);
        buttonPanel.add(cancelButton);

        return buttonPanel;
    }

    /**
     * Created a styled button
     * @param text The text on the button
     * @param baseColor The button base color
     * @return The configured button
     */
    private JButton createStyledButton(String text, Color baseColor) {
        // configuring button UI
        JButton button = new JButton(text);
        button.setFont(new Font("Segoe UI", Font.BOLD, 14));
        button.setForeground(BUTTON_TEXT_COLOR);
        button.setBackground(BUTTON_BG_COLOR);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(BUTTON_TEXT_COLOR, 2),
            BorderFactory.createEmptyBorder(8, 15, 8, 15)
        ));
        button.setPreferredSize(new Dimension(140, 40));

        // cool hover feature
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(BUTTON_BG_COLOR.darker());
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(BUTTON_BG_COLOR);
            }
        });

        return button;
    }

    /**
     * Create groups on the form
     * @return Configred groups
     */
    private JPanel createGroupedFormPanel() {
        // configuring UI
        JPanel groupedPanel = new JPanel(new GridBagLayout());
        groupedPanel.setOpaque(false);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;

        // Making the header stuff
        JLabel headerLabel = new JLabel("<html><div style='text-align: center;'>"
                + "Use this form to record a new payment.<br>"
                + "Fields marked with an asterisk (*) are required.</div></html>");
        headerLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        headerLabel.setForeground(Color.WHITE);
        headerLabel.setHorizontalAlignment(SwingConstants.CENTER);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.weightx = 1.0;
        groupedPanel.add(headerLabel, gbc);

        // making customer details and payment details groups
        gbc.gridwidth = 1;
        addGroupPanel(groupedPanel, createCustomerDetailsPanel(), "Customer Details", ++gbc.gridy);
        addGroupPanel(groupedPanel, createPaymentDetailsPanel(), "Payment Details", ++gbc.gridy);

        return groupedPanel;
    }

    /**
     * Method to add a group panel
     * @param parent The parent panel in the background
     * @param panel The panel to be added
     * @param title The title of the panel
     * @param gridy The y dimension of the panel
     */
    private void addGroupPanel(JPanel parent, JPanel panel, String title, int gridy) {
        panel.setOpaque(false);
        TitledBorder titledBorder = BorderFactory.createTitledBorder(title);
        titledBorder.setTitleColor(Color.WHITE);
        panel.setBorder(titledBorder);

        // making dimensions
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
     * Customer details panel
     * @return Configured costumer details panel
     */
    private JPanel createCustomerDetailsPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setOpaque(false);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        
        customerIdField = new JTextField(15);
        orderIdField = new JTextField(15);
        
        addFormField(panel, "Customer ID*:", customerIdField, gbc);
        addFormField(panel, "Order ID*:", orderIdField, gbc);

        return panel;
    }

    /**
     * Payment details panel
     * @return Configured payment details panel
     */
    private JPanel createPaymentDetailsPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setOpaque(false);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        // adding text fields
        amountField = new JTextField(15);
        paymentDateField = new JTextField(10); // Added payment date field
        paymentMethodComboBox = new JComboBox<>(new String[]{"Check", "Mastercard", "Visa", "Discover", "Debit"});
        cardNumberField = new JTextField(15);
        cardExpirationField = new JTextField(5);
        cardHolderField = new JTextField(20);

        // adding field names
        addFormField(panel, "Amount ($)*:", amountField, gbc);
        addFormField(panel, "Payment Date (YYYY-MM-DD)*:", paymentDateField, gbc); // Added payment date field
        addFormField(panel, "Payment Method*:", paymentMethodComboBox, gbc);
        addFormField(panel, "Card Number*:", cardNumberField, gbc);
        addFormField(panel, "Card Expiration* (MM/YY):", cardExpirationField, gbc);
        addFormField(panel, "Card Holder*:", cardHolderField, gbc);

        paymentMethodComboBox.addActionListener(e -> updateCardFields());
        updateCardFields();

        return panel;
    }

    /**
     * Add a form field
     * @param panel The panel to be added to
     * @param label The label of the form field
     * @param field The field to be added
     * @param gbc Dimension constroller
     */
    private void addFormField(JPanel panel, String label, JComponent field, GridBagConstraints gbc) {
        // creating dimensions of the form field
        gbc.gridx = 0;
        gbc.gridy++;
        gbc.gridwidth = 1;
        gbc.weightx = 0.3;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.WEST;

        // font and text stuff
        JLabel labelComponent = new JLabel(label);
        labelComponent.setForeground(Color.WHITE);
        labelComponent.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        panel.add(labelComponent, gbc);

        gbc.gridx = 1;
        gbc.weightx = 0.7;
        gbc.anchor = GridBagConstraints.EAST;
        field.setPreferredSize(new Dimension(300, 25));

        // adding colors and UI
        if (field instanceof JTextField) {
            field.setBackground(SECONDARY_COLOR);
            field.setForeground(TEXT_COLOR);
            ((JTextField) field).setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(ACCENT_COLOR),
                BorderFactory.createEmptyBorder(2, 5, 2, 5)
            ));
        }
        panel.add(field, gbc);
    }

    /**
     * Update the card fields if its not a check payment method
     */
    private void updateCardFields() {
        boolean isCardPayment = !paymentMethodComboBox.getSelectedItem().equals("Check");
        cardNumberField.setEnabled(isCardPayment);
        cardExpirationField.setEnabled(isCardPayment);
        cardHolderField.setEnabled(isCardPayment);
    }

    /**
     * Check if customer id is valid
     * @param customerId
     * @return if its valid
     */
    private boolean isValidCustomerId(int customerId) {
        return customerId > 0 && customerId <= CustomerService.getCustomerCount();
    }

    /**
     * Check if order id is valid
     * @param orderId
     * @return if its valid
     */
    private boolean isValidOrderId(int orderId) {
        return orderId > 0 && orderId <= OrderService.getOrderCount();
    }

    /**
     * Submit a payment
     */
    private void submitPayment() {
        Payment newPayment = new Payment();
        try {
            // Validate Customer ID
            if (CustomerService.getCustomerCount() == 0) {
                JOptionPane.showMessageDialog(this,
                    "Please load a customer first",
                    "Input Error",
                    JOptionPane.ERROR_MESSAGE);
                return;
            }

            // if the customer id not in bounds throw error
            int customerId = Integer.parseInt(customerIdField.getText());
            if (customerId < 1 || customerId > CustomerService.getCustomerCount()) {
                JOptionPane.showMessageDialog(this,
                    "Customer ID must be between 1 and " + CustomerService.getCustomerCount(),
                    "Input Error",
                    JOptionPane.ERROR_MESSAGE);
                return;
            }
            newPayment.setCustomerID(customerId);

            // Validate Order ID
            if (OrderService.getOrderCount() == 0) {
                JOptionPane.showMessageDialog(this,
                    "Please load an order first",
                    "Input Error",
                    JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            // if order id not in bounds throw error
            int orderId = Integer.parseInt(orderIdField.getText());
            if (orderId < 1 || orderId > OrderService.getOrderCount()) {
                JOptionPane.showMessageDialog(this,
                    "Order ID must be between 1 and " + OrderService.getOrderCount(),
                    "Input Error",
                    JOptionPane.ERROR_MESSAGE);
                return;
            }
            newPayment.setOrderID(orderId);

            // Validate Amount
            double amount = Double.parseDouble(amountField.getText());
            if (amount <= 0) {
                JOptionPane.showMessageDialog(this,
                    "Amount must be greater than 0",
                    "Input Error",
                    JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Validate Payment Date (must be today)
            String paymentDateStr = paymentDateField.getText();
            Date paymentDate = Date.valueOf(paymentDateStr);
            Date today = new Date(System.currentTimeMillis());
            if (!paymentDate.toString().equals(today.toString())) {
                JOptionPane.showMessageDialog(this,
                    "Payment date must be today's date",
                    "Input Error",
                    JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Validate Card Expiration if it's a card payment
            if (!paymentMethodComboBox.getSelectedItem().equals("Check") && !cardExpirationField.getText().isEmpty()) {
                Date expirationDate = Date.valueOf(cardExpirationField.getText());
                if (expirationDate.before(today)) {
                    JOptionPane.showMessageDialog(this,
                        "Card expiration date cannot be in the past",
                        "Input Error",
                        JOptionPane.ERROR_MESSAGE);
                    return;
                }
            }

            // Set payment details
            newPayment.setAmount(amount);
            newPayment.setDate(paymentDate);
            newPayment.setMethod((String) paymentMethodComboBox.getSelectedItem());
            newPayment.setCardNumber(cardNumberField.getText());
            newPayment.setCardOwner(cardHolderField.getText());
            
            if (!cardExpirationField.getText().isEmpty()) {
                newPayment.setCardExpirationDate(Date.valueOf(cardExpirationField.getText()));
            }
            newPayment.setCreditCard((String) paymentMethodComboBox.getSelectedItem());

            boolean result = PaymentService.addPayment(newPayment);
            if (result) {
                JOptionPane.showMessageDialog(this,
                    "Payment data saved successfully.",
                    "Success",
                    JOptionPane.INFORMATION_MESSAGE);
                dispose();
            } else {
                JOptionPane.showMessageDialog(this,
                    "Failed to save payment data.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            }

        // showing user exceptions
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                e.getMessage(),
                "Input Error",
                JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Main method to launch the application.
     * @param args Command line arguments (not used)
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new PaymentPresentation().setVisible(true));
    }
}
