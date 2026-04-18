import java.util.Scanner;

/**
 * Main.java
 * Entry point. Runs the operator console menu in a loop.
 * The operator interacts with this menu to manage all reservations.
 *
 * Data structure used: N/A (uses Estacionamiento which holds all structures)
 * Calls: Estacionamiento (all operations)
 * Called by: JVM (main method)
 */
public class Main {

    public static void main(String[] args) {

        // TODO: create Scanner for input
        // TODO: create Estacionamiento instance

        // TODO: loop — show menu, read operator choice, call correct method

        // Menu options:
        // 1. Make reservation
        // 2. Cancel reservation
        // 3. Change section
        // 4. Undo last action
        // 5. Display options (show sub-menu)
        // 6. Exit

    }

    /**
     * Shows and handles the display sub-menu (options a through f).
     *
     * @param estacionamiento  the parking system instance
     * @param scanner          shared Scanner for input
     * Called by: main loop when operator selects option 5
     */
    private static void menuDisplay(Estacionamiento estacionamiento, Scanner scanner) {
        // TODO: show sub-menu:
        // a. All reservations this week
        // b. Reservations > 2 hours on a given day
        // c. Reservations by cost range ($50, $100, or custom)
        // d. Reservations by date range
        // e. Reservations by student
        // f. All transactions

        // TODO: read choice and call the corresponding Estacionamiento method
    }

    /**
     * Collects student and car info from the operator via console.
     *
     * @param scanner  shared Scanner for input
     * @return a fully populated Estudiante object (with Auto linked)
     * Called by: main loop when making a reservation
     */
    private static Estudiante recopilarInfoEstudiante(Scanner scanner) {
        // TODO: prompt and read: nombre, numeroEstudiante, email, telefono
        // TODO: prompt and read car: tablilla, marca, modelo, anio
        // TODO: create Auto, create Estudiante with Auto, return it
        return null;
    }

    /**
     * Prints a divider line for cleaner console output.
     * Called by: various menu sections
     */
    private static void printDivider() {
        // TODO: print something like "=============================="
    }

}
