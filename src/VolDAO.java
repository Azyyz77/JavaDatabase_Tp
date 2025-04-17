import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class VolDAO {
    String url = "jdbc:mysql://localhost:3306/aeroport";
    String user = "root";
    String password = "";

    public List<Vol> getAllVols() {
        List<Vol> vols = new ArrayList<>();
        String sql = "SELECT * FROM vol";

        try (Connection conn = DriverManager.getConnection(url, user, password);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Vol vol = new Vol(
                        rs.getString("numero_vol"),
                        rs.getString("ville_depart"),
                        rs.getString("ville_arrivee")
                );
                vols.add(vol);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return vols;
    }

    public void ajouterVol(Vol vol) {
        String sql = "INSERT INTO vol (numero_vol, ville_depart, ville_arrivee) VALUES (?, ?, ?)";

        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, vol.getNumeroVol());
            ps.setString(2, vol.getVilleDepart());
            ps.setString(3, vol.getVilleArrivee());

            ps.executeUpdate();
            System.out.println(" Vol ajouté avec succès.");

        } catch (SQLException e) {
            System.out.println(" Erreur lors de l'ajout du vol : " + e.getMessage());
        }
    }

    public void modifierDestination(String numeroVol, String nouvelleDestination) {
        String sql = "UPDATE vol SET ville_arrivee = ? WHERE numero_vol = ?";

        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, nouvelleDestination);
            ps.setString(2, numeroVol);

            int updated = ps.executeUpdate();
            if (updated > 0) {
                System.out.println(" Destination mise à jour.");
            } else {
                System.out.println(" Vol non trouvé.");
            }

        } catch (SQLException e) {
            System.out.println(" Erreur lors de la modification : " + e.getMessage());
        }
    }

    public Vol chercherVol(String numeroVol) {
        String sql = "SELECT * FROM vol WHERE numero_vol = ?";

        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, numeroVol);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return new Vol(
                        rs.getString("numero_vol"),
                        rs.getString("ville_depart"),
                        rs.getString("ville_arrivee")
                );
            }

        } catch (SQLException e) {
            System.out.println(" Erreur lors de la recherche du vol : " + e.getMessage());
        }

        return null;
    }
}
