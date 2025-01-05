import javax.swing.*;
import java.awt.*;

// This class contains the main method where application is run
public class Main {
   public static void main(String[] args) {

    DatabaseConnection dbc = DatabaseConnection.getInstance();

    if(dbc.isConnected()){ // If database connection is successful, application runs
        // Run GUI in the event dispatching thread
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new MainGUI().setVisible(true);
            }
        });
    }

   }
}
