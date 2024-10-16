package tequePack;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

public class InsertBooks {

    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/biblio";
        String user = "root";
        String password = "";

        String[][] livres = {
                {"Le souffle des ancêtres", "Pierre Claver Ilboudo", "Roman", "disponible"},
                {"Le Cri du silence", "Fulgence Zoungrana", "Essai", "disponible"},
                {"La terre qui chante", "Béatrice Dembélé", "Poésie", "disponible"},
                {"L'enfant noir", "Camara Laye", "Roman", "emprunter"},
                {"Quand les cauris s'effondrent", "Isidore Zongo", "Roman", "disponible"},
                {"Soleil des indépendances", "Ahmadou Kourouma", "Roman", "emprunter"},
                {"Les tambours de la nuit", "Thomas Sankara", "Histoire", "emprunter"},
                {"Le secret du baobab", "Awa Koné", "Conte", "disponible"},
                {"La bataille de Ouagadougou", "Moussa Diallo", "Histoire", "emprunter"},
                {"Dieu n'aime pas le mensonge", "Jacqueline Ki-Zerbo", "Roman", "disponible"},
                {"L'épopée des moissons", "Seydou Ouedraogo", "Roman", "emprunter"},
                {"Sous le manguier", "Salif Sanfo", "Poésie", "disponible"},
                {"Le sacré baobab", "Nathalie Soré", "Conte", "emprunter"},
                {"La route de Yennenga", "Ibrahim Ouédraogo", "Histoire", "emprunter"},
                {"L'aventure ambiguë", "Cheikh Hamidou Kane", "Roman", "disponible"},
                {"Une si longue lettre", "Mariama Bâ", "Roman", "emprunter"},
                {"Le devoir de violence", "Yambo Ouologuem", "Roman", "disponible"},
                {"Les impatientes", "Djaïli Amadou Amal", "Roman", "disponible"},
                {"Le vieux nègre et la médaille", "Ferdinand Oyono", "Roman", "emprunter"},
                {"Le sang des masques", "Jean-Baptiste Bassolé", "Roman", "disponible"}
        };

        try (Connection connection = DriverManager.getConnection(url, user, password)) {
            String query = "INSERT INTO books (titre, auteur, categorie, statut) VALUES (?, ?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(query);

            for (String[] livre : livres) {
                statement.setString(1, livre[0]); // Titre
                statement.setString(2, livre[1]); // Auteur
                statement.setString(3, livre[2]); // Catégorie
                statement.setString(4, livre[3]); // Statut
                statement.addBatch();
            }

            statement.executeBatch();
            System.out.println("Livres ajoutés avec succès.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
