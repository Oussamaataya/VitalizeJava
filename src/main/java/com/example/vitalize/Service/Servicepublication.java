package com.example.vitalize.Service;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import com.example.vitalize.Util.MyDataBase;
import com.example.vitalize.Entity.Commentaire;
import com.example.vitalize.Entity.Publication;
import com.example.vitalize.Entity.React;

public class Servicepublication {

    public Publication getPublicationById(int id) {
        Publication publication = null;
        try {
            String req = "SELECT * FROM publication WHERE id = ?";
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                publication = new Publication();
                publication.setId(rs.getInt(1));
                publication.setIduser(rs.getInt(2));
                publication.setType(rs.getString(3));
                publication.setTitre(rs.getString(4));
                publication.setDescription(rs.getString(5));
                publication.setImage(rs.getString(6));
            }
            rs.close();
            ps.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return publication;
    }

    Connection cnx = MyDataBase.getInstance().getConnection();

    public List<Commentaire> getCommentsForPublication(int publicationId) {
        List<Commentaire> comments = new ArrayList<>();

        try {
            String query = "SELECT * FROM commentaire WHERE id_pub = ?";
            PreparedStatement ps = cnx.prepareStatement(query);
            ps.setInt(1, publicationId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("id");
                int userId = rs.getInt("id_user");
                String contenu = rs.getString("contenu");
                // You may need additional fields from the database

                // Create Commentaire object and add it to the list
                Commentaire commentaire = new Commentaire(id, userId, publicationId, contenu);
                comments.add(commentaire);
            }

            rs.close();
            ps.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
            // Handle exceptions as per your application's requirements
        }

        return comments;
    }

        public void add(Publication p) {
            try {

                String req = "INSERT INTO `publication`(`id_user_id`, `type`, `titre`, `description`, `image`) VALUES (?,?,?,?,?)";
                PreparedStatement ps = cnx.prepareStatement(req);
                ps.setInt(1, p.getIduser());
                ps.setString(   2, p.getType());
                ps.setString(3, p.getTitre());
                ps.setString(   4, p.getDescription());
                ps.setString(5, p.getImage());





                ps.executeUpdate();
                System.out.println("Publication Added Successfully!");

            } catch (SQLException ex) {
                ex.printStackTrace();
            }


        }

    public List<React> getAllReactsForPublication(int publicationId) {
        List<React> reacts = new ArrayList<>();
        try {
            // Assume you have a database connection and a React table named 'react'
            Connection cnx = MyDataBase.getInstance().getConnection();
            String query = "SELECT * FROM react WHERE publication_id = ?";
            PreparedStatement ps = cnx.prepareStatement(query);
            ps.setInt(1, publicationId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                int likeCount = rs.getInt("like_count");
                int dislikeCount = rs.getInt("dislike_count");
                // Assuming you have a method to get a publication by its ID
                Publication publication = getPublicationById(publicationId);
                React react = new React(id, publication, likeCount, dislikeCount);
                reacts.add(react);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return reacts;
    }


        public ObservableList<Publication> fetch() {
            ObservableList<Publication> Publication = FXCollections.observableArrayList();
            try {

                String req = "SELECT * FROM publication";
                Statement st = cnx.createStatement();
                ResultSet rs = st.executeQuery(req);
                while (rs.next()) {
                    Publication p = new Publication();
                    p.setId(rs.getInt(1));
                    p.setIduser(rs.getInt(2));
                    p.setType(rs.getString(3));
                    p.setTitre(rs.getString(4));
                    p.setDescription(rs.getString(5));
                    p.setImage(rs.getString(6));
               

                    Publication.add(p);
                }

            } catch (SQLException ex) {
                ex.printStackTrace();
            }

            return Publication;
        }


    public List<Publication> getAllPublications() {
        return fetch();
    }


    public void delete(int p) {
            try {
                String req ="DELETE FROM `publication`  WHERE id = ?";
                PreparedStatement ps = cnx.prepareStatement(req);
                ps.setInt(1, p);
                ps.executeUpdate();
                System.out.println("Publication Deleted successfully!");
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        public List<Publication> recherchePublication(int id) {
            List<Publication> Publication = new ArrayList<>();
            try {

                String req = "SELECT * FROM Publication WHERE id LIKE CONCAT(?, '%')";
                PreparedStatement st = cnx.prepareStatement(req);
                st.setInt(1, id);
                ResultSet rs = st.executeQuery();
                while (rs.next()) {
                    Publication p = new Publication();
                    p.setId(rs.getInt(1));
                    p.setIduser(rs.getInt(2));
                    p.setType(rs.getString(3));
                    p.setTitre(rs.getString(4));
                    p.setDescription(rs.getString(5));
                    p.setImage(rs.getString(6));
                    Publication.add(p);
                }


            } catch (SQLException ex) {
                ex.printStackTrace();
            }

            return Publication;
        }


        public void Edit(int e,String type, String titre, String description, String image) {
            try {
                String req = "UPDATE `Publication` SET `type`=?, `titre`=?, `description`=?, `image`=? WHERE `id`=?";
                PreparedStatement ps = cnx.prepareStatement(req);

                // Set values for each parameter
                ps.setString(1, type);
                ps.setString(2, titre);
                ps.setString(3, description);
                ps.setString(4, image);

                ps.setInt(5, e);

                // Execute the update
                int rowsUpdated = ps.executeUpdate();

                if (rowsUpdated > 0) {
                    System.out.println("Publication updated successfully!");
                } else {
                    System.out.println("Failed to update Publication. No matching record found.");
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }

        }
}
