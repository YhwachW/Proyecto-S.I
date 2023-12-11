// Version: 1.0
import javax.swing.*;
import javax.swing.DefaultListCellRenderer.UIResource;
import java.util.UUID;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class MenuApp extends JFrame {
    private JTextArea carritoTextArea;
    private JPanel subProductosPanel;
    private JLabel totalLabel;
    private List<Product> products = new ArrayList<>(); // Lista de productos
    public MenuApp() {
        // Configuración del JFrame
        setTitle("Menú de Comidas");
        setSize(1080, 720);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Crear paneles
        JPanel carritoPanel = createCarritoPanel();
        subProductosPanel = createSubMenuPanel();
        JPanel productosPanel = createProductosPanel();
        JPanel totalPanel = createTotalPanel();
        JButton imprimirCarritoBtn = createImprimirCarritoButton();
        JPanel panelVacio = new JPanel(); 
        panelVacio.setPreferredSize(new Dimension(200, 10));// Espacio para agregar los paneles
        JPanel panelVacio2 = new JPanel();

        // Agregar paneles al JFrame
        //add(panelVacio);


        // Utilizar GridLayout para organizar los paneles
        setLayout(new GridLayout(4,4,50,50));
        // Agregar paneles al JFrame
        add(productosPanel);
        add(panelVacio);
        add(subProductosPanel);
        add(carritoPanel);
        add(panelVacio);
        add(panelVacio2);
        add(totalPanel);
        add(imprimirCarritoBtn);

        // Mostrar la ventana
        setVisible(true);
    }

    private JPanel createCarritoPanel() {
        JPanel carritoPanel = new JPanel(new BorderLayout());
        carritoPanel.setBorder(BorderFactory.createTitledBorder("Carrito"));
        carritoPanel.setPreferredSize(new Dimension(20, 20)); // Establecer el tamaño preferido del panel del carrito
    
        carritoTextArea = new JTextArea();
        carritoTextArea.setEditable(false);
    
        JScrollPane scrollPane = new JScrollPane(carritoTextArea);
        carritoPanel.add(scrollPane, BorderLayout.CENTER);
    
        return carritoPanel;
    }

    private JPanel createSubMenuPanel() {
        JPanel subMenuPanel = new JPanel(new GridLayout(2, 2));
        subMenuPanel.setBorder(BorderFactory.createTitledBorder("Subproductos"));
        return subMenuPanel;
    }

    private JPanel createProductosPanel() {
        JPanel productosPanel = new JPanel(new GridLayout(3, 5));
        ArrayList<JButton> productButtons = new ArrayList<>();
       // productButtons.add(createProductButton("Churrasco", new String[]{"Chacarero", "Italiano"}, 2000));
        //  productButtons.add(createProductButton("Completo", new String[]{"Italiano", "Dinámico"}, 1500));
       // productButtons.add(createProductButton("Papas Fritas", new String[]{"Grande", "Mediana", "Chica"}, 1000));
       // productButtons.add(createProductButton("Hamburguesa", new String[]{"Italiano", "Dinámico"}, 2000));
        Product chacarero = new Product(UUID.randomUUID().toString(),"Chacarero", 2300);
        Product italiano = new Product(UUID.randomUUID().toString(),"Italiano", 2500);
        Product papasGrandes = new Product(UUID.randomUUID().toString(),"Grandes", 2190);
        Product papasMedianas = new Product(UUID.randomUUID().toString(),"Medianas", 1790);
        Product papasPequeñas = new Product(UUID.randomUUID().toString(),"Pequeñas", 1390);
        Product hamburguesa = new Product(UUID.randomUUID().toString(),"Italiana", 2590);
        Product[] churrascos = new Product[]{chacarero,italiano};
        Product[] papas = new Product[]{papasGrandes,papasMedianas,papasPequeñas};
        Product[] hamburguesas = new Product[]{hamburguesa};
        Product[] completos = new Product[]{chacarero,italiano};
        productButtons.add(createProductButton("Churrasco", churrascos));
        productButtons.add(createProductButton("Papas Fritas", papas));
        productButtons.add(createProductButton("Hamburguesa", hamburguesas));
        // productButtons.add(createProductButton("Completo", new Product[]{"Italiano", "Dinámico"}, 1500));
        // productButtons.add(createProductButton("Papas Fritas", new Product[]{"Grande", "Mediana", "Chica"}, 1000));
        // productButtons.add(createProductButton("Hamburguesa", new Product[]{"Italiano", "Dinámico"}, 2000));
        for (JButton button : productButtons) {
            productosPanel.add(button);
        }
        return productosPanel;
    }

    private JPanel createTotalPanel() {
        JPanel totalPanel = new JPanel();
        totalPanel.setBorder(BorderFactory.createTitledBorder("Total de la Compra"));
        JLabel totalLabel = new JLabel();
        this.totalLabel = totalLabel;
        totalPanel.add(totalLabel);
        return totalPanel;
    }

    private JButton createProductButton(String productName, Product[] subProducts) {
        JButton button = new JButton(productName);
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Limpiar submenú anterior
                subProductosPanel.removeAll();
                // Agregar nuevos subproductos al submenú
                for (Product subProduct : subProducts) {
                    JButton subProductButton = new JButton(subProduct.getName());
                    subProductButton.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            carritoTextArea.append(productName + " - " + subProduct.getName() + " - $" + subProduct.getPrice() + "\n");
                            products.add(subProduct);
                            actualizarTotal();
                        }
                    });
                    subProductosPanel.add(subProductButton);
                }
                // Actualizar el panel de subproductos
                subProductosPanel.revalidate();
                subProductosPanel.repaint();
            }
        });
        return button;
    }

    private JButton createImprimirCarritoButton() {
        JButton button = new JButton("Imprimir Carrito");
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String carrito = carritoTextArea.getText();
                JOptionPane.showMessageDialog(null, carrito, "Carrito de Compras", JOptionPane.INFORMATION_MESSAGE);
            }
        });
        return button;
    }

    private void actualizarTotal() {
        int total = (products != null && products.size()>0) ? products.stream().mapToInt(Product::getPrice).sum() : 0;
        System.out.println(total);
        totalLabel.setText("Total: $" + total);
    }



    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new MenuApp();
            }
        });
    }
}