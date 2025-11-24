import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.border.*;

/**
 * Class to edit a sales rep
 * @author Ethan C and Aarav S
 * @version 1.0.0
 */
public class SalesRepEditPresentation extends JFrame {
    private JTextField salesRepIdField, firstNameField, lastNameField, businessNumberField, cellNumberField, homeNumberField, faxNumberField;
    private JTextField businessStreetField, businessCityField, businessZipCodeField, commissionField, managerIdField;
    private JComboBox<String> titleComboBox, stateComboBox, editModeComboBox;
    private JButton saveButton, cancelButton, loadSalesRepButton;

    // Modern color scheme (matching MainMenuPresentation)
    private static final Color PRIMARY_COLOR = new Color(41, 128, 185); // Professional blue
    private static final Color SECONDARY_COLOR = new Color(236, 240, 241); // Light gray
    private static final Color ACCENT_COLOR = new Color(52, 152, 219); // Bright blue
    private static final Color TEXT_COLOR = new Color(44, 62, 80); // Dark blue-gray
    private static final Color HEADER_COLOR = new Color(52, 73, 94); // Dark slate
    private static final Color BACKGROUND_COLOR = new Color(75, 101, 132); // Blue-grey
    private static final Color BUTTON_BG_COLOR = Color.WHITE;
    private static final Color BUTTON_TEXT_COLOR = HEADER_COLOR;

    private final String[] states = {"AL", "AK", "AZ", "AR", "CA", "CO", "CT", "DE", "FL", "GA", "HI", "ID", "IL", "IN", "IA", "KS", "KY", "LA", "ME", "MD", "MA", "MI", "MN", "MS", "MO", "MT", "NE", "NV", "NH", "NJ", "NM", "NY", "NC", "ND", "OH", "OK", "OR", "PA", "RI", "SC", "SD", "TN", "TX", "UT", "VT", "VA", "WA", "WV", "WI", "WY"};
    private final String[] titles = {"Sales Associate", "Sales Manager", "Regional Manager"};

    /**
     * Constructor initializes the form and components
     */
    public SalesRepEditPresentation() {
        setTitle("Edit/New Sales Representative");
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

        addInputValidation();
    }

    /**
     * Create header panel
     * @return configured panel
     */
    private JPanel createHeaderPanel() {
        // configuring panel
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(HEADER_COLOR);
        headerPanel.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));
        
        // font and text stuff
        JLabel headerLabel = new JLabel("Sales Representative Management", JLabel.CENTER);
        headerLabel.setFont(new Font("Segoe UI Light", Font.BOLD, 28));
        headerLabel.setForeground(Color.WHITE);
        headerPanel.add(headerLabel, BorderLayout.CENTER);
        
        return headerPanel;
    }

    /**
     * Create button panel
     * @return configured panel
     */
    private JPanel createButtonPanel() {
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.setOpaque(false);
        
        // save and cancel button
        saveButton = createStyledButton("Save", ACCENT_COLOR);
        cancelButton = createStyledButton("Cancel", new Color(231, 76, 60));
        
        buttonPanel.add(saveButton);
        buttonPanel.add(cancelButton);

        // adding functionality
        saveButton.addActionListener(e -> saveSalesRep());
        cancelButton.addActionListener(e -> dispose());

        return buttonPanel;
    }

    /**
     * Create styled button panel
     * @param text The button text
     * @param baseColor The button base color
     * @return configured panel
     */
    private JButton createStyledButton(String text, Color baseColor) {
        // configuring UI and dimensons
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
        
        // cool hover feature
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
     * Create group panel
     * @return configured panel
     */
    private JPanel createGroupedFormPanel() {
        // confirguing UI and dimensions
        JPanel groupedPanel = new JPanel(new GridBagLayout());
        groupedPanel.setOpaque(false);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;

        // Header label styling
        JLabel headerLabel = new JLabel("<html><div style='text-align: center;'>"
                + "Use this form to edit or create a new sales representative.<br>"
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

        // adding a group panel
        addGroupPanel(groupedPanel, createEditModePanel(), "Edit Mode", ++gbc.gridy);
        addGroupPanel(groupedPanel, createPersonalInfoPanel(), "Personal Information", ++gbc.gridy);
        addGroupPanel(groupedPanel, createContactInfoPanel(), "Contact Information", ++gbc.gridy);
        addGroupPanel(groupedPanel, createAddressInfoPanel(), "Address", ++gbc.gridy);
        addGroupPanel(groupedPanel, createOtherInfoPanel(), "Other Information", ++gbc.gridy);

        return groupedPanel;
    }

    /**
     * Add group panel
     * @param parent The parent to be added to
     * @param panel The panel to add
     * @param title The panel title
     * @param gridy The panel y dimensions
     */
    private void addGroupPanel(JPanel parent, JPanel panel, String title, int gridy) {
        // configuing UI
        panel.setOpaque(false);
        TitledBorder titledBorder = BorderFactory.createTitledBorder(title);
        titledBorder.setTitleColor(Color.WHITE);
        panel.setBorder(titledBorder);
        
        // configuring dimensions
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
     * Create edit mode panel
     * @return configured panel
     */
    private JPanel createEditModePanel() {
        // configuring dimensions
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setOpaque(false);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // adding text fields
        editModeComboBox = new JComboBox<>(new String[]{"New Sales Rep", "Edit Existing"});
        salesRepIdField = new JTextField(15);
        loadSalesRepButton = createStyledButton("Load Sales Rep", ACCENT_COLOR);

        // field names
        addFormField(panel, "Mode*:", editModeComboBox, gbc);
        addFormField(panel, "Sales Rep ID:", salesRepIdField, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        gbc.gridwidth = 2;
        gbc.weightx = 1.0;
        panel.add(loadSalesRepButton, gbc);

        salesRepIdField.setEnabled(false);
        loadSalesRepButton.setEnabled(false);
        
        // edit mode functionality
        editModeComboBox.addActionListener(e -> {
            boolean isEditing = editModeComboBox.getSelectedItem().equals("Edit Existing");
            salesRepIdField.setEnabled(isEditing);
            loadSalesRepButton.setEnabled(isEditing);
            if (!isEditing) {
                clearForm();
            }
        });
        
        loadSalesRepButton.addActionListener(e -> loadSalesRepData());

        return panel;
    }

    /**
     * Create personal info panel
     * @return configured panel
     */
    private JPanel createPersonalInfoPanel() {
        // configuring UI
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setOpaque(false);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        // adding text field names
        addFormField(panel, "First Name*:", firstNameField = new JTextField(20), gbc);
        addFormField(panel, "Last Name*:", lastNameField = new JTextField(20), gbc);
        addFormField(panel, "Title*:", titleComboBox = new JComboBox<>(titles), gbc);
        addFormField(panel, "Manager ID:", managerIdField = new JTextField(10), gbc);

        return panel;
    }

    /**
     * Create contact info panel
     * @return configured panel
     */
    private JPanel createContactInfoPanel() {
        // configuring UI
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setOpaque(false);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        // adding field names
        addFormField(panel, "Business Number*:", businessNumberField = new JTextField(15), gbc);
        addFormField(panel, "Cell Number:", cellNumberField = new JTextField(15), gbc);
        addFormField(panel, "Home Number:", homeNumberField = new JTextField(15), gbc);
        addFormField(panel, "Fax Number:", faxNumberField = new JTextField(15), gbc);

        return panel;
    }

    /**
     * Create adress info panel
     * @return configured panel
     */
    private JPanel createAddressInfoPanel() {
        // configuring UI
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setOpaque(false);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        // adding field names
        addFormField(panel, "Street Address*:", businessStreetField = new JTextField(30), gbc);
        addFormField(panel, "City*:", businessCityField = new JTextField(20), gbc);
        addFormField(panel, "State*:", stateComboBox = new JComboBox<>(states), gbc);
        addFormField(panel, "Zip Code*:", businessZipCodeField = new JTextField(10), gbc);

        return panel;
    }

    /**
     * Create other info panel
     * @return configured panel
     */
    private JPanel createOtherInfoPanel() {
        // configuring UI
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setOpaque(false);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        addFormField(panel, "Decimal Commission Rate (0.05-0.10)*:", commissionField = new JTextField(10), gbc);

        return panel;
    }

    /**
     * Add form field
     * @param panel The panel to add to
     * @param label The field label
     * @param field The field to add
     * @param gbc Dimension controller
     */
    private void addFormField(JPanel panel, String label, JComponent field, GridBagConstraints gbc) {
        // configuring dimensions
        gbc.gridx = 0;
        gbc.gridy++;
        gbc.gridwidth = 1;
        gbc.weightx = 0.3;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.WEST;
        
        // font and text
        JLabel labelComponent = new JLabel(label);
        labelComponent.setForeground(Color.WHITE);
        labelComponent.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        panel.add(labelComponent, gbc);

        gbc.gridx = 1;
        gbc.weightx = 0.7;
        gbc.anchor = GridBagConstraints.EAST;

        // configuring UI
        field.setPreferredSize(new Dimension(300, 25));
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
     * Toggle edit mode
     */
    private void toggleEditMode() {
        // turning edit mode on
        boolean isEditing = editModeComboBox.getSelectedItem().equals("Yes");
        salesRepIdField.setEnabled(isEditing);
        loadSalesRepButton.setEnabled(isEditing);
        if (!isEditing) {
            clearForm();
        }
    }

    /**
     * Load sales rep data
     */
    private void loadSalesRepData() {
        try {
            // loading data into the DB
            int salesRepId = Integer.parseInt(salesRepIdField.getText());
            SalesRep salesRep = SalesRepService.getSalesRepByID(salesRepId);
            lastNameField.setText(salesRep.getLastName());
            firstNameField.setText(salesRep.getFirstName());
            businessNumberField.setText(salesRep.getBusinessNumber());
            cellNumberField.setText(salesRep.getCellNumber());
            homeNumberField.setText(salesRep.getHomeNumber());
            faxNumberField.setText(salesRep.getFaxNumber());
            titleComboBox.setSelectedItem(salesRep.getTitle());
            businessStreetField.setText(salesRep.getStreet());
            businessCityField.setText(salesRep.getCity());
            businessZipCodeField.setText(salesRep.getZipCode());
            stateComboBox.setSelectedItem(salesRep.getState());
            commissionField.setText(String.valueOf(salesRep.getCommission()));
            managerIdField.setText(String.valueOf(salesRep.getManagerID()));
        // if error then show this
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Please enter a valid Customer ID (integer).", "Input Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Clear the form
     */
    private void clearForm() {
        // setting all text fields to blank
        salesRepIdField.setText("");
        firstNameField.setText("");
        lastNameField.setText("");
        businessNumberField.setText("");
        cellNumberField.setText("");
        homeNumberField.setText("");
        faxNumberField.setText("");
        businessStreetField.setText("");
        businessCityField.setText("");
        businessZipCodeField.setText("");
        stateComboBox.setSelectedIndex(0);
        titleComboBox.setSelectedIndex(0);
        managerIdField.setText("");
        commissionField.setText("");
    }

    /**
     * Validate input
     */
    private void addInputValidation() {
        // validating fields
        salesRepIdField.addFocusListener(new FocusAdapter() {
            public void focusLost(FocusEvent e) {
                if (!salesRepIdField.getText().isEmpty()) {
                    try {
                        int id = Integer.parseInt(salesRepIdField.getText());
                        int maxId = SalesRepService.getSalesRepCount();
                        // must have a sales rep
                        if (maxId == 0) {
                            JOptionPane.showMessageDialog(SalesRepEditPresentation.this,
                                "Please load a sales rep first", 
                                "Input Error", JOptionPane.ERROR_MESSAGE);
                            salesRepIdField.setText("");
                        }
                        // sales rep must be in bounds
                        else if (id < 1 || id > maxId) {
                            JOptionPane.showMessageDialog(SalesRepEditPresentation.this,
                                "Sales Rep ID must be between 1 and " + maxId, 
                                "Input Error", JOptionPane.ERROR_MESSAGE);
                            salesRepIdField.setText("");
                        }
                    // showing user this error
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(SalesRepEditPresentation.this,
                            "Sales Rep ID must be a valid integer.", 
                            "Input Error", JOptionPane.ERROR_MESSAGE);
                        salesRepIdField.setText("");
                    }
                }
            }
        });

        // validating manager id
        managerIdField.addFocusListener(new FocusAdapter() {
            public void focusLost(FocusEvent e) {
                if (!managerIdField.getText().isEmpty()) {
                    try {
                        int id = Integer.parseInt(managerIdField.getText());
                        int maxId = SalesRepService.getSalesRepCount();
                        // must have a possible manager
                        if (maxId == 0) {
                            JOptionPane.showMessageDialog(SalesRepEditPresentation.this,
                                "Please load a sales rep first", 
                                "Input Error", JOptionPane.ERROR_MESSAGE);
                            salesRepIdField.setText("");
                        }
                        // manager must be in bounds
                        else if (id < 1 || id > maxId) {
                            JOptionPane.showMessageDialog(SalesRepEditPresentation.this,
                                "Manager ID must be between 1 and " + maxId, 
                                "Input Error", JOptionPane.ERROR_MESSAGE);
                            managerIdField.setText("");
                        }
                    // show user this error
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(SalesRepEditPresentation.this,
                            "Manager ID must be a valid integer.", 
                            "Input Error", JOptionPane.ERROR_MESSAGE);
                        managerIdField.setText("");
                    }
                }
            }
        });

        // Phone number validations
        FocusAdapter phoneValidator = new FocusAdapter() {
            public void focusLost(FocusEvent e) {
                JTextField field = (JTextField) e.getSource();
                if (!field.getText().isEmpty() && !field.getText().matches("\\d{10}")) {
                    JOptionPane.showMessageDialog(SalesRepEditPresentation.this,
                        "Phone number must be exactly 10 digits.", 
                        "Input Error", JOptionPane.ERROR_MESSAGE);
                    field.setText("");
                }
            }
        };

        // must validate these
        businessNumberField.addFocusListener(phoneValidator);
        cellNumberField.addFocusListener(phoneValidator);
        homeNumberField.addFocusListener(phoneValidator);
        faxNumberField.addFocusListener(phoneValidator);

        // Zip code validation
        businessZipCodeField.addFocusListener(new FocusAdapter() {
            public void focusLost(FocusEvent e) {
                if (!businessZipCodeField.getText().matches("\\d{5}")) {
                    JOptionPane.showMessageDialog(SalesRepEditPresentation.this,
                        "Zip code must be exactly 5 digits.", 
                        "Input Error", JOptionPane.ERROR_MESSAGE);
                    businessZipCodeField.setText("");
                }
            }
        });

        // comission field validation
        commissionField.addFocusListener(new FocusAdapter() {
            public void focusLost(FocusEvent e) {
                try {
                    double commission = Double.parseDouble(commissionField.getText());
                    // must be in bounds
                    if (commission < 0.05 || commission > 0.1) {
                        throw new NumberFormatException();
                    }
                // otherwise show user this
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(SalesRepEditPresentation.this, 
                        "Commission rate must be between 0.05 and 0.1.", 
                        "Input Error", JOptionPane.ERROR_MESSAGE);
                    commissionField.setText("");
                }
            }
        });
    }

    /**
     * Save sales rep
     */
    private void saveSalesRep() {
        // First validate required fields
        if (firstNameField.getText().isEmpty() || 
            lastNameField.getText().isEmpty() || 
            businessNumberField.getText().isEmpty() ||
            businessStreetField.getText().isEmpty() ||
            businessCityField.getText().isEmpty() ||
            businessZipCodeField.getText().isEmpty() ||
            commissionField.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, 
                "Please fill in all required fields.", 
                "Input Error", 
                JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Validate phone number format
        if (!businessNumberField.getText().matches("\\d{10}")) {
            JOptionPane.showMessageDialog(this,
                "Business phone number must be exactly 10 digits.",
                "Input Error",
                JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Validate optional phone numbers if provided
        if (!cellNumberField.getText().isEmpty() && !cellNumberField.getText().matches("\\d{10}")) {
            JOptionPane.showMessageDialog(this,
                "Cell phone number must be exactly 10 digits.",
                "Input Error",
                JOptionPane.ERROR_MESSAGE);
            return;
        }

        // validate home number
        if (!homeNumberField.getText().isEmpty() && !homeNumberField.getText().matches("\\d{10}")) {
            JOptionPane.showMessageDialog(this,
                "Home phone number must be exactly 10 digits.",
                "Input Error",
                JOptionPane.ERROR_MESSAGE);
            return;
        }

        // validate fax number
        if (!faxNumberField.getText().isEmpty() && !faxNumberField.getText().matches("\\d{10}")) {
            JOptionPane.showMessageDialog(this,
                "Fax number must be exactly 10 digits.",
                "Input Error",
                JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Validate zip code
        if (!businessZipCodeField.getText().matches("\\d{5}")) {
            JOptionPane.showMessageDialog(this,
                "Zip code must be exactly 5 digits.",
                "Input Error",
                JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Validate commission rate
        try {
            double commission = Double.parseDouble(commissionField.getText());
            if (commission < 0.05 || commission > 0.1) {
                JOptionPane.showMessageDialog(this,
                    "Commission rate must be between 0.05 and 0.1.",
                    "Input Error",
                    JOptionPane.ERROR_MESSAGE);
                return;
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this,
                "Please enter a valid commission rate.",
                "Input Error",
                JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Validate manager ID if provided
        if (!managerIdField.getText().isEmpty()) {
            try {
                int managerId = Integer.parseInt(managerIdField.getText());
                int maxId = SalesRepService.getSalesRepCount();
                // must have a manager loaded
                if (maxId == 0) {
                    JOptionPane.showMessageDialog(this,
                        "No sales representatives exist in the system.",
                        "Input Error",
                        JOptionPane.ERROR_MESSAGE);
                    return;
                }
                // manager id must be in bounds
                if (managerId < 1 || managerId > maxId) {
                    JOptionPane.showMessageDialog(this,
                        "Manager ID must be between 1 and " + maxId,
                        "Input Error",
                        JOptionPane.ERROR_MESSAGE);
                    return;
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this,
                    "Please enter a valid manager ID.",
                    "Input Error",
                    JOptionPane.ERROR_MESSAGE);
                return;
            }
        }

        // If all validations pass, create and save the SalesRep
        try {
            SalesRep newSalesRep = new SalesRep();
            // loading data into DB
            newSalesRep.setFirstName(firstNameField.getText());
            newSalesRep.setLastName(lastNameField.getText());
            newSalesRep.setBusinessNumber(businessNumberField.getText());
            newSalesRep.setCellNumber(cellNumberField.getText());
            newSalesRep.setHomeNumber(homeNumberField.getText());
            newSalesRep.setFaxNumber(faxNumberField.getText());
            newSalesRep.setTitle((String)titleComboBox.getSelectedItem());
            newSalesRep.setStreet(businessStreetField.getText());
            newSalesRep.setCity(businessCityField.getText());
            newSalesRep.setState((String)stateComboBox.getSelectedItem());
            newSalesRep.setZipCode(businessZipCodeField.getText());
            newSalesRep.setCommission(Double.parseDouble(commissionField.getText()));
            
            if (!managerIdField.getText().isEmpty()) {
                newSalesRep.setManagerID(Integer.parseInt(managerIdField.getText()));
            }

            boolean result;
            if (editModeComboBox.getSelectedItem().equals("New Sales Rep")) {
                result = SalesRepService.addSalesRep(newSalesRep);
            } else {
                newSalesRep.setSalesRepID(Integer.parseInt(salesRepIdField.getText()));
                result = SalesRepService.editSalesRep(newSalesRep);
            }

            if (result) {
                JOptionPane.showMessageDialog(this, 
                    "Sales rep data saved successfully.", 
                    "Success", 
                    JOptionPane.INFORMATION_MESSAGE);
                clearForm();
            } else {
                JOptionPane.showMessageDialog(this, 
                    "Failed to save sales rep data.", 
                    "Error", 
                    JOptionPane.ERROR_MESSAGE);
            }
        // show user this error
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this,
                "An error occurred while saving the sales rep: " + ex.getMessage(),
                "Error",
                JOptionPane.ERROR_MESSAGE);
        }
    }
    
    /**
     * Main method to launch the application.
     * @param args Command line arguments (not used)
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new SalesRepEditPresentation().setVisible(true));
    }
}