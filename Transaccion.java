import java.time.LocalDateTime;

/**
 * Transaccion.java
 * Represents any action performed in the system (reserve, cancel, change, undo).
 * 
 * Data structure used: Elements stored in LinkedList<Transaccion> (transaction log)
 * and in Stack<Transaccion> (for undo).
 * 
 * Called by: Estacionamiento (every modifying action creates one)
 * Calls: Nothing (plain model)
 */
public class Transaccion {

    // TODO: declare attributes
    // - String tipo              ("RESERVAR", "CANCELAR", "CAMBIAR", "DESHACER")
    // - Reservacion reservacion  (the reservation this transaction refers to)
    // - LocalDateTime timestamp  (when the action happened)
    // - double monto             (amount charged or refunded)

    // TODO: Constructor with all fields

    // TODO: Getters

    // TODO: toString() — readable log entry for display option (f)

}
