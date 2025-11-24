import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.border.*;
import java.util.regex.*;

/**
 * CustomerEditPresentation provides a graphical user interface for creating and editing customer records.
 * This class implements a modern, professional design with validation and user-friendly features.
 * @author Ethan C and Aarav S
 */
public class CustomerEditPresentation extends JFrame {

    // Color scheme constants for UI theming
    private static final Color PRIMARY_COLOR = new Color(41, 128, 185); // Professional blue
    private static final Color SECONDARY_COLOR = new Color(236, 240, 241); // Light gray
    private static final Color ACCENT_COLOR = new Color(52, 152, 219); // Bright blue
    private static final Color TEXT_COLOR = new Color(44, 62, 80); // Dark blue-gray
    private static final Color HEADER_COLOR = new Color(52, 73, 94); // Dark slate
    private static final Color BACKGROUND_COLOR = new Color(75, 101, 132); // Blue-grey
    private static final Color BUTTON_BG_COLOR = Color.WHITE;
    private static final Color BUTTON_TEXT_COLOR = HEADER_COLOR;

    // Data arrays for dropdown selections
    private final String[] states = {"AL", "AK", "AZ", "AR", "CA", "CO", "CT", "DE", "FL", "GA", "HI", "ID", "IL", "IN", "IA", "KS", "KY", "LA", "ME", "MD", "MA", "MI", "MN", "MS", "MO", "MT", "NE", "NV", "NH", "NJ", "NM", "NY", "NC", "ND", "OH", "OK", "OR", "PA", "RI", "SC", "SD", "TN", "TX", "UT", "VT", "VA", "WA", "WV", "WI", "WY"};
    private final String[] statuses = {"Active", "Inactive"};

    // Form field declarations
    private JTextField customerIdField, firstNameField, lastNameField, streetAddressField, cityField, zipCodeField;
    private JTextField companyField, websiteField, emailField, businessNumberField, cellNumberField, titleField, creditField, salesRepIdField;
    private JComboBox<String> stateComboBox, statusComboBox, editModeComboBox;
    private JTextArea notesArea;
    private JButton saveButton, cancelButton, loadCustomerButton;

    /**
     * Constructor initializes the customer edit form with all UI components
     * and sets up the layout, styling, and event handlers.
     */
    public CustomerEditPresentation() {
        setTitle("Edit/New Customer");
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

        // Initialize and add main UI components
        JPanel headerPanel = createHeaderPanel();
        mainPanel.add(headerPanel, BorderLayout.NORTH);

        // Scrollable content panel
        JScrollPane scrollPane = new JScrollPane(createGroupedFormPanel());
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.getViewport().setOpaque(false);
        scrollPane.setOpaque(false);
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        // Button panel for actions
        JPanel buttonPanel = createButtonPanel();
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        addInputValidation();
    }

    /**
     * Creates and configures the header panel with title and styling
     * @return Configured JPanel for the header section
     */
    private JPanel createHeaderPanel() {
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(HEADER_COLOR);
        headerPanel.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));
        
        JLabel headerLabel = new JLabel("Customer Management", JLabel.CENTER);
        headerLabel.setFont(new Font("Segoe UI Light", Font.BOLD, 28));
        headerLabel.setForeground(Color.WHITE);
        headerPanel.add(headerLabel, BorderLayout.CENTER);
        
        return headerPanel;
    }

    /**
     * Creates the button panel with save and cancel actions
     * @return Configured JPanel containing action buttons
     */
    private JPanel createButtonPanel() {
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.setOpaque(false);
        
        saveButton = createStyledButton("Save", ACCENT_COLOR);
        cancelButton = createStyledButton("Cancel", new Color(231, 76, 60));
        
        buttonPanel.add(saveButton);
        buttonPanel.add(cancelButton);
        saveButton.addActionListener(e -> saveCustomer());
        cancelButton.addActionListener(e -> dispose());
        return buttonPanel;
    }
    /**
     * Creates a styled button with consistent visual appearance
     * @param text The button label text
     * @param baseColor The primary color for the button
     * @return Configured JButton with styling and hover effects
     */
    private JButton createStyledButton(String text, Color baseColor) {
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
     * Creates the main form panel with grouped sections
     * @return JPanel containing all form input groups
     */
    private JPanel createGroupedFormPanel() {
        JPanel groupedPanel = new JPanel(new GridBagLayout());
        groupedPanel.setOpaque(false);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;

        // Add header instructions
        JLabel headerLabel = new JLabel("<html><div style='text-align: center;'>"
                + "Use this form to edit or create a new customer.<br>"
                + "Fields marked with an asterisk (*) are required.</div></html>");
        headerLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        headerLabel.setForeground(Color.WHITE);
        headerLabel.setHorizontalAlignment(SwingConstants.CENTER);

        // Position header
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.weightx = 1.0;
        groupedPanel.add(headerLabel, gbc);
        gbc.gridwidth = 1;

        // Add form sections
        addGroupPanel(groupedPanel, createEditModePanel(), "Edit Mode", ++gbc.gridy);
        addGroupPanel(groupedPanel, createNamePanel(), "Customer Name", ++gbc.gridy);
        addGroupPanel(groupedPanel, createAddressPanel(), "Customer Address", ++gbc.gridy);
        addGroupPanel(groupedPanel, createContactPanel(), "Customer Contacts", ++gbc.gridy);
        addGroupPanel(groupedPanel, createOtherInfoPanel(), "Other Information", ++gbc.gridy);

        return groupedPanel;
    }
    /**
     * Adds a grouped panel to the main form with proper styling
     * @param parent The parent panel to add to
     * @param panel The panel to be added
     * @param title The title for the group
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
     * Creates the edit mode panel with mode selection and customer ID input
     * @return Configured panel for edit mode selection
     */
    private JPanel createEditModePanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setOpaque(false);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Initialize components
        editModeComboBox = new JComboBox<>(new String[]{"New Customer", "Edit Existing"});
        customerIdField = new JTextField(15);
        loadCustomerButton = createStyledButton("Load Customer", ACCENT_COLOR);

        // Add components to panel
        addFormField(panel, "Mode*:", editModeComboBox, 0);
        addFormField(panel, "Customer ID:", customerIdField, 1);
        
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        gbc.weightx = 1.0;
        panel.add(loadCustomerButton, gbc);

        // Set initial state
        customerIdField.setEnabled(false);
        loadCustomerButton.setEnabled(false);
        
        // Add mode change listener
        editModeComboBox.addActionListener(e -> {
            boolean isEditing = editModeComboBox.getSelectedItem().equals("Edit Existing");
            customerIdField.setEnabled(isEditing);
            loadCustomerButton.setEnabled(isEditing);
            if (!isEditing) {
                clearForm();
            }
        });
        
        loadCustomerButton.addActionListener(e -> loadCustomerData());
        return panel;
    }
    /**
     * Creates the customer name input panel
     * @return Configured panel for name-related fields
     */
    private JPanel createNamePanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setOpaque(false);
        addFormField(panel, "First Name*:", firstNameField = new JTextField(20), 0);
        addFormField(panel, "Last Name*:", lastNameField = new JTextField(20), 1);
        addFormField(panel, "Company:", companyField = new JTextField(20), 2);
        addFormField(panel, "Title:", titleField = new JTextField(20), 3);
        return panel;
    }

    /**
     * Creates the address input panel
     * @return Configured panel for address-related fields
     */
    private JPanel createAddressPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setOpaque(false);
        addFormField(panel, "Street Address*:", streetAddressField = new JTextField(30), 0);
        addFormField(panel, "City*:", cityField = new JTextField(20), 1);
        addFormField(panel, "State*:", stateComboBox = new JComboBox<>(states), 2);
        addFormField(panel, "Zip Code*:", zipCodeField = new JTextField(10), 3);
        return panel;
    }

    /**
     * Creates the contact information input panel
     * @return Configured panel for contact-related fields
     */
    private JPanel createContactPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setOpaque(false);
        addFormField(panel, "Email*:", emailField = new JTextField(25), 0);
        addFormField(panel, "Business Number:", businessNumberField = new JTextField(15), 1);
        addFormField(panel, "Cell Number:", cellNumberField = new JTextField(15), 2);
        addFormField(panel, "Website:", websiteField = new JTextField(25), 3);
        return panel;
    }

    /**
     * Creates the additional information input panel
     * @return Configured panel for other customer information
     */
    private JPanel createOtherInfoPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setOpaque(false);
        
        // Initialize components with default values
        creditField = new JTextField("0", 10);
        statusComboBox = new JComboBox<>(statuses);
        salesRepIdField = new JTextField(15);
        notesArea = new JTextArea(5, 15);
        notesArea.setLineWrap(true);
        notesArea.setWrapStyleWord(true);
        JScrollPane notesScrollPane = new JScrollPane(notesArea);

        // Add components to panel
        addFormField(panel, "Credit ($)*:", creditField, 0);
        addFormField(panel, "Status*:", statusComboBox, 1);
        addFormField(panel, "Sales Rep ID*:", salesRepIdField, 2);
        addFormField(panel, "Notes:", notesScrollPane, 3);
        
        return panel;
    }
    /**
     * Adds a form field with label to a panel using consistent styling
     * @param panel The panel to add the field to
     * @param label The label text for the field
     * @param field The input component
     * @param gridy The vertical position in the grid
     */
    private void addFormField(JPanel panel, String label, JComponent field, int gridy) {
        GridBagConstraints labelGbc = new GridBagConstraints();
        labelGbc.gridx = 0;
        labelGbc.gridy = gridy;
        labelGbc.anchor = GridBagConstraints.WEST;
        labelGbc.insets = new Insets(5, 5, 5, 10);
        labelGbc.weightx = 0.3;

        GridBagConstraints fieldGbc = new GridBagConstraints();
        fieldGbc.gridx = 1;
        fieldGbc.gridy = gridy;
        fieldGbc.fill = GridBagConstraints.HORIZONTAL;
        fieldGbc.insets = new Insets(5, 0, 5, 5);
        fieldGbc.weightx = 0.7;

        // Create and style the label
        JLabel labelComponent = new JLabel(label);
        labelComponent.setForeground(Color.WHITE);
        labelComponent.setFont(new Font("Segoe UI", Font.PLAIN, 14));

        // Style the input field based on its type
        if (field instanceof JTextField) {
            field.setPreferredSize(new Dimension(300, 25));
            field.setBackground(SECONDARY_COLOR);
            field.setForeground(TEXT_COLOR);
            ((JTextField) field).setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(ACCENT_COLOR),
                BorderFactory.createEmptyBorder(2, 5, 2, 5)
            ));
        } else if (field instanceof JScrollPane) {
            field.setPreferredSize(new Dimension(300, 100));
            JTextArea textArea = (JTextArea) ((JScrollPane) field).getViewport().getView();
            textArea.setBackground(SECONDARY_COLOR);
            textArea.setForeground(TEXT_COLOR);
        }

        panel.add(labelComponent, labelGbc);
        panel.add(field, fieldGbc);
    }

    /**
     * Adds input validation to all relevant form fields
     * Includes validation for:
     * - Zip codes
     * - Email addresses
     * - Phone numbers
     * - Credit amounts
     * - Customer IDs
     * - Sales Rep IDs
     */
    private void addInputValidation() {
        // Zip code validation
        zipCodeField.addFocusListener(new FocusAdapter() {
            public void focusLost(FocusEvent e) {
                if (!zipCodeField.getText().isEmpty() &&
                        !zipCodeField.getText().matches("\\d{5}(-\\d{4})?")) {
                    showError("Invalid Zip Code format. Use 12345 or 12345-6789.");
                }
            }
        });
        // Email validation
        emailField.addFocusListener(new FocusAdapter() {
            public void focusLost(FocusEvent e) {
                if (!emailField.getText().isEmpty() && !isValidEmail(emailField.getText())) {
                    showError("Invalid email format. Use format: username@domain.com");
                }
            }
        });

        // Business phone number validation
        businessNumberField.addFocusListener(new FocusAdapter() {
            public void focusLost(FocusEvent e) {
                if (!businessNumberField.getText().isEmpty() &&
                        !businessNumberField.getText().matches("\\d{10}")) {
                    showError("Invalid phone number format. Use 10 digits.");
                }
            }
        });

        // Cell phone number validation
        cellNumberField.addFocusListener(new FocusAdapter() {
            public void focusLost(FocusEvent e) {
                if (!cellNumberField.getText().isEmpty() &&
                        !cellNumberField.getText().matches("\\d{10}")) {
                    showError("Invalid phone number format. Use 10 digits.");
                }
            }
        });

        // Credit amount validation
        creditField.addFocusListener(new FocusAdapter() {
            public void focusLost(FocusEvent e) {
                try {
                    if (!creditField.getText().isEmpty()) {
                        double credit = Double.parseDouble(creditField.getText());
                        if (credit < 0) {
                            throw new NumberFormatException();
                        }
                    }
                } catch (NumberFormatException ex) {
                    showError("Credit must be a valid positive number.");
                }
            }
        });

        // Customer ID validation
        customerIdField.addFocusListener(new FocusAdapter() {
            public void focusLost(FocusEvent e) {
                if (!customerIdField.getText().isEmpty()) {
                    try {
                        int id = Integer.parseInt(customerIdField.getText());
                        int maxCustomers = CustomerService.getCustomerCount();
                        if (maxCustomers == 0) {
                            showError("Please load a customer first");
                            salesRepIdField.setText("");
                        }
                        else if (id < 1 || id > maxCustomers) {
                            showError("Customer ID must be between 1 and " + maxCustomers);
                            customerIdField.setText("");
                        }
                    } catch (NumberFormatException ex) {
                        showError("Customer ID must be a valid number");
                        customerIdField.setText("");
                    }
                }
            }
        });
        // Sales Rep ID validation
        salesRepIdField.addFocusListener(new FocusAdapter() {
            public void focusLost(FocusEvent e) {
                if (!salesRepIdField.getText().isEmpty()) {
                    try {
                        int id = Integer.parseInt(salesRepIdField.getText());
                        int maxSalesReps = SalesRepService.getSalesRepCount();
                        if (maxSalesReps == 0) {
                            showError("Please load a sales rep first");
                            salesRepIdField.setText("");
                        }
                        else if (id < 1 || id > maxSalesReps) {
                            showError("Sales Rep ID must be between 1 and " + maxSalesReps);
                            salesRepIdField.setText("");
                        }
                    } catch (NumberFormatException ex) {
                        showError("Sales Rep ID must be a valid number");
                        salesRepIdField.setText("");
                    }
                }
            }
        });
    }

    /**
     * Validates email format using regex pattern
     * @param email The email address to validate
     * @return true if email format is valid, false otherwise
     */
    private boolean isValidEmail(String email) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        return email.matches(emailRegex);
    }

    /**
     * Displays an error message dialog
     * @param message The error message to display
     */
    private void showError(String message) {
        JOptionPane.showMessageDialog(this, message, "Input Error", JOptionPane.ERROR_MESSAGE);
    }

    /**
     * Loads customer data into the form fields based on customer ID
     */
    private void loadCustomerData() {
        try {
            int customerId = Integer.parseInt(customerIdField.getText());
            Customer customer = CustomerService.getCustomerByID(customerId);
            
            // Populate form fields with customer data
            firstNameField.setText(customer.getFirstName());
            lastNameField.setText(customer.getLastName());
            streetAddressField.setText(customer.getStreet());
            cityField.setText(customer.getCity());
            zipCodeField.setText(customer.getZipCode());
            stateComboBox.setSelectedItem(customer.getState());
            emailField.setText(customer.getEmail());
            businessNumberField.setText(customer.getBusinessNumber());
            cellNumberField.setText(customer.getCellNumber());
            websiteField.setText(customer.getWebsite());
            titleField.setText(customer.getTitle());
            companyField.setText(customer.getCompany());
            creditField.setText(String.valueOf(customer.getCredit()));
            statusComboBox.setSelectedItem(customer.getStatus());
            salesRepIdField.setText(String.valueOf(customer.getSalesRepID()));
            notesArea.setText(customer.getNotes());
        } catch (NumberFormatException e) {
            showError("Please enter a valid Customer ID (integer).");
        }
    }
    /**
     * Clears all form fields and resets to default values
     */
    private void clearForm() {
        customerIdField.setText("");
        firstNameField.setText("");
        lastNameField.setText("");
        streetAddressField.setText("");
        cityField.setText("");
        zipCodeField.setText("");
        stateComboBox.setSelectedIndex(0);
        emailField.setText("");
        businessNumberField.setText("");
        cellNumberField.setText("");
        websiteField.setText("");
        titleField.setText("");
        companyField.setText("");
        statusComboBox.setSelectedIndex(0);
        salesRepIdField.setText("");
        notesArea.setText("");
        creditField.setText("0");
    }

    /**
     * Saves the customer data from form fields
     * Handles both new customer creation and existing customer updates
     */
    private void saveCustomer() {
        Customer newCustomer = new Customer();
        try {
            if (!validateRequiredFields()) {
                return;
            }

            // Populate customer object with form data
            newCustomer.setFirstName(firstNameField.getText());
            newCustomer.setLastName(lastNameField.getText());
            newCustomer.setStreet(streetAddressField.getText());
            newCustomer.setCity(cityField.getText());
            newCustomer.setState((String)stateComboBox.getSelectedItem());
            newCustomer.setZipCode(zipCodeField.getText());
            newCustomer.setCredit(Double.parseDouble(creditField.getText()));
            newCustomer.setSalesRepID(Integer.parseInt(salesRepIdField.getText()));
            newCustomer.setCompany(companyField.getText());
            newCustomer.setWebsite(websiteField.getText());
            newCustomer.setEmail(emailField.getText());
            newCustomer.setBusinessNumber(businessNumberField.getText());
            newCustomer.setCellNumber(cellNumberField.getText());
            newCustomer.setTitle(titleField.getText());
            newCustomer.setStatus((String)statusComboBox.getSelectedItem());
            newCustomer.setNotes(notesArea.getText());

            boolean result = false;
            if (editModeComboBox.getSelectedItem().equals("New Customer"))
            {
                result = CustomerService.addCustomer(newCustomer);
            } else {
                newCustomer.setCustomerID(Integer.parseInt(customerIdField.getText()));
                result = CustomerService.editCustomer(newCustomer);
            }

            if (result) {
                JOptionPane.showMessageDialog(this, "Customer data saved successfully.", 
                    "Success", JOptionPane.INFORMATION_MESSAGE);
                dispose();
            } else {
                showError("Failed to save customer data.");
            }
        } catch (NumberFormatException e) {
            showError("Please ensure all numeric fields contain valid numbers.");
        }
    }
    /**
     * Validates all required fields before saving
     * @return true if all required fields are valid, false otherwise
     */
    private boolean validateRequiredFields() {
        if (firstNameField.getText().isEmpty() ||
                lastNameField.getText().isEmpty() ||
                streetAddressField.getText().isEmpty() ||
                cityField.getText().isEmpty() ||
                zipCodeField.getText().isEmpty() ||
                emailField.getText().isEmpty() ||
                creditField.getText().isEmpty() ||
                salesRepIdField.getText().isEmpty()) {
            showError("Please fill in all required fields.");
            return false;
        }

        // Validate sales rep ID
        try {
            int salesRepId = Integer.parseInt(salesRepIdField.getText());
            int maxSalesReps = SalesRepService.getSalesRepCount();
            if (salesRepId < 1 || salesRepId > maxSalesReps) {
                showError("Sales Rep ID must be between 1 and " + maxSalesReps);
                return false;
            }
        } catch (NumberFormatException e) {
            showError("Sales Rep ID must be a valid number");
            return false;
        }

        return true;
    }

    /**
     * Main method to launch the customer edit presentation
     * @param args Command line arguments (not used)
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new CustomerEditPresentation().setVisible(true));
    }
}
