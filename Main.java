import java.util.*;
import java.time.LocalDate;

/**
 * Main.java
 * Entry point. Runs the operator console menu in a loop.
 *
 * Data structure used: N/A (uses Estacionamiento which holds all structures)
 * Calls: Estacionamiento (all operations)
 * Called by: JVM (main method)
 */
public class Main {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        Estacionamiento estacionamiento = new Estacionamiento();
        boolean salir = false;

        while (!salir) {
            printDivider();
            System.out.println("--- MENU DE OPERADOR - DATASTRUGGLE ---");
            System.out.println("1. Hacer reservacion");
            System.out.println("2. Cancelar reservacion");
            System.out.println("3. Cambiar seccion");
            System.out.println("4. Undo (Deshacer ultima accion)");
            System.out.println("5. Opciones de Visualizacion");
            System.out.println("6. Salir");
            System.out.print("Seleccione una opcion: ");

            String opcion = scanner.nextLine();

            switch (opcion) {
                case "1":
                    // 1. Recopilar datos del estudiante y auto
                    Estudiante estudiante = recopilarInfoEstudiante(scanner);

                    // 2. Pedir detalles de la reservacion
                    printDivider();
                    System.out.println(">>> DETALLES DE LA RESERVACION");
                    System.out.print("Seccion (General / VIP / Electrico): ");
                    String seccion = scanner.nextLine();

                    System.out.println("Fecha de reservacion:");
                    System.out.print("  Anio (YYYY): ");
                    int rAnio = Integer.parseInt(scanner.nextLine());
                    System.out.print("  Mes (1-12): ");
                    int rMes = Integer.parseInt(scanner.nextLine());
                    System.out.print("  Dia (1-31): ");
                    int rDia = Integer.parseInt(scanner.nextLine());
                    LocalDate fecha = LocalDate.of(rAnio, rMes, rDia);

                    System.out.print("Hora de inicio (7-17): ");
                    int horaInicio = Integer.parseInt(scanner.nextLine());

                    System.out.print("Duracion en horas (1-8): ");
                    int duracion = Integer.parseInt(scanner.nextLine());

                    // Validaciones
                    if (horaInicio < 7 || horaInicio > 17) {
                        System.out.println("Error: Hora invalida. Debe ser entre 7 y 17.");
                        break;
                    }
                    if (duracion < 1 || duracion > 8) {
                        System.out.println("Error: Duracion invalida. Debe ser entre 1 y 8 horas.");
                        break;
                    }

                    // 3. Pedir add-ons segun la seccion
                    List<String> servicios = new ArrayList<>();

                    if (seccion.equalsIgnoreCase("General")) {
                        System.out.print("Chequeo de aire de gomas? ($1) (s/n): ");
                        if (scanner.nextLine().equalsIgnoreCase("s")) servicios.add("aire");
                        System.out.print("Chequeo de fluidos? ($2) (s/n): ");
                        if (scanner.nextLine().equalsIgnoreCase("s")) servicios.add("fluidos");

                    } else if (seccion.equalsIgnoreCase("VIP")) {
                        System.out.print("Lavado exterior? ($50) (s/n): ");
                        if (scanner.nextLine().equalsIgnoreCase("s")) servicios.add("lavado");
                        System.out.print("Chequeo de aire de gomas? ($1) (s/n): ");
                        if (scanner.nextLine().equalsIgnoreCase("s")) servicios.add("aire");
                        System.out.print("Chequeo de fluidos? ($2) (s/n): ");
                        if (scanner.nextLine().equalsIgnoreCase("s")) servicios.add("fluidos");
                        System.out.print("Chequeo de frenos y aceite? ($5) (s/n): ");
                        if (scanner.nextLine().equalsIgnoreCase("s")) servicios.add("frenos");

                    } else if (seccion.equalsIgnoreCase("Electrico")) {
                        System.out.print("Lavado exterior? ($50) (s/n): ");
                        if (scanner.nextLine().equalsIgnoreCase("s")) servicios.add("lavado");
                        System.out.print("Chequeo de aire de gomas? ($1) (s/n): ");
                        if (scanner.nextLine().equalsIgnoreCase("s")) servicios.add("aire");
                    }

                    // 4. Procesar reservacion
                    Reservacion resultado = estacionamiento.hacerReservacion(
                        estudiante, seccion, fecha, horaInicio, duracion, servicios
                    );

                    printDivider();
                    if (resultado != null) {
                        System.out.println("RESERVACION EXITOSA:");
                        System.out.println(resultado);
                    } else {
                        System.out.println("No se pudo completar la reservacion.");
                    }
                    break;

                case "2":
                    // TODO: JEZER
                    break;

                case "3":
                    // TODO: JEZER
                    break;

                case "4":
                    // TODO: AIDHAN
                    break;

                case "5":
                    menuDisplay(estacionamiento, scanner);
                    break;

                case "6":
                    salir = true;
                    System.out.println("Saliendo del sistema...");
                    break;

                default:
                    System.out.println("Opcion no valida. Intente de nuevo.");
            }
        }
        scanner.close();
    }

    /**
     * Shows and handles the display sub-menu (options a through f).
     *
     * @param estacionamiento  the parking system instance
     * @param scanner          shared Scanner for input
     * Called by: main loop when operator selects option 5
     */
    private static void menuDisplay(Estacionamiento estacionamiento, Scanner scanner) {
        printDivider();
        System.out.println("--- OPCIONES DE VISUALIZACION ---");
        System.out.println("a. Todas las reservaciones de la semana");
        System.out.println("b. Reservaciones de mas de 2 horas en un dia");
        System.out.println("c. Reservaciones por costo");
        System.out.println("d. Reservaciones por periodo de tiempo");
        System.out.println("e. Reservaciones de un estudiante");
        System.out.println("f. Todas las transacciones");
        System.out.print("Seleccione una opcion: ");
        String opcion = scanner.nextLine();

        switch (opcion) {
            case "a":
                // TODO: JEZER
                estacionamiento.mostrarTodasReservacionesSemana();
                break;
            case "b":
                // TODO: JEZER
                System.out.print("Dia (1-31): ");
                int dia = Integer.parseInt(scanner.nextLine());
                System.out.print("Mes (1-12): ");
                int mes = Integer.parseInt(scanner.nextLine());
                System.out.print("Anio (YYYY): ");
                int anio = Integer.parseInt(scanner.nextLine());
                estacionamiento.mostrarReservacionesMasDe2Horas(LocalDate.of(anio, mes, dia));
                break;
            case "c":
                // TODO: AIDHAN
                System.out.print("Costo minimo: $");
                double min = Double.parseDouble(scanner.nextLine());
                System.out.print("Costo maximo: $");
                double max = Double.parseDouble(scanner.nextLine());
                estacionamiento.mostrarReservacionesPorCosto(min, max);
                break;
            case "d":
                // TODO: AIDHAN
                System.out.print("Fecha inicio - Dia: ");
                int d1 = Integer.parseInt(scanner.nextLine());
                System.out.print("Fecha inicio - Mes: ");
                int m1 = Integer.parseInt(scanner.nextLine());
                System.out.print("Fecha inicio - Anio: ");
                int a1 = Integer.parseInt(scanner.nextLine());
                System.out.print("Fecha fin - Dia: ");
                int d2 = Integer.parseInt(scanner.nextLine());
                System.out.print("Fecha fin - Mes: ");
                int m2 = Integer.parseInt(scanner.nextLine());
                System.out.print("Fecha fin - Anio: ");
                int a2 = Integer.parseInt(scanner.nextLine());
                estacionamiento.mostrarReservacionesPorPeriodo(LocalDate.of(a1, m1, d1), LocalDate.of(a2, m2, d2));
                break;
            case "e":
                System.out.print("Numero de estudiante: ");
                String numEst = scanner.nextLine();
                estacionamiento.mostrarReservacionesPorEstudiante(numEst);
                break;
            case "f":
                estacionamiento.mostrarTodasTransacciones();
                break;
            default:
                System.out.println("Opcion no valida.");
        }
    }

    /**
     * Collects student and car info from the operator via console.
     *
     * @param scanner  shared Scanner for input
     * @return a fully populated Estudiante object (with Auto linked)
     * Called by: main loop when making a reservation
     */
    private static Estudiante recopilarInfoEstudiante(Scanner scanner) {
        printDivider();
        System.out.println(">>> INFORMACION DEL ESTUDIANTE");
        System.out.print("Nombre completo: ");
        String nombre = scanner.nextLine();
        System.out.print("Numero de Estudiante: ");
        String id = scanner.nextLine();
        System.out.print("Email: ");
        String email = scanner.nextLine();
        System.out.print("Telefono: ");
        String tel = scanner.nextLine();

        printDivider();
        System.out.println(">>> INFORMACION DEL AUTO");
        System.out.print("Tablilla: ");
        String tablilla = scanner.nextLine();
        System.out.print("Marca: ");
        String marca = scanner.nextLine();
        System.out.print("Modelo: ");
        String modelo = scanner.nextLine();
        System.out.print("Anio: ");
        int anio = Integer.parseInt(scanner.nextLine());

        Auto nuevoAuto = new Auto(tablilla, marca, modelo, anio);
        return new Estudiante(nombre, id, email, tel, nuevoAuto);
    }

    /**
     * Prints a divider line for cleaner console output.
     * Called by: various menu sections
     */
    private static void printDivider() {
        System.out.println("==============================================");
    }
}