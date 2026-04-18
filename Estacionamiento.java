import java.time.LocalDate;
import java.util.*;

/**
 * Estacionamiento.java
 * Central manager class. Handles all reservations, cancellations,
 * waitlists, undo, and display queries.
 *
 * Data structures used:
 * - Set<Espacio>: Three HashSets (one per section) storing available spaces.
 *   Chosen because: O(1) add/remove/contains, and naturally prevents duplicates.
 *
 * - HashMap<String, Reservacion>: Maps car tablilla → active Reservacion.
 *   Chosen because: O(1) lookup by tablilla for fast reservation retrieval.
 *
 * - LinkedList<Transaccion>: Full ordered log of all system actions.
 *   Chosen because: Efficient insertion at both ends; good for sequential history.
 *
 * - Stack<Transaccion>: Tracks last actions for undo functionality.
 *   Chosen because: LIFO structure — last action is always on top.
 *
 * - Queue<Estudiante>: Three queues (one per section) for waitlists.
 *   Chosen because: FIFO — first student to wait is first to get a space.
 *
 * Called by: Main
 */
public class Estacionamiento {

    // -------------------------
    // Attributes / Data Structures
    // -------------------------

    // TODO: declare Set<Espacio> generalDisponibles
    // TODO: declare Set<Espacio> vipDisponibles
    // TODO: declare Set<Espacio> electricosDisponibles

    // TODO: declare HashMap<String, Reservacion> reservacionesActivas
    //       Key = tablilla del auto

    // TODO: declare LinkedList<Transaccion> historialTransacciones

    // TODO: declare Stack<Transaccion> undoStack

    // TODO: declare Queue<Estudiante> generalWaitlist
    // TODO: declare Queue<Estudiante> vipWaitlist
    // TODO: declare Queue<Estudiante> electricosWaitlist

    // -------------------------
    // Constructor
    // -------------------------

    /**
     * Estacionamiento constructor.
     * Initializes all data structures and populates the available spaces sets.
     */
    public Estacionamiento() {
        // TODO: initialize all Sets, HashMap, LinkedList, Stack, Queues
        // TODO: call initEspacios() to populate the three Sets
    }

    // -------------------------
    // Initialization
    // -------------------------

    /**
     * Populates the three Sets with their corresponding Espacio objects.
     * General: 100 spaces, VIP: 50 spaces, Electrico: 50 spaces.
     * Called by: constructor
     */
    private void initEspacios() {
        // TODO: loop and add Espacio objects to each Set
    }

    // -------------------------
    // Core Operations
    // -------------------------

    /**
     * Makes a reservation for a student.
     * Checks availability, calculates cost, updates Set/HashMap/LinkedList/Stack.
     * If section is full, offers waitlist or alternative section.
     *
     * @param estudiante  the student making the reservation
     * @param seccion     "General", "VIP", or "Electrico"
     * @param fecha       the date of the reservation
     * @param horaInicio  start hour (7–17)
     * @param duracion    hours (1–8)
     * @param servicios   list of additional services requested
     * @return the created Reservacion, or null if unsuccessful
     * Called by: Main
     */
    public Reservacion hacerReservacion(Estudiante estudiante, String seccion,
                                        LocalDate fecha, int horaInicio,
                                        int duracion, List<String> servicios) {
        // TODO: validate horaInicio and duracion
        // TODO: check if student already has an active reservation
        // TODO: check availability in the requested section (Set is not empty)
        // TODO: if available: pick a space, build Reservacion, update HashMap, Set, push to Stack, add to LinkedList
        // TODO: if full: prompt waitlist or show other sections
        return null;
    }

    /**
     * Cancels an existing reservation by car tablilla.
     * Charges $10 fee, frees the space, checks waitlist.
     *
     * @param tablilla  the car's license plate
     * @return true if cancelled successfully, false if not found
     * Called by: Main
     */
    public boolean cancelarReservacion(String tablilla) {
        // TODO: look up in HashMap
        // TODO: if not found: print message, return false
        // TODO: remove from HashMap, return space to Set
        // TODO: log Transaccion to LinkedList, push to Stack
        // TODO: check waitlist Queue — if not empty, poll() and call hacerReservacion for them
        return false;
    }

    /**
     * Changes a student's reservation to a different section. Fee: $6.
     *
     * @param tablilla    car tablilla to find the current reservation
     * @param nuevaSeccion  the desired new section
     * @return true if changed successfully
     * Called by: Main
     */
    public boolean cambiarEstacionamiento(String tablilla, String nuevaSeccion) {
        // TODO: find current reservation via HashMap
        // TODO: check availability in nuevaSeccion
        // TODO: free old space, assign new space, update HashMap
        // TODO: recalculate cost difference + $6 fee
        // TODO: log Transaccion, push to Stack
        return false;
    }

    /**
     * Undoes the last modifying action using the Stack.
     * Called by: Main
     */
    public void deshacerUltimaAccion() {
        // TODO: check if Stack is empty — if so, print message and return
        // TODO: pop from Stack
        // TODO: reverse the action based on Transaccion.tipo
        // TODO: log the undo as a new Transaccion in LinkedList
    }

    // -------------------------
    // Waitlist
    // -------------------------

    /**
     * Adds a student to the waitlist for a given section.
     *
     * @param estudiante  student to add
     * @param seccion     section they are waiting for
     * Called by: hacerReservacion (when section is full)
     */
    public void agregarAWaitlist(Estudiante estudiante, String seccion) {
        // TODO: enqueue into the correct Queue based on seccion
    }

    // -------------------------
    // Display Methods
    // -------------------------

    /**
     * (a) Displays all reservations for the current week (Mon–Fri).
     * Called by: Main
     */
    public void mostrarTodasReservacionesSemana() {
        // TODO: iterate historialTransacciones
        // TODO: filter only RESERVAR type within this week
        // TODO: group and print by day
    }

    /**
     * (b) Displays reservations longer than 2 hours on a given day.
     *
     * @param fecha  the day to check
     * Called by: Main
     */
    public void mostrarReservacionesMasDe2Horas(LocalDate fecha) {
        // TODO: filter reservaciones where fecha matches and duracion > 2
        // TODO: print sorted by horaInicio
    }

    /**
     * (c) Displays reservations within a cost range.
     *
     * @param min  minimum cost (inclusive)
     * @param max  maximum cost (inclusive)
     * Called by: Main
     */
    public void mostrarReservacionesPorCosto(double min, double max) {
        // TODO: iterate and filter where costoTotal >= min && <= max
        // TODO: print results or "No reservations found"
    }

    /**
     * (d) Displays reservations within a date range.
     *
     * @param desde  start date (inclusive)
     * @param hasta  end date (inclusive)
     * Called by: Main
     */
    public void mostrarReservacionesPorPeriodo(LocalDate desde, LocalDate hasta) {
        // TODO: filter where fecha is between desde and hasta
        // TODO: print sorted by date then horaInicio
    }

    /**
     * (e) Displays all reservations made by a specific student.
     *
     * @param numeroEstudiante  the student's ID number
     * Called by: Main
     */
    public void mostrarReservacionesPorEstudiante(String numeroEstudiante) {
        // TODO: search LinkedList for reservations matching numeroEstudiante
        // TODO: print all found, or "No reservations found"
    }

    /**
     * (f) Displays all transactions in the system.
     * Called by: Main
     */
    public void mostrarTodasTransacciones() {
        // TODO: iterate and print every Transaccion in historialTransacciones
    }

    // -------------------------
    // Helpers
    // -------------------------

    /**
     * Returns the available Set for a given section.
     *
     * @param seccion  "General", "VIP", or "Electrico"
     * @return the corresponding Set<Espacio>, or null if invalid
     * Called by: hacerReservacion, cambiarEstacionamiento
     */
    private Set<Espacio> getSetPorSeccion(String seccion) {
        // TODO: return the matching Set based on seccion string
        return null;
    }

    /**
     * Returns the waitlist Queue for a given section.
     *
     * @param seccion  "General", "VIP", or "Electrico"
     * @return the corresponding Queue<Estudiante>
     * Called by: agregarAWaitlist, cancelarReservacion
     */
    private Queue<Estudiante> getWaitlistPorSeccion(String seccion) {
        // TODO: return the matching Queue
        return null;
    }

}
