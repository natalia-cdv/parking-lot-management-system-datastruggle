/**
 * Auto.java
 * Represents a car registered in the parking system.
 * 
 * Data structure used: N/A (plain model class)
 * Called by: Estudiante, Reservacion, Main
 */
public class Auto {

    // Auto attributes
    private String tablilla;
    private String marca;
    private String modelo;
    private int año;

    // Constructor with all fields
    public Auto(String tablilla, String marca, String modelo, int año) {
        this.tablilla = tablilla;
        this.marca = marca;
        this.modelo = modelo;
        this.año = año;
    }

    // Getters and setters for each attribute
    public String getTablilla() {
        return tablilla;
    }

    public void setTablilla(String tablilla) {
        this.tablilla = tablilla;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public int getAño() {
        return año;
    }

    public void setAño(int año) {
        this.año = año;
    }

    @Override
    public String toString() {
        return  "    [ Auto ]" +
                "\n      Tablilla: " + tablilla +
                "\n      Marca: " + marca + " | Modelo: " + modelo + " (" + año + ")";
    }
    

}
