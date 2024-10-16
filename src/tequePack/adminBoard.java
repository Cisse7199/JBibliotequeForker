package tequePack;

import biblioPack.Button;
import biblioPack.Jscroll;
import biblioPack.MyTextField;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class adminBoard extends JFrame {
    public static final Color COLOR_OUTLINE = new Color(103, 112, 120);
    public static final Color COLOR_BACKGROUND = new Color(37, 51, 61);
    public static final Color COLOR_INTERACTIVE = new Color(108, 216, 158);
    public static final Color COLOR_INTERACTIVE_DARKER = new Color(87, 171, 127);
    public static final Color OFFWHITE = new Color(229, 229, 229);

    private ImageIcon[] images = {
            resizeImageIcon(new ImageIcon(ClassLoader.getSystemResource("images/ajout.png")), 130, 130),
            resizeImageIcon(new ImageIcon(ClassLoader.getSystemResource("images/gestion.png")), 130, 130),
            resizeImageIcon(new ImageIcon(ClassLoader.getSystemResource("images/exit.png")), 130, 130)
    };

    private DefaultListModel<String> resultListModel;
    private JButton enregistrer;
    private JScrollPane resultScrollPane;
    private JPanel mainPanel;
    private JPanel ajoutPanel;
    private JPanel consultationPanel;
    private MyTextField searchField;
    private JButton searchButton;
    private String selectedBookId = null;
    private JButton modifierButton;
    private JButton supprimerButton;
    private MyTextField titre;
    private MyTextField auteur;
    private MyTextField categorie;
    private JList<String> bookList;

    public adminBoard() {
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

        JLabel auLabel = new JLabel("Développé par ILBOUDO, DABIRE, DAH");
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

        initAjoutPanel();
        initConsultationPanel();

        panel.add(ajoutPanel);
        panel.add(consultationPanel);

        return panel;
    }

    private void initAjoutPanel() {
        ajoutPanel = new JPanel();
        ajoutPanel.setLayout(null);
        ajoutPanel.setBounds(200, 150, 780, 400);
        ajoutPanel.setBackground(COLOR_BACKGROUND);

        titre = new MyTextField();
        titre.setHint("Titre");
        titre.setBounds(130, 50, 500, 40);
        ajoutPanel.add(titre);

        auteur = new MyTextField();
        auteur.setHint("Auteur");
        auteur.setBounds(130, 110, 500, 40);
        ajoutPanel.add(auteur);

        categorie = new MyTextField();
        categorie.setHint("Catégorie");
        categorie.setBounds(130, 170, 500, 40);
        ajoutPanel.add(categorie);

        enregistrer = new Button();
        enregistrer.setText("Ajouter");
        enregistrer.setBounds(130, 230, 500, 40);
        enregistrer.setBackground(COLOR_INTERACTIVE);
        enregistrer.addActionListener(e -> {
            if (selectedBookId == null) {
                enregistrerLivre(titre.getText(), auteur.getText(), categorie.getText());
            } else {
                updateLivre(selectedBookId, titre.getText(), auteur.getText(), categorie.getText());
            }
        });
        ajoutPanel.add(enregistrer);

        ajoutPanel.setVisible(true);
    }

    private void initConsultationPanel() {
        consultationPanel = new JPanel();
        consultationPanel.setLayout(null);
        consultationPanel.setBounds(200, 150, 780, 400);
        consultationPanel.setBackground(COLOR_BACKGROUND);

        searchField = new MyTextField();
        searchField.setHint("Recherche");
        searchField.setBounds(50, 50, 500, 40);
        consultationPanel.add(searchField);

        searchButton = new Button();
        searchButton.setText("Rechercher");
        searchButton.setBounds(580, 50, 130, 40);
        searchButton.setBackground(COLOR_INTERACTIVE);
        searchButton.addActionListener(e -> searchBooks());
        consultationPanel.add(searchButton);

        resultListModel = new DefaultListModel<>();
        bookList = new JList<>(resultListModel);
        bookList.setCellRenderer(new BookListRenderer());
        bookList.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 1) {
                    String selectedBook = bookList.getSelectedValue();
                    if (selectedBook != null) {
                        String[] bookData = selectedBook.split(" %50 ");

                        titre.setText(bookData[0]);
                        auteur.setText(bookData[1]);
                        categorie.setText(bookData[2]);

                        enregistrer.setText("Update");
                        modifierButton.setVisible(true);
                        supprimerButton.setVisible(true);
                        selectedBookId = bookData[3];
                    }
                }
            }
        });
        resultScrollPane = new Jscroll();
        resultScrollPane.setViewportView(bookList);
        resultScrollPane.setBounds(50, 110, 660, 250);
        consultationPanel.add(resultScrollPane);

        modifierButton = new Button();
        modifierButton.setText("Modifier");
        modifierButton.setBounds(150, 370, 200, 40);
        modifierButton.setBackground(COLOR_INTERACTIVE);
        modifierButton.setVisible(false);
        modifierButton.addActionListener(e -> ajoutPanel.setVisible(true));
        consultationPanel.add(modifierButton);

        supprimerButton = new Button();
        supprimerButton.setText("Supprimer");
        supprimerButton.setBounds(400, 370, 200, 40);
        supprimerButton.setBackground(COLOR_INTERACTIVE);
        supprimerButton.setVisible(false);
        supprimerButton.addActionListener(e -> supprimerLivre());
        consultationPanel.add(supprimerButton);

        fetchBookDetails();
        consultationPanel.setVisible(false);
    }

    private void supprimerLivre() {
        String selectedBook = bookList.getSelectedValue();
        if (selectedBook == null) {
            JOptionPane.showMessageDialog(null, "Veuillez sélectionner un livre à supprimer.");
            return;
        }

        String[] bookData = selectedBook.split(" %50 ");
        String bookTitle = bookData[0];

        String url = "jdbc:mysql://localhost:3306/biblio";
        String user = "root";
        String password = "";

        try (Connection connection = DriverManager.getConnection(url, user, password);
             PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM books WHERE titre = ?")) {

            preparedStatement.setString(1, bookTitle.trim());
            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected > 0) {
                JOptionPane.showMessageDialog(null, "Livre supprimé avec succès !");
                fetchBookDetails();
            } else {
                JOptionPane.showMessageDialog(null, "Échec de la suppression !");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void updateLivre(String bookId, String titre, String auteur, String categorie) {
        String url = "jdbc:mysql://localhost:3306/biblio";
        String user = "root";
        String password = "";

        try (Connection connection = DriverManager.getConnection(url, user, password);
             PreparedStatement preparedStatement = connection.prepareStatement("UPDATE books SET titre = ?, auteur = ?, categorie = ? WHERE id_book = ?")) {

            preparedStatement.setString(1, titre);
            preparedStatement.setString(2, auteur);
            preparedStatement.setString(3, categorie);
            preparedStatement.setString(4, bookId);

            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected > 0) {
                JOptionPane.showMessageDialog(null, "Livre mis à jour avec succès !");
                fetchBookDetails();
            } else {
                JOptionPane.showMessageDialog(null, "Échec de la mise à jour !");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void enregistrerLivre(String titre, String auteur, String categorie) {
        String url = "jdbc:mysql://localhost:3306/biblio";
        String user = "root";
        String password = "";

        try (Connection connection = DriverManager.getConnection(url, user, password);
             PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO books (titre, auteur, categorie, statut) VALUES (?, ?, ?, ?)")) {

            preparedStatement.setString(1, titre);
            preparedStatement.setString(2, auteur);
            preparedStatement.setString(3, categorie);
            preparedStatement.setString(4, "diponible");

            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected > 0) {
                JOptionPane.showMessageDialog(null, "Livre enregistré avec succès !");
                fetchBookDetails();
            } else {
                JOptionPane.showMessageDialog(null, "Échec de l'enregistrement !");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void fetchBookDetails() {
        resultListModel.clear();

        String url = "jdbc:mysql://localhost:3306/biblio";
        String user = "root";
        String password = "";

        try (Connection connection = DriverManager.getConnection(url, user, password);
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT * FROM books")) {

            while (resultSet.next()) {
                String bookInfo = resultSet.getString("titre") + " %50 " +
                        resultSet.getString("auteur") + " %50 " +
                        resultSet.getString("categorie") + " %50 " +
                        resultSet.getString("id_book");
                resultListModel.addElement(bookInfo);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void searchBooks() {
        String searchText = searchField.getText();
        resultListModel.clear();

        String url = "jdbc:mysql://localhost:3306/biblio";
        String user = "root";
        String password = "";

        try (Connection connection = DriverManager.getConnection(url, user, password);
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM books WHERE titre LIKE ? OR auteur LIKE ? OR categorie LIKE ?")) {

            preparedStatement.setString(1, "%" + searchText + "%");
            preparedStatement.setString(2, "%" + searchText + "%");
            preparedStatement.setString(3, "%" + searchText + "%");

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                String bookInfo = resultSet.getString("titre") + " %50 " +
                        resultSet.getString("auteur") + " %50 " +
                        resultSet.getString("categorie") + " %50 " +
                        resultSet.getString("id");
                resultListModel.addElement(bookInfo);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private ImageIcon resizeImageIcon(ImageIcon originalImage, int width, int height) {
        Image scaledImage = originalImage.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
        return new ImageIcon(scaledImage);
    }

    private JLabel createImageButton(ImageIcon imageIcon, int index) {
        JLabel imageLabel = new JLabel(imageIcon);
        imageLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        imageLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (index == 1) {
                    ajoutPanel.setVisible(true);
                    consultationPanel.setVisible(false);
                } else if (index == 2) {
                    ajoutPanel.setVisible(false);
                    consultationPanel.setVisible(true);
                } else if (index == 3) {
                    System.exit(0);
                }
            }
        });

        return imageLabel;
    }
}
