import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import java.text.DecimalFormat;

public class IntervaloMediaTStudent extends JFrame {

   
    private static final Color FONDO_GENERAL       = new Color(225, 240, 225);
    private static final Color FONDO_PANEL         = new Color(240, 250, 240);
    private static final Color VERDE_TITULO        = new Color(40, 90, 50);
    private static final Color VERDE_ENCABEZADO    = new Color(76, 140, 90);
    private static final Color VERDE_BOTON_PRINC   = new Color(56, 142, 60);
    private static final Color VERDE_BOTON_SEC     = new Color(102, 187, 106);
    private static final Color AZUL_BOTON          = new Color(70, 130, 180);
    private static final Color GRIS_LIMPIAR        = new Color(130, 150, 130);
    private static final Color ROJO_SALIR          = new Color(200, 80, 80);
    private static final Color BORDE_PANEL         = new Color(110, 170, 120);
    private static final Color TEXTO_RESULTADO     = new Color(30, 70, 40);
    private static final Color FONDO_RESULTADO     = new Color(250, 255, 248);
    private static final Color FONDO_PROCEDIMIENTO = new Color(252, 255, 250);

    
    private JTextField txtN;
    private JTextField txtMedia;
    private JTextField txtS;
    private JComboBox<String> cmbConfianza;

 
    private JTextField txtGL;
    private JTextField txtT;
    private JTextField txtError;
    private JTextField txtMargen;
    private JTextField txtLI;
    private JTextField txtLS;
    private JTextField txtIntervalo;

   
    private JTextArea txtProcedimiento;
    private PanelGraficaT panelGrafica;

   
    private final DecimalFormat df4 = new DecimalFormat("0.0000");
    private final DecimalFormat df3 = new DecimalFormat("0.000");

    private int    valorN;
    private int    valorGL;
    private double valorMedia;
    private double valorS;
    private double valorAlfa;
    private double valorAlfaMedio;
    private double valorT;
    private double valorError;
    private double valorMargen;
    private double valorLI;
    private double valorLS;
    private boolean datosValidos = false;

 
    private static final double[][] TABLA_T = {
        {6.314, 12.706, 63.657},
         {2.920,  4.303,  9.925},
         {2.353,  3.182,  5.841},
         {2.132,  2.776,  4.604},
         {2.015,  2.571,  4.032},
         {1.943,  2.447,  3.707},
         {1.895,  2.365,  3.499},
         {1.860,  2.306,  3.355},
         {1.833,  2.262,  3.250},
       {1.812,  2.228,  3.169},
         {1.796,  2.201,  3.106},
         {1.782,  2.179,  3.055},
        {1.771,  2.160,  3.012},
        {1.761,  2.145,  2.977},
        {1.753,  2.131,  2.947},
         {1.746,  2.120,  2.921},
         {1.740,  2.110,  2.898},
         {1.734,  2.101,  2.878},
        {1.729,  2.093,  2.861},
        {1.725,  2.086,  2.845},
         {1.721,  2.080,  2.831},
         {1.717,  2.074,  2.819},
         {1.714,  2.069,  2.807},
         {1.711,  2.064,  2.797},
         {1.708,  2.060,  2.787},
         {1.706,  2.056,  2.779},
        {1.703,  2.052,  2.771},
        {1.701,  2.048,  2.763},
        {1.699,  2.045,  2.756},
         {1.697,  2.042,  2.750}
    };

    private static final double[] VALORES_Z = {1.645, 1.960, 2.576};

   
    public IntervaloMediaTStudent() {
        configurarVentana();
        construirInterfaz();
    }

    private void configurarVentana() {
        setTitle("Intervalo de Confianza - Distribucion t de Student (Dos Colas)");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1180, 820);
        setMinimumSize(new Dimension(1080, 760));
        setLocationRelativeTo(null);
        getContentPane().setBackground(FONDO_GENERAL);
        setLayout(new BorderLayout(0, 0));
    }

    private void construirInterfaz() {
        
        add(crearEncabezado(), BorderLayout.NORTH);

        JPanel contenedor = new JPanel(new BorderLayout(12, 12));
        contenedor.setBackground(FONDO_GENERAL);
        contenedor.setBorder(new EmptyBorder(12, 15, 12, 15));

        
        JPanel filaSuperior = new JPanel(new GridLayout(1, 2, 12, 0));
        filaSuperior.setOpaque(false);
        filaSuperior.add(crearPanelEntrada());
        filaSuperior.add(crearPanelResultados());

        JPanel filaBotones = crearPanelBotones();

       
        JPanel filaInferior = new JPanel(new GridLayout(1, 2, 12, 0));
        filaInferior.setOpaque(false);
        filaInferior.add(crearPanelProcedimiento());
        filaInferior.add(crearPanelGrafica());

      
        JPanel pilaCentral = new JPanel();
        pilaCentral.setLayout(new BoxLayout(pilaCentral, BoxLayout.Y_AXIS));
        pilaCentral.setOpaque(false);

        filaSuperior.setAlignmentX(Component.LEFT_ALIGNMENT);
        filaBotones.setAlignmentX(Component.LEFT_ALIGNMENT);
        filaInferior.setAlignmentX(Component.LEFT_ALIGNMENT);

        pilaCentral.add(filaSuperior);
        pilaCentral.add(Box.createVerticalStrut(10));
        pilaCentral.add(filaBotones);
        pilaCentral.add(Box.createVerticalStrut(10));
        pilaCentral.add(filaInferior);

        contenedor.add(pilaCentral, BorderLayout.CENTER);
        add(contenedor, BorderLayout.CENTER);

        
        add(crearPiePagina(), BorderLayout.SOUTH);
    }

   
    private JPanel crearEncabezado() {
        JPanel encabezado = new JPanel();
        encabezado.setLayout(new BoxLayout(encabezado, BoxLayout.Y_AXIS));
        encabezado.setBackground(VERDE_ENCABEZADO);
        encabezado.setBorder(new EmptyBorder(14, 10, 14, 10));

        JLabel l1 = new JLabel("UNIVERSIDAD MARIANO GALVEZ DE GUATEMALA");
        l1.setForeground(Color.WHITE);
        l1.setFont(new Font("Segoe UI", Font.BOLD, 18));
        l1.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel l2 = new JLabel("PROYECTO  -  ESTADISTICA II");
        l2.setForeground(new Color(225, 250, 230));
        l2.setFont(new Font("Segoe UI", Font.BOLD, 14));
        l2.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel l3 = new JLabel("Calculo de Intervalo de Confianza para la Media Poblacional - t de Student (dos colas)");
        l3.setForeground(new Color(240, 255, 240));
        l3.setFont(new Font("Segoe UI", Font.ITALIC, 13));
        l3.setAlignmentX(Component.CENTER_ALIGNMENT);

        encabezado.add(l1);
        encabezado.add(Box.createVerticalStrut(3));
        encabezado.add(l2);
        encabezado.add(Box.createVerticalStrut(2));
        encabezado.add(l3);
        return encabezado;
    }

    private JPanel crearPanelEntrada() {
        JPanel p = new JPanel(new GridBagLayout());
        p.setBackground(FONDO_PANEL);
        p.setBorder(crearBordeTitulado("DATOS DE LA MUESTRA"));

        GridBagConstraints g = new GridBagConstraints();
        g.insets = new Insets(7, 10, 7, 10);
        g.fill = GridBagConstraints.HORIZONTAL;
        g.anchor = GridBagConstraints.WEST;

        Font fLbl = new Font("Segoe UI", Font.BOLD, 13);
        Font fTxt = new Font("Segoe UI", Font.PLAIN, 13);

        
        g.gridx = 0; g.gridy = 0; g.weightx = 0;
        JLabel lN = new JLabel("Tamaño de la muestra (n):");
        lN.setFont(fLbl); lN.setForeground(VERDE_TITULO);
        p.add(lN, g);
        g.gridx = 1; g.weightx = 1;
        txtN = new JTextField(); txtN.setFont(fTxt);
        p.add(txtN, g);

       
        g.gridx = 0; g.gridy = 1; g.weightx = 0;
        JLabel lM = new JLabel("Media muestral (X̄):");
        lM.setFont(fLbl); lM.setForeground(VERDE_TITULO);
        p.add(lM, g);
        g.gridx = 1; g.weightx = 1;
        txtMedia = new JTextField(); txtMedia.setFont(fTxt);
        p.add(txtMedia, g);

        
        g.gridx = 0; g.gridy = 2; g.weightx = 0;
        JLabel lS = new JLabel("Desviacion estandar (S):");
        lS.setFont(fLbl); lS.setForeground(VERDE_TITULO);
        p.add(lS, g);
        g.gridx = 1; g.weightx = 1;
        txtS = new JTextField(); txtS.setFont(fTxt);
        p.add(txtS, g);

        
        g.gridx = 0; g.gridy = 3; g.weightx = 0;
        JLabel lC = new JLabel("Nivel de confianza:");
        lC.setFont(fLbl); lC.setForeground(VERDE_TITULO);
        p.add(lC, g);
        g.gridx = 1; g.weightx = 1;
        cmbConfianza = new JComboBox<>(new String[]{"90 %", "95 %", "99 %"});
        cmbConfianza.setSelectedIndex(1); // 95% por defecto
        cmbConfianza.setFont(fTxt);
        cmbConfianza.setBackground(Color.WHITE);
        p.add(cmbConfianza, g);


        return p;
    }

    
    private JPanel crearPanelResultados() {
        JPanel p = new JPanel(new GridBagLayout());
        p.setBackground(FONDO_PANEL);
        p.setBorder(crearBordeTitulado("RESULTADOS DEL CALCULO"));

        GridBagConstraints g = new GridBagConstraints();
        g.insets = new Insets(4, 8, 4, 8);
        g.fill = GridBagConstraints.HORIZONTAL;
        g.anchor = GridBagConstraints.WEST;

        Font fLbl = new Font("Segoe UI", Font.BOLD, 12);

        txtGL        = crearCampoResultado();
        txtT         = crearCampoResultado();
        txtError     = crearCampoResultado();
        txtMargen    = crearCampoResultado();
        txtLI        = crearCampoResultado();
        txtLS        = crearCampoResultado();
        txtIntervalo = crearCampoResultado();
        txtIntervalo.setFont(new Font("Segoe UI", Font.BOLD, 14));
        txtIntervalo.setForeground(new Color(15, 70, 30));
        txtIntervalo.setBackground(new Color(232, 248, 232));

        agregarFilaResultado(p, g, 0, "Grados de libertad (gl):",    fLbl, txtGL);
        agregarFilaResultado(p, g, 1, "Valor critico t:",            fLbl, txtT);
        agregarFilaResultado(p, g, 2, "Error estandar  S/√(n-1):",   fLbl, txtError);
        agregarFilaResultado(p, g, 3, "Margen de error  E:",         fLbl, txtMargen);
        agregarFilaResultado(p, g, 4, "Limite inferior  LI:",        fLbl, txtLI);
        agregarFilaResultado(p, g, 5, "Limite superior  LS:",        fLbl, txtLS);
        agregarFilaResultado(p, g, 6, "Intervalo de confianza:",     fLbl, txtIntervalo);

        return p;
    }

    private void agregarFilaResultado(JPanel p, GridBagConstraints g, int fila,
                                      String etiqueta, Font fLbl, JTextField campo) {
        g.gridx = 0; g.gridy = fila; g.weightx = 0;
        JLabel l = new JLabel(etiqueta);
        l.setFont(fLbl); l.setForeground(VERDE_TITULO);
        p.add(l, g);
        g.gridx = 1; g.weightx = 1;
        p.add(campo, g);
    }

    private JTextField crearCampoResultado() {
        JTextField t = new JTextField();
        t.setEditable(false);
        t.setBackground(FONDO_RESULTADO);
        t.setForeground(TEXTO_RESULTADO);
        t.setFont(new Font("Consolas", Font.BOLD, 13));
        t.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(170, 200, 175), 1),
                new EmptyBorder(4, 6, 4, 6)));
        return t;
    }

   
    private JPanel crearPanelBotones() {
        JPanel p = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 6));
        p.setBackground(FONDO_PANEL);
        p.setBorder(crearBordeTitulado("ACCIONES"));

        JButton btnGL      = crearBoton("Calcular gl",       VERDE_BOTON_SEC);
        JButton btnT       = crearBoton("Calcular t",        VERDE_BOTON_SEC);
        JButton btnInt     = crearBoton("Calcular intervalo",VERDE_BOTON_SEC);
        JButton btnTodo    = crearBoton("CALCULAR TODO",     VERDE_BOTON_PRINC);
        JButton btnGraf    = crearBoton("Graficar",          AZUL_BOTON);
        JButton btnLimpiar = crearBoton("Limpiar",           GRIS_LIMPIAR);
        JButton btnSalir   = crearBoton("Salir",             ROJO_SALIR);

        btnGL.addActionListener(e -> accionCalcularGL());
        btnT.addActionListener(e -> accionCalcularT());
        btnInt.addActionListener(e -> accionCalcularIntervalo());
        btnTodo.addActionListener(e -> accionCalcularTodo());
        btnGraf.addActionListener(e -> accionGraficar());
        btnLimpiar.addActionListener(e -> accionLimpiar());
        btnSalir.addActionListener(e -> accionSalir());

        p.add(btnGL);
        p.add(btnT);
        p.add(btnInt);
        p.add(btnTodo);
        p.add(btnGraf);
        p.add(btnLimpiar);
        p.add(btnSalir);
        return p;
    }

    private JButton crearBoton(String texto, Color color) {
        JButton b = new JButton(texto);
        b.setBackground(color);
        b.setForeground(Color.BLACK);
        b.setFont(new Font("Segoe UI", Font.BOLD, 12));
        b.setFocusPainted(false);
        b.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(color.darker(), 1),
                new EmptyBorder(7, 14, 7, 14)));
        b.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        Color original = color;
        Color hover = color.brighter();
        b.addMouseListener(new MouseAdapter() {
            @Override public void mouseEntered(MouseEvent e) { b.setBackground(hover); }
            @Override public void mouseExited (MouseEvent e) { b.setBackground(original); }
        });
        return b;
    }

   
    private JPanel crearPanelProcedimiento() {
        JPanel p = new JPanel(new BorderLayout());
        p.setBackground(FONDO_PANEL);
        p.setBorder(crearBordeTitulado("PROCEDIMIENTO PASO A PASO"));

        txtProcedimiento = new JTextArea();
        txtProcedimiento.setEditable(false);
        txtProcedimiento.setFont(new Font("Consolas", Font.PLAIN, 12));
        txtProcedimiento.setBackground(FONDO_PROCEDIMIENTO);
        txtProcedimiento.setForeground(new Color(35, 60, 40));
        txtProcedimiento.setMargin(new Insets(8, 10, 8, 10));
        txtProcedimiento.setLineWrap(true);
        txtProcedimiento.setWrapStyleWord(true);
        txtProcedimiento.setText(
                " procedimiento \n" +
                "\n");

        JScrollPane sp = new JScrollPane(txtProcedimiento);
        sp.setBorder(BorderFactory.createLineBorder(new Color(170, 200, 175)));
        sp.setPreferredSize(new Dimension(500, 280));
        p.add(sp, BorderLayout.CENTER);
        return p;
    }

    
    private JPanel crearPanelGrafica() {
        JPanel p = new JPanel(new BorderLayout());
        p.setBackground(FONDO_PANEL);
        p.setBorder(crearBordeTitulado("DISTRIBUCION t DE STUDENT (DOS COLAS)"));

        panelGrafica = new PanelGraficaT();
        panelGrafica.setPreferredSize(new Dimension(500, 280));
        p.add(panelGrafica, BorderLayout.CENTER);
        return p;
    }

    
    private JPanel crearPiePagina() {
        JPanel p = new JPanel();
        p.setBackground(VERDE_ENCABEZADO);
        p.setBorder(new EmptyBorder(8, 10, 8, 10));
        JLabel l = new JLabel("Formula:   I  =  X̄  ±  t · S / √(n - 1)         |         Proyecto Estadistica II");
        l.setForeground(Color.WHITE);
        l.setFont(new Font("Segoe UI", Font.BOLD, 12));
        p.add(l);
        return p;
    }

    
    private Border crearBordeTitulado(String titulo) {
        TitledBorder tb = BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(BORDE_PANEL, 2, true),
                "  " + titulo + "  ");
        tb.setTitleFont(new Font("Segoe UI", Font.BOLD, 13));
        tb.setTitleColor(VERDE_TITULO);
        return BorderFactory.createCompoundBorder(tb, new EmptyBorder(6, 6, 6, 6));
    }

    

    private boolean leerYValidarDatos() {
        datosValidos = false;

        // Validar n
        String sN = txtN.getText().trim();
        if (sN.isEmpty()) {
            mostrarError("Debe ingresar el tamaño de la muestra (n).");
            txtN.requestFocus();
            return false;
        }
        try {
            valorN = Integer.parseInt(sN);
        } catch (NumberFormatException ex) {
            mostrarError("El tamaño de la muestra (n) debe ser un numero entero.");
            txtN.requestFocus();
            return false;
        }
        if (valorN <= 1) {
            mostrarError("El tamaño de la muestra (n) debe ser MAYOR que 1.");
            txtN.requestFocus();
            return false;
        }

        // Validar media
        String sM = txtMedia.getText().trim().replace(',', '.');
        if (sM.isEmpty()) {
            mostrarError("Debe ingresar la media muestral (X̄).");
            txtMedia.requestFocus();
            return false;
        }
        try {
            valorMedia = Double.parseDouble(sM);
        } catch (NumberFormatException ex) {
            mostrarError("La media muestral debe ser un numero valido.");
            txtMedia.requestFocus();
            return false;
        }

        // Validar S
        String sS = txtS.getText().trim().replace(',', '.');
        if (sS.isEmpty()) {
            mostrarError("Debe ingresar la desviacion estandar (S).");
            txtS.requestFocus();
            return false;
        }
        try {
            valorS = Double.parseDouble(sS);
        } catch (NumberFormatException ex) {
            mostrarError("La desviacion estandar debe ser un numero valido.");
            txtS.requestFocus();
            return false;
        }
        if (valorS <= 0) {
            mostrarError("La desviacion estandar (S) debe ser MAYOR que cero.");
            txtS.requestFocus();
            return false;
        }

        // Nivel de confianza -> alfa
        int idx = cmbConfianza.getSelectedIndex(); // 0=90, 1=95, 2=99
        switch (idx) {
            case 0: valorAlfa = 0.10; break;
            case 1: valorAlfa = 0.05; break;
            case 2: valorAlfa = 0.01; break;
            default: valorAlfa = 0.05;
        }
        valorAlfaMedio = valorAlfa / 2.0;

        datosValidos = true;
        return true;
    }

  
    private void accionCalcularGL() {
        if (!leerYValidarDatos()) return;
        valorGL = valorN - 1;
        txtGL.setText(String.valueOf(valorGL));
        info("gl = n - 1 = " + valorN + " - 1 = " + valorGL);
    }

  
    private void accionCalcularT() {
        if (!leerYValidarDatos()) return;
        valorGL = valorN - 1;
        valorT = obtenerValorT(valorGL, cmbConfianza.getSelectedIndex());
        txtGL.setText(String.valueOf(valorGL));
        txtT.setText(df3.format(valorT));
        info("Para gl = " + valorGL +
                " y nivel de confianza " + cmbConfianza.getSelectedItem() +
                " se obtiene  t = " + df3.format(valorT));
    }

    private void accionCalcularIntervalo() {
        if (!leerYValidarDatos()) return;
        calcularTodo();
        info("Intervalo calculado correctamente.");
    }

    private void accionCalcularTodo() {
        if (!leerYValidarDatos()) return;
        calcularTodo();
        construirProcedimiento();
        panelGrafica.actualizarValores(valorT, valorAlfaMedio, cmbConfianza.getSelectedIndex());
        panelGrafica.repaint();
    }

    private void accionGraficar() {
        if (!leerYValidarDatos()) return;
        valorGL = valorN - 1;
        valorT = obtenerValorT(valorGL, cmbConfianza.getSelectedIndex());
        panelGrafica.actualizarValores(valorT, valorAlfaMedio, cmbConfianza.getSelectedIndex());
        panelGrafica.repaint();
    }

    private void accionLimpiar() {
        txtN.setText("");
        txtMedia.setText("");
        txtS.setText("");
        cmbConfianza.setSelectedIndex(1);
        txtGL.setText("");
        txtT.setText("");
        txtError.setText("");
        txtMargen.setText("");
        txtLI.setText("");
        txtLS.setText("");
        txtIntervalo.setText("");
        txtProcedimiento.setText(
                " procedimiento \n" +
                "\n");
        panelGrafica.actualizarValores(0, 0, -1);
        panelGrafica.repaint();
        txtN.requestFocus();
    }

    private void accionSalir() {
        int op = JOptionPane.showConfirmDialog(this,
                "¿Desea salir del programa?", "Salir",
                JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        if (op == JOptionPane.YES_OPTION) System.exit(0);
    }

   
    private void calcularTodo() {
        // 1) Grados de libertad
        valorGL = valorN - 1;
        // 2) Valor critico de t
        valorT = obtenerValorT(valorGL, cmbConfianza.getSelectedIndex());
        // 3) Error estandar  S
        valorError = valorS / Math.sqrt(valorN - 1);
        // 4) Margen de error
        valorMargen = valorT * valorError;
        // 5) Limites
        valorLI = valorMedia - valorMargen;
        valorLS = valorMedia + valorMargen;

        // Mostrar en pantalla
        txtGL.setText(String.valueOf(valorGL));
        txtT.setText(df3.format(valorT));
        txtError.setText(df4.format(valorError));
        txtMargen.setText(df4.format(valorMargen));
        txtLI.setText(df4.format(valorLI));
        txtLS.setText(df4.format(valorLS));
        txtIntervalo.setText("I = [ " + df4.format(valorLI) + " , " + df4.format(valorLS) + " ]");
    }

    // Busca el valor critico de t en la tabla
    private double obtenerValorT(int gl, int indiceConfianza) {
        if (gl >= 1 && gl <= 30) {
            return TABLA_T[gl - 1][indiceConfianza];
        } else {
            // Para gl > 30 se usa aproximacion normal
            return VALORES_Z[indiceConfianza];
        }
    }

    
    private void construirProcedimiento() {
        StringBuilder sb = new StringBuilder();
        String nivel = (String) cmbConfianza.getSelectedItem();
        double nivelConf = 1.0 - valorAlfa;

        sb.append("======================================================\n");
        sb.append("   PROCEDIMIENTO COMPLETO DEL INTERVALO DE CONFIANZA\n");
        sb.append("======================================================\n\n");

        sb.append("1) DATOS INGRESADOS:\n");
        sb.append("     n   = ").append(valorN).append("\n");
        sb.append("     X̄   = ").append(valorMedia).append("\n");
        sb.append("     S   = ").append(valorS).append("\n");
        sb.append("     Nivel de confianza = ").append(nivel).append("\n\n");

        sb.append("2) CALCULO DEL NIVEL DE SIGNIFICANCIA (alfa):\n");
        sb.append("     alfa     = 1 - ").append(nivelConf).append(" = ").append(df3.format(valorAlfa)).append("\n");
        sb.append("     alfa/2   = ").append(df3.format(valorAlfa)).append(" / 2 = ").append(df4.format(valorAlfaMedio)).append("\n");
        sb.append("     \n\n");

        sb.append("3) GRADOS DE LIBERTAD:\n");
        sb.append("     gl = n - 1\n");
        sb.append("     gl = ").append(valorN).append(" - 1 = ").append(valorGL).append("\n\n");

        sb.append("4) VALOR CRITICO DE t de Student):\n");
        sb.append("     Con gl = ").append(valorGL);
        sb.append("  y  alfa/2 = ").append(df4.format(valorAlfaMedio)).append("\n");
        if (valorGL > 30) {
            sb.append("     Como gl > 30, se usa la aproximacion normal Z.\n");
        }
        sb.append("     t = ").append(df3.format(valorT)).append("\n\n");

        sb.append("5) ERROR ESTANDAR:\n");
        sb.append("     Error estandar = S / √(n - 1)\n");
        sb.append("     Error estandar = ").append(valorS).append(" / √").append(valorGL).append("\n");
        sb.append("     Error estandar = ").append(valorS).append(" / ").append(df4.format(Math.sqrt(valorGL))).append("\n");
        sb.append("     Error estandar = ").append(df4.format(valorError)).append("\n\n");

        sb.append("6) MARGEN DE ERROR:\n");
        sb.append("     E = t * (S / √(n - 1))\n");
        sb.append("     E = ").append(df3.format(valorT)).append(" * ").append(df4.format(valorError)).append("\n");
        sb.append("     E = ").append(df4.format(valorMargen)).append("\n\n");

        sb.append("7) FORMULA GENERAL DEL INTERVALO:\n");
        sb.append("     I = X̄ ± t * S / √(n - 1)\n");
        sb.append("     I = ").append(valorMedia).append(" ± ").append(df4.format(valorMargen)).append("\n\n");

        sb.append("8) CALCULO DE LIMITES:\n");
        sb.append("     Limite inferior  LI = X̄ - E\n");
        sb.append("     LI = ").append(valorMedia).append(" - ").append(df4.format(valorMargen));
        sb.append(" = ").append(df4.format(valorLI)).append("\n\n");
        sb.append("     Limite superior  LS = X̄ + E\n");
        sb.append("     LS = ").append(valorMedia).append(" + ").append(df4.format(valorMargen));
        sb.append(" = ").append(df4.format(valorLS)).append("\n\n");

        sb.append("=========================================================\n");
        sb.append("                    RESPUESTA FINAL\n");
        sb.append("=========================================================\n");
        sb.append("   I = [ ").append(df4.format(valorLI));
        sb.append(" , ").append(df4.format(valorLS)).append(" ]\n\n");
        sb.append("   Con un ").append(nivel).append(" de confianza, la verdadera media\n");
        sb.append("   poblacional µ se encuentra entre ").append(df4.format(valorLI));
        sb.append(" y ").append(df4.format(valorLS)).append(".\n");

        txtProcedimiento.setText(sb.toString());
        txtProcedimiento.setCaretPosition(0);
    }

   
    private void mostrarError(String msg) {
        JOptionPane.showMessageDialog(this, msg, "Dato invalido",
                JOptionPane.WARNING_MESSAGE);
    }

    private void info(String msg) {
        JOptionPane.showMessageDialog(this, msg, "Informacion",
                JOptionPane.INFORMATION_MESSAGE);
    }

    private class PanelGraficaT extends JPanel {
        private double tDibujo = 0;
        private double alfaMedioDibujo = 0;
        private int    idxConf = -1;

        public PanelGraficaT() {
            setBackground(new Color(252, 255, 252));
        }

        public void actualizarValores(double t, double alfaMedio, int indice) {
            this.tDibujo = t;
            this.alfaMedioDibujo = alfaMedio;
            this.idxConf = indice;
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON);

            int w = getWidth();
            int h = getHeight();
            int margenIzq = 50;
            int margenDer = 30;
            int margenSup = 30;
            int margenInf = 50;
            int anchoG = w - margenIzq - margenDer;
            int altoG  = h - margenSup - margenInf;
            int ejeY   = h - margenInf;          // linea base

            
            double xMin = -4.0, xMax = 4.0;

           
            int puntos = 200;
            int[] xs = new int[puntos];
            int[] ys = new int[puntos];
            for (int i = 0; i < puntos; i++) {
                double x = xMin + (xMax - xMin) * i / (puntos - 1);
                double y = Math.exp(-x * x / 2.0);
                xs[i] = margenIzq + (int) ((x - xMin) / (xMax - xMin) * anchoG);
                ys[i] = ejeY - (int) (y * altoG * 0.85);
            }

          
            if (tDibujo > 0 && tDibujo < 4.0) {
                // Cola izquierda: x = -infinito a -t
                Polygon polIzq = new Polygon();
                polIzq.addPoint(margenIzq, ejeY);
                for (int i = 0; i < puntos; i++) {
                    double x = xMin + (xMax - xMin) * i / (puntos - 1);
                    if (x <= -tDibujo) {
                        polIzq.addPoint(xs[i], ys[i]);
                    }
                }
                int xCorteIzq = margenIzq + (int) (((-tDibujo) - xMin) / (xMax - xMin) * anchoG);
                polIzq.addPoint(xCorteIzq, ejeY);
                g2.setColor(new Color(255, 170, 170, 180));
                g2.fillPolygon(polIzq);

              
                Polygon polDer = new Polygon();
                int xCorteDer = margenIzq + (int) ((tDibujo - xMin) / (xMax - xMin) * anchoG);
                polDer.addPoint(xCorteDer, ejeY);
                for (int i = 0; i < puntos; i++) {
                    double x = xMin + (xMax - xMin) * i / (puntos - 1);
                    if (x >= tDibujo) {
                        polDer.addPoint(xs[i], ys[i]);
                    }
                }
                polDer.addPoint(margenIzq + anchoG, ejeY);
                g2.setColor(new Color(255, 170, 170, 180));
                g2.fillPolygon(polDer);

          
                Polygon polCentro = new Polygon();
                polCentro.addPoint(xCorteIzq, ejeY);
                for (int i = 0; i < puntos; i++) {
                    double x = xMin + (xMax - xMin) * i / (puntos - 1);
                    if (x >= -tDibujo && x <= tDibujo) {
                        polCentro.addPoint(xs[i], ys[i]);
                    }
                }
                polCentro.addPoint(xCorteDer, ejeY);
                g2.setColor(new Color(170, 230, 180, 130));
                g2.fillPolygon(polCentro);

               
                g2.setColor(new Color(180, 30, 30));
                g2.setStroke(new BasicStroke(2f));
                int yArribaIzq = ejeY - (int) (Math.exp(-tDibujo * tDibujo / 2.0) * altoG * 0.85);
                int yArribaDer = yArribaIzq;
                g2.drawLine(xCorteIzq, ejeY, xCorteIzq, yArribaIzq);
                g2.drawLine(xCorteDer, ejeY, xCorteDer, yArribaDer);
            }

          
            g2.setColor(new Color(60, 60, 60));
            g2.setStroke(new BasicStroke(1.5f));
            g2.drawLine(margenIzq, ejeY, margenIzq + anchoG, ejeY);

            
            int xCero = margenIzq + (int) ((0 - xMin) / (xMax - xMin) * anchoG);
            g2.drawLine(xCero, ejeY - 4, xCero, ejeY + 4);

           
            g2.setColor(new Color(20, 70, 40));
            g2.setStroke(new BasicStroke(2.2f));
            for (int i = 1; i < puntos; i++) {
                g2.drawLine(xs[i - 1], ys[i - 1], xs[i], ys[i]);
            }

          
            g2.setFont(new Font("Segoe UI", Font.BOLD, 12));
            g2.setColor(new Color(40, 70, 50));
            g2.drawString("0", xCero - 4, ejeY + 16);

            if (tDibujo > 0 && tDibujo < 4.0) {
                int xCorteIzq = margenIzq + (int) (((-tDibujo) - xMin) / (xMax - xMin) * anchoG);
                int xCorteDer = margenIzq + (int) ((tDibujo - xMin) / (xMax - xMin) * anchoG);

                g2.setColor(new Color(150, 20, 20));
                g2.setFont(new Font("Segoe UI", Font.BOLD, 12));
                g2.drawString("-t = -" + df3.format(tDibujo), xCorteIzq - 35, ejeY + 18);
                g2.drawString("+t = " + df3.format(tDibujo), xCorteDer - 18, ejeY + 18);

                g2.setColor(new Color(120, 30, 30));
                g2.setFont(new Font("Segoe UI", Font.ITALIC, 11));
                g2.drawString("α/2 = " + df4.format(alfaMedioDibujo),
                        xCorteIzq - 65, ejeY - 5);
                g2.drawString("α/2 = " + df4.format(alfaMedioDibujo),
                        xCorteDer + 5, ejeY - 5);

               
                g2.setColor(new Color(20, 80, 35));
                g2.setFont(new Font("Segoe UI", Font.BOLD, 13));
                double conf = 1.0 - 2 * alfaMedioDibujo;
                String nivelTxt = String.format("1 - α = %.2f", conf);
                int anchoTxt = g2.getFontMetrics().stringWidth(nivelTxt);
                g2.drawString(nivelTxt, xCero - anchoTxt / 2, margenSup + altoG / 2);
            }

           
            g2.setColor(VERDE_TITULO);
            g2.setFont(new Font("Segoe UI", Font.BOLD, 13));
            String titulo = "Curva t de Student - dos colas";
            int anchoT = g2.getFontMetrics().stringWidth(titulo);
            g2.drawString(titulo, (w - anchoT) / 2, margenSup - 10);
        }
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ignored) {}

        SwingUtilities.invokeLater(() -> {
            IntervaloMediaTStudent v = new IntervaloMediaTStudent();
            v.setVisible(true);
        });
    }
}
