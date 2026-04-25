package estructura;

import estructura.excepciones.PilaVaciaException;
import estructura.excepciones.PilaLlenaException;
import modelo.EventoInspeccion;

// Pila manual con arreglo. Principio LIFO. No usa java.util.Stack.
public class PilaEventos {

    private EventoInspeccion[] arreglo;
    private int tope;
    private int capacidad;

    public PilaEventos(int capacidad) {
        this.capacidad = capacidad;
        this.arreglo = new EventoInspeccion[capacidad];
        this.tope = -1;
    }

    public void push(EventoInspeccion evento) throws PilaLlenaException {
        if (estaLlena()) {
            throw new PilaLlenaException("La pila esta llena. Capacidad maxima: " + capacidad);
        }
        arreglo[++tope] = evento;
    }

    public EventoInspeccion pop() throws PilaVaciaException {
        if (estaVacia()) {
            throw new PilaVaciaException("La pila esta vacia. No hay eventos para retirar.");
        }
        EventoInspeccion evento = arreglo[tope];
        arreglo[tope] = null;
        tope--;
        return evento;
    }

    public EventoInspeccion peek() throws PilaVaciaException {
        if (estaVacia()) {
            throw new PilaVaciaException("La pila esta vacia. No hay eventos para consultar.");
        }
        return arreglo[tope];
    }

    public boolean estaVacia() {
        return tope == -1;
    }

    public boolean estaLlena() {
        return tope == capacidad - 1;
    }

    public int tamanio() {
        return tope + 1;
    }

    public void mostrarDesdeElTope() {
        if (estaVacia()) {
            System.out.println("  La pila esta vacia.");
            return;
        }
        System.out.println("  Total de eventos: " + tamanio());
        System.out.println("  ---- TOPE ----");
        for (int i = tope; i >= 0; i--) {
            System.out.println(arreglo[i]);
            System.out.println();
        }
        System.out.println("  ---- BASE ----");
    }
}
