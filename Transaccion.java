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
// Class: Transaccion
public class Transaccion {

    private String tipo; // "RESERVAR", "CANCELAR", "CAMBIAR", "DESHACER"
    private Reservacion reservacion;
    private LocalDateTime timestamp;
    private double monto;

    // Constructor
    public Transaccion(String tipo, Reservacion reservacion, LocalDateTime timestamp, double monto) {
        this.tipo = tipo;
        this.reservacion = reservacion;
        this.timestamp = timestamp;
        this.monto = monto;
    }
    
    // Getters 
    public String getTipo() {
        return tipo;
    }
    public Reservacion getReservacion() {
        return reservacion;
    }
    public LocalDateTime getTimestamp() {
        return timestamp;
    }
    public double getMonto() {
        return monto;
    }

// Returns a readable string with all the "Transaccion" attributes.
    @Override
    public String toString() {
        return "Transaccion{" +
                "tipo='" + tipo + '\'' +
                ", reservacion=" + reservacion +
                ", timestamp=" + timestamp +
                ", monto=$" + String.format("%.2f", monto) +
                '}';
    }
}
