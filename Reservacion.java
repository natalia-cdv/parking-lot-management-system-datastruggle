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
// Class: Reservacion
public class Reservacion {
    private Estudiante estudiante;
    private Espacio espacio;
    private LocalDate fecha;
    private int horaInicio; // 7 to 17
    private int duracion; // 1 to 8
    private double costoTotal;
    private List<String> serviciosAdicionales;
    private String seccion; // General, VIP, Electrico

// Constructor
    public Reservacion(Estudiante estudiante, Espacio espacio, LocalDate fecha, int horaInicio, int duracion, double costoTotal, List<String> serviciosAdicionales, String seccion) {
        this.estudiante = estudiante;
        this.espacio = espacio;
        this.fecha = fecha;
        this.horaInicio = horaInicio;
        this.duracion = duracion;
        this.costoTotal = costoTotal;
        this.serviciosAdicionales = serviciosAdicionales;
        this.seccion = seccion;
    }

    // Getters 
    public Estudiante getEstudiante() {
        return estudiante;
    }
    public Espacio getEspacio() {
        return espacio;
    }
    public LocalDate getFecha() {
        return fecha;
    }
    public int getHoraInicio() {
        return horaInicio;
    }
    public int getDuracion() {
        return duracion;
    }
    public double getCostoTotal() {
        return costoTotal;
    }
    public List<String> getServiciosAdicionales() {
        return serviciosAdicionales;
    }
    public String getSeccion() {
        return seccion;
    }

    // Setters
    public void setEstudiante(Estudiante estudiante) {
        this.estudiante = estudiante;
    }
    public void setEspacio(Espacio espacio) {
        this.espacio = espacio;
    }
    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }
    public void setHoraInicio(int horaInicio) {
        this.horaInicio = horaInicio;
    }
    public void setDuracion(int duracion) {
        this.duracion = duracion;
    }
    public void setCostoTotal(double costoTotal) {
        this.costoTotal = costoTotal;
    }
    public void setServiciosAdicionales(List<String> serviciosAdicionales) {
        this.serviciosAdicionales = serviciosAdicionales;
    }
    public void setSeccion(String seccion) {
        this.seccion = seccion;
    }

    public double calcularCosto() {

        double costoHora = 0;
        double costoServicios = 0;

        if(seccion.toLowerCase().equals("general")) {
            costoHora = 2.00;

            if(serviciosAdicionales != null) {
                for(String servicio : serviciosAdicionales) {
                    if(servicio.toLowerCase().equals("aire")) {
                        costoServicios += 1;
                    }
                     if(servicio.toLowerCase().equals("fluidos")) {
                        costoServicios += 2;
                    }
                }
            }
    
        } else if(seccion.toLowerCase().equals("vip")) {
            costoHora = 4.00;

            if(serviciosAdicionales != null) {
                for(String servicio : serviciosAdicionales) {
                    if(servicio.toLowerCase().equals("lavado")) {
                        costoServicios += 50;
                    }
                    if(servicio.toLowerCase().equals("aire")) {
                        costoServicios += 1;
                    }
                     if(servicio.toLowerCase().equals("fluidos")) {
                        costoServicios += 2;
                    }
                    if(servicio.toLowerCase().equals("liquidos")) {
                        costoServicios += 10;
                    }
                }
            }

        } else if(seccion.toLowerCase().equals("electrico")) {
            costoHora = 8.00;

             if(serviciosAdicionales != null) {
                for(String servicio : serviciosAdicionales) {
                    if(servicio.toLowerCase().equals("aire")) {
                        costoServicios += 1;
                    }
                     if(servicio.toLowerCase().equals("lavado")) {
                        costoServicios += 50;
                    }
                }
            }
        }
        costoTotal = (costoHora*duracion) + costoServicios;
        return costoTotal;
    }
        @Override
    public String toString() {
        return "Reservacion{" +
                "estudiante=" + estudiante +
                ", espacio=" + espacio +
                ", fecha=" + fecha +
                ", horaInicio=" + horaInicio +
                ", duracion=" + duracion +
                ", costoTotal=" + costoTotal +
                ", serviciosAdicionales=" + serviciosAdicionales +
                ", seccion='" + seccion + '\'' +
                '}';
    }

  
   
}
