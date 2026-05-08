import java.time.LocalDate;
import java.util.*;

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
                    
                    String seccion = "";
                    while (true) {
                        System.out.print("Seccion (General / VIP / Electrico): ");
                        seccion = scanner.nextLine().trim();
                        if (seccion.equalsIgnoreCase("General") || seccion.equalsIgnoreCase("VIP") || seccion.equalsIgnoreCase("Electrico")) break;
                        System.out.println("Error: Sección inválida. Escriba: General, VIP o Electrico.");
                    }

                    System.out.println("Fecha de reservacion:");
                    int rAnio = leerEntero(scanner, "  Año (2026-2027): ", 2026, 2027);
                    int rMes = leerEntero(scanner, "  Mes (1-12): ", 1, 12);
                    int rDia = leerEntero(scanner, "  Dia (1-31): ", 1, 31);
                    LocalDate fecha = null;
                    while (fecha == null) {
                        try {
                            fecha = LocalDate.of(rAnio, rMes, rDia);
                        } catch (Exception e) {
                            System.out.println("Fecha invalida (ese mes no tiene ese dia). Intente de nuevo.");
                            rMes = leerEntero(scanner, "  Mes (1-12): ", 1, 12);
                            rDia = leerEntero(scanner, "  Dia (1-31): ", 1, 31);
                        }
                    }

                    int horaInicio = leerEntero(scanner, "Hora de inicio (7-17): ", 7, 17);
                    int duracion = leerEntero(scanner, "Duracion en horas (1-8): ", 1, 8);


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
                        estudiante, seccion, fecha, horaInicio, duracion, servicios, scanner
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
                    printDivider();
                    System.out.println(">>> CANCELAR RESERVACION");
                    System.out.print("Tablilla del vehiculo: ");
                    String tablillaCancel = scanner.nextLine();
                    boolean cancelado = estacionamiento.cancelarReservacion(tablillaCancel);
                    if (cancelado) {
                        System.out.println("Reservacion cancelada exitosamente.");
                    } else {
                        System.out.println("No se encontro reservacion para esa tablilla.");
                    }
                    break;

                case "3":
                    printDivider();
                    System.out.println(">>> CAMBIAR SECCION");
                    System.out.print("Tablilla del vehiculo: ");
                    String tablillaCambio = scanner.nextLine();
                    System.out.print("Nueva seccion (General / VIP / Electrico): ");
                    String nuevaSeccion = scanner.nextLine();
                    boolean cambiado = estacionamiento.cambiarEstacionamiento(tablillaCambio, nuevaSeccion);
                    if (cambiado) {
                        System.out.println("Seccion cambiada exitosamente.");
                    } else {
                        System.out.println("No se pudo cambiar la seccion.");
                    }
                    break;

                case "4":
                    estacionamiento.deshacerUltimaAccion();
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
        System.out.println("0. Volver al menu principal");
        System.out.print("Seleccione una opcion: ");
        String opcion = scanner.nextLine();

        switch (opcion) {
            case "a":
                estacionamiento.mostrarTodasReservacionesSemana();
                break;
            case "b":
                int dia  = leerEntero(scanner, "Dia (1-31): " , 1, 31);
                int mes = leerEntero(scanner, "Mes (1-12): ", 1, 12);
                int anio = leerEntero(scanner, "Año (YYYY): ", 2026, 2999);
                LocalDate fechaConsulta = null;
                while (fechaConsulta == null) {
                    try {
                        fechaConsulta = LocalDate.of(anio, mes, dia);
                    } catch (Exception e) {
                        System.out.println("Fecha invalida. Intente de nuevo.");
                        mes = leerEntero(scanner, "Mes (1-12): ", 1, 12);
                        dia = leerEntero(scanner, "Dia (1-31): ", 1, 31);
                    }
                }
                estacionamiento.mostrarReservacionesMasDe2Horas(fechaConsulta);
                break;
            case "c":
                double min = leerDouble(scanner, "Costo minimo: $", 0, Double.MAX_VALUE);
                double max = leerDouble(scanner, "Costo maximo: $", min, Double.MAX_VALUE);
                estacionamiento.mostrarReservacionesPorCosto(min, max);
                break;
            case "d":
                int d1 = leerEntero(scanner, "Fecha inicio - Dia: ", 1, 31);
                int m1 = leerEntero(scanner, "Fecha inicio - Mes: ", 1, 12);
                int a1 = leerEntero(scanner, "Fecha inicio - Año: ", 2026, 2999);
                int d2 = leerEntero(scanner, "Fecha fin - Dia: ", 1, 31);
                int m2 = leerEntero(scanner, "Fecha fin - Mes: ", 1, 12);
                int a2 = leerEntero(scanner, "Fecha fin - Año: ", 2026, 2999);
                LocalDate fechaInicio = null;
                while (fechaInicio == null) {
                    try {
                        fechaInicio = LocalDate.of(a1, m1, d1);
                    } catch (Exception e) {
                        System.out.println("Fecha inicio invalida. Intente de nuevo.");
                        m1 = leerEntero(scanner, "Fecha inicio - Mes: ", 1, 12);
                        d1 = leerEntero(scanner, "Fecha inicio - Dia: ", 1, 31);
                    }
                }
                LocalDate fechaFin = null;
                while (fechaFin == null) {
                    try {
                        fechaFin = LocalDate.of(a2, m2, d2);
                    } catch (Exception e) {
                        System.out.println("Fecha fin invalida. Intente de nuevo.");
                        m2 = leerEntero(scanner, "Fecha fin - Mes: ", 1, 12);
                        d2 = leerEntero(scanner, "Fecha fin - Dia: ", 1, 31);
                    }
                }
                estacionamiento.mostrarReservacionesPorPeriodo(fechaInicio, fechaFin);
                break;
            case "e":
                System.out.print("Numero de estudiante: ");
                String numEst = scanner.nextLine();
                estacionamiento.mostrarReservacionesPorEstudiante(numEst);
                break;
            case "f":
                estacionamiento.mostrarTodasTransacciones();
                break;
            case "0":
                System.out.println("Volviendo al menu principal...");
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
        
        String nombre;
        while (true) {
            System.out.print("Nombre completo: ");
            nombre = scanner.nextLine().trim();
            if (nombre.length() >= 3 && nombre.matches("^[a-zA-Z\\s]+$")) break;
            System.out.println("Error: Nombre inválido (mínimo 3 letras, sin números).");
        }

        System.out.print("Numero de Estudiante: ");
        String id = scanner.nextLine().trim();

        String email;
        while (true) {
            System.out.print("Email: ");
            email = scanner.nextLine().trim();
            if (email.contains("@") && email.contains(".")) break;
            System.out.println("Error: Formato de email inválido (ejemplo@upr.edu).");
        }

        String tel;
        while (true) {
            System.out.print("Telefono: ");
            tel = scanner.nextLine().trim();
            // Cuenta los dígitos para asegurar que haya al menos 10
            if (tel.replaceAll("\\D", "").length() >= 10) break;
            System.out.println("Error: El teléfono debe tener al menos 10 dígitos numéricos.");
        }

        printDivider();
        System.out.println(">>> INFORMACION DEL AUTO");

        String tablilla;
        while (true) {
            System.out.print("Tablilla: ");
            tablilla = scanner.nextLine().trim().toUpperCase();
            if (tablilla.matches("^[A-Z]{3}.*")) break;
            System.out.println("Error: La tablilla debe comenzar con al menos 3 letras (ej. ABC-1234 o ABC).");
        }

        String marca;
            while (true) {
                System.out.print("Marca: ");
                marca = scanner.nextLine().trim();
                if (marca.length() >= 3 && marca.matches("^[a-zA-Z\\s]+$")) break;
                System.out.println("Error: La marca debe ser solo letras (min. 3).");
            }

            // 5. MODELO: Solo letras, min 3
            String modelo;
            while (true) {
                System.out.print("Modelo: ");
                modelo = scanner.nextLine().trim();
                if (modelo.length() >= 3 && modelo.matches("^[a-zA-Z0-9\\s]+$")) break;
                System.out.println("Error: El modelo debe tener al menos 3 letras o números.");
            }
        int anio = leerEntero(scanner, "Año (YYYY): ", 1886, 2026);

        Auto nuevoAuto = new Auto(tablilla, marca, modelo, anio);
        return new Estudiante(nombre, id, email, tel, nuevoAuto);
    }


    private static int leerEntero(Scanner scanner, String prompt, int min, int max) {
        while (true) {
            try {
                System.out.print(prompt);
                int valor = Integer.parseInt(scanner.nextLine().trim());
                if (valor >= min && valor <= max) {
                    return valor;
                }
                System.out.println("Error: El valor debe estar entre " + min + " y " + max + ".");
            } catch (NumberFormatException e) {
                System.out.println("Entrada invalida. Por favor ingrese un numero entero.");
            }
        }
    }

    private static double leerDouble(Scanner scanner, String prompt, double min, double max) {
        while (true) {
            try {
                System.out.print(prompt);
                double valor = Double.parseDouble(scanner.nextLine().trim());
                if (valor >= min && valor <= max) {
                    return valor;
                }
                System.out.println("Error: El valor debe estar entre " + min + " y " + max + ".");
            } catch (NumberFormatException e) {
                System.out.println("Entrada invalida. Por favor ingrese un numero.");
            }
        }
    }

    /**
     * Prints a divider line for cleaner console output.
     * Called by: various menu sections
     */
    private static void printDivider() {
        System.out.println("==============================================");
    }
}