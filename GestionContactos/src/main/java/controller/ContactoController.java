package controller;

import view.VentanaPrincipal;
import model.Contacto;
import utils.FileManager;
import java.awt.event.*;
import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

public class ContactoController {
    private VentanaPrincipal vista;
    private ArrayList<Contacto> listaContactos;

    public ContactoController(VentanaPrincipal vista) {
        this.vista = vista;
        this.listaContactos = new ArrayList<>();

        cargarDatosPrueba();

        // Exportar CSV (existente)
        this.vista.itemExportar.addActionListener(e -> exportarCSV());

        // JSON (nuevo)
        this.vista.itemImportarJSON.addActionListener(e -> importarJSON());
        this.vista.itemExportarJSON.addActionListener(e -> exportarJSON());

        // Atajo CTRL+E para CSV
        this.vista.tablaContactos.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.isControlDown() && e.getKeyCode() == KeyEvent.VK_E) {
                    exportarCSV();
                }
            }
        });
    }

    private void cargarDatosPrueba() {
        listaContactos.add(new Contacto("Carlos Morales", "0987654321", "carlos@ejemplo.com"));
        listaContactos.add(new Contacto("Maria Lopez", "0991234567", "maria@ejemplo.com"));
        
        for (Contacto c : listaContactos) {
            vista.modeloTabla.addRow(new Object[]{c.getNombre(), c.getTelefono(), c.getEmail()});
        }
    }

    // ========== CSV ==========
    private void exportarCSV() {
        vista.barraProgreso.setValue(0);
        
        try (PrintWriter pw = new PrintWriter(new File("contactos.csv"))) {
            int totalFilas = vista.modeloTabla.getRowCount();
            
            for (int i = 0; i < totalFilas; i++) {
                String nombre = vista.modeloTabla.getValueAt(i, 0).toString();
                String telf = vista.modeloTabla.getValueAt(i, 1).toString();
                String email = vista.modeloTabla.getValueAt(i, 2).toString();
                
                pw.println(nombre + "," + telf + "," + email);
                
                int porcentaje = (int) (((double) (i + 1) / totalFilas) * 100);
                vista.barraProgreso.setValue(porcentaje);
                
                Thread.sleep(100);
            }
            
            JOptionPane.showMessageDialog(vista, "¡Archivo CSV exportado con éxito!");
            
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(vista, "Error al exportar CSV: " + ex.getMessage());
        }
    }

    // ========== JSON ==========
    private void importarJSON() {
        List<Contacto> contactosImportados = FileManager.importarConDialogo(vista);
        
        if (contactosImportados != null && !contactosImportados.isEmpty()) {
            for (Contacto c : contactosImportados) {
                listaContactos.add(c);
                vista.modeloTabla.addRow(new Object[]{c.getNombre(), c.getTelefono(), c.getEmail()});
            }
            JOptionPane.showMessageDialog(vista, "Se importaron " + contactosImportados.size() + " contactos desde JSON.");
        } else if (contactosImportados != null) {
            JOptionPane.showMessageDialog(vista, "El archivo JSON estaba vacío.");
        }
    }
    
    private void exportarJSON() {
        List<Contacto> contactosActuales = new ArrayList<>();
        for (int i = 0; i < vista.modeloTabla.getRowCount(); i++) {
            String nombre = vista.modeloTabla.getValueAt(i, 0).toString();
            String telefono = vista.modeloTabla.getValueAt(i, 1).toString();
            String email = vista.modeloTabla.getValueAt(i, 2).toString();
            contactosActuales.add(new Contacto(nombre, telefono, email));
        }
        
        if (contactosActuales.isEmpty()) {
            JOptionPane.showMessageDialog(vista, "No hay contactos para exportar.");
            return;
        }
        
        FileManager.exportarConDialogo(vista, contactosActuales);
    }
}