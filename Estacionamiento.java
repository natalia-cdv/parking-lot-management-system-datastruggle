import java.time.LocalDate;
import java.time.LocalDateTime;
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

    private Set<Espacio> generalDisponibles;
    private Set<Espacio> vipDisponibles;
    private Set<Espacio> electricosDisponibles;

    private HashMap<String, Reservacion> reservacionesActivas; // Key: Tablilla
    private LinkedList<Transaccion> historialTransacciones;
    private Stack<Transaccion> undoStack;

    private Queue<Estudiante> generalWaitlist;
    private Queue<Estudiante> vipWaitlist;
    private Queue<Estudiante> electricosWaitlist;

    // -------------------------
    // Constructor
    // -------------------------

    /**
     * Estacionamiento constructor.
     * Initializes all data structures and populates the available spaces sets.
     */
    public Estacionamiento() {
        generalDisponibles = new HashSet<>();
        vipDisponibles = new HashSet<>();
        electricosDisponibles = new HashSet<>();
        
        reservacionesActivas = new HashMap<>();
        historialTransacciones = new LinkedList<>();
        undoStack = new Stack<>();
        
        generalWaitlist = new LinkedList<>();
        vipWaitlist = new LinkedList<>();
        electricosWaitlist = new LinkedList<>();

        initEspacios();
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
       for (int i=0; i < 100; i++){
        generalDisponibles.add(new Espacio(1, i, "General", true));

       }

       for (int i=0; i < 50; i++){
        vipDisponibles.add(new Espacio(1, i, "VIP", true));
       }

       for (int i=0; i < 50; i++){
        electricosDisponibles.add(new Espacio(1, i, "Electrico", true));
       }
       

    }

    public Set<Espacio> getDisponibles(String seccion){
        switch (seccion) {
            case "General":
                return generalDisponibles;

            case "VIP":
                return vipDisponibles;

            case "Electrico":
                return electricosDisponibles;

            default:
                throw new IllegalArgumentException ("Sección invalida: " + seccion); 
        }
    }

    public void marcarOcupado(Espacio e){

        Set<Espacio> set = getDisponibles(e.getSeccion());

        if (set != null){
            set.remove(e);
            e.setDisponible(false );
        }

    }

    public void marcarDisponible (Espacio e){
        Set<Espacio> set = getDisponibles(e.getSeccion());

        if (set != null){
            set.add(e);
            e.setDisponible(true);
        }

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
                                        int duracion, List<String> servicios, Scanner scanner) {
    // Validar si ya tiene reservación activa
        if (reservacionesActivas.containsKey(estudiante.getTablillaAuto())) {
            System.out.println("Error: El vehículo ya tiene una reservación activa.");
            return null;
        }

        //  Obtener el set de la sección correspondiente
        Set<Espacio> disponibles = getSetPorSeccion(seccion);
        
        // Verificar disponibilidad
        if (disponibles != null && !disponibles.isEmpty()) {
            // Tomar el primer espacio disponible (iterador) y removerlo del Set
            Espacio espacioAsignado = disponibles.iterator().next();
            disponibles.remove(espacioAsignado);

            // Crear la reservación (asumiendo que el constructor de Reservacion calcula el costo)
            Reservacion nueva = new Reservacion(estudiante, espacioAsignado, fecha, horaInicio, duracion, 0, servicios, seccion);
            nueva.calcularCosto(); // esto actualiza costoTotal internamente

            // --- ACTUALIZAR ESTRUCTURAS ---
            // Registrar en el HashMap por tablilla
            reservacionesActivas.put(estudiante.getTablillaAuto(), nueva);
            
            // Registrar la transacción en el historial y el Stack para deshacer
            Transaccion t = new Transaccion("RESERVAR", nueva, java.time.LocalDateTime.now(), nueva.getCostoTotal());
            historialTransacciones.add(t);
            undoStack.push(t);

            return nueva;
        } else {
            System.out.println("Sección " + seccion + " llena. ¿Desea entrar a la lista de espera? (s/n)");
            String WLresponse = scanner.nextLine();
            
            if (WLresponse.equalsIgnoreCase("s")){
                agregarAWaitlist(estudiante, seccion);
                System.out.println("Añadido a la lista de espera");

            } else{
                System.out.println("No se añadió a la lista de espera.");

            }
            
            return null;
        }
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

        if (reservacionesActivas.containsKey(tablilla)){
           Reservacion r = reservacionesActivas.get(tablilla);
           marcarDisponible(r.getEspacio());
           reservacionesActivas.remove(tablilla);  

           System.out.println("Cargo por cancelación: $10.");  
           Transaccion t = new Transaccion("CANCELAR", r, java.time.LocalDateTime.now(), 10.0);

           historialTransacciones.add(t);
           undoStack.push(t);



           String s = r.getEspacio().getSeccion();
           Queue<Estudiante> q = getWaitlistPorSeccion(s);

            if (!q.isEmpty()){
            Estudiante next = q.poll();

            System.out.println("Asignando espacio a siguiente en waitlist: " + next.getNumeroEstudiante());

            Reservacion nueva = new Reservacion(next, r.getEspacio(), r.getFecha(), r.getHoraInicio(), r.getDuracion(), 0, r.getServiciosAdicionales(), r.getSeccion());

            reservacionesActivas.put(next.getTablillaAuto(), nueva);

            historialTransacciones.add(new Transaccion("RESERVAR", nueva, java.time.LocalDateTime.now(), 0.0));
            }

        } else{
            System.out.println("Reservación no existe.");
            return false;
        }
        return true;
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
        
        Reservacion r = reservacionesActivas.get(tablilla);
        Set<Espacio> nuevoSet = getSetPorSeccion(nuevaSeccion);

        if (r == null){
            System.out.println("No existe reservación.");
            return false;
        }

        if (nuevoSet.isEmpty()){
            System.out.println("NO hay espacios en la sección: " + nuevaSeccion);
            return false;
        }
        
        Espacio nuevoEspacio = nuevoSet.iterator().next();
        nuevoSet.remove(nuevoEspacio);
        marcarDisponible(r.getEspacio());
        marcarOcupado(nuevoEspacio);

        double costoAnterior = r.getCostoTotal();
        double nuevoCosto = costoAnterior + 6.0;

        System.err.println("Cambio de sección. Cargo adicional: $6");

        Reservacion nueva = new Reservacion(r.getEstudiante(), nuevoEspacio, r.getFecha(), r.getHoraInicio(), r.getDuracion(), 0, r.getServiciosAdicionales(), nuevaSeccion);


        nueva.setSeccion(nuevaSeccion);
        nueva.setCostoTotal(nuevoCosto);

        reservacionesActivas.remove(tablilla);
        reservacionesActivas.put(tablilla, nueva);

        Transaccion t = new Transaccion("CAMBIAR", nueva, java.time.LocalDateTime.now(), 6.0);

        historialTransacciones.add(t);
        undoStack.push(t);

               

        return true;
    }

    /**
     * Undoes the last modifying action using the Stack.
     * Called by: Main
     */
    public void deshacerUltimaAccion() {

        if(undoStack.isEmpty()) {
            System.out.println("No hay acciones que deshacer.");
            return;
        }

        Transaccion ultima = undoStack.pop();
        Reservacion reserva = ultima.getReservacion();

        if(ultima.getTipo().equals("RESERVAR")) {
            reservacionesActivas.remove(reserva.getEstudiante().getTablillaAuto());
        
            Set<Espacio> disponibles = getSetPorSeccion(reserva.getSeccion());
            disponibles.add(reserva.getEspacio());

            System.out.println("Reservación deshecha.");
        }

        else if(ultima.getTipo().equals("CANCELAR")) {
            reservacionesActivas.put(reserva.getEstudiante().getTablillaAuto(), reserva);

            Set<Espacio> disponibles = getSetPorSeccion(reserva.getSeccion());
            disponibles.remove(reserva.getEspacio());
            System.out.println("Cancelación deshecha.");
        }

        else if(ultima.getTipo().equals("CAMBIAR")) {
            String tablilla = reserva.getEstudiante().getTablillaAuto();
            Reservacion reservaAnterior = null;
            for (int i = historialTransacciones.size() - 1; i >= 0; i--) {
                Transaccion t = historialTransacciones.get(i);
                if ((t.getTipo().equals("RESERVAR") || t.getTipo().equals("CAMBIAR"))
                        && t.getReservacion().getEstudiante().getTablillaAuto().equals(tablilla)
                        && t.getReservacion() != reserva) {
                    reservaAnterior = t.getReservacion();
                    break;
                }
            }
            if (reservaAnterior != null) {
                // Liberar el espacio nuevo
                marcarDisponible(reserva.getEspacio());
                // Ocupar el espacio anterior
                marcarOcupado(reservaAnterior.getEspacio());
                // Restaurar la reservacion anterior en el mapa
                reservacionesActivas.put(tablilla, reservaAnterior);
                System.out.println("Cambio de seccion deshecho.");
            } else {
                System.out.println("No se pudo deshacer el cambio: historial insuficiente.");
            }
        }
        historialTransacciones.add(new Transaccion("DESHACER", reserva, LocalDateTime.now(), reserva.getCostoTotal()));

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

        switch (seccion) {
            case "General" :
                generalWaitlist.offer(estudiante);
                break;
            case "VIP" : 
                vipWaitlist.offer(estudiante);
                break;
            case "Electrico" :
                electricosWaitlist.offer(estudiante);
                break;
            default:
                throw new IllegalArgumentException("Sección Invalida: " + seccion);
        }




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
        
            showAllReservationsWeek();
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

            showReservationsOver2Hours(fecha);
    }

    /**
     * (c) Displays reservations within a cost range.
     *
     * @param min  minimum cost (inclusive)
     * @param max  maximum cost (inclusive)
     * Called by: Main
     */
    // Displays all reservations within a specific total cost range.
    public void mostrarReservacionesPorCosto(double min, double max) {

        boolean result = false;

        for(Reservacion reserva : reservacionesActivas.values()) {
           if(reserva.getCostoTotal() >= min && reserva.getCostoTotal() <= max) {
            System.out.println(reserva);
            result = true;
           }
        }
        if(result == false) {
            System.out.println("No hay reservaciones.");
        }
    }

    

    /**
     * (d) Displays reservations within a date range.
     *
     * @param desde  start date (inclusive)
     * @param hasta  end date (inclusive)
     * Called by: Main
     */


    // Displays all reservations made within a specific time period.
    public void mostrarReservacionesPorPeriodo(LocalDate desde, LocalDate hasta) {

        List<Reservacion> resultados = new ArrayList<>();
        for(Reservacion reserva : reservacionesActivas.values()) {
            if(reserva.getFecha().isEqual(desde) || reserva.getFecha().isAfter(desde) && reserva.getFecha().isEqual(hasta) || reserva.getFecha().isEqual(hasta)) {
                resultados.add(reserva);
            }
        }

        for (int i = 0; i < resultados.size() - 1; i++) {
            for (int j = i + 1; j < resultados.size(); j++) {
            Reservacion reserva1 = resultados.get(i);
            Reservacion reserva2 = resultados.get(j);
            
                if (reserva1.getFecha().isAfter(reserva2.getFecha())) {
                    Reservacion temp = resultados.get(i);
                    resultados.set(i, resultados.get(j));
                    resultados.set(j, temp);
                    
                } else if (reserva1.getFecha().isEqual(reserva2.getFecha())) {

                    if (reserva1.getHoraInicio() > reserva2.getHoraInicio()) {
                        Reservacion temp = resultados.get(i);
                        resultados.set(i, resultados.get(j));
                        resultados.set(j, temp);
                    }
                }
            }
        }

       if(resultados.isEmpty()) {
        System.out.println("No hay reservaciones.");
       } else {
        for(Reservacion reserva : resultados) {
            System.out.println(reserva);
        }
       }
     
    }

    /**
     * Displays all reservations made by a specific student.
     *
     * @param numeroEstudiante  the student's ID number
     * Called by: Main
     */
    public void mostrarReservacionesPorEstudiante(String numeroEstudiante) {
        // Imprime encabezado con el número de estudiante
        System.out.println("--- Historial del Estudiante: " + numeroEstudiante + " ---");
        boolean encontro = false;

        // Itera sobre el historial de transacciones para encontrar las reservaciones del estudiante
        for (Transaccion t : historialTransacciones) {
            Reservacion r = t.getReservacion();

            // Verifica si la transacción es de tipo RESERVAR o CAMBIAR y si el número de estudiante coincide
            if (r.getEstudiante().getNumeroEstudiante().equals(numeroEstudiante)) {
                // Imprime el tipo de transacción, fecha y espacio asignado
                System.out.println(t.getTipo() + " | Fecha: " + r.getFecha() + " | Espacio: " + r.getEspacio().toString());
                encontro = true;
            }
        }
        
        // Si no se encontraron reservaciones para el estudiante, imprime un mensaje indicando que no hay registros
        if (!encontro) System.out.println("No se encontraron registros.");
    }
    

    /**
     * Displays all transactions in the system.
     * Called by: Main
     */
    public void mostrarTodasTransacciones() {
        if (historialTransacciones.isEmpty()) {
            // Imprime mensaje si no hay transacciones registradas
            System.out.println("No hay transacciones registradas.");
            return;
        }
        
        System.out.println("======= REPORTE GLOBAL DE TRANSACCIONES =======");

        // Itera sobre el historial de transacciones e imprime cada una utilizando su método toString()
        for (Transaccion t : historialTransacciones) {
            System.out.println(t.toString());
        }
    
}

    public void mostrarWaitlist(String seccion){
        Queue<Estudiante> waitlist = getWaitlistPorSeccion(seccion);

        if (waitlist.isEmpty()){
            System.out.println("No hay estudiantes en espera.");
        }

        for (Estudiante e : waitlist){
            System.out.println(e);
        }
        
        
    }

    public void showAllReservationsWeek() {

    if (reservacionesActivas.isEmpty()) {
        System.out.println("No hay reservaciones.");
        return;
    }

    System.out.println("=== RESERVACIONES DE LA SEMANA ===");

    for (Reservacion r : reservacionesActivas.values()) {

        LocalDate fecha = r.getFecha();

       
        if (fecha.getDayOfWeek().getValue() >= 1 &&
            fecha.getDayOfWeek().getValue() <= 5) {

            System.out.println(
                fecha.getDayOfWeek() + " | " +
                r.getHoraInicio() + " | " +
                r.getEspacio()
            );
        }
    }
}

public void showReservationsOver2Hours(LocalDate date) {

    List<Reservacion> lista = new ArrayList<>();

    for (Reservacion r : reservacionesActivas.values()) {

        if (r.getFecha().equals(date) && r.getDuracion() > 2) {
            lista.add(r);
        }
    }

    if (lista.isEmpty()) {
        System.out.println("No hay reservaciones de más de 2 horas.");
        return;
    }

   
    lista.sort(Comparator.comparingInt(Reservacion::getHoraInicio));

    System.out.println("=== RESERVACIONES > 2 HORAS ===");

    for (Reservacion r : lista) {
        System.out.println(
            "Hora: " + r.getHoraInicio() +
            " | Duración: " + r.getDuracion() +
            " | Espacio: " + r.getEspacio()
        );
    }
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
        return getDisponibles(seccion);
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
        
        switch (seccion) {
            case "General" :
               return generalWaitlist;
                
            case "VIP" : 
                return vipWaitlist;
                
            case "Electrico" :
                return electricosWaitlist;
            default:
                throw new IllegalArgumentException("Sección Invalida: " + seccion);
        
        }
    }

    public LinkedList<Transaccion> getAllTransacciones() {
        return historialTransacciones;
    }
}
