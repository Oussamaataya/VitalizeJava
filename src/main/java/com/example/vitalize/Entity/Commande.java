package tn.esprit.entities;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Commande {
    public int id ;
    public String client_name, client_family_name,client_adresse,client_phone;

    public  String methodePaiement;
    public String etatCommande;
    public LocalDateTime date;
    public String instructionSpeciale;
    public float prixtotal ;
    private List<Meal> meals;

    public Commande() {
        meals = new ArrayList<>();
    }



    public Commande(int id, String client_name, String client_family_name, String client_adresse, String client_phone, String methodePaiement, String etatCommande, LocalDateTime date, String instructionSpeciale, float prixtotal) {
        this.id = id;
        this.client_name = client_name;
        this.client_family_name = client_family_name;
        this.client_adresse = client_adresse;
        this.client_phone = client_phone;

        this.methodePaiement = methodePaiement;
        this.etatCommande = etatCommande;
        this.date = date;
        this.instructionSpeciale = instructionSpeciale;
        this.prixtotal = prixtotal;
        meals = new ArrayList<>();
    }

    public Commande(String client_name, String client_family_name, String client_adresse, String client_phone, String methodePaiement, String etatCommande, LocalDateTime date, String instructionSpeciale, float prixtotal) {
        this.client_name = client_name;
        this.client_family_name = client_family_name;
        this.client_adresse = client_adresse;
        this.client_phone = client_phone;

        this.methodePaiement = methodePaiement;
        this.etatCommande = etatCommande;
        this.date = date;
        this.instructionSpeciale = instructionSpeciale;
        this.prixtotal = prixtotal;
        meals = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public String getClient_name() {
        return client_name;
    }

    public String getClient_family_name() {
        return client_family_name;
    }

    public String getClient_adresse() {
        return client_adresse;
    }

    public String getClient_phone() {
        return client_phone;
    }



    public String getMethodePaiement() {
        return methodePaiement;
    }

    public String getEtatCommande() {
        return etatCommande;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public String getInstructionSpeciale() {
        return instructionSpeciale;
    }

    public float getPrixtotal() {
        return prixtotal;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setClient_name(String client_name) {
        this.client_name = client_name;
    }

    public void setClient_family_name(String client_family_name) {
        this.client_family_name = client_family_name;
    }

    public void setClient_adresse(String client_adresse) {
        this.client_adresse = client_adresse;
    }

    public void setClient_phone(String client_phone) {
        this.client_phone = client_phone;
    }



    public void setMethodePaiement(String methodePaiement) {
        this.methodePaiement = methodePaiement;
    }

    public void setEtatCommande(String etatCommande) {
        this.etatCommande = etatCommande;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public void setInstructionSpeciale(String instructionSpeciale) {
        this.instructionSpeciale = instructionSpeciale;
    }

    public void setPrixtotal(float prixtotal) {
        this.prixtotal= prixtotal;
    }

    @Override
    public String toString() {
        return "Commande{" +
                "id=" + id +
                ", client_name='" + client_name + '\'' +
                ", client_family_name='" + client_family_name + '\'' +
                ", client_adresse='" + client_adresse + '\'' +
                ", client_phone='" + client_phone + '\'' +
                ", methodePaiement='" + methodePaiement + '\'' +
                ", etatCommande='" + etatCommande + '\'' +
                ", date=" + date +
                ", instructionSpeciale='" + instructionSpeciale + '\'' +
                ", prixtotal=" + prixtotal +
                '}';
    }
    public List<Meal> getMeals() {
        return meals;
    }

    public void setMeals(List<Meal> meals) {
        this.meals = meals;
    }
}
