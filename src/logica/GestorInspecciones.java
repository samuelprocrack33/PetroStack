package logica;

import estructura.PilaEventos;
import estructura.excepciones.PilaLlenaException;
import estructura.excepciones.PilaVaciaException;
import modelo.EventoInspeccion;
import modelo.TipoEvento;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class GestorInspecciones {

    private PilaEventos pilaPrincipal;
    private int contadorId;
    private List<String> historialAcciones;
    private List<EventoInspeccion> historialEventos;

    private static final String ARCHIVO_EVENTOS = "eventos.txt";
    private static final int CAPACIDAD = 100;

    public GestorInspecciones() {
        pilaPrincipal = new PilaEventos(CAPACIDAD);
        contadorId = 1;
        historialAcciones = new ArrayList<>();
        historialEventos = new ArrayList<>();
        cargarDesdeArchivo();
    }

    public void registrarEvento(String codigoPozo, String municipio,
                                TipoEvento tipoEvento, String descripcion, String fechaHora) {
        EventoInspeccion evento = new EventoInspeccion(
                contadorId++, codigoPozo, municipio, tipoEvento, descripcion, fechaHora);
        try {
            pilaPrincipal.push(evento);
            historialAcciones.add("push");
            historialEventos.add(evento);
            System.out.println("\n  Evento registrado exitosamente.");
            System.out.println(evento);
        } catch (PilaLlenaException e) {
            System.out.println("\n  ERROR: " + e.getMessage());
        }
    }

    public void revisarUltimo() {
        try {
            EventoInspeccion evento = pilaPrincipal.peek();
            System.out.println("\n  Evento en el tope de la pila:");
            System.out.println(evento);
        } catch (PilaVaciaException e) {
            System.out.println("\n  INFO: " + e.getMessage());
        }
    }

    public void retirarUltimo() {
        try {
            EventoInspeccion evento = pilaPrincipal.pop();
            historialAcciones.add("pop");
            historialEventos.add(evento);
            System.out.println("\n  Evento retirado de la pila:");
            System.out.println(evento);
        } catch (PilaVaciaException e) {
            System.out.println("\n  INFO: " + e.getMessage());
        }
    }

    public void deshacerUltimaAccion() {
        if (historialAcciones.isEmpty()) {
            System.out.println("\n  INFO: No hay acciones para deshacer.");
            return;
        }

        String ultimaAccion = historialAcciones.remove(historialAcciones.size() - 1);
        EventoInspeccion ultimoEvento = historialEventos.remove(historialEventos.size() - 1);

        try {
            if (ultimaAccion.equals("push")) {
                pilaPrincipal.pop();
                System.out.println("\n  Undo: Se deshizo el registro del evento:");
                System.out.println(ultimoEvento);
            } else if (ultimaAccion.equals("pop")) {
                pilaPrincipal.push(ultimoEvento);
                System.out.println("\n  Undo: Se restauro el evento retirado:");
                System.out.println(ultimoEvento);
            }
        } catch (PilaVaciaException | PilaLlenaException e) {
            System.out.println("\n  ERROR al deshacer: " + e.getMessage());
        }
    }

    public void listarEventos() {
        System.out.println("\n  === PILA DE EVENTOS (tope -> base) ===");
        pilaPrincipal.mostrarDesdeElTope();
    }

    public void mostrarTamanio() {
        System.out.println("\n  Eventos actualmente en la pila: " + pilaPrincipal.tamanio());
    }

    public void guardarEnArchivo() {
        List<EventoInspeccion> lista = new ArrayList<>();

        try {
            while (!pilaPrincipal.estaVacia()) {
                lista.add(pilaPrincipal.pop());
            }
            // Restaurar la pila en el orden original
            for (int i = lista.size() - 1; i >= 0; i--) {
                pilaPrincipal.push(lista.get(i));
            }
        } catch (PilaVaciaException | PilaLlenaException e) {
            System.out.println("  Advertencia al guardar: " + e.getMessage());
        }

        try (PrintWriter escritor = new PrintWriter(new FileWriter(ARCHIVO_EVENTOS))) {
            for (EventoInspeccion ev : lista) {
                escritor.println(ev.getId() + "|" + ev.getCodigoPozo() + "|" +
                        ev.getMunicipio() + "|" + ev.getTipoEvento() + "|" +
                        ev.getDescripcion() + "|" + ev.getFechaHora());
            }
            System.out.println("  Estado guardado en " + ARCHIVO_EVENTOS);
        } catch (IOException e) {
            System.out.println("  ERROR al guardar archivo: " + e.getMessage());
        }
    }

    private void cargarDesdeArchivo() {
        File archivo = new File(ARCHIVO_EVENTOS);
        if (!archivo.exists()) return;

        List<EventoInspeccion> lista = new ArrayList<>();
        try (BufferedReader lector = new BufferedReader(new FileReader(archivo))) {
            String linea;
            while ((linea = lector.readLine()) != null) {
                String[] partes = linea.split("\\|");
                if (partes.length == 6) {
                    int id = Integer.parseInt(partes[0]);
                    EventoInspeccion ev = new EventoInspeccion(
                            id, partes[1], partes[2],
                            TipoEvento.valueOf(partes[3]), partes[4], partes[5]);
                    lista.add(ev);
                    if (id >= contadorId) contadorId = id + 1;
                }
            }
            for (int i = lista.size() - 1; i >= 0; i--) {
                pilaPrincipal.push(lista.get(i));
            }
            if (!lista.isEmpty()) {
                System.out.println("  Se cargaron " + lista.size() + " eventos desde " + ARCHIVO_EVENTOS);
            }
        } catch (IOException | PilaLlenaException e) {
            System.out.println("  Advertencia al cargar archivo: " + e.getMessage());
        }
    }
}
