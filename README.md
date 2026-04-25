# PetroStack Casanare — Gestión de eventos de inspección en pozos petroleros

**Autor:** [Tu nombre completo]  
**Código de estudiante:** [Tu código]  
**Asignatura:** Algoritmos II  
**Fecha:** Junio 2024  
**Uniremington — Sede Yopal, Casanare**

---

## 1. Descripción del caso

PetroStack Casanare S.A.S. presta servicios de inspección técnica a pozos petroleros en municipios del Casanare (Aguazul, Tauramena, Monterrey, Maní y Villanueva). Las cuadrillas de campo registran eventos durante cada turno: fugas, lecturas de presión, mantenimientos y cierres. Este sistema en Java gestiona esos eventos usando una pila (Stack), de manera que el evento más reciente sea el primero en ser revisado por el supervisor.

## 2. Justificación del uso de pila (LIFO)

El principio LIFO (Last In, First Out) modela correctamente este dominio porque en inspección de pozos, el evento más reciente es siempre el más urgente. Si una cuadrilla registra una fuga justo antes de cerrar el turno, ese hallazgo debe ser el primero que el supervisor atienda, no el último. La pila garantiza exactamente eso: el último evento ingresado es el primero en salir. Además, la funcionalidad de deshacer (Undo) es natural con una segunda pila auxiliar, permitiendo revertir el último push o pop sin complicaciones. Intentar resolver este problema con una cola (FIFO) sería incorrecto, ya que procesaría primero los eventos más antiguos, que pueden ser menos urgentes.

## 3. Estructura del proyecto

```
PetroStack-Casanare/
└── src/
    ├── modelo/
    │   ├── EventoInspeccion.java
    │   ├── TipoEvento.java       (enum)
    │   └── Pozo.java
    ├── estructura/
    │   ├── PilaEventos.java
    │   └── excepciones/
    │       ├── PilaVaciaException.java
    │       └── PilaLlenaException.java
    ├── logica/
    │   └── GestorInspecciones.java
    └── main/
        ├── MenuConsola.java
        └── Principal.java
```

## 4. Instrucciones de compilación y ejecución

**Requisito:** Java 17 o superior instalado.

```bash
# Desde la raiz del proyecto, compilar:
mkdir -p out
javac -d out -sourcepath src src/main/Principal.java

# Ejecutar:
java -cp out main.Principal
```

## 5. Funcionalidades implementadas

| RF | Funcionalidad | Estado |
|----|---------------|--------|
| RF-01 | Registrar evento (push) | ✅ |
| RF-02 | Consultar tope (peek) | ✅ |
| RF-03 | Retirar evento (pop) | ✅ |
| RF-04 | Deshacer ultima accion (Undo) | ✅ |
| RF-05 | Listar pila desde el tope | ✅ |
| RF-06 | Consultar tamaño | ✅ |
| RF-07 | Validar entrada | ✅ |
| RF-08 | Persistencia en eventos.txt | ✅ (+0.3 bonificacion) |
| RF-09 | Salida controlada | ✅ |

## 6. Diagrama de clases (UML simplificado)

```
Principal
    └── MenuConsola
            └── GestorInspecciones
                    ├── PilaEventos (pila principal)
                    ├── PilaEventos (pila undo)
                    └── EventoInspeccion
                            ├── TipoEvento (enum)
                            └── Pozo

PilaEventos
    ├── push(EventoInspeccion) throws PilaLlenaException
    ├── pop() throws PilaVaciaException
    ├── peek() throws PilaVaciaException
    ├── estaVacia()
    ├── estaLlena()
    ├── tamanio()
    └── mostrarDesdeElTope()
```

## 7. Análisis de complejidad

| Operacion | Complejidad |
|-----------|-------------|
| push | O(1) |
| pop | O(1) |
| peek | O(1) |
| tamanio | O(1) |
| mostrarDesdeElTope | O(n) |
| guardarEnArchivo | O(n) |

## 8. Reflexión personal

Este parcial me permitió entender de forma práctica por qué las pilas son útiles más allá de la teoría. Al principio confundía cuándo usar una pila auxiliar para el Undo, pero al pensar en que cada acción puede "invertirse" guardando su estado, todo tuvo sentido. La parte más retadora fue implementar la pila desde cero sin usar java.util.Stack, pero también fue la más satisfactoria. Aprendí que las estructuras de datos no son solo código, sino decisiones de diseño que reflejan cómo funciona el problema real.

## 9. Declaración de uso de IA

Se utilizó Claude (Anthropic) como apoyo para estructurar el código, revisar la lógica de la pila de Undo y verificar que se cumplieran todos los requerimientos del parcial. Todo el código fue revisado, comprendido y es explicable por el estudiante.
