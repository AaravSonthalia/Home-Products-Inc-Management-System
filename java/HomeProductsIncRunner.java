import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;
import java.io.IOException;

/**
* Class to run the HomeProductsInc DB
* @author Ethan C and Aarav S
*/
public class HomeProductsIncRunner 
{

	/**
	 * Main method
	 * @param args Command line arguments
	 */
	public static void main(String[] args)
	{
		//Running the application
		System.out.println("Starting Application...");
        SwingUtilities.invokeLater(() -> {
            System.out.println("Inside invokeLater...");
            new MainMenuPresentation();
        });
        System.out.println("Main method execution finished.");
	}
}