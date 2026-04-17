package RealAssignment;
import javax.swing.*;
import java.awt.*;

public class MainMenu extends JFrame {
    private InventoryManager manager;

    public MainMenu(InventoryManager manager) {
        this.manager = manager;
        setTitle("Care4U Menu");
        setSize(300, 250);
        setLayout(new GridLayout(3, 1, 10, 10));

        JButton btnInv = new JButton("Manage Inventory");
        JButton btnPos = new JButton("POS System");
        JButton btnExit = new JButton("Exit");

        btnInv.addActionListener(e -> { new InventoryFrame(manager); this.dispose(); });
        btnPos.addActionListener(e -> { new POSFrame(manager); this.dispose(); });
        btnExit.addActionListener(e -> System.exit(0));

        add(btnInv); add(btnPos); add(btnExit);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
    }
}