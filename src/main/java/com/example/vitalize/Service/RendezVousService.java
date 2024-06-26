package services;

import interfaces.IService;
import models.RendezVous;
import utils.MaConnexion;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class RendezVousService implements IService<RendezVous> {
    private Connection cnx;

    public RendezVousService() {
        cnx = MaConnexion.getInstance().getCnx();
    }

    @Override
    public void add(RendezVous rendezVous) {
        String req = "INSERT INTO `rendez_vous` (`id_doctor`, `date`, `type`, `is_available`) VALUES (?, ?, ?, ?)";
        try (PreparedStatement preparedStatement = cnx.prepareStatement(req)) {
            preparedStatement.setInt(1, rendezVous.getDoctorId());
            preparedStatement.setObject(2, rendezVous.getDate());
            preparedStatement.setString(3, rendezVous.getType());
            preparedStatement.setBoolean(4, rendezVous.isAvailable());
            preparedStatement.executeUpdate();
            System.out.println("RendezVous added successfully.");
        } catch (SQLException e) {
            System.err.println("Error occurred while adding RendezVous: " + e.getMessage());
        }
    }

    @Override
    public void update(RendezVous rendezVous) {
        String req = "UPDATE `rendez_vous` SET `id_doctor` = ?, `date` = ?, `type` = ?, `is_available` = ? WHERE `rdv_id` = ?";
        try (PreparedStatement preparedStatement = cnx.prepareStatement(req)) {
            preparedStatement.setInt(1, rendezVous.getDoctorId());
            preparedStatement.setObject(2, rendezVous.getDate());
            preparedStatement.setString(3, rendezVous.getType());
            preparedStatement.setBoolean(4, rendezVous.isAvailable());
            preparedStatement.setInt(5, rendezVous.getRdvId());
            preparedStatement.executeUpdate();
            System.out.println("RendezVous updated successfully.");
        } catch (SQLException e) {
            System.err.println("Error occurred while updating RendezVous: " + e.getMessage());
        }
    }

    @Override
    public void delete(RendezVous rendezVous) {
        String req = "DELETE FROM `rendez_vous` WHERE `rdv_id` = ?";
        try (PreparedStatement preparedStatement = cnx.prepareStatement(req)) {
            preparedStatement.setInt(1, rendezVous.getRdvId());
            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("RendezVous deleted successfully.");
            } else {
                System.out.println("No RendezVous found with ID: " + rendezVous.getRdvId());
            }
        } catch (SQLException e) {
            System.err.println("Error occurred while deleting RendezVous: " + e.getMessage());
        }
    }

    @Override
    public List<RendezVous> getAll() {
        List<RendezVous> rendezVousList = new ArrayList<>();
        String req = "SELECT * FROM `rendez_vous`";
        try (PreparedStatement preparedStatement = cnx.prepareStatement(req)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                RendezVous rendezVous = new RendezVous();
                rendezVous.setRdvId(resultSet.getInt("rdv_id"));
                rendezVous.setDoctorId(resultSet.getInt("id_doctor"));
                rendezVous.setDate(resultSet.getObject("date", LocalDateTime.class));
                rendezVous.setType(resultSet.getString("type"));
                rendezVous.setAvailable(resultSet.getBoolean("is_available"));
                rendezVousList.add(rendezVous);
            }
        } catch (SQLException e) {
            System.err.println("Error occurred while fetching RendezVous: " + e.getMessage());
        }
        return rendezVousList;
    }

    @Override
    public RendezVous getOne(int id) {
        RendezVous rendezVous = null;
        String req = "SELECT * FROM `rendez_vous` WHERE `rdv_id` = ?";
        try (PreparedStatement preparedStatement = cnx.prepareStatement(req)) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                rendezVous = new RendezVous();
                rendezVous.setRdvId(resultSet.getInt("rdv_id"));
                rendezVous.setDoctorId(resultSet.getInt("id_doctor"));
                rendezVous.setDate(resultSet.getObject("date", LocalDateTime.class));
                rendezVous.setType(resultSet.getString("type"));
                rendezVous.setAvailable(resultSet.getBoolean("is_available"));
            } else {
                System.out.println("No RendezVous found with ID: " + id);
            }
        } catch (SQLException e) {
            System.err.println("Error occurred while fetching RendezVous: " + e.getMessage());
        }
        return rendezVous;
    }
    public boolean exists(RendezVous rdv) throws SQLException {
        String query = "SELECT COUNT(*) FROM rendez_vous WHERE date = ? AND type = ?";
        try (PreparedStatement statement = cnx.prepareStatement(query)) {
            statement.setObject(1, rdv.getDate());
            statement.setString(2, rdv.getType());

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    int count = resultSet.getInt(1);
                    return count > 0;
                }
            }
        }
        return false;
    }
    public boolean existsForDifferentType(models.RendezVous rdv) throws SQLException {
        String query = "SELECT COUNT(*) FROM rendez_vous WHERE date = ? AND type != ?";
        try (PreparedStatement statement = cnx.prepareStatement(query)) {
            statement.setObject(1, rdv.getDate());
            statement.setString(2, rdv.getType());

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    int count = resultSet.getInt(1);
                    return count > 0;
                }
            }
        }
        return false;
    }

}
