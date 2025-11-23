package com.firmabiometrica;

import com.topaz.sigplus.*;
import com.github.sarxos.webcam.Webcam;
import com.github.sarxos.webcam.WebcamResolution;
import java.beans.Beans;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class Main {
        // Variables de conexión a la base de datos
        private static String DB_URL = "";
        private static String DB_USER = "";
        private static String DB_PASS = "";

        // Cargar configuración desde config.conf
        static {
            java.util.Properties props = new java.util.Properties();
            try (java.io.FileInputStream fis = new java.io.FileInputStream("config.conf")) {
                props.load(fis);
                DB_URL = props.getProperty("DB_URL", DB_URL);
                DB_USER = props.getProperty("DB_USER", DB_USER);
                DB_PASS = props.getProperty("DB_PASS", DB_PASS);
            } catch (Exception e) {
                System.err.println("No se pudo leer config.conf, usando valores por defecto o vacíos.");
            }
        }
    // Usuario y clave estáticos
    private static final String USUARIO = "admin";
    private static final String CLAVE = "1234";

    public static void main(String[] args) {
        // Usar el look and feel del sistema
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            // Ignorar
        }
        SwingUtilities.invokeLater(Main::mostrarLogin);
    }

    private static void mostrarLogin() {
        JFrame frame = new JFrame("Login - Firma Biométrica");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(350, 200);
        frame.setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel usuarioLabel = new JLabel("Usuario:");
        JTextField usuarioField = new JTextField(15);
        JLabel claveLabel = new JLabel("Clave:");
        JPasswordField claveField = new JPasswordField(15);
        JButton loginButton = new JButton("Ingresar");

        gbc.gridx = 0; gbc.gridy = 0;
        panel.add(usuarioLabel, gbc);
        gbc.gridx = 1;
        panel.add(usuarioField, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        panel.add(claveLabel, gbc);
        gbc.gridx = 1;
        panel.add(claveField, gbc);

        gbc.gridx = 0; gbc.gridy = 2; gbc.gridwidth = 2;
        panel.add(loginButton, gbc);

        frame.getContentPane().add(panel);

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String usuario = usuarioField.getText();
                String clave = new String(claveField.getPassword());
                if (USUARIO.equals(usuario) && CLAVE.equals(clave)) {
                    JOptionPane.showMessageDialog(frame, "Bienvenido, " + usuario + "!", "Acceso concedido", JOptionPane.INFORMATION_MESSAGE);
                    frame.dispose();
                    Main.mostrarVentanaPrincipal();
                } else {
                    JOptionPane.showMessageDialog(frame, "Usuario o clave incorrectos", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        frame.setVisible(true);
    }

    // Ventana principal tras login
    private static void mostrarVentanaPrincipal() {
        // Usa las variables de clase DB_URL, DB_USER, DB_PASS cargadas desde config.conf

        JFrame mainFrame = new JFrame("Registro de Persona");
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setSize(600, 500);
        mainFrame.setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel dniLabel = new JLabel("DNI:");
        JTextField dniField = new JTextField(12);
        JLabel fechasPreviasLabel = new JLabel("Fechas previas:");
        JTextArea fechasPreviasArea = new JTextArea(3, 20);
        fechasPreviasArea.setEditable(false);
        fechasPreviasArea.setLineWrap(true);
        fechasPreviasArea.setWrapStyleWord(true);
        JScrollPane fechasScroll = new JScrollPane(fechasPreviasArea);
        JLabel nombresLabel = new JLabel("Nombres:");
        JTextField nombresField = new JTextField(20);
        JLabel apellidosLabel = new JLabel("Apellidos:");
        JTextField apellidosField = new JTextField(20);

        JLabel firmaLabel = new JLabel("Firma:");
        JLabel firmaImg = new JLabel();
        JLabel huellaLabel = new JLabel("Huella:");
        JLabel huellaImg = new JLabel();
        JLabel fotoLabel = new JLabel("Foto:");
        JLabel fotoImg = new JLabel();

        JButton firmaBtn = new JButton("Capturar Firma");
        JButton huellaBtn = new JButton("Capturar Huella");
        JButton fotoBtn = new JButton("Capturar Foto");
        JButton guardarBtn = new JButton("Guardar Registro");
        JButton cerrarBtn = new JButton("Cerrar");

        // Para almacenar la foto capturada
        final byte[][] fotoBytes = new byte[1][];

        // Panel para Topaz (firma y huella)
        SigPlus sigObj = null;
        JPanel sigPanel = new JPanel(new BorderLayout());
        try {
            ClassLoader cl = (com.topaz.sigplus.SigPlus.class).getClassLoader();
            sigObj = (SigPlus) Beans.instantiate(cl, "com.topaz.sigplus.SigPlus");
            sigPanel.add(sigObj, BorderLayout.CENTER);
            sigObj.setTabletModel("SignatureGemLCD1X5"); // Ajusta según tu modelo
            sigObj.setTabletComPort("HID1"); // Ajusta según tu dispositivo
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Error inicializando Topaz: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }

        // Layout
        gbc.gridx = 0; gbc.gridy = 0;
        panel.add(dniLabel, gbc);
        gbc.gridx = 1;
        panel.add(dniField, gbc);
        gbc.gridx = 2;
        panel.add(fechasPreviasLabel, gbc);
        gbc.gridx = 3;
        panel.add(fechasScroll, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        panel.add(nombresLabel, gbc);
        gbc.gridx = 1;
        panel.add(nombresField, gbc);

        gbc.gridx = 0; gbc.gridy = 2;
        panel.add(apellidosLabel, gbc);
        gbc.gridx = 1;
        panel.add(apellidosField, gbc);

        gbc.gridx = 0; gbc.gridy = 3;
        panel.add(firmaLabel, gbc);
        gbc.gridx = 1;
        panel.add(firmaImg, gbc);
        gbc.gridx = 2;
        panel.add(firmaBtn, gbc);

        gbc.gridx = 0; gbc.gridy = 4;
        panel.add(huellaLabel, gbc);
        gbc.gridx = 1;
        panel.add(huellaImg, gbc);
        gbc.gridx = 2;
        panel.add(huellaBtn, gbc);

        gbc.gridx = 0; gbc.gridy = 5;
        panel.add(fotoLabel, gbc);
        gbc.gridx = 1;
        panel.add(fotoImg, gbc);
        gbc.gridx = 2;
        panel.add(fotoBtn, gbc);

        gbc.gridx = 0; gbc.gridy = 6; gbc.gridwidth = 3;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1.0; gbc.weighty = 1.0;
        panel.add(sigPanel, gbc);
        gbc.weightx = 0; gbc.weighty = 0; gbc.gridwidth = 1; gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 1; gbc.gridy = 7; gbc.gridwidth = 1;
        panel.add(guardarBtn, gbc);
        gbc.gridx = 2;
        panel.add(cerrarBtn, gbc);
        // Acción para cerrar la aplicación
        cerrarBtn.addActionListener(e -> {
            mainFrame.dispose();
            System.exit(0);
        });

        // Consultar fechas previas al ingresar DNI y perder foco
        dniField.addFocusListener(new java.awt.event.FocusAdapter() {
            @Override
            public void focusLost(java.awt.event.FocusEvent e) {
                mostrarFechasPrevias(dniField.getText().trim(), fechasPreviasArea);
            }
        });
        // Consultar fechas previas al presionar Enter en el campo DNI
        dniField.addActionListener(e -> mostrarFechasPrevias(dniField.getText().trim(), fechasPreviasArea));

        mainFrame.getContentPane().add(panel);
        mainFrame.setVisible(true);

        // Lógica para capturar firma y guardar registro

        final SigPlus sigPlusRef = sigObj;
        final JLabel firmaImgRef = firmaImg;
        final JLabel huellaImgRef = huellaImg;
        final byte[][] firmaBytes = new byte[1][]; // Para almacenar la firma capturada
        final byte[][] huellaBytes = new byte[1][]; // Para almacenar la huella capturada
        final JLabel fotoImgRef = fotoImg;
        // Lógica para capturar foto con webcam
        fotoBtn.addActionListener(e -> {
            try {
                Webcam webcam = Webcam.getDefault();
                if (webcam != null) {
                    webcam.setViewSize(WebcamResolution.QVGA.getSize());
                    webcam.open();
                    java.awt.image.BufferedImage image = webcam.getImage();
                    if (image != null) {
                        java.io.ByteArrayOutputStream baos = new java.io.ByteArrayOutputStream();
                        javax.imageio.ImageIO.write(image, "png", baos);
                        baos.flush();
                        fotoBytes[0] = baos.toByteArray();
                        baos.close();
                        fotoImgRef.setIcon(new ImageIcon(fotoBytes[0]));
                        JOptionPane.showMessageDialog(mainFrame, "Foto capturada correctamente.");
                    } else {
                        JOptionPane.showMessageDialog(mainFrame, "No se pudo capturar la foto.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                    webcam.close();
                } else {
                    JOptionPane.showMessageDialog(mainFrame, "No se detectó webcam.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(mainFrame, "Error capturando la foto: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        // Lógica para capturar firma
        firmaBtn.addActionListener(e -> {
            if (sigPlusRef != null) {
                sigPlusRef.clearTablet();
                sigPlusRef.setTabletState(1); // Habilita la tableta
                JOptionPane.showMessageDialog(mainFrame, "Por favor, firme en el dispositivo y presione OK cuando termine.");
                sigPlusRef.setTabletState(0); // Deshabilita la tableta
                try {
                    java.awt.image.BufferedImage img = sigPlusRef.sigImage();
                    if (img != null) {
                        java.io.ByteArrayOutputStream baos = new java.io.ByteArrayOutputStream();
                        javax.imageio.ImageIO.write(img, "png", baos);
                        baos.flush();
                        firmaBytes[0] = baos.toByteArray();
                        baos.close();
                        firmaImgRef.setIcon(new ImageIcon(firmaBytes[0]));
                    } else {
                        JOptionPane.showMessageDialog(mainFrame, "No se pudo capturar la firma.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(mainFrame, "Error capturando la firma: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // Lógica para capturar huella (simulada usando el mismo dispositivo)
        huellaBtn.addActionListener(e -> {
            if (sigPlusRef != null) {
                sigPlusRef.clearTablet();
                sigPlusRef.setTabletState(1); // Habilita la tableta
                JOptionPane.showMessageDialog(mainFrame, "Por favor, coloque su dedo en el dispositivo y presione OK cuando termine.");
                sigPlusRef.setTabletState(0); // Deshabilita la tableta
                try {
                    java.awt.image.BufferedImage img = sigPlusRef.sigImage();
                    if (img != null) {
                        java.io.ByteArrayOutputStream baos = new java.io.ByteArrayOutputStream();
                        javax.imageio.ImageIO.write(img, "png", baos);
                        baos.flush();
                        huellaBytes[0] = baos.toByteArray();
                        baos.close();
                        huellaImgRef.setIcon(new ImageIcon(huellaBytes[0]));
                    } else {
                        JOptionPane.showMessageDialog(mainFrame, "No se pudo capturar la huella.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(mainFrame, "Error capturando la huella: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        guardarBtn.addActionListener(e -> {
            String dni = dniField.getText().trim();
            String nombres = nombresField.getText().trim();
            String apellidos = apellidosField.getText().trim();
            byte[] firma = firmaBytes[0];
            byte[] huella = huellaBytes[0];
            byte[] foto = fotoBytes[0];
            if (dni.isEmpty() || nombres.isEmpty() || apellidos.isEmpty() || firma == null || huella == null || foto == null) {
                JOptionPane.showMessageDialog(mainFrame, "Completa todos los campos y captura la firma, huella y foto.", "Faltan datos", JOptionPane.WARNING_MESSAGE);
                return;
            }
            try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS)) {
                // 1. Registrar/actualizar paciente en 'patients'
                String upsertPatient = "INSERT INTO patients (dni_id, name) VALUES (?, ?) ON DUPLICATE KEY UPDATE name = VALUES(name)";
                try (PreparedStatement ps = conn.prepareStatement(upsertPatient)) {
                    ps.setString(1, dni);
                    ps.setString(2, nombres + " " + apellidos);
                    ps.executeUpdate();
                }
                // 2. Guardar registro biométrico en 'entry_data'
                String sql = "INSERT INTO entry_data (patient_dni_id, visit_date, patient_img, signature_img, signature_jpgimg, signature_img2, signature) VALUES (?, NOW(), ?, ?, NULL, NULL, ?)";
                try (PreparedStatement ps = conn.prepareStatement(sql)) {
                    ps.setString(1, dni);
                    ps.setBytes(2, foto);
                    ps.setBytes(3, firma);
                    // Puedes adaptar para guardar otros formatos si los tienes
                    ps.setBytes(4, huella); // Guardamos la huella en 'signature' por compatibilidad, puedes crear otro campo si lo prefieres
                    int res = ps.executeUpdate();
                    if (res > 0) {
                        JOptionPane.showMessageDialog(mainFrame, "Registro guardado correctamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                        dniField.setText(""); nombresField.setText(""); apellidosField.setText("");
                        firmaImgRef.setIcon(null); firmaBytes[0] = null;
                        huellaImgRef.setIcon(null); huellaBytes[0] = null;
                        fotoImgRef.setIcon(null); fotoBytes[0] = null;
                    } else {
                        JOptionPane.showMessageDialog(mainFrame, "No se pudo guardar el registro.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(mainFrame, "Error al guardar en la base de datos: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
    }

    // Consulta y muestra las fechas previas de registros para un DNI
    private static void mostrarFechasPrevias(String dni, JTextArea area) {
        area.setText("");
        if (dni.isEmpty()) return;
        // Usa las variables de clase DB_URL, DB_USER, DB_PASS cargadas desde config.conf
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS)) {
            String sql = "SELECT visit_date FROM entry_data WHERE patient_dni_id = ? ORDER BY visit_date DESC";
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setString(1, dni);
                try (ResultSet rs = ps.executeQuery()) {
                    StringBuilder sb = new StringBuilder();
                    while (rs.next()) {
                        sb.append(rs.getString("visit_date")).append("\n");
                    }
                    if (sb.length() > 0) {
                        area.setText(sb.toString());
                    } else {
                        area.setText("Sin registros previos");
                    }
                }
            }
        } catch (SQLException ex) {
            area.setText("Error consultando fechas");
        }
    }
}
