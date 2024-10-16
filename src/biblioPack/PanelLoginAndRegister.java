package biblioPack;

import java.awt.Color;
import java.awt.Font;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import net.miginfocom.swing.MigLayout;

public class PanelLoginAndRegister extends javax.swing.JLayeredPane {

    public PanelLoginAndRegister() {
        initComponents();
        initRegister();
        initLogin();
        login.setVisible(false);
        register.setVisible(true);
    }

    private void initRegister() {
        register.setLayout(new MigLayout("wrap", "push[center]push", "push[]25[]10[]10[]25[]push"));
        JLabel label = new JLabel("Inscription");
        label.setFont(new Font("sanserif", Font.BOLD, 30));
        label.setForeground(new Color(7, 164, 121));
        register.add(label);
        MyTextField txtUser = new MyTextField();
        ImageIcon iUser = new ImageIcon("images/user.png");
        txtUser.setPrefixIcon(iUser);
        txtUser.setHint("Nom complet");
        register.add(txtUser, "w 60%");
        MyTextField txtEmail = new MyTextField();
        ImageIcon iEmail = new ImageIcon("images/mail.png");
        txtEmail.setPrefixIcon(iEmail);
        txtEmail.setHint("Email");
        register.add(txtEmail, "w 60%");
        MyPasswordField txtPass = new MyPasswordField();
        ImageIcon iPass = new ImageIcon("images/pass.png");
        txtPass.setPrefixIcon(iPass);
        txtPass.setHint("Mot de passe");
        register.add(txtPass, "w 60%");
        Button cmd = new Button();
        cmd.setBackground(new Color(35, 166, 151));
        cmd.setForeground(new Color(250, 250, 250));
        cmd.setText("S'inscrire");
        register.add(cmd, "w 40%, h 40");
    }

    private void initLogin() {
        login.setLayout(new MigLayout("wrap", "push[center]push", "push[]25[]10[]10[]25[]push"));
        JLabel label = new JLabel("Connexion");
        label.setFont(new Font("sanserif", Font.BOLD, 30));
        label.setForeground(new Color(35, 166, 151));
        login.add(label);
        MyTextField txtEmail = new MyTextField();
        ImageIcon iEmail = new ImageIcon("images/mail.png");
        txtEmail.setPrefixIcon(iEmail);
        txtEmail.setHint("Email");
        login.add(txtEmail, "w 60%");
        MyPasswordField txtPass = new MyPasswordField();
        ImageIcon iPass = new ImageIcon("images/pass.png");
        txtPass.setPrefixIcon(iPass);
        txtPass.setHint("Mot de passe");
        login.add(txtPass, "w 60%");
        Button cmd = new Button();
        cmd.setBackground(new Color(35, 166, 151));
        cmd.setForeground(new Color(250, 250, 250));
        cmd.setText("Se connecter");
        login.add(cmd, "w 40%, h 40");
    }

    public void showRegister(boolean show) {
        if (show) {
            register.setVisible(true);
            login.setVisible(false);
        } else {
            register.setVisible(false);
            login.setVisible(true);
        }
    }


    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        login = new javax.swing.JPanel();
        register = new javax.swing.JPanel();

        setLayout(new java.awt.CardLayout());

        login.setBackground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout loginLayout = new javax.swing.GroupLayout(login);
        login.setLayout(loginLayout);
        loginLayout.setHorizontalGroup(
            loginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 327, Short.MAX_VALUE)
        );
        loginLayout.setVerticalGroup(
            loginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );

        add(login, "card3");

        register.setBackground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout registerLayout = new javax.swing.GroupLayout(register);
        register.setLayout(registerLayout);
        registerLayout.setHorizontalGroup(
            registerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 327, Short.MAX_VALUE)
        );
        registerLayout.setVerticalGroup(
            registerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );

        add(register, "card2");
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel login;
    private javax.swing.JPanel register;
    // End of variables declaration//GEN-END:variables
}
