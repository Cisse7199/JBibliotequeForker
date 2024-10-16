package tequePack;

import biblioPack.Button;
import biblioPack.MyPasswordField;
import biblioPack.MyTextField;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Objects;

public class loginPanel extends JFrame {
    private final JPanel cardPanel;
    private final JPanel loginPanel;
    private final JPanel registerPanel;
    public static final Color COLOR_OUTLINE = new Color(103, 112, 120);
    public static final Color COLOR_BACKGROUND = new Color(37, 51, 61);
    public static final Color COLOR_INTERACTIVE = new Color(108, 216, 158);
    public static final Color COLOR_INTERACTIVE_DARKER = new Color(87, 171, 127);
    public static final Color OFFWHITE = new Color(229, 229, 229);
    private Connection connection;

    public loginPanel() {
        connectToDatabase();

        JPanel mainJPanel = getMainJPanel();
        cardPanel = new JPanel(new CardLayout());
        loginPanel = new JPanel();
        registerPanel = new JPanel();
        login(cardPanel);
        register(cardPanel);

        mainJPanel.setLayout(new BorderLayout());
        mainJPanel.add(getImagePanel(), BorderLayout.WEST);
        mainJPanel.add(cardPanel, BorderLayout.CENTER);

        this.add(mainJPanel);
        this.pack();
        this.setVisible(true);
        this.toFront();

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setLocation(screenSize.width / 2 - getWidth() / 2, screenSize.height / 2 - getHeight() / 2);
    }

    private JPanel getMainJPanel() {
        Dimension size = new Dimension(1000, 500);
        setTitle("Connexion");

        JPanel panel1 = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                ImageIcon background = new ImageIcon(ClassLoader.getSystemResource("cv.png"));
                g.drawImage(background.getImage(), 0, 0, getWidth(), getHeight(), this);
            }
        };
        panel1.setSize(size);
        panel1.setPreferredSize(size);
        panel1.setLayout(null);

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                try {
                    if (connection != null && !connection.isClosed()) {
                        connection.close();
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                System.exit(0);
            }
        });

        return panel1;
    }

    private boolean isLoginVisible = true;

    private JPanel getImagePanel() {
        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("images/cv.png"));

        JLabel changer = new JLabel("S'inscrire");
        changer.setFont(new Font("Arial", Font.BOLD, 20));
        changer.setBounds(770, 400, 400, 50);
        changer.setForeground(Color.WHITE);
        add(changer);

        changer.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (isLoginVisible) {
                    showRegister();
                    changer.setText("Se connecter");
                } else {
                    showLogin();
                    changer.setText("S'inscrire");
                }
                isLoginVisible = !isLoginVisible;
            }
        });



        JPanel imagePanel = new JPanel();
        imagePanel.setPreferredSize(new Dimension(400, 500));
        imagePanel.setLayout(new BorderLayout());
        imagePanel.setBackground(Color.LIGHT_GRAY);

        JLabel imageLabel = new JLabel(i1);
        imageLabel.setHorizontalAlignment(JLabel.CENTER);
        imageLabel.setVerticalAlignment(JLabel.CENTER);
        imageLabel.setIcon(new ImageIcon(i1.getImage().getScaledInstance(400, 500, Image.SCALE_SMOOTH)));
        imagePanel.add(imageLabel, BorderLayout.CENTER);


        return imagePanel;
    }

    private void login(JPanel panel) {


        JLabel auLabel = new JLabel("Développé par ILBOUDO, DABIRE, DAH");
        auLabel.setFont(new Font("Arial", Font.BOLD, 20));
        auLabel.setBounds(510, 20, 400, 50);
        auLabel.setForeground(Color.WHITE);
        loginPanel.add(auLabel);

        loginPanel.setLayout(new MigLayout("wrap", "push[center]push", "push[]25[]10[]10[]25[]push"));
        loginPanel.setBackground(COLOR_BACKGROUND);

        JLabel label = new JLabel("Connexion");
        label.setFont(new Font("sanserif", Font.BOLD, 30));
        label.setForeground(COLOR_INTERACTIVE);
        loginPanel.add(label);

        MyTextField txtEmail = new MyTextField();
        ImageIcon iEmail = new ImageIcon(ClassLoader.getSystemResource("images/mail.png"));
        txtEmail.setPrefixIcon(iEmail);
        txtEmail.setHint("Email");
        loginPanel.add(txtEmail, "w 60%");

        MyPasswordField txtPass = new MyPasswordField();
        ImageIcon iPass = new ImageIcon(ClassLoader.getSystemResource("images/pass.png"));
        txtPass.setPrefixIcon(iPass);
        txtPass.setHint("Mot de passe");
        loginPanel.add(txtPass, "w 60%");

        biblioPack.Button cmdLogin = new biblioPack.Button();
        cmdLogin.setBackground(COLOR_INTERACTIVE_DARKER);
        cmdLogin.setForeground(OFFWHITE);
        cmdLogin.setText("Se connecter");
        cmdLogin.addActionListener(e -> loginUser(txtEmail.getText(), txtPass.getText()));
        loginPanel.add(cmdLogin, "w 60%, h 40");
        panel.add(loginPanel, "login");
    }

    private void register(JPanel panel) {

        registerPanel.setLayout(new MigLayout("wrap", "push[center]push", "push[]25[]10[]10[]25[]push"));
        registerPanel.setBackground(COLOR_BACKGROUND);

        JLabel label = new JLabel("Inscription");
        label.setFont(new Font("sanserif", Font.BOLD, 30));
        label.setForeground(COLOR_INTERACTIVE);
        registerPanel.add(label);

        MyTextField txtUser = new MyTextField();
        ImageIcon iUser = new ImageIcon(ClassLoader.getSystemResource("images/user.png"));
        txtUser.setPrefixIcon(iUser);
        txtUser.setHint("Nom complet");
        registerPanel.add(txtUser, "w 60%");

        MyTextField txtEmail = new MyTextField();
        ImageIcon iEmail = new ImageIcon(ClassLoader.getSystemResource("images/mail.png"));
        txtEmail.setPrefixIcon(iEmail);
        txtEmail.setHint("Email");
        registerPanel.add(txtEmail, "w 60%");

        MyPasswordField txtPass = new MyPasswordField();
        ImageIcon iPass = new ImageIcon(ClassLoader.getSystemResource("images/pass.png"));
        txtPass.setPrefixIcon(iPass);
        txtPass.setHint("Mot de passe");
        registerPanel.add(txtPass, "w 60%");

        biblioPack.Button cmdRegister = new Button();
        cmdRegister.setBackground(COLOR_INTERACTIVE_DARKER);
        cmdRegister.setForeground(OFFWHITE);
        cmdRegister.setText("S'inscrire");
        cmdRegister.addActionListener(e -> registerUser(txtUser.getText(), txtEmail.getText(), txtPass.getText()));
        registerPanel.add(cmdRegister, "w 60%, h 40");
        panel.add(registerPanel, "register");
    }

    private void showLogin() {
        CardLayout cl = (CardLayout) cardPanel.getLayout();
        cl.show(cardPanel, "login");
    }

    private void showRegister() {
        CardLayout cl = (CardLayout) cardPanel.getLayout();
        cl.show(cardPanel, "register");
    }

    private void connectToDatabase() {
        try {
            String url = "jdbc:mysql://localhost:3306/biblio";
            String user = "root";
            String password = "";
            connection = DriverManager.getConnection(url, user, password);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loginUser(String email, String password) {

        if(Objects.equals(email, "admin") && Objects.equals(password, "admin")){
            new tequePack.adminBoard();
            dispose();
        } else{

            try {
                String query = "SELECT pass FROM users WHERE mail = ?";
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                preparedStatement.setString(1, email);
                ResultSet resultSet = preparedStatement.executeQuery();

                if (resultSet.next()) {
                    String storedPassword = resultSet.getString("pass");
                    if (storedPassword.equals(password)) {
                        new tequePack.userBoard();
                        dispose();
                    } else {
                        JOptionPane.showMessageDialog(this, "Mot de passe incorrect.");
                    }
                } else {
                    JOptionPane.showMessageDialog(this, "Email non trouvé.");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void registerUser(String name, String email, String password) {
        try {
            String query = "INSERT INTO users (nom, mail, pass) VALUES (?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, email);
            preparedStatement.setString(3, password);
            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected > 0) {
                JOptionPane.showMessageDialog(this, "Inscription réussie !");
                showLogin();
            } else {
                JOptionPane.showMessageDialog(this, "Échec de l'inscription.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
