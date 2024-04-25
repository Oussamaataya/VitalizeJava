package com.example.vitalize.Service;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import com.example.vitalize.Util.MyDataBase;
import com.example.vitalize.Entity.Commentaire;
import com.example.vitalize.Entity.Publication;
import com.example.vitalize.Entity.React;
import com.example.vitalize.Service.Servicepublication;

public class ServiceReact {

    Connection cnx = MyDataBase.getInstance().getConnection();

    public void add(React react) {
        try {
            String req = "INSERT INTO `react`(`publication_id`, `like_count`, `dislike_count`) VALUES (?,?,?)";
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setInt(1, react.getPublication().getId());
            ps.setInt(2, react.getLikeCount());
            ps.setInt(3, react.getDislikeCount());
            ps.executeUpdate();
            System.out.println("React Added Successfully!");
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public List<React> fetch() {
        List<React> reacts = new ArrayList<>();
        try {
            String req = "SELECT * FROM react";
            Statement st = cnx.createStatement();
            ResultSet rs = st.executeQuery(req);
            while (rs.next()) {
                React react = new React();
                react.setId(rs.getInt("id"));
                // Fetch the associated publication from the database based on its ID
                int publicationId = rs.getInt("publication_id");
                Servicepublication publicationService = new Servicepublication(); // Assuming you have a PublicationService class
                Publication publication = publicationService.getPublicationById(publicationId);
                react.setPublication(publication);
                react.setLikeCount(rs.getInt("like_count"));
                react.setDislikeCount(rs.getInt("dislike_count"));
                reacts.add(react);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return reacts;
    }

    public void delete(int id) {
        try {
            String req = "DELETE FROM `react` WHERE id = ?";
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setInt(1, id);
            ps.executeUpdate();
            System.out.println("React Deleted successfully!");
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public void edit(React react) {
        try {
            String req = "UPDATE `react` SET `publication_id`=?, `like_count`=?, `dislike_count`=? WHERE `id`=?";
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setInt(1, react.getPublication().getId());
            ps.setInt(2, react.getLikeCount());
            ps.setInt(3, react.getDislikeCount());
            ps.setInt(4, react.getId());
            int rowsUpdated = ps.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("React updated successfully!");
            } else {
                System.out.println("Failed to update React. No matching record found.");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
}
