package view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import com.formdev.flatlaf.FlatLightLaf;

public class VentanaPrincipal extends JFrame {
    private static final long serialVersionUID = 1L;
    public JTable tablaContactos;
    public DefaultTableModel modeloTabla;
    public JProgressBar barraProgreso;
    public JTabbedPane panelPestañas;
    public JMenuItem itemExportar;        // CSV
    public JMenuItem itemImportarJSON;    // JSON importar
    public JMenuItem itemExportarJSON;    // JSON exportar

    public VentanaPrincipal() {
        try {
            FlatLightLaf.setup();
        } catch (Exception e) {
            System.err.println("Error al cargar FlatLaf: " + e.getMessage());
        }
        
        setTitle("Gestión de Contactos - Tarea Unidad 4");
        setSize(700, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        panelPestañas = new JTabbedPane();

        JPanel panelContactos = new JPanel(new BorderLayout());
        String[] columnas = {"Nombre", "Teléfono", "Email"};
        modeloTabla = new DefaultTableModel(columnas, 0);
        tablaContactos = new JTable(modeloTabla);
        JScrollPane scrollTabla = new JScrollPane(tablaContactos);
        panelContactos.add(scrollTabla, BorderLayout.CENTER);

        JPanel panelEstadisticas = new JPanel();
        panelEstadisticas.add(new JLabel("Aquí se mostrarán las estadísticas próximamente."));

        panelPestañas.addTab("Lista de Contactos", panelContactos);
        panelPestañas.addTab("Estadísticas", panelEstadisticas);

        barraProgreso = new JProgressBar(0, 100);
        barraProgreso.setStringPainted(true);

        JPopupMenu popupMenu = new JPopupMenu();
        itemExportar = new JMenuItem("Exportar Contactos a CSV");
        popupMenu.add(itemExportar);
        popupMenu.addSeparator();
        itemImportarJSON = new JMenuItem("Importar Contactos desde JSON");
        itemExportarJSON = new JMenuItem("Exportar Contactos a JSON");
        popupMenu.add(itemImportarJSON);
        popupMenu.add(itemExportarJSON);
        tablaContactos.setComponentPopupMenu(popupMenu);

        add(panelPestañas, BorderLayout.CENTER);
        add(barraProgreso, BorderLayout.SOUTH);
    }
}