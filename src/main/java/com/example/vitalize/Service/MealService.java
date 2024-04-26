package tn.esprit.services;

import tn.esprit.entities.Commande;
import tn.esprit.entities.TypeRepas;
import tn.esprit.interfaces.IService;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import tn.esprit.entities.Meal;
import tn.esprit.util.MaConnexion;

public class MealService implements IService <Meal> {


    Connection cnx = MaConnexion.getInstance().getCnx();
    @Override
    public void add(Meal meal) {

        String req = "INSERT INTO `meal`( `nom_repas`, `ingredients`, `recette`, `type_repas`, `image`, `nombre_personnes`, `duree_preparation`, `prix`, `quantity`) VALUES ('" + meal.getNom_repas() + "','" + meal.getIngredients() + "','" + meal.getRecette() + "','" + meal.getType_repas() + "','" + meal.getImage() + "'," + meal.getNombre_personnes() + ",'" + meal.getDuree_preparation() + "'," + meal.getPrix() + "," + meal.getQuantity() + ")";
        try {
            Statement st = cnx.createStatement();
            st.executeUpdate(req);
            System.out.println("meal added sucessfully");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void update(Meal meal) {
        String req = "UPDATE meal SET nom_repas = '" + meal.getNom_repas() + "', ingredients = '" + meal.getIngredients() + "', recette = '" + meal.getRecette() + "', type_repas = '" + meal.getType_repas() + "', image = '" + meal.getImage() + "', nombre_personnes = " + meal.getNombre_personnes()+ ", duree_preparation = '" + meal.getDuree_preparation() + "', prix = " + meal.getPrix() + ", quantity = " + meal.getQuantity() + " WHERE id = " + meal.getId();
        try {
            Statement st = cnx.createStatement();
            st.executeUpdate(req);
            System.out.println("Meal updated successfully");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(Meal meal) {
        String req = "DELETE FROM meal WHERE id = " + meal.getId();
        try {
            Statement st = cnx.createStatement();
            st.executeUpdate(req);
            System.out.println("Meal deleted successfully");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }



    @Override
    public List<Meal> getAll() {
        List<Meal> meals = new ArrayList<>();
        String req = "SELECT m.*, c.* FROM meal m LEFT JOIN meal_commande cm ON m.id = cm.meal_id LEFT JOIN commande c ON cm.commande_id = c.id";
        try {
            Statement st = cnx.createStatement();
            ResultSet res = st.executeQuery(req);
            while (res.next()) {
                Meal meal = new Meal();
                meal.setId(res.getInt("m.id"));
                meal.setNom_repas(res.getString("m.nom_repas"));
                meal.setIngredients(res.getString("m.ingredients"));
                meal.setRecette(res.getString("m.recette"));
                meal.setType_repas(TypeRepas.valueOf(res.getString("m.type_repas")));
                meal.setDuree_preparation(res.getString("m.duree_preparation"));
                meal.setImage(res.getString("m.image"));
                meal.setNombre_personnes(res.getInt("m.nombre_personnes"));
                meal.setQuantity(res.getInt("m.quantity"));
                meal.setPrix(res.getDouble("m.prix"));

                // Création d'une nouvelle instance de Commande associée à ce repas
                Commande commande = new Commande();
                commande.setId(res.getInt("c.id"));
                commande.setClient_name(res.getString("c.client_name"));
                commande.setClient_family_name(res.getString("c.client_family_name"));
                commande.setClient_adresse(res.getString("c.client_adresse"));
                commande.setClient_phone(res.getString("c.client_phone"));
                commande.setMethodePaiement(res.getString("c.methode_paiement"));
                commande.setEtatCommande(res.getString("c.etat_commande"));
                // Parsing de la date à partir de la chaîne de caractères
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                String dateString = res.getString("c.date");
                LocalDateTime date = null;
                if (dateString != null) {
                    date = LocalDateTime.parse(dateString, formatter);
                }
                commande.setDate(date);
                commande.setInstructionSpeciale(res.getString("c.instruction_speciale"));
                commande.setPrixtotal(res.getInt("c.prixtotal"));

                // Ajout de la commande au repas
                if (meal.getCommandes() != null) {
                    meal.getCommandes().add(commande);
                }


                // Ajout du repas à la liste des repas
                meals.add(meal);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return meals;
    }


    @Override
    public Meal getOne(int id) {
        String req = "SELECT * FROM Meal WHERE id = " + id;
        try {
            Statement st = cnx.createStatement();
            ResultSet res = st.executeQuery(req);
            if (res.next()) {
                Meal meal = new Meal();
                meal.setId(res.getInt("id"));
                meal.setNom_repas(res.getString("nom_repas"));
                meal.setIngredients(res.getString("ingredients"));
                meal.setRecette(res.getString("recette"));
                meal.setType_repas(TypeRepas.valueOf(res.getString("type_repas")));
                meal.setDuree_preparation(res.getString("duree_preparation"));
                meal.setNombre_personnes(res.getInt("nombre_personnes"));
                meal.setQuantity(res.getInt("quantity"));
                meal.setPrix(res.getDouble("prix"));
                return meal;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }


}
