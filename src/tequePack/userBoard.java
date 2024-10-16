package tequePack;

import biblioPack.Button;
import biblioPack.Jscroll;
import biblioPack.MyTextField;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class userBoard extends JFrame {
    public static final Color COLOR_OUTLINE = new Color(103, 112, 120);
    public static final Color COLOR_BACKGROUND = new Color(37, 51, 61);
    public static final Color COLOR_INTERACTIVE = new Color(108, 216, 158);
    public static final Color COLOR_INTERACTIVE_DARKER = new Color(87, 171, 127);
    public static final Color OFFWHITE = new Color(229, 229, 229);

    private ImageIcon[] images = {
            resizeImageIcon(new ImageIcon(ClassLoader.getSystemResource("emprunt.png")), 130, 130),
            resizeImageIcon(new ImageIcon(ClassLoader.getSystemResource("consulter.png")), 130, 130),
            resizeImageIcon(new ImageIcon(ClassLoader.getSystemResource("exit.png")), 130, 130)
    };

    private JList<String> bookList;
    private DefaultListModel<String> bookListModel;
    private JScrollPane scrollPane;
    private DefaultListModel<String> resultListModel;
    private JButton emprunterButton;
    private JScrollPane resultScrollPane;
    private JPanel mainPanel;
    MyTextField searchField = new MyTextField();
    private JButton searchButton;

    public userBoard() {
        mainPanel = getMainPanel();
        this.add(mainPanel);
        this.pack();
        this.setVisible(true);
        this.setLocationRelativeTo(null);
    }

    private JPanel getMainPanel() {

        JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.setBackground(COLOR_BACKGROUND);
        panel.setPreferredSize(new Dimension(1000, 600));

        for (int i = 0; i < images.length; i++) {
            JLabel imageLabel = createImageButton(images[i], i + 1);
            imageLabel.setBounds(20, 100 + (i * 160), 150, 150);
            panel.add(imageLabel);
        }

        JLabel titleLabel = new JLabel("Bibliothèque Numérique");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setBounds(20, 20, 300, 50);
        titleLabel.setForeground(Color.WHITE);
        panel.add(titleLabel);

        JLabel auLabel = new JLabel("Developper par ILBOUDO, DABIRE, DAH");
        auLabel.setFont(new Font("Arial", Font.BOLD, 20));
        auLabel.setBounds(510, 20, 400, 50);
        auLabel.setForeground(Color.WHITE);
        panel.add(auLabel);

        JLabel descriptionLabel = new JLabel("<html>"
                + "Découvrez une vaste collection d'ouvrages numériques, disponibles à tout moment et où que vous soyez."
                + " Explorez dès maintenant et profitez d'une expérience de lecture immersive et enrichissante, adaptée à tous vos besoins de découverte.</html>");
        descriptionLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        descriptionLabel.setBounds(250, 70, 660, 100);
        descriptionLabel.setForeground(Color.WHITE);
        panel.add(descriptionLabel);

        initBookList(panel);
        return panel;
    }

    private void initBookList(JPanel panel) {

        bookListModel = new DefaultListModel<>();
        bookList = new JList<>(bookListModel);
        bookList.setCellRenderer(new BookListRenderer());
        scrollPane = new Jscroll();
        scrollPane.setViewportView(bookList);
        scrollPane.setBounds(250, 220, 660, 350);
        scrollPane.setVisible(true);
        panel.add(scrollPane);

        emprunterButton = new Button();
        emprunterButton.setText("Emprunter");
        emprunterButton.setBounds(760, 160, 150, 40);
        emprunterButton.setBackground(COLOR_INTERACTIVE);
        emprunterButton.setVisible(true);
        emprunterButton.addActionListener(e -> emprunterLivre());
        panel.add(emprunterButton);

        bookList.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                emprunterButton.setVisible(!bookList.isSelectionEmpty());
            }
        });

        fetchBookNames();
    }

    private void fetchBookNames() {
        String url = "jdbc:mysql://localhost:3306/biblio";
        String user = "root";
        String password = "";

        try (Connection connection = DriverManager.getConnection(url, user, password);
             Statement statement = connection.createStatement()) {

            String query = "SELECT titre, auteur FROM books WHERE statut = 'disponible'";
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                String bookTitle = resultSet.getString("titre");
                String author = resultSet.getString("auteur");
                bookListModel.addElement(bookTitle + " %50 " + author);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void emprunterLivre() {
        String selectedBook = bookList.getSelectedValue();
        if (selectedBook == null) return;

        String url = "jdbc:mysql://localhost:3306/biblio";
        String user = "root";
        String password = "";

        try (Connection connection = DriverManager.getConnection(url, user, password)) {
            String query = "UPDATE books SET statut = 'emprunter' WHERE titre = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, selectedBook.split("%50")[0].trim());

            int rowsUpdated = statement.executeUpdate();

            if (rowsUpdated > 0) {
                JOptionPane.showMessageDialog(this, "Livre emprunté avec succès!");
                mainPanel.remove(scrollPane);
                initBookList(mainPanel);
                mainPanel.revalidate();
                mainPanel.repaint();
            } else {
                JOptionPane.showMessageDialog(this, "Erreur lors de l'emprunt du livre.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Erreur lors de la connexion à la base de données.");
        }
    }

    private JLabel createImageButton(ImageIcon image, int buttonNumber) {
        JLabel label = new JLabel(image);
        label.setPreferredSize(new Dimension(150, 150));
        label.setOpaque(true);
        label.setBackground(COLOR_OUTLINE);
        label.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(COLOR_OUTLINE, 2),
                new EmptyBorder(5, 5, 5, 5)
        ));
        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setVerticalAlignment(SwingConstants.CENTER);

        label.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                switch (buttonNumber) {
                    case 1:
                        scrollPane.setVisible(true);
                        if (emprunterButton != null) emprunterButton.setVisible(true);
                        if (searchField != null) searchField.setVisible(false);
                        if (searchButton != null) searchButton.setVisible(false);
                        if (resultScrollPane != null) resultScrollPane.setVisible(false);
                        break;
                    case 2:
                        scrollPane.setVisible(false);
                        if (emprunterButton != null) emprunterButton.setVisible(false);
                        if (resultScrollPane != null) resultScrollPane.setVisible(true);
                        if (searchField != null) searchField.setVisible(true);
                        if (searchButton != null) {
                            searchButton.setVisible(true);
                        } else {
                            initConsultation(mainPanel);
                        }
                        break;
                    case 3:
                        new loginPanel();
                        dispose();
                        break;
                }
            }
        });


        return label;
    }

    private void initConsultation(JPanel panel) {
        searchField.setHint("Recherche");
        searchField.setBounds(250, 160, 500, 40);
        panel.add(searchField, "w 60%");

        searchButton = new Button();
        searchButton.setText("Rechercher");
        searchButton.setBounds(780, 160, 130, 40);
        searchButton.setBackground(COLOR_INTERACTIVE);
        searchButton.addActionListener(e -> searchBooks());
        panel.add(searchButton);

        resultListModel = new DefaultListModel<>();
        JList<String> resultList = new JList<>(resultListModel);
        resultList.setCellRenderer(new BookListRenderer());
        resultScrollPane = new Jscroll();
        resultScrollPane.setViewportView(resultList);
        resultScrollPane.setBounds(250, 220, 660, 350);
        resultScrollPane.getVerticalScrollBar().setPreferredSize(new Dimension(0, 0));
        resultScrollPane.setVisible(true);
        panel.add(resultScrollPane);

        fetchBookDetails();
    }

    private void fetchBookDetails() {
        String url = "jdbc:mysql://localhost:3306/biblio";
        String user = "root";
        String password = "";

        try (Connection connection = DriverManager.getConnection(url, user, password);
             Statement statement = connection.createStatement()) {

            String query = "SELECT titre, auteur, statut FROM books";
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                String bookTitle = resultSet.getString("titre");
                String author = resultSet.getString("auteur");
                String statut = resultSet.getString("statut");
                resultListModel.addElement(bookTitle + " %50 " + author + " %50 " + statut);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void searchBooks() {
        String searchTerm = searchField.getText().trim();
        if (searchTerm.isEmpty()) return;

        String url = "jdbc:mysql://localhost:3306/biblio";
        String user = "root";
        String password = "";

        try (Connection connection = DriverManager.getConnection(url, user, password)) {
            String query = "SELECT titre, auteur FROM books WHERE titre LIKE ? OR auteur LIKE ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, "%" + searchTerm + "%");
            statement.setString(2, "%" + searchTerm + "%");
            ResultSet resultSet = statement.executeQuery();

            resultListModel.clear();

            while (resultSet.next()) {
                String bookTitle = resultSet.getString("titre");
                String author = resultSet.getString("auteur");
                resultListModel.addElement(bookTitle + " %50 " + author + " %50 ");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private ImageIcon resizeImageIcon(ImageIcon icon, int width, int height) {
        Image image = icon.getImage();
        Image newImg = image.getScaledInstance(width, height, java.awt.Image.SCALE_SMOOTH);
        return new ImageIcon(newImg);
    }
}

