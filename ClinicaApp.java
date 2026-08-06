import java.util.ArrayList;
import java.util.Objects;
import java.util.Scanner;

/**
 * Sistema de Gestión de Turnos - Clínica San Rafael
 *
 * Cada turno se representa como un arreglo de String de 5 posiciones:
 * turno = [idTurno, nombrePaciente, especialidad, duracionMinutos, valorMinuto]
 *
 * Todos los turnos se almacenan en: ArrayList<String[]> turnos
 *
 * IMPORTANTE: como todo se guarda como texto, los datos numéricos deben
 * convertirse con Integer.parseInt(...) o Double.parseDouble(...) al usarlos.
 */
public class ClinicaApp {

    // ====== Índices de cada campo (usarlos SIEMPRE en lugar de 0,1,2...) ======
    static final int ID = 0;
    static final int PACIENTE = 1;
    static final int ESPECIALIDAD = 2;
    static final int DURACION = 3;
    static final int VALOR_MINUTO = 4;
    static final int CAMPOS = 5;

    static ArrayList<String[]> turnos = new ArrayList<>();
    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        int opcion;
        do {
            mostrarMenu();
            opcion = leerEntero("Seleccione una opción: ");

            switch (opcion) {
                case 1:
        registrarTurno();
        break;
    case 2:
        mostrarTurnos();
        break;
    case 3:
        buscarTurnoPorId();
        break;
    case 4:
        actualizarTurno();
        break;
    case 5:
        cancelarTurno();
        break;
    case 6:
        calcularTotalFacturado();
        break;
    case 7:
        reportePorEspecialidad();
        break;
    case 8:
        System.out.println("Cerrando el sistema. Hasta pronto.");
        break;
    default:
        System.out.println("Opción inválida. Intente de nuevo.");
        break;
            }
            System.out.println();
        } while (opcion != 8);

        sc.close();
    }

    static void mostrarMenu() {
        System.out.println("=== Clínica San Rafael: Gestión de Turnos ===");
        System.out.println("1. Registrar nuevo turno");
        System.out.println("2. Mostrar todos los turnos");
        System.out.println("3. Buscar turno por ID");
        System.out.println("4. Actualizar un turno");
        System.out.println("5. Cancelar un turno");
        System.out.println("6. Calcular total facturado");
        System.out.println("7. Reporte por especialidad");
        System.out.println("8. Salir");
    }

    // ================= ROL A: feature/menu-base =================
    // Responsable de: mostrarMenu (ya dado), registrarTurno, mostrarTurnos

    static void registrarTurno() {
        // TODO (Rol A)
        // 1. Pedir id, paciente, especialidad, duración y valor por minuto.
        // 2. Validar que el ID no exista ya (usar buscarIndicePorId).
        // 3. Crear el arreglo: String[] turno = new String[CAMPOS];
        // 4. Agregarlo a la lista con turnos.add(turno);
        String idTurno= leerTexto("Digite su identificador: ");
        Integer idExiste= buscarIndicePorId();
        if (idExiste != -1){
            System.out.println("El ID ya existe");
            return;
        }
        String nombrePaciente= leerTexto("Escriba el nombre del paciente: ");
        String especialidad= leerTexto("Escriba la especialidad: ");
        String duracionMinutos= leerTexto("Digite la duración: ");
        String valorMinuto= leerTexto("Digite el valor por minuto: ");
        String [] turno = new String[CAMPOS];
        turno[ID] = (idTurno);
        turno[PACIENTE] = (nombrePaciente);
        turno[ESPECIALIDAD] = (especialidad);
        turno[DURACION] = (duracionMinutos);
        turno[VALOR_MINUTO] = (valorMinuto);
        turnos.add(turno);
        System.out.println("Turno registrado correctamente.");
    }

    static void mostrarTurnos() {
        // TODO (Rol A)
        // Si la lista está vacía, avisar al usuario.
        // Recorrer la lista e imprimir cada turno en formato tabular y legible.
        // Sugerencia: System.out.printf("%-6s %-20s %-15s %8s %12s%n", ...);
        if (turnos.isEmpty()){
            System.out.println("La lista está vacía.");
        } else {
            System.out.printf("%-6s %-20s %-15s %-10s %-12s%n","ID", "Paciente", "Especialidad", "Duración", "Valor");
            for (String [] turno: turnos){
                System.out.printf("%-6s %-20s %-15s %-10s %-12s%n", turno[ID],turno[PACIENTE],turno[ESPECIALIDAD],turno[DURACION],turno[VALOR_MINUTO]);
            }
        }
    }

    // ================= ROL B: feature/crud-turnos =================
    // Responsable de: buscarTurnoPorId, actualizarTurno, cancelarTurno, buscarIndicePorId

    static int buscarIndicePorId() {
        // Recorrer la lista y devolver la POSICIÓN del turno cuyo ID coincida.
        // Si no existe, devolver -1. Este método lo reutilizan los demás roles.
        String id = leerTexto("Ingresa un ID: ");

        for (int i = 0; i < turnos.size(); i++){
            if (Objects.equals(id, turnos.get(i)[0])){
                return i;
            }
        }
        return -1;
    }

    static void buscarTurnoPorId() {
        // Pedir el ID, usar buscarIndicePorId y mostrar los datos o un mensaje de "no existe".
        int indiceID = buscarIndicePorId();
        if (indiceID == -1){
            System.out.println("el turno con ese ID no existe");
        }
        else {
            System.out.println(turnos.get(indiceID));
        }

    }

    static void actualizarTurno() {
        // Pedir el ID, verificar que exista y mostrar un submenú para elegir
        // qué campo modificar: paciente, especialidad, duración o valor por minuto.
        int indice = buscarIndicePorId();

        if (indice == -1) {
            System.out.println("El turno con ese ID no existe");
            return;
        }

        String[] turno = turnos.get(indice);

        System.out.println("\nDatos actuales del turno");
        System.out.println("ID: " + turno[ID]);
        System.out.println("Paciente: " + turno[PACIENTE]);
        System.out.println("Especialidad: " + turno[ESPECIALIDAD]);
        System.out.println("Duracion: " + turno[DURACION] + " minutos");
        System.out.println("Valor por minuto: $" + turno[VALOR_MINUTO]);

        int opcion;

        do {
            System.out.println("\nQue campo desea modificar?");
            System.out.println("1. Paciente");
            System.out.println("2. Especialidad");
            System.out.println("3. Duración");
            System.out.println("4. Valor por minuto");

            opcion = leerEntero("Ingrese una opción: ");

            switch (opcion) {
                case 1:
        turno[PACIENTE] = leerTexto("Ingrese el nuevo paciente: ");
        System.out.println("Paciente actualizado correctamente.");
        break;
    case 2:
        turno[ESPECIALIDAD] = leerTexto("Ingrese la nueva especialidad: ");
        System.out.println("Especialidad actualizada correctamente.");
        break;
    case 3: {
        int duracion;
        do {
            duracion = leerEntero("Ingrese la nueva duracion: ");
            if (duracion <= 0) {
                System.out.println("La duracion debe ser mayor que cero");
            }
        } while (duracion <= 0);
        turno[DURACION] = String.valueOf(duracion);
        System.out.println("Duración actualizada correctamente");
        break;
    }
    case 4: {
        double valorMinuto;
        do {
            valorMinuto = leerDecimal("Ingrese el nuevo valor por minuto: ");
            if (valorMinuto <= 0) {
                System.out.println("El valor por minuto debe ser mayor que cero");
            }
        } while (valorMinuto <= 0);
        turno[VALOR_MINUTO] = String.valueOf(valorMinuto);
        System.out.println("Valor por minuto actualizado correctamente.");
        break;
    }
    default:
        System.out.println("Opcion invalida");
        break;
                }
            } while (opcion < 1 || opcion > 4);
    }


    static void cancelarTurno() {
        // Pedir el ID, verificar que exista, pedir confirmación (S/N) y eliminar
        // con turnos.remove(indice);

        int indice = buscarIndicePorId();
        if (indice == -1) {
            System.out.println("El turno con ese ID no existe.");
            return;
        }

        String[] turno = turnos.get(indice);

        System.out.println("\n=== Turno encontrado ===");
        System.out.println("ID: " + turno[ID]);
        System.out.println("Paciente: " + turno[PACIENTE]);
        System.out.println("Especialidad: " + turno[ESPECIALIDAD]);

        String confirmacion;

        do{
            confirmacion = leerTexto("¿Está seguro de cancelar el turno? (S/N): ");
            if (confirmacion.equalsIgnoreCase("S")){
                turnos.remove(indice);
                System.out.println("Turno cancelado");
            } else if (confirmacion.equalsIgnoreCase("N")){
                System.out.println("Cancelacion anulada.");
            }
            else{
                System.out.println("Ingresa una opcion valida");
            }
        } while (!confirmacion.equalsIgnoreCase("S") && !confirmacion.equalsIgnoreCase("N"));
    }

    // ============ ROL C: feature/calculos-validaciones ============
    // Responsable de: calcularTotalFacturado, reportePorEspecialidad, validaciones

    static void calcularTotalFacturado() {
        // TODO (Rol C)
        // Para cada turno: duracionMinutos * valorMinuto.
        // Mostrar el subtotal de cada turno y el gran total al final.
        // Recuerde convertir el texto a número antes de operar.
         if (turnos.isEmpty()) {
        System.out.println("No se encuentran turnos registrados");
        return;
    }
    double total = 0;
    for (int i = 0; i < turnos.size(); i++) {
        double duracion = Double.parseDouble(turnos.get(i)[3]);
        double valorminuto = Double.parseDouble(turnos.get(i)[4]);
        double subtotal = duracion * valorminuto;
        System.out.println("ID: " + turnos.get(i)[0] + " Nombre:" + turnos.get(i)[1] + " Subtotal: " + subtotal);
        total += subtotal;
    }
    System.out.println("El total facturado es de: $" + total);
        

    }

    static void reportePorEspecialidad() {
        // TODO (Rol C)
        // Pedir una especialidad y mostrar solo los turnos de esa especialidad,
        // junto con la cantidad de turnos y el promedio de duración en minutos.
        // Comparar con equalsIgnoreCase para no depender de mayúsculas.
        if (turnos.isEmpty()) {
        System.out.println("No se encuentran turnos registrados");
        return;
    }
    String especialidad = leerTexto("Ingrese la especialidad de la que desee el reporte: ");
    int cantidad = 0;
    int sumaDuracion = 0;
    System.out.println("Turnos de " + especialidad + " :");

    for (int i = 0; i < turnos.size(); i++) {
        String[] turno = turnos.get(i);

        if (turno[2].equalsIgnoreCase(especialidad)) {
            System.out.println("ID: " + turnos.get(i)[0] + " Nombre:" + turnos.get(i)[1] + " Duración: " + turnos.get(i)[3]);
            cantidad++;
            sumaDuracion += Integer.parseInt(turno[3]);
        }
    }
    if (cantidad == 0) {
        System.out.println("No hay turnos registrados para esa especialidad.");
        return;
    }
    double promedio = (double) sumaDuracion / cantidad;
    System.out.println("Cantidad de turnos: " + cantidad);
    System.out.println("Promedio de duración:"+ promedio);
    }

    // ====== Utilidades (ya implementadas, no es necesario modificarlas) ======

    static int leerEntero(String msg) {
        while (true) {
            System.out.print(msg);
            try {
                return Integer.parseInt(sc.nextLine().trim());
            } catch (Exception e) {
                System.out.println("Ingrese un número entero válido.");
            }
        }
    }

    static double leerDecimal(String msg) {
        while (true) {
            System.out.print(msg);
            try {
                return Double.parseDouble(sc.nextLine().trim().replace(",", "."));
            } catch (Exception e) {
                System.out.println("Ingrese un número válido (ej: 1500.50).");
            }
        }
    }

    static String leerTexto(String msg) {
        String valor;
        do {
            System.out.print(msg);
            valor = sc.nextLine().trim();
            if (valor.isEmpty()) System.out.println("Este campo no puede quedar vacío.");
        } while (valor.isEmpty());
        return valor;
    }
}
