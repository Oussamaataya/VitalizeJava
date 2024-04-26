package models;

import java.time.LocalDateTime;

public class RendezVous {
    private int rdvId;
    private int doctorId;
    private LocalDateTime date;
    private String type;
    private boolean isAvailable;

    public RendezVous() {
    }

    public RendezVous(int rdvId, int doctorId, LocalDateTime date, String type, boolean isAvailable) {
        this.rdvId = rdvId;
        this.doctorId = doctorId;
        this.date = date;
        this.type = type;
        this.isAvailable = isAvailable;
    }

    public RendezVous(int doctorId, LocalDateTime date, String type, boolean isAvailable) {
        this.doctorId = doctorId;
        this.date = date;
        this.type = type;
        this.isAvailable = isAvailable;
    }

    public int getRdvId() {
        return rdvId;
    }

    public void setRdvId(int rdvId) {
        this.rdvId = rdvId;
    }

    public int getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(int doctorId) {
        this.doctorId = doctorId;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean available) {
        isAvailable = available;
    }

    @Override
    public String toString() {
        return "RendezVous{" +
                "rdvId=" + rdvId +
                ", doctorId=" + doctorId +
                ", date=" + date +
                ", type='" + type + '\'' +
                ", isAvailable=" + isAvailable +
                '}';
    }

}
