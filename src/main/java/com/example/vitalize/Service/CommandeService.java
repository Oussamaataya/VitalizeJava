package tn.esprit.services;


import tn.esprit.entities.Commande;
import tn.esprit.entities.Meal;
import tn.esprit.entities.TypeRepas;
import tn.esprit.interfaces.IService;
import tn.esprit.util.MaConnexion;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
public class CommandeService implements IService<Commande> {
    Connection cnx = MaConnexion.getInstance().getCnx();
    public void add(Commande commande) {
        String req = "INSERT INTO `commande` (`client_name`, `client_family_name`, `client_adresse`, `client_phone`, `methode_paiement`, `etat_commande`, `date`, `instruction_speciale`, `prixtotal`) VALUES ('" +
                commande.getClient_name() + "','" + commande.getClient_family_name() + "','" + commande.getClient_adresse() + "','" + commande.getClient_phone() + "','" +
                commande.getMethodePaiement() + "','" + commande.getEtatCommande() + "','" +
                commande.getDate().toString() + "','" + commande.getInstructionSpeciale() + "','" + commande.getPrixtotal() + "')";

        try {
            Statement st = cnx.createStatement();
            st.executeUpdate(req);
            System.out.println("Commande added successfully");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(Commande commande) {
        String req = "UPDATE commande SET client_name = '" + commande.getClient_name() +
                "', client_family_name = '" + commande.getClient_family_name() +
                "', client_adresse = '" + commande.getClient_adresse() +
                "', client_phone = '" + commande.getClient_phone() +
                "', methode_paiement = '" + commande.getMethodePaiement() +
                "', etat_commande = '" + commande.getEtatCommande() +
                "', date = '" + commande.getDate().toString() +
                "', instruction_speciale = '" + commande.getInstructionSpeciale() +
                "', prixtotal = " + commande.getPrixtotal() + // Suppression des guillemets autour de prixtotal
                " WHERE id = " + commande.getId();
        try {
            Statement st = cnx.createStatement();
            st.executeUpdate(req);
            System.out.println("Commande updated successfully");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(Commande commande) {
        String req = "DELETE FROM commande WHERE id = " + commande.getId();
        try {
            Statement st = cnx.createStatement();
            st.executeUpdate(req);
            System.out.println("Commande deleted successfully");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    @Override
    public List<Commande> getAll() {
        List<Commande> commandes = new ArrayList<>();
        String req = "SELECT c.*, m.* FROM commande c LEFT JOIN meal_commande cm ON c.id = cm.commande_id LEFT JOIN meal m ON cm.meal_id = m.id";
        try {
            Statement st = cnx.createStatement();
            ResultSet res = st.executeQuery(req);
            while (res.next()) {
                Commande commande = new Commande();
                commande.setId(res.getInt("c.id"));
                commande.setClient_name(res.getString("c.client_name"));
                commande.setClient_family_name(res.getString("c.client_family_name"));
                commande.setClient_adresse(res.getString("c.client_adresse"));
                commande.setClient_phone(res.getString("c.client_phone"));
                commande.setMethodePaiement(res.getString("c.methode_paiement"));
                commande.setEtatCommande(res.getString("c.etat_commande"));
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                commande.setDate(LocalDateTime.parse(res.getString("c.date"), formatter));
                commande.setInstructionSpeciale(res.getString("c.instruction_speciale"));
                commande.setPrixtotal(res.getInt("c.prixtotal"));

                // Création d'une nouvelle instance de Meal associée à cette commande
                Meal meal = new Meal();
                meal.setId(res.getInt("m.id"));
                meal.setNom_repas(res.getString("m.nom_repas"));
                meal.setIngredients(res.getString("m.ingredients"));
                meal.setRecette(res.getString("m.recette"));
                String typeRepasString = res.getString("m.type_repas");
                TypeRepas typeRepas = (typeRepasString != null) ? TypeRepas.valueOf(typeRepasString) : null;
                meal.setDuree_preparation(res.getString("m.duree_preparation"));
                meal.setImage(res.getString("m.image"));
                meal.setNombre_personnes (res.getInt("m.nombre_personnes"));
                meal.setPrix(res.getDouble("m.prix"));

                // Ajout du repas à la commande
                commande.getMeals().add(meal);

                // Ajout de la commande à la liste des commandes
                commandes.add(commande);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return commandes;
    }

    @Override
    public Commande getOne(int id) {
        String req = "SELECT * FROM commande WHERE id = " + id;
        try {
            Statement st = cnx.createStatement();
            ResultSet res = st.executeQuery(req);
            if (res.next()) {
                Commande commande = new Commande();
                commande.setId(res.getInt("id"));
                commande.setClient_name(res.getString("client_name"));
                commande.setClient_family_name(res.getString("client_family_name"));
                commande.setClient_adresse(res.getString("client_adresse"));
                commande.setClient_phone(res.getString("client_phone"));
                commande.setMethodePaiement(res.getString("methode_paiement"));
                commande.setEtatCommande(res.getString("etat_commande"));
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                commande.setDate(LocalDateTime.parse(res.getString("date"), formatter));
                commande.setInstructionSpeciale(res.getString("instruction_speciale"));
                commande.setPrixtotal(res.getInt("prixtotal"));
                return commande;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }
    public void addMealToCommande(List<Meal> meals, Commande commande) {
        String req = "INSERT INTO meal_commande (commande_id, meal_id) VALUES (?, ?)";
        try {
            PreparedStatement ps = cnx.prepareStatement(req);
            for (Meal meal : meals) {
                ps.setInt(1, commande.getId());
                ps.setInt(2, meal.getId());
                ps.addBatch();
            }
            ps.executeBatch();
            System.out.println("Meals added to Commande successfully");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
