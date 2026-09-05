# AGENTS.md

## Project overview

Java 17 console application — a vehicle rental management system. NetBeans IDE project using Apache Ant. No external dependencies; standard library only.

Main class: `vehicle_management_rental_system.Vehicle_Management_Rental_System`

## Build & run

```bash
# Build via Ant (requires Ant on PATH or use NetBeans)
ant clean jar

# Run the built JAR
java -jar dist/Vehicle_Management_Rental_System.jar

# Run directly from compiled classes
java -cp build/classes vehicle_management_rental_system.Vehicle_Management_Rental_System
```

NetBeans project properties: `nbproject/project.properties` — key settings: `javac.source=17`, `javac.target=17`, `main.class=vehicle_management_rental_system.Vehicle_Management_Rental_System`.

## Testing

No tests exist. `test.src.dir=test` is configured in project properties but the `test/` directory does not exist. If tests are added later, use: `ant test`

## Architecture

Single package: `src/vehicle_management_rental_system/`

**Domain model** (inheritance):
- `User` (abstract) → `Admin`, `Customer`
- `Vehicle` (base) → `Car`, `MotorCycle`, `Truck`

**Managers** (business logic + in-memory storage via `ArrayList`):
- `CustomerManager` — CRUD, auth, search
- `VehicleManager` — CRUD, search, price-range filter
- `RentalManager` — create/cancel/return, status transitions
- `PaymentManager` — create (prevents double-pay), lookup

**UI**: `Vehicle_Management_Rental_System.java` — single main class, all `static` methods, console menus via `Scanner`.

**Enums**: `VehicleType`, `VehicleStatus`, `RentalStatus`, `PaymentStatus`, `PaymentMethod`, `Role`

## Key gotchas

- **In-memory only**: No database, no file persistence. All data lost on restart. Static ID counters (`countId`) reset each run.
- **Hardcoded admin**: `CustomerManager` constructor seeds an admin account — username: `admin`, password: `jamal000`. This is the only way to access admin menus.
- **Static main class**: All UI methods in `Vehicle_Management_Rental_System.java` are `public static`. New menu options require adding a static method and wiring it into the corresponding switch statement.
- **No Maven/Gradle**: Do not create `pom.xml` or `build.gradle`. Build is Ant-based via `nbproject/`.
- **NetBeans conventions**: `nbproject/` contains IDE-specific config. `build/` and `dist/` are gitignored output directories.
- **`readInput()` cancel pattern**: Typing `0` at any registration prompt throws `IllegalStateException("CANCEL")` caught upstream — this is intentional, not a bug.
- **Vehicle deletion blocked if rented**: `VehicleManager.deleteVehicle()` returns false when status is `RENTED`.
- **Payment deduplication**: `PaymentManager.createPayment()` rejects a rental that already has a payment, regardless of status.
- **Rental requires AVAILABLE vehicle**: `RentalManager.createRental()` only succeeds when `vehicle.isAvailable()`.
- **Date input format**: Rentals require `YYYY-MM-DD` format (parsed by `LocalDate.parse()`).
