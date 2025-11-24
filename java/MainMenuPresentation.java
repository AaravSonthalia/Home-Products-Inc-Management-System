import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;
import java.io.IOException;

/**
 * MainMenuPresentation serves as the main entry point and navigation hub for the Home Products Inc Management System.
 * This class creates a modern, visually appealing GUI with a gradient background and interactive buttons.
 * We have neither given nor received any unauthorized aid on this assignment.
 * @author Ethan C and Aarav S
 * @version 1.0.0
 */
public class MainMenuPresentation extends JFrame {

    private BufferedImage backgroundImage;
    private static final String VERSION = "1.0.0";

    // Modern color scheme constants
    private static final Color PRIMARY_COLOR = new Color(41, 128, 185);    // Professional blue
    private static final Color SECONDARY_COLOR = new Color(236, 240, 241); // Light gray
    private static final Color ACCENT_COLOR = new Color(52, 152, 219);     // Bright blue
    private static final Color TEXT_COLOR = new Color(44, 62, 80);         // Dark blue-gray
    private static final Color HEADER_COLOR = new Color(52, 73, 94);       // Dark slate

    /**
     * Constructor initializes the main menu window with all UI components.
     * Sets up the frame properties, loads background image, and creates the layout.
     */
    public MainMenuPresentation() {
        System.out.println("Initializing Main Menu Presentation...");
        
        // Basic frame setup
        setTitle("Home Products Inc");
        setSize(750, 550);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Load and handle background image
        try {
            backgroundImage = ImageIO.read(new File("NewBackground.png"));
            System.out.println("Background image loaded successfully.");
        } catch (IOException e) {
            System.err.println("Error loading background image: " + e.getMessage());
        }

        // Create main panel with custom background painting
        JPanel mainPanel = new JPanel(new BorderLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
                
                if (backgroundImage != null) {
                    g2d.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
                } else {
                    // Fallback gradient background
                    GradientPaint gradient = new GradientPaint(0, 0, new Color(41, 128, 185, 220),
                            0, getHeight(), new Color(44, 62, 80, 220));
                    g2d.setPaint(gradient);
                    g2d.fillRect(0, 0, getWidth(), getHeight());
                }
                
                // Add overlay for better readability
                g2d.setColor(new Color(255, 255, 255, 30));
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };

        setContentPane(mainPanel);

        // Add main UI components
        mainPanel.add(createHeaderPanel(), BorderLayout.NORTH);
        mainPanel.add(createButtonPanel(), BorderLayout.CENTER);
        mainPanel.add(createFooterPanel(), BorderLayout.SOUTH);

        setVisible(true);
        System.out.println("Main Menu Presentation Initialized Successfully.");
    }

    /**
     * Creates and returns the header panel with the application title.
     * @return Configured header panel
     */
    private JPanel createHeaderPanel() {
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(HEADER_COLOR);
        headerPanel.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));
        
        JLabel headerLabel = new JLabel("Home Products Inc Management System", JLabel.CENTER);
        headerLabel.setFont(new Font("Segoe UI Light", Font.BOLD, 28));
        headerLabel.setForeground(Color.WHITE);
        headerPanel.add(headerLabel, BorderLayout.CENTER);
        
        return headerPanel;
    }

    /**
     * Creates a styled button with consistent appearance and hover effects.
     * @param text Button label text
     * @return Configured button with styling
     */
    private JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Segoe UI", Font.BOLD, 14));
        button.setForeground(TEXT_COLOR);
        button.setBackground(SECONDARY_COLOR);
        button.setFocusPainted(false);
        button.setPreferredSize(new Dimension(200, 70));
        button.setOpaque(true);
        
        // Configure hover effects
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                if (text.equals("Exit")) {
                    button.setBackground(new Color(231, 76, 60).brighter());
                } else if (text.equals("Help")) {
                    button.setBackground(new Color(241, 196, 15).brighter());
                } else {
                    button.setBackground(ACCENT_COLOR);
                    button.setForeground(Color.WHITE);
                }
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                if (text.equals("Exit")) {
                    button.setBackground(new Color(231, 76, 60));
                } else if (text.equals("Help")) {
                    button.setBackground(new Color(241, 196, 15));
                } else {
                    button.setBackground(SECONDARY_COLOR);
                    button.setForeground(TEXT_COLOR);
                }
            }
        });
        
        return button;
    }

    /**
     * Creates the main button panel with navigation options.
     * @return Configured panel containing all navigation buttons
     */
    private JPanel createButtonPanel() {
        JPanel buttonPanel = new JPanel(new GridBagLayout());
        buttonPanel.setOpaque(false);
        
        // Define button labels
        String[] buttonLabels = {
            "View Customers", "Edit/New Customer", "View Orders",
            "New Order", "New Payment", "Edit/New Sales Rep",
            "Edit/New Product", "Exit", "Help"
        };
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(12, 18, 12, 18);
        gbc.fill = GridBagConstraints.BOTH;
        
        // Create and add buttons to panel
        for (int i = 0; i < buttonLabels.length; i++) {
            JButton button;
            if (i == 7) { // Exit button styling
                button = createStyledButton(buttonLabels[i]);
                button.setBackground(new Color(231, 76, 60));
                button.setForeground(Color.WHITE);
                button.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(new Color(192, 57, 43), 2),
                    BorderFactory.createEmptyBorder(10, 15, 10, 15)
                ));
            } else if (i == 8) { // Help button styling
                button = createStyledButton(buttonLabels[i]);
                button.setBackground(new Color(241, 196, 15));
                button.setForeground(Color.BLACK);
                button.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(new Color(243, 156, 18), 2),
                    BorderFactory.createEmptyBorder(10, 15, 10, 15)
                ));
            } else { // Standard button styling
                button = createStyledButton(buttonLabels[i]);
                button.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(new Color(52, 152, 219), 2),
                    BorderFactory.createEmptyBorder(10, 15, 10, 15)
                ));
            }
            
            button.setPreferredSize(new Dimension(200, 85));
            
            gbc.gridx = i % 3;
            gbc.gridy = i / 3;
            buttonPanel.add(button, gbc);
            
            // Add action listeners for navigation
            final int index = i;
            button.addActionListener(e -> {
                switch(buttonLabels[index]) {
                    case "View Customers": openCustomerView(); break;
                    case "Edit/New Customer": openCustomerEditPresentation(); break;
                    case "View Orders": openOrderViewPresentation(); break;
                    case "New Order": openOrderEditPresentation(); break;
                    case "New Payment": openPaymentPresentation(); break;
                    case "Edit/New Sales Rep": openSalesRepsEditPresentation(); break;
                    case "Edit/New Product": openProductsEditPresentation(); break;
                    case "Exit": System.exit(0); break;
                    case "Help": showHelpDialog(); break;
                }
            });
        }
        
        return buttonPanel;
    }

    /**
     * Creates a utility button with specific styling.
     * @param text Button label text
     * @param baseColor Base color for the button
     * @return Configured utility button
     */
    private JButton createUtilityButton(String text, Color baseColor) {
        JButton button = new JButton(text);
        button.setFont(new Font("Segoe UI", Font.BOLD, 14));
        button.setForeground(Color.WHITE);
        button.setBackground(baseColor);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(8, 15, 8, 15));
        button.setPreferredSize(new Dimension(120, 40));
        
        // Add hover effects
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(baseColor.brighter());
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(baseColor);
            }
        });
        
        return button;
    }

    /**
     * Creates the footer panel with system information and version number.
     * @return Configured footer panel
     */
    private JPanel createFooterPanel() {
        JPanel footerPanel = new JPanel(new BorderLayout());
        footerPanel.setBackground(new Color(44, 62, 80, 200));
        footerPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        
        // Add welcome text
        JTextArea descTextArea = new JTextArea(
            "Welcome to Home Products Inc Management System. Use the buttons above to navigate through different forms and manage your data."
        );
        descTextArea.setLineWrap(true);
        descTextArea.setWrapStyleWord(true);
        descTextArea.setOpaque(false);
        descTextArea.setEditable(false);
        descTextArea.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        descTextArea.setForeground(Color.WHITE);
        
        // Add version label
        JLabel versionLabel = new JLabel("Version " + VERSION, JLabel.RIGHT);
        versionLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        versionLabel.setForeground(Color.WHITE);
        
        footerPanel.add(descTextArea, BorderLayout.CENTER);
        footerPanel.add(versionLabel, BorderLayout.EAST);
        
        return footerPanel;
    }

    /**
     * Navigation method handlers
     */
    private void openCustomerView() {
        SwingUtilities.invokeLater(() -> new CustomerViewPresentation().setVisible(true));
    }

    private void openCustomerEditPresentation() {
        SwingUtilities.invokeLater(() -> new CustomerEditPresentation().setVisible(true));
    }

    private void openOrderViewPresentation() {
        SwingUtilities.invokeLater(() -> new OrderViewPresentation().setVisible(true));
    }

    private void openOrderEditPresentation() {
        SwingUtilities.invokeLater(() -> new OrderEditPresentation().setVisible(true));
    }

    private void openPaymentPresentation() {
        SwingUtilities.invokeLater(() -> new PaymentPresentation().setVisible(true));
    }

    private void openSalesRepsEditPresentation() {
        SwingUtilities.invokeLater(() -> new SalesRepEditPresentation().setVisible(true));
    }

    private void openProductsEditPresentation() {
        SwingUtilities.invokeLater(() -> new ProductEditPresentation().setVisible(true));
    }

    /**
     * Displays the help dialog with system information and contact details.
     */
    private void showHelpDialog() {
        JOptionPane.showMessageDialog(
            this,
            "This is the main menu of the Home Products Inc Management System.\n" +
            "Click on the buttons to navigate through different forms and manage your data.\n" +
            "For further assistance, please contact asonthalia2025@pingry.org or echan2025@pingry.org.",
            "Help",
            JOptionPane.INFORMATION_MESSAGE
        );
    }

    /**
     * Main method to launch the application.
     * @param args Command line arguments (not used)
     */
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        SwingUtilities.invokeLater(MainMenuPresentation::new);
    }
}
