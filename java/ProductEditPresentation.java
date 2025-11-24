import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.border.*;

/**
 * Class to edit a product
 * @author Ethan C and Aarav S
 * @version 1.0.0
 */
public class ProductEditPresentation extends JFrame {
    private JTextField productIdField, descriptionField, unitPriceField, unitsOnHandField, warehouseIdField;
    private JComboBox<String> classComboBox, editModeComboBox;
    private JButton saveButton, cancelButton, loadProductButton;

    // Modern color scheme
    private static final Color PRIMARY_COLOR = new Color(41, 128, 185); // Professional blue
    private static final Color SECONDARY_COLOR = new Color(236, 240, 241); // Light gray
    private static final Color ACCENT_COLOR = new Color(52, 152, 219); // Bright blue
    private static final Color TEXT_COLOR = new Color(44, 62, 80); // Dark blue-gray
    private static final Color HEADER_COLOR = new Color(52, 73, 94); // Dark slate
    private static final Color BACKGROUND_COLOR = new Color(75, 101, 132); // Blue-grey
    private static final Color BUTTON_BG_COLOR = Color.WHITE;
    private static final Color BUTTON_TEXT_COLOR = HEADER_COLOR;

    private final String[] classes = {"HW", "SG", "AP", "TO", "GS"};

    /**
     * Constructor initializes the form and components
     */
    public ProductEditPresentation() {
        setTitle("Edit/New Product");
        setSize(600, 620);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Main panel with solid background
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10)) {
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

        // cool scroll bar
        JScrollPane scrollPane = new JScrollPane(createGroupedFormPanel());
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        // adding button panels
        JPanel buttonPanel = createButtonPanel();
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        addInputValidation();
    }

    /**
     * Create the header panel
     * @return Configured header panel
     */
    private JPanel createHeaderPanel() {
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(HEADER_COLOR);
        headerPanel.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));
        
        // font and text
        JLabel headerLabel = new JLabel("Product Management", JLabel.CENTER);
        headerLabel.setFont(new Font("Segoe UI Light", Font.BOLD, 28));
        headerLabel.setForeground(Color.WHITE);
        headerPanel.add(headerLabel, BorderLayout.CENTER);
        
        return headerPanel;
    }

    /**
     * Create the button panel
     * @return Configured button panel
     */
    private JPanel createButtonPanel() {
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.setOpaque(false);
        
        // save and cancel buttons
        saveButton = createStyledButton("Save", ACCENT_COLOR);
        cancelButton = createStyledButton("Cancel", new Color(231, 76, 60));
        
        buttonPanel.add(saveButton);
        buttonPanel.add(cancelButton);

        saveButton.addActionListener(e -> saveProduct());
        cancelButton.addActionListener(e -> dispose());

        return buttonPanel;
    }

    /**
     * Create a styled button
     * @param text The button text
     * @param baseColor The button color
     * @return Configured styled panel
     */
    private JButton createStyledButton(String text, Color baseColor) {
        // font and text
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
     * Create a grouped panel
     * @return Configured group panel
     */
    private JPanel createGroupedFormPanel() {
        // configuring UI
        JPanel groupedPanel = new JPanel(new GridBagLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
                g2d.setColor(BACKGROUND_COLOR);
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        groupedPanel.setOpaque(false);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);

        // header font and text
        JLabel headerLabel = new JLabel("<html><div style='text-align: center;'>"
                + "Use this form to edit or create a new product.<br>"
                + "Fields marked with an asterisk (*) are required.</div></html>");
        headerLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        headerLabel.setForeground(Color.WHITE);
        headerLabel.setHorizontalAlignment(SwingConstants.CENTER);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        groupedPanel.add(headerLabel, gbc);

        gbc.gridwidth = 1;

        // edit group and product unfo group
        addGroupPanel(groupedPanel, createEditModePanel(), "New/Edit Product", ++gbc.gridy);
        addGroupPanel(groupedPanel, createProductInfoPanel(), "Product Information", ++gbc.gridy);

        return groupedPanel;
    }

    /**
     * Adding a group panel
     * @param parent The panel to be added to
     * @param panel The panel to add
     * @param title The panel title
     * @param gridy The y dimension of the panel
     */
    private void addGroupPanel(JPanel parent, JPanel panel, String title, int gridy) {
        panel.setOpaque(false);
        TitledBorder titledBorder = BorderFactory.createTitledBorder(title);
        titledBorder.setTitleColor(Color.WHITE);
        panel.setBorder(titledBorder);
        
        // configuring dimensions of the panel
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
     * @return Configured edit mode panel
     */
    private JPanel createEditModePanel() {
        // dimenions and configuiring UI
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setOpaque(false);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        editModeComboBox = new JComboBox<>(new String[]{"No", "Yes"});
        productIdField = new JTextField(15);
        loadProductButton = createStyledButton("Load Product", ACCENT_COLOR);

        // form fields
        addFormField(panel, "Edit Existing Product*:", editModeComboBox, gbc);
        addFormField(panel, "Product ID*:", productIdField, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        gbc.gridwidth = 2;
        panel.add(loadProductButton, gbc);

        // productIdField.setEnabled(true);
        loadProductButton.setEnabled(true);
        editModeComboBox.addActionListener(e -> toggleEditMode());
        loadProductButton.addActionListener(e -> loadProductData());

        return panel;
    }

    /**
     * Create the product info panel
     * @return Configured product info panel
     */
    private JPanel createProductInfoPanel() {
        // configuring UI and dimensions
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setOpaque(false);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        classComboBox = new JComboBox<>(classes);
        classComboBox.setPreferredSize(new Dimension(300, 25));

        // adding form fields
        addFormField(panel, "Description*:", descriptionField = new JTextField(20), gbc);
        addFormField(panel, "Unit Price ($):", unitPriceField = new JTextField(20), gbc);
        addFormField(panel, "Units On Hand*:", unitsOnHandField = new JTextField(20), gbc);
        addFormField(panel, "Class*:", classComboBox, gbc);
        addFormField(panel, "Warehouse ID*:", warehouseIdField = new JTextField(20), gbc);

        return panel;
    }

    /**
     * Adding a form field
     * @param The panel to be added to
     * @param The label of the form field
     * @param The field to be added
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
        
        // font and text stuff
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
            field.setBackground(Color.WHITE);
            field.setForeground(TEXT_COLOR);
            ((JTextField) field).setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(HEADER_COLOR),
                BorderFactory.createEmptyBorder(2, 5, 2, 5)
            ));
        } else if (field instanceof JComboBox) {
            field.setBackground(Color.WHITE);
            field.setForeground(TEXT_COLOR);
        }
        panel.add(field, gbc);
    }

    /**
     * Toggle edit mode
     */
    private void toggleEditMode() {
        // can edit id if select yes
        boolean isEditing = editModeComboBox.getSelectedItem().equals("Yes");
        loadProductButton.setEnabled(isEditing);
        if (!isEditing) {
            clearForm();
        }
    }

    /**
     * Load the product data
     */
    private void loadProductData() {
        // id check
        String productId = productIdField.getText();
        if (productId.isEmpty()) {
            showErrorDialog("Please enter a valid Product ID.");
            return;
        }

        Product product = ProductService.getProductByID(productId);

        // if not null then set the data in the form to the right values
        if (product != null)
        {
            productIdField.setText(product.getProductID());
            descriptionField.setText(product.getDescription());
            unitPriceField.setText(String.valueOf(product.getUnitPrice()));
            unitsOnHandField.setText(String.valueOf(product.getUnitsOnHand()));
            classComboBox.setSelectedItem(product.getProductClass());
            warehouseIdField.setText(String.valueOf(product.getWarehouseID()));
        }
    }

    /**
     * Clearing the form
     */
    private void clearForm() {
        productIdField.setText("");
        descriptionField.setText("");
        unitPriceField.setText("");
        unitsOnHandField.setText("");
        classComboBox.setSelectedIndex(0);
        warehouseIdField.setText("");
    }

    /**
     * Validating input
     */
    private void addInputValidation() {
        // validating uunit price here
        unitPriceField.addFocusListener(new FocusAdapter() {
            public void focusLost(FocusEvent e) {
                if (!unitPriceField.getText().isEmpty()) {
                    try {
                        double price = Double.parseDouble(unitPriceField.getText());
                        if (price < 0) {
                            throw new NumberFormatException();
                        }
                    } catch (NumberFormatException ex) {
                        showErrorDialog("Unit Price must be a positive number.");
                    }
                }
            }
        });

        // validating units on hand here
        unitsOnHandField.addFocusListener(new FocusAdapter() {
            public void focusLost(FocusEvent e) {
                try {
                    int units = Integer.parseInt(unitsOnHandField.getText());
                    if (units < 0) {
                        throw new NumberFormatException();
                    }
                } catch (NumberFormatException ex) {
                    showErrorDialog("Units On Hand must be a positive integer.");
                }
            }
        });

        // validating warehouse ID here
        warehouseIdField.addFocusListener(new FocusAdapter() {
            public void focusLost(FocusEvent e) {
                if (!warehouseIdField.getText().isEmpty()) {
                    try {
                        int warehouseId = Integer.parseInt(warehouseIdField.getText());
                        if (warehouseId <= 0) {
                            throw new NumberFormatException();
                        }
                    } catch (NumberFormatException ex) {
                        showErrorDialog("Warehouse ID must be a positive integer.");
                    }
                }
            }
        });
    }

    /**
     * Show error to user
     * @param message The error message
     */
    private void showErrorDialog(String message) {
        JOptionPane.showMessageDialog(this, message, "Input Error", JOptionPane.ERROR_MESSAGE);
    }

    /**
     * Saving a product
     */
    private void saveProduct() {
        try {
            boolean isEditing = editModeComboBox.getSelectedItem().equals("Yes");
            
            // Product ID validation only when editing
            if (isEditing) {
                String productId = productIdField.getText();
                if (ProductService.getProductCount() == 0) {
                    showErrorDialog("Please load a product first");
                    return;
                }
                if (productId.isEmpty() || ProductService.getProductByID(productId) == null) {
                    showErrorDialog("Please enter a valid Product ID");
                    return;
                }
            }

            // Basic required field validation (excluding product ID for new products)
            if (descriptionField.getText().isEmpty() || 
                unitsOnHandField.getText().isEmpty() || 
                unitPriceField.getText().isEmpty() || 
                warehouseIdField.getText().isEmpty()) {
                showErrorDialog("Please fill in all required fields.");
                return;
            }

            // Unit price validation
            double unitPrice = Double.parseDouble(unitPriceField.getText());
            if (unitPrice <= 0) {
                showErrorDialog("Unit price must be greater than 0.");
                return;
            }

            // Units on hand validation
            int unitsOnHand = Integer.parseInt(unitsOnHandField.getText());
            int totalUnits = ProductService.getTotalUnitsOnHand();
            if (unitsOnHand < 0 || unitsOnHand > totalUnits) {
                showErrorDialog("Units on hand must be between 0 and " + totalUnits + ".");
                return;
            }

            // Warehouse ID validation
            int warehouseId = Integer.parseInt(warehouseIdField.getText());
            int maxWarehouse = ProductService.getWarehouseCount();
            if (maxWarehouse == 0) {
                showErrorDialog("Please load a warehouse first");
                return;
            }
            else if (warehouseId < 1 || warehouseId > maxWarehouse) {
                showErrorDialog("Warehouse ID must be between 0 and " + maxWarehouse + ".");
                return;
            }

            // Create and populate product object
            Product newProduct = new Product();
            newProduct.setProductID(productIdField.getText());
            newProduct.setDescription(descriptionField.getText());
            newProduct.setUnitPrice(unitPrice);
            newProduct.setUnitsOnHand(unitsOnHand);
            newProduct.setProductClass((String)classComboBox.getSelectedItem());
            newProduct.setWarehouseID(warehouseId);

            // Save the product
            boolean result = (editModeComboBox.getSelectedItem()).equals("No") 
                ? ProductService.addProduct(newProduct)
                : ProductService.editProduct(newProduct);

            if (result) {
                JOptionPane.showMessageDialog(this, "Product data saved.", "Success", 
                    JOptionPane.INFORMATION_MESSAGE);
                dispose();
            } else {
                showErrorDialog("Product data not saved.");
            }

        // showing user error
        } catch (NumberFormatException ex) {
            showErrorDialog("Please ensure all numeric fields contain valid numbers.");
        }
    }

    /**
     * Main method to launch the application.
     * @param args Command line arguments (not used)
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new ProductEditPresentation().setVisible(true));
    }
}