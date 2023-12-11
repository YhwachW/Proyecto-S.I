import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MenuApp extends JFrame {

    private JTextArea carritoTextArea;
    private JPanel subProductosPanel;
    private double total;
    private Map<String, List<String>> subProductosMap;

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

        // Utilizar GridLayout para organizar los paneles
        setLayout(new GridLayout(2, 4));


        // Agregar paneles al JFrame
        
        add(subProductosPanel);
        add(productosPanel);
        add(totalPanel);
        add(carritoPanel);
        add(imprimirCarritoBtn);


        subProductosMap = new HashMap<>();
        subProductosMap.put("Churrasco", List.of("Italiano", "Chacarero", "As", "Original"));
        subProductosMap.put("Completo", List.of("Italiano", "Chacarero", "As", "Original"));
        subProductosMap.put("Papas Fritas", List.of("Grande", "Mediana", "Chica"));
        subProductosMap.put("Hamburguesa", List.of("Italiano", "Chacarero", "As", "Original"));
        // Mostrar la ventana
        setVisible(true);
    }

    private JPanel createCarritoPanel() {
        JPanel carritoPanel = new JPanel(new BorderLayout());
        carritoPanel.setBorder(BorderFactory.createTitledBorder("Carrito"));
        carritoTextArea = new JTextArea();
        carritoTextArea.setEditable(false);

        JScrollPane scrollPane = new JScrollPane(carritoTextArea);
        carritoPanel.add(scrollPane, BorderLayout.CENTER);

        return carritoPanel;
    }

    private JPanel createSubMenuPanel() {
        JPanel subMenuPanel = new JPanel(new FlowLayout());
        subMenuPanel.setBorder(BorderFactory.createTitledBorder("Subproductos"));
    
        return subMenuPanel;
    }

    private JPanel createProductosPanel() {
        JPanel productosPanel = new JPanel(new GridLayout(3, 5));

        List<Producto> productos = new ArrayList<>();
        productos.add(new Producto("Churrasco", 4990));
        productos.add(new Producto("Completo", 3990));
        productos.add(new Producto("Papas Fritas", 1990));
        productos.add(new Producto("Hamburguesa", 3490));

        for (Producto producto : productos) {
            JButton button = createProductButton(producto.getNombre(), producto.getPrecio());
            productosPanel.add(button);
        }

        return productosPanel;
    }

    private JPanel createTotalPanel() {
        JPanel totalPanel = new JPanel();
        totalPanel.setBorder(BorderFactory.createTitledBorder("Total de la Compra"));
        JLabel totalLabel = new JLabel("Total: $0.00");
        totalLabel.setName("totalLabel"); // Agregar un nombre al JLabel
        totalPanel.add(totalLabel);
    
        return totalPanel;
    }

    private JButton createProductButton(String productName, double price) {
        JButton button = new JButton(productName + " - $" + price);
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                carritoTextArea.append(productName + " - $" + price + "\n");
                total += price;
                actualizarTotal(total);
            }
        });
    
        subProductosPanel.removeAll();
        List<String> subProductos = subProductosMap.get(productName);
        for (String subProducto : subProductos) {
            JButton subProductButton = new JButton(subProducto);
            subProductButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    JButton subProductButton = (JButton) e.getSource();
                    String subProduct = subProductButton.getText();
                    carritoTextArea.append(productName + " - " + subProduct + " - $" + price + "\n");
                    total += price;
                    actualizarTotal(total);
                }
            });
            subProductosPanel.add(subProductButton);
        }
    
        subProductosPanel.revalidate();
        subProductosPanel.repaint();
    
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

    private void actualizarTotal(double total) {
        Component[] components = getContentPane().getComponents();
        for (Component component : components) {
            if (component instanceof JPanel) {
                JPanel panel = (JPanel) component;
                Component[] panelComponents = panel.getComponents();
                for (Component panelComponent : panelComponents) {
                    if (panelComponent instanceof JLabel && panelComponent.getName().equals("totalLabel")) {
                        JLabel totalLabel = (JLabel) panelComponent;
                        totalLabel.setText("Total: $" + String.format("%.2f", total));
                        return;
                    }
                }
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new MenuApp();
            }
        });
    }

    private static class Producto {
        private String nombre;
        private double precio;

        public Producto(String nombre, double precio) {
            this.nombre = nombre;
            this.precio = precio;
        }

        public String getNombre() {
            return nombre;
        }

        public double getPrecio() {
            return precio;
        }

    }
}



    



