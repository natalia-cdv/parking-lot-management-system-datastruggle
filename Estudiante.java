/**
 * Estudiante.java
 * Represents a university student making a parking reservation.
 * 
 * Data structure used: N/A (plain model class)
 * Called by: Estacionamiento, Reservacion, Queue (waitlist), Main
 */
public class Estudiante {

    // Attributes
    private String nombre;
    private String numeroEstudiante;
    private String email;
    private String telefono;
    private Auto auto;

    // Constructor with all fields
    public Estudiante(String nombre, String numeroEstudiante, String email, String telefono, Auto auto) {
        this.nombre = nombre;
        this.numeroEstudiante = numeroEstudiante;
        this.email = email;
        this.telefono = telefono;
        this.auto = auto;
    }

    // Getters and setters for each attribute
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getNumeroEstudiante() {
        return numeroEstudiante;
    }

    public void setNumeroEstudiante(String numeroEstudiante) {
        this.numeroEstudiante = numeroEstudiante;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public Auto getAuto() {
        return auto;
    }

    public void setAuto(Auto auto) {
        this.auto = auto;
    }

    public String getTablillaAuto() {
    return auto.getTablilla();
    }

    // toString() — readable summary for operator display
    @Override
    public String toString() {
        return "Estudiante{" +
                "nombre='" + nombre + '\'' +
                ", numeroEstudiante='" + numeroEstudiante + '\'' +
                ", email='" + email + '\'' +
                ", telefono='" + telefono + '\'' +
                ", auto=" + auto +
                '}';
    }

}
