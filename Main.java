package RealAssignment;
import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        String pass = JOptionPane.showInputDialog(null, "Admin Password :", "Login", JOptionPane.QUESTION_MESSAGE);

        if (pass.equals("123")) {
            InventoryManager manager = new InventoryManager();
            new MainMenu(manager);
        } else {
            JOptionPane.showMessageDialog(null, "Wrong password");
            System.exit(0);
        }
    }
}