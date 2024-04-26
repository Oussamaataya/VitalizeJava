package tn.esprit.entities;

public class Meal_Commande {

    public int commande_id;
    public int meal_id;

    public Meal_Commande() {
    }

    public Meal_Commande(int commande_id, int meal_id) {
        this.commande_id = commande_id;
        this.meal_id = meal_id;
    }

    public int getCommande_id() {
        return commande_id;
    }

    public int getMeal_id() {
        return meal_id;
    }

    public void setCommande_id(int commande_id) {
        this.commande_id = commande_id;
    }

    public void setMeal_id(int meal_id) {
        this.meal_id = meal_id;
    }
}
