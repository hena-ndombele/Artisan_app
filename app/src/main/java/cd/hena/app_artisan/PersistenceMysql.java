package cd.hena.app_artisan;

import static cd.hena.app_artisan.Constance.IP;
import static cd.hena.app_artisan.Constance.PORT;
import static cd.hena.app_artisan.Constance.databasename;
import static cd.hena.app_artisan.Constance.password;
import static cd.hena.app_artisan.Constance.username;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class PersistenceMysql {
    private static final String URL = "jdbc:mysql://localhost:3306/artisan_app";

    String url_mysql = "jdbc:jtds:sqlserver://" + IP + ":" + PORT + ";databasename=" + databasename + ";User=" + username + ";password=" + password + ";";
    private static final String UTILISATEUR = "hena";
    private static final String MOT_DE_PASSE = "12345";

    public static void main(String[] args) {
        // Exemple de données pour créer un compte
        String nom = "Hena Ndombele";
        String email = "hena@gmail.com";
        String password = "12345";

        // Établir une connexion à la base de données
        try (Connection connexion = DriverManager.getConnection(URL, UTILISATEUR, MOT_DE_PASSE)) {
            // Requête SQL pour insérer un compte
            String requeteSQL = "INSERT INTO comptes (nom, email, motDePasse) VALUES (?, ?, ?)";

            // Créer un objet PreparedStatement et définir les valeurs des paramètres
            try (PreparedStatement declaration = connexion.prepareStatement(requeteSQL)) {
                declaration.setString(1, nom);
                declaration.setString(2, email);
                declaration.setString(3, password);

                // Exécuter la requête SQL
                int lignesModifiees = declaration.executeUpdate();
                if (lignesModifiees > 0) {
                    System.out.println("Le compte a été créé avec succès.");
                } else {
                    System.out.println("Impossible de créer le compte.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}