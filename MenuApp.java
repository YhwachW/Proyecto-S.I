import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MenuApp extends JFrame {

    private JTextArea carritoTextArea;
    private JPanel subProductosPanel;

    public MenuApp() {
        // Configuración del JFrame
        setTitle("Menú de Comidas");
        setSize(1080, 720);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Crear paneles
        JPanel carritoPanel = createCarritoPanel();
        subProductosPanel = createSubMenuPanel();
        JPanel productosPanel = createProductosPanel();
        JButton imprimirCarritoBtn = createImprimirCarritoButton();

        // Agregar paneles al JFrame
        add(carritoPanel, BorderLayout.EAST);
        add(subProductosPanel, BorderLayout.SOUTH);
        add(productosPanel, BorderLayout.CENTER);
        add(imprimirCarritoBtn, BorderLayout.SOUTH);

        // Mostrar la ventana
        setVisible(true);
    }

    private JPanel createCarritoPanel() {
        JPanel carritoPanel = new JPanel(new BorderLayout());
        carritoPanel.setBorder(BorderFactory.createTitledBorder("Carrito"));
        carritoPanel.setPreferredSize(new Dimension(200, 200));
        carritoTextArea = new JTextArea();
        carritoTextArea.setEditable(false);

        JScrollPane scrollPane = new JScrollPane(carritoTextArea);
        carritoPanel.add(scrollPane, BorderLayout.CENTER);

        return carritoPanel;
    }

    private JPanel createSubMenuPanel() {
        JPanel subMenuPanel = new JPanel(new GridLayout(2,2));
        subMenuPanel.setBorder(BorderFactory.createTitledBorder("Subproductos"));
        subMenuPanel.setPreferredSize(new Dimension(200, 100));
    
        return subMenuPanel;
    }

    private JPanel createProductosPanel() {
        JPanel productosPanel = new JPanel(new GridLayout(3, 5));
        productosPanel.setPreferredSize(new Dimension(50, 50));

        JButton churrascoBtn1 = createProductButton("Churrasco", new String[]{"Chacarero","Italiano"});
        JButton completoBtn = createProductButton("Completo", new String[]{"Italiano", "Dinámico"});
        JButton papasBtn = createProductButton("Papas Fritas", new String[]{"Grande", "Mediana", "Chica"});
        JButton hamburguesaBtn = createProductButton("Hamburguesa", new String[]{"Italiano", "Dinámico"});

        productosPanel.add(churrascoBtn1);
        productosPanel.add(completoBtn);
        productosPanel.add(papasBtn);
        productosPanel.add(hamburguesaBtn);

        return productosPanel;
    }

    private JButton createProductButton(String productName, String[] subProducts) {
        JButton button = new JButton(productName);

        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Limpiar submenú anterior
                subProductosPanel.removeAll();

                // Agregar nuevos subproductos al submenú
                for (String subProduct : subProducts) {
                    JButton subProductButton = new JButton(subProduct);
                    subProductButton.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            // Agregar al carrito cuando se selecciona un subproducto
                            carritoTextArea.append(productName + " - " + subProduct + "\n");
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
        JButton imprimirCarritoBtn = new JButton("Imprimir Carrito");

        imprimirCarritoBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Imprimir contenido del carrito en la consola
                System.out.println("Contenido del Carrito:\n" + carritoTextArea.getText());
                carritoTextArea.setText("");
            }
        });

        return imprimirCarritoBtn;
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