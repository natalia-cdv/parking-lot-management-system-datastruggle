/**
 * Espacio.java
 * Represents a single parking space in the lot.
 * 
 * Data structure used: Stored inside a HashSet<Espacio> in Estacionamiento.
 * equals() and hashCode() are overridden so the Set can correctly identify
 * duplicate or matching spaces.
 * 
 * Called by: Estacionamiento, Reservacion
 */
public class Espacio {

// Espacio Attributes

    private int numeroFila;
    private int numeroEspacio;
    private String seccion;
    private boolean disponible;

    // Constructor with all fields
    public Espacio(int numFila, int numEspacio, String seccion, boolean disponible){
        this.numeroFila = numFila;
        this.numeroEspacio = numEspacio;
        this.seccion = seccion;
        this.disponible = disponible;
    }

    // Getters and setters for each attribute

    public int getNumeroFila(){
        return numeroFila;
    }

    public void setNumeroFila(int numFila){
        this.numeroFila = numFila;
    }

    public int getNumeroEspacio(){
        return numeroEspacio;
    }

    public void setNumeroEspacio(int numEspacio){
        this.numeroEspacio = numEspacio;
    }

    public String getSeccion(){
        return seccion;
    }

    public void setSeccion(String seccion){
        this.seccion = seccion;
    }

    public boolean isDisponible(){
        return disponible;
    }

    public void setDisponible(boolean disponible){
        this.disponible = disponible;
    }



     @Override
    public String toString() {
        return  "  [ Ubicación Asignada ]" +
                "\n    Fila: " + numeroFila + " | Espacio: " + numeroEspacio +
                "\n    Estado: " + (disponible ? "Confirmado" : "Pendiente");
    }

//    Compares two Espacios to see if they're Equal

    @Override
    public boolean equals(Object o){
        if (this == o){
            return true;
        }

        if (o == null || getClass() != o.getClass()) return false;

        Espacio espacio = (Espacio) o;

        return numeroFila == espacio.numeroFila &&
        numeroEspacio == espacio.numeroEspacio &&
        java.util.Objects.equals(seccion, espacio.seccion);


        
    }

    @Override
    public int hashCode(){
        return java.util.Objects.hash(numeroFila, numeroEspacio, seccion);
    }

}
