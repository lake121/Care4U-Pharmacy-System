package RealAssignment;
import java.util.*;
import java.io.*;

public class InventoryManager {
    private ArrayList<Product> inventoryList = new ArrayList<>();
    private final String filename = "inventory.txt";

    public InventoryManager() {
        loadFromFile();
    }

    public void addOrUpdateProduct(Product p){
        if(p.getStock() > 200) throw new IllegalArgumentException("Max stock is 200");
        if(p.getPrice() <0) throw new IllegalArgumentException("Price cannot lower than 0");
        Product existing = findProductById(p.getId());
        if(existing != null){
            existing.setName(p.getName());
            existing.setPrice(p.getPrice());
            existing.setStock(p.getStock());
        } else {
            inventoryList.add(p);
        }
        saveToFile();
    }

    public boolean sellProduct(String id, int qty) {
        Product p = findProductById(id);
        if (p != null && p.getStock() >= qty) {
            p.setStock(p.getStock() - qty);
            saveToFile();

            return true;
        }
        return false;
    }

    public void saveToFile() {
        try (FileWriter fw = new FileWriter(filename)) {
            for (Product p : inventoryList) {
                fw.write(p.getId() + "," + p.getName() + "," + p.getPrice() + "," + p.getStock() + "\n");
            }
        } catch (IOException e) { e.printStackTrace(); }
    }

    public void loadFromFile() {
        File file = new File(filename);
        if (!file.exists()) return;
        try (Scanner sc = new Scanner(file)) {
            while (sc.hasNextLine()) {
                String[] pts = sc.nextLine().split(",");
                if (pts.length == 4) {
                    inventoryList.add(new Product(pts[0], pts[1], Double.parseDouble(pts[2]), Integer.parseInt(pts[3])));
                }
            }
        } catch (Exception e) { e.printStackTrace(); }
    }

    public Product findProductById(String id) {
        for (Product p : inventoryList) if (p.getId().equalsIgnoreCase(id)) return p;
        return null;
    }


    public ArrayList<Product> getInventoryList() { return inventoryList; }
}