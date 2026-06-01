package utils;

import java.awt.Component;
import java.io.*;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import model.Contacto;

public class FileManager {

    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    public static void exportarContactos(List<Contacto> contactos, File archivo) throws IOException {
        try (Writer writer = new FileWriter(archivo)) {
            gson.toJson(contactos, writer);
        }
    }

    public static List<Contacto> importarContactos(File archivo) throws IOException {
        try (Reader reader = new FileReader(archivo)) {
            Type tipoLista = new TypeToken<ArrayList<Contacto>>(){}.getType();
            return gson.fromJson(reader, tipoLista);
        }
    }

    public static void exportarConDialogo(Component parent, List<Contacto> contactos) {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileFilter(new FileNameExtensionFilter("Archivos JSON (*.json)", "json"));
        if (fileChooser.showSaveDialog(parent) == JFileChooser.APPROVE_OPTION) {
            File archivo = fileChooser.getSelectedFile();
            String ruta = archivo.getAbsolutePath();
            if (!ruta.endsWith(".json")) {
                archivo = new File(ruta + ".json");
            }
            try {
                exportarContactos(contactos, archivo);
                JOptionPane.showMessageDialog(parent, "Contactos exportados correctamente a JSON.");
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(parent, "Error al exportar JSON: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public static List<Contacto> importarConDialogo(Component parent) {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileFilter(new FileNameExtensionFilter("Archivos JSON (*.json)", "json"));
        if (fileChooser.showOpenDialog(parent) == JFileChooser.APPROVE_OPTION) {
            File archivo = fileChooser.getSelectedFile();
            try {
                return importarContactos(archivo);
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(parent, "Error al importar JSON: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
        return null;
    }
}