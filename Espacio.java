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

    // TODO: declare attributes
    // - int numeroFila
    // - int numeroEspacio
    // - String seccion   ("General", "VIP", "Electrico")
    // - boolean disponible

    // TODO: Constructor with all fields

    // TODO: Getters and setters for each attribute

    // TODO: toString()

    // TODO: equals(Object o)
    // Two espacios are equal if they have the same seccion + fila + numero
    // This is required for the HashSet to work correctly

    // TODO: hashCode()
    // Must be consistent with equals()
    // Hint: use Objects.hash(numeroFila, numeroEspacio, seccion)

}
