package main;

import logica.GestorInspecciones;
import modelo.TipoEvento;

import java.util.Scanner;

public class MenuConsola {

    private GestorInspecciones gestor;
    private Scanner lector;

    private static final String[] MUNICIPIOS_VALIDOS = {
        "Aguazul", "Tauramena", "Monterrey", "Mani", "Villanueva"
    };

    public MenuConsola() {
        gestor = new GestorInspecciones();
        lector = new Scanner(System.in);
    }

    public void iniciar() {
        System.out.println("=================================================");
        System.out.println("  PetroStack Casanare S.A.S. - Sistema de Pilas  ");
        System.out.println("  Uniremington - Algoritmos II                   ");
        System.out.println("=================================================");

        int opcion = -1;
        while (opcion != 0) {
            mostrarMenu();
            opcion = leerEnteroValido("Ingrese una opcion: ", 0, 7);
            procesarOpcion(opcion);
        }
    }

    private void mostrarMenu() {
        System.out.println("\n----------- MENU PRINCIPAL -----------");
        System.out.println("  1. Registrar evento (push)");
        System.out.println("  2. Consultar ultimo evento (peek)");
        System.out.println("  3. Retirar ultimo evento (pop)");
        System.out.println("  4. Deshacer ultima accion (undo)");
        System.out.println("  5. Listar todos los eventos");
        System.out.println("  6. Consultar cantidad de eventos");
        System.out.println("  7. Guardar en archivo");
        System.out.println("  0. Salir");
        System.out.println("--------------------------------------");
    }

    private void procesarOpcion(int opcion) {
        switch (opcion) {
            case 1: solicitarNuevoEvento(); break;
            case 2: gestor.revisarUltimo(); break;
            case 3: gestor.retirarUltimo(); break;
            case 4: gestor.deshacerUltimaAccion(); break;
            case 5: gestor.listarEventos(); break;
            case 6: gestor.mostrarTamanio(); break;
            case 7: gestor.guardarEnArchivo(); break;
            case 0: salir(); break;
            default: System.out.println("  Opcion no valida."); break;
        }
    }

    private void solicitarNuevoEvento() {
        System.out.println("\n  --- Registro de nuevo evento ---");
        String codigoPozo = leerTextoNoVacio("  Codigo del pozo (ej. YOP-AGZ-017): ");
        String municipio = leerMunicipio();
        TipoEvento tipoEvento = leerTipoEvento();
        String descripcion = leerTextoNoVacio("  Descripcion del evento: ");
        String fechaHora = leerTextoNoVacio("  Fecha y hora (ej. 2024-06-15 08:30): ");
        gestor.registrarEvento(codigoPozo, municipio, tipoEvento, descripcion, fechaHora);
    }

    private String leerMunicipio() {
        System.out.println("  Municipios validos: Aguazul, Tauramena, Monterrey, Mani, Villanueva");
        while (true) {
            System.out.print("  Municipio: ");
            String entrada = lector.nextLine().trim();
            for (String m : MUNICIPIOS_VALIDOS) {
                if (m.equalsIgnoreCase(entrada)) return m;
            }
            System.out.println("  ERROR: Municipio no valido. Intente de nuevo.");
        }
    }

    private TipoEvento leerTipoEvento() {
        System.out.println("  Tipos validos: 1=FUGA, 2=MANTENIMIENTO, 3=LECTURA_PRESION, 4=CIERRE_TURNO");
        int num = leerEnteroValido("  Tipo de evento (1-4): ", 1, 4);
        switch (num) {
            case 1: return TipoEvento.FUGA;
            case 2: return TipoEvento.MANTENIMIENTO;
            case 3: return TipoEvento.LECTURA_PRESION;
            case 4: return TipoEvento.CIERRE_TURNO;
            default: return TipoEvento.FUGA;
        }
    }

    private String leerTextoNoVacio(String mensaje) {
        while (true) {
            System.out.print(mensaje);
            String entrada = lector.nextLine().trim();
            if (!entrada.isEmpty()) return entrada;
            System.out.println("  ERROR: El campo no puede estar vacio.");
        }
    }

    private int leerEnteroValido(String mensaje, int minimo, int maximo) {
        while (true) {
            System.out.print(mensaje);
            String entrada = lector.nextLine().trim();
            try {
                int numero = Integer.parseInt(entrada);
                if (numero >= minimo && numero <= maximo) return numero;
                System.out.println("  ERROR: Ingrese un numero entre " + minimo + " y " + maximo + ".");
            } catch (NumberFormatException e) {
                System.out.println("  ERROR: Ingrese un numero valido.");
            }
        }
    }

    private void salir() {
        gestor.guardarEnArchivo();
        System.out.println("\n  PetroStack Casanare S.A.S.");
        System.out.println("  Sistema cerrado correctamente. Hasta luego.");
        lector.close();
    }
}
