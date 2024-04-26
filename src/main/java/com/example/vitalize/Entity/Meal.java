package tn.esprit.entities;
import java.util.ArrayList;
import java.util.List;
public class Meal {
    public String nom_repas;
    public String ingredients;
    public String recette;
    public String duree_preparation;
    public String image;
    public int id;
    public int nombre_personnes;
    public int quantity;
    public double prix;
    public TypeRepas type_repas;
    public List<Commande> commandes;

    public Meal() {
        commandes = new ArrayList<>();
    }


    public Meal(String text, String recettetfText, String ingredientstfText, String imagetfText, String typeRepadtfText, int i, String duréetfText, int parseInt) {
        commandes = new ArrayList<>();
    }

    public Meal(String nom_repas, String ingredients, String recette, TypeRepas type_repas, String duree_preparation, String image, int id, int nombre_personnes, int quantity, double prix) {
        this.nom_repas = nom_repas;
        this.ingredients = ingredients;
        this.recette = recette;
        this.type_repas = type_repas;
        this.duree_preparation = duree_preparation;
        this.image = image;
        this.id = id;
        this.nombre_personnes = nombre_personnes;
        this.quantity = quantity;
        this.prix = prix;
        commandes = new ArrayList<>();
    }



    public Meal(String nom_repas, String ingredients, String recette, TypeRepas type_repas, String duree_preparation, String image, int nombre_personnes, int quantity, double prix) {
        this.nom_repas = nom_repas;
        this.ingredients = ingredients;
        this.recette = recette;
        this.type_repas = type_repas;
        this.duree_preparation = duree_preparation;
        this.image = image;
        this.nombre_personnes = nombre_personnes;
        this.quantity = quantity;
        this.prix = prix;
        commandes = new ArrayList<>();
    }


    public String getNom_repas() {
        return nom_repas;
    }

    public void setNom_repas(String nom_repas) {
        this.nom_repas = nom_repas;
    }

    public String getIngredients() {
        return ingredients;
    }

    public void setIngredients(String ingredients) {
        this.ingredients = ingredients;
    }

    public String getRecette() {
        return recette;
    }

    public void setRecette(String recette) {
        this.recette = recette;
    }

    public String getDuree_preparation() {
        return duree_preparation;
    }

    public void setDuree_preparation(String duree_preparation) {
        this.duree_preparation = duree_preparation;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getNombre_personnes() {
        return nombre_personnes;
    }

    public void setNombre_personnes(int nombre_personnes) {
        this.nombre_personnes= nombre_personnes;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getPrix() {
        return prix;
    }

    public void setPrix(double prix) {
        this.prix = prix;
    }

    public TypeRepas getType_repas() {
        return type_repas;
    }

    public void setType_repas(TypeRepas type_repas) {
        this.type_repas = type_repas;
    }

    public List<Commande> getCommandes() {
        return commandes;
    }

    public void setCommandes(List<Commande> commandes) {
        this.commandes = commandes;
    }
}
