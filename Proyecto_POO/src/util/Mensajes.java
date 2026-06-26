package util;

import javax.swing.JOptionPane;

public class Mensajes {

    private Mensajes() {
    }

    // Campos incompletos
    public static void camposVacios() {
        JOptionPane.showMessageDialog(null,
                "Complete todos los campos.",
                "Advertencia",
                JOptionPane.WARNING_MESSAGE);
    }

    // Campos incorrectos
    public static void loginIncorrecto() {
        JOptionPane.showMessageDialog(null,
                "Usuario o contraseña incorrectos.",
                "Error",
                JOptionPane.ERROR_MESSAGE);
    }

    // Confirmación de guardar un registro
    public static void registroGuardado() {
        JOptionPane.showMessageDialog(null,
                "Registro guardado correctamente.",
                "Éxito",
                JOptionPane.INFORMATION_MESSAGE);
    }

    // Confirmación de actualizado un registro
    public static void registroActualizado() {
        JOptionPane.showMessageDialog(null,
                "Registro actualizado correctamente.",
                "Éxito",
                JOptionPane.INFORMATION_MESSAGE);
    }

    // Confirmación de eliminación un registro 
    public static void registroEliminado() {
        JOptionPane.showMessageDialog(null,
                "Registro eliminado correctamente.",
                "Éxito",
                JOptionPane.INFORMATION_MESSAGE);
    }

    // Registro no encontrado
    public static void registroNoEncontrado() {
        JOptionPane.showMessageDialog(null,
                "No se encontró el registro.",
                "Información",
                JOptionPane.INFORMATION_MESSAGE);
    }

    // Contraseña inválida
    public static void contraseñaInvalida() {
    JOptionPane.showMessageDialog(
            null,
            "La contraseña debe tener al menos 8 caracteres, una mayúscula, una minúscula y un número.",
            "Advertencia",
            JOptionPane.WARNING_MESSAGE
    );
}
}