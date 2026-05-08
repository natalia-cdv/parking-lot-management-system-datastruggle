# Sistema de Reservación de Estacionamiento Universitario
### CIIC4020 – Spring 2026 | Team DataStruggle

---

## Team

| Name | Student ID | Contributions |
|------|-----------|---------------|
| Natalia Camejo Del Valle | 802-24-7094 | `Auto.java`, `Estudiante.java`, HashMap integration, `hacerReservacion()`, Display (e)(f), `Main.java` operator flow |
| Jezer | 802-XX-XXXX | `Espacio.java`, Set management, Queue/Waitlist, `cancelarReservacion()`, `cambiarEstacionamiento()`, Display (a)(b) |
| Aidhan | 802-XX-XXXX | `Reservacion.java`, `Transaccion.java`, LinkedList, Stack/Undo, Display (c)(d), input validation helpers |

---

## Project Summary

This is a console-based parking lot reservation system built from the perspective of a **university parking operator**. The system manages a 200-space lot divided into three sections — General (100), VIP (50), and Electric (50) — and supports reservations, cancellations, section changes, waitlists, undo functionality, and multiple display/filter queries.

The system operates Monday through Friday, 7:00 AM to 5:00 PM. All interactions are handled by an operator on behalf of students.

---

## Data Structures Used & Justification

| Structure | Location | Reason |
|-----------|----------|--------|
| `HashSet<Espacio>` | `Estacionamiento.java` | O(1) add/remove/lookup; prevents duplicate spaces naturally. Three sets: one per section. |
| `HashMap<String, Reservacion>` | `Estacionamiento.java` | Maps car `tablilla` → active `Reservacion`. O(1) lookup by license plate for fast reservation retrieval. |
| `LinkedList<Transaccion>` | `Estacionamiento.java` | Ordered log of all system actions. Efficient insertion at tail; ideal for sequential history. |
| `Stack<Transaccion>` | `Estacionamiento.java` | LIFO — the last action is always on top, making undo O(1). |
| `Queue<Estudiante>` | `Estacionamiento.java` | FIFO waitlist per section — first student to wait is first to get a space when one opens up. |
| `ArrayList<Reservacion>` | Display methods | Used locally to collect and sort filtered results before printing. |
| `List<String>` | `Reservacion.java` | Stores selected add-on services per reservation. |

---

## How to Compile & Run

### Requirements
- Java JDK 11 or higher
- VS Code with Java Extension Pack (recommended) or any terminal

### Steps

**1. Clone the repository:**
```bash
git clone https://github.com/UPRM-CIIC4020-Spring2026/parking-lot-management-system-datastruggle.git
cd parking-lot-management-system-datastruggle
```

**2. Compile all files:**
```bash
javac src/*.java -d out/
```
Or if all `.java` files are in the root:
```bash
javac *.java
```

**3. Run the program:**
```bash
java -cp out/ Main
```
Or from root:
```bash
java Main
```

You will see the operator menu in the terminal:
```
==============================================
--- MENU DE OPERADOR - DATASTRUGGLE ---
1. Hacer reservacion
2. Cancelar reservacion
3. Cambiar seccion
4. Undo (Deshacer ultima accion)
5. Opciones de Visualizacion
6. Salir
```

---

## Project Structure

```
parking-lot-management-system-datastruggle/
├── Auto.java           → Car model (tablilla, marca, modelo, año)
├── Estudiante.java     → Student model (nombre, id, email, telefono, Auto)
├── Espacio.java        → Parking space model (fila, numero, seccion, disponible)
├── Reservacion.java    → Reservation model + calcularCosto() logic
├── Transaccion.java    → Transaction log entry (tipo, reservacion, timestamp, monto)
├── Estacionamiento.java→ Core system — all data structures & business logic
├── Main.java           → Operator console menu (entry point)
└── README.md
```

---

## Operator Features

| # | Feature | Details |
|---|---------|---------|
| 1 | Make reservation | Collects student + car info, selects section/date/time/add-ons, confirms and reserves |
| 2 | Cancel reservation | Looks up by tablilla, frees space, charges $10 fee, auto-assigns to next in waitlist |
| 3 | Change section | Moves reservation to another section, charges $6 fee |
| 4 | Undo last action | Reverses the last RESERVAR or CANCELAR action using Stack |
| 5a | Display – week | Shows all reservations for the current Mon–Fri week |
| 5b | Display – >2 hrs | Shows reservations longer than 2 hours on a given day, sorted by start time |
| 5c | Display – cost | Filters reservations by cost range (e.g. >$50, >$100, or custom range) |
| 5d | Display – period | Filters reservations within any date range, sorted by date then start time |
| 5e | Display – student | Shows all transactions for a specific student number |
| 5f | Display – all | Shows complete transaction log with timestamps and amounts |

---

## Pricing & Add-ons

| Section | Spaces | Rate | Available Add-ons |
|---------|--------|------|-------------------|
| General | 100 | $2/hr | Tire air check ($1), Fluid check ($2) |
| VIP | 50 | $4/hr | Exterior wash ($50), Tire air check ($1), Fluid check ($2), Brake/oil check ($5) |
| Electric | 50 | $8/hr + charging | Exterior wash ($50), Tire air check ($1) |

**Additional fees:** Cancellation = $10 | Section change = $6

**Time rules:** Minimum 1 hour, maximum 8 consecutive hours. Hours must be between 7 and 17 (7AM–5PM). Reservations available Mon–Fri, up to one week in advance.

---

## Input Validations Implemented

The system validates all operator input before processing. Specifically:

- **Student name:** Letters only, minimum 3 characters
- **Student number:** Does not contains validation as it depends from university
- **Email:** Must contain `@` and `.`
- **Phone:** Minimum 10 digits
- **License plate (tablilla):** Must start with at least 3 letters
- **Car make/brand:** Letters only, minimum 3 characters
- **Car Year:** Between 1886 and 2026
- **Section:** Must be exactly `General`, `VIP`, or `Electrico` (case-insensitive)
- **Year:** Between 2026 and 2999
- **Month:** Between 1 and 12
- **Day:** Between 1 and 31, with invalid calendar date detection (e.g. Feb 30)
- **Start hour:** Between 7 and 17 inclusive
- **Duration:** Between 1 and 8 hours inclusive
- **Cost range:** Minimum must be ≥ 0; maximum must be ≥ minimum


---

## Design Clarifications

- **Space assignment:** When a reservation is made, the system picks the next available `Espacio` from the section's `HashSet` using an iterator. Spaces are returned to the Set upon cancellation.
- **Waitlist flow:** If a section is full, the operator is offered the option to add the student to the Queue. When a cancellation occurs, the system automatically polls the Queue and attempts to reassign the freed space to the next waiting student.
- **Undo scope:** The Stack tracks the last modifying action (RESERVAR or CANCELAR). Undoing a RESERVAR restores the space to the Set and removes from HashMap. Undoing a CANCELAR restores the reservation. Every undo is also logged in the LinkedList as a DESHACER transaction.
- **Transaction log:** The `LinkedList<Transaccion>` records every action — RESERVAR, CANCELAR, CAMBIAR, and DESHACER — with timestamp and amount. This powers both display option (f) and the student history lookup in option (e).
- **Cost calculation:** `calcularCosto()` in `Reservacion.java` computes the total based on section rate × duration + selected add-on costs. It is called immediately after construction and updates `costoTotal` in place.
- **Section change cost:** When a student changes section, the $6 fee is added on top of the recalculated cost for the new section. The old space is freed and returned to its Set.
- **Immutable History:** Every action is logged as a permanent entry in the LinkedList. Using the Stack for an undo does not delete records; it adds a new DESHACER transaction to ensure an audit trail.

---
