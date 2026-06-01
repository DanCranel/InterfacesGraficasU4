import view.VentanaPrincipal;
import controller.ContactoController;

public class Main {
    public static void main(String[] args) {
        // Ejecutar en el hilo de eventos de Swing por seguridad
        javax.swing.SwingUtilities.invokeLater(() -> {
            VentanaPrincipal vista = new VentanaPrincipal();
            new ContactoController(vista); // Conecta la vista con el controlador
            vista.setVisible(true);
        });
    }
}