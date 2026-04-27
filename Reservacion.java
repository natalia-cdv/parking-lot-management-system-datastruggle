import java.time.LocalDate;
import java.util.List;

/**
 * Reservacion.java
 * Represents a single parking reservation made by a student.
 * 
 * Data structure used: Stored as values in HashMap<String, Reservacion>
 * and as elements inside LinkedList<Transaccion> via Transaccion.
 * 
 * Called by: Estacionamiento, Transaccion, Main
 */
public class Reservacion {

    // TODO: declare attributes
    // - Estudiante estudiante
    // - Espacio espacio
    // - LocalDate fecha
    // - int horaInicio        (valid range: 7 to 17, no fractions)
    // - int duracion          (valid range: 1 to 8 hours)
    // - double costoTotal
    // - List<String> serviciosAdicionales
    // - String seccion        ("General", "VIP", "Electrico")

    // TODO: Constructor with all fields

    // TODO: Getters and setters

    // TODO: toString() — full summary: student, car, space, date, time, cost, services

    // TODO: calcularCosto()
    // Uses seccion + duracion + serviciosAdicionales to compute costoTotal
    // Rates: General=$2/hr, VIP=$4/hr, Electrico=$8/hr
    // Add-on costs depend on section (see project spec)
    // Called by: Estacionamiento when building the reservation

}
