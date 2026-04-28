## Solución - Problema #2: Tienda Virtual (Sistema de Pagos)

### 🎯 Descripción del Problema

Implementar un sistema de tienda virtual que permita:
1. **Gestionar productos e inventario** con disponibilidad en tiempo real
2. **Procesar múltiples métodos de pago** (Tarjeta de Crédito, PayPal, Criptomoneda)
3. **Generar facturas personalizadas** con detalles de la compra
4. **Enviar notificaciones** automáticas de éxito/fallo
5. **Mantener la integridad del inventario** durante transacciones

---

### 📋 Patrones de Diseño Aplicados

El sistema implementa **tres patrones principales**:

#### 1. **Patrón Factory (Creacional)**
- **Interfaz**: `PaymentFactory` define el contrato para crear métodos de pago
- **Implementaciones Concretas**:
  - `CreditCardPaymentFactory` → crea instancias de `CreditCardFactory`
  - `PaypalPaymentFactory` → crea instancias de `PaypalFactory`
  - `CryptoPaymentFactory` → crea instancias de `CryptoFactory`
- **Ventaja**: Abstrae la creación de diferentes tipos de pago, permitiendo agregar nuevos métodos sin modificar código existente

#### 2. **Patrón Observer (Comportamiento)**
- **Interfaz**: `PaymentObserver` define el contrato para observadores de eventos de pago
- **Implementación**: `PaymentEventObserver` reacciona a eventos de pago exitosos/fallidos
- **Responsabilidades**:
  - Reducir inventario en pagos exitosos
  - Generar facturas
  - Enviar notificaciones
- **Ventaja**: Desacopla el procesamiento de pagos de las acciones posteriores (inventario, facturación, notificaciones)

#### 3. **Patrón Facade (Estructural)**
- **Clase**: `VirtualStore` simplifica la interfaz compleja del sistema
- **Encapsula**:
  - `Inventory` (gestión de productos)
  - `ECIPayment` (procesamiento de pagos)
  - `Notification` (notificaciones)
  - `Facturation` (generación de facturas)
- **Ventaja**: Proporciona una interfaz unificada y simple para interactuar con todo el sistema

---

### ❌ Errores Identificados en el Código Original

#### Error 1: Clase `PaymentFactory` Inexistente
**Ubicación**: `ECIPayment.java` línea 21
```java
PaymentMethod payment = factory.createPaymentMethod(amount, customerId, description);
```
**Problema**: El parámetro `factory` es de tipo `PaymentFactory`, pero la clase/interfaz no existía.
**Impacto**: Compilación falla con "cannot find symbol: class PaymentFactory"

#### Error 2: Import Incorrecto en `PaymentEventObserver`
**Ubicación**: `PaymentEventObserver.java` línea 3
```java
import javax.management.Notification;  // ❌ INCORRECTO
```
**Problema**: Importa `Notification` de `javax.management` (API de JMX) en lugar de la clase local `eci.edu.byteProgramming.ejercicio.paper.util.Notification`
**Impacto**: Los métodos `sendConfirmationEmail()` y `sendFailureNotification()` no existen en `javax.management.Notification`, causando errores de compilación.

#### Error 3: Constructor Incorrecto en `PaymentMethod`
**Ubicación**: `PaymentMethod.java` línea 10-12
```java
public PaymentMethod(double amount, String transactionID, String description) {
    this.amount = amount;
    this.customerID = customerID;  // ❌ NULO - customerID no es parámetro
```
**Problema**: El parámetro se llama `transactionID` pero el código intenta asignar `customerID` que es null
**Impacto**: Todas las transacciones quedan con `customerID = null`

#### Error 4: Variable Unitializada en `CryptoFactory`
**Ubicación**: `CryptoFactory.java` línea 15
```java
this.token = token;  // ❌ token no existe como parámetro
```
**Problema**: Intenta asignar un parámetro `token` que no existe
**Impacto**: Compilación falla o comportamiento indefinido

---

### ✅ Correcciones Realizadas

#### 1. Creación del Patrón Factory Completo
**Archivos creados**:
- `PaymentFactory.java` - Interfaz que define el contrato
- `CreditCardPaymentFactory.java` - Factory para tarjetas de crédito
- `PaypalPaymentFactory.java` - Factory para PayPal
- `CryptoPaymentFactory.java` - Factory para criptomonedas

**Código ejemplo**:
```java
public interface PaymentFactory {
    PaymentMethod createPaymentMethod(double amount, String customerId, String description);
}

public class CreditCardPaymentFactory implements PaymentFactory {
    @Override
    public PaymentMethod createPaymentMethod(double amount, String customerId, String description) {
        return new CreditCardFactory(amount, customerId, description, number, name, expirationDate, cvv, address);
    }
}
```

#### 2. Corrección de Import en `PaymentEventObserver`
```java
// ANTES:
import javax.management.Notification;

// DESPUÉS:
// Simplemente se eliminó el import incorrecto, Java usa la clase del mismo paquete
```

#### 3. Corrección de Constructor en `PaymentMethod`
```java
// ANTES:
public PaymentMethod(double amount, String transactionID, String description) {
    this.customerID = customerID;  // ❌ NULO

// DESPUÉS:
public PaymentMethod(double amount, String customerId, String description) {
    this.customerID = customerId;  // ✅ CORRECTO
```

#### 4. Corrección de `CryptoFactory`
```java
// ANTES:
public CryptoFactory(double amount, String customerId, String description,
         String walletAddress, String cryptoType, double walletBalance) {
    super(amount, customerId, description);
    this.walletAddress = walletAddress;
    this.cryptoType = cryptoType;
    this.token = token;  // ❌ No existe como parámetro
    this.walletBalance = walletBalance;
}

// DESPUÉS:
public CryptoFactory(double amount, String customerId, String description,
         String walletAddress, String cryptoType, double walletBalance) {
    super(amount, customerId, description);
    this.walletAddress = walletAddress;
    this.cryptoType = cryptoType;
    this.walletBalance = walletBalance;
}
```

#### 5. Creación de Facade `VirtualStore`
**Responsabilidades**:
- Orquestar la interacción entre Inventory, ECIPayment, Notification y Facturation
- Proporcionar interfaz simple para procesar compras
- Validar disponibilidad de productos
- Gestionar el flujo completo de transacción

---

### 📦 Estructura de Clases del Sistema

```
┌─────────────────────────────────────────────┐
│         VirtualStore (FACADE)               │
│  Simplifica la interfaz del sistema         │
└────────────────┬────────────────────────────┘
                 │
        ┌────────┼────────┬────────────┐
        │        │        │            │
   ┌────▼──┐ ┌──▼───┐ ┌──▼────┐ ┌────▼──────┐
   │Inventory│ECIPayment│Notification│Facturation│
   └────────┘ └────┬───┘ └──────────┘ └──────────┘
                   │
            ┌──────┴──────┐
            │             │
       ┌────▼────┐  ┌────▼──────────┐
       │PaymentObserver│PaymentEventObserver│
       │(Interface)    │(Implements)        │
       └───────────┘  └─────────────────┘
                │
         ┌──────┴────────────────────────┐
         │                               │
    ┌────▼──────────┐  ┌────────────────▼──┐
    │PaymentFactory │  │PaymentMethod      │
    │(Interface)    │  │(Abstract)         │
    │               │  │                   │
    ├───────────────┤  ├───────────────────┤
    │+ createPaymentMethod() │+ processPayment()    │
    └────┬────┬──────┬──┘  └────────────────┘
         │    │      │
    ┌────▼─┐ ┌┴──┐ ┌─┴──────┐
    │CC...│ │PayPal... │Crypto...│
    │    │ │        │      │
    └────┘ └────────┘      └───────┘
```

---

### 🧪 Suite de Pruebas

Se crearon **19 pruebas unitarias** que validan:

#### Pruebas de VirtualStore (11 pruebas)
1. Creación correcta de VirtualStore
2. Recuperación de productos
3. Consulta de stock
4. Pago exitoso con tarjeta de crédito
5. Pago exitoso con PayPal
6. Pago exitoso con criptomoneda
7. Fallo en producto inexistente
8. Validación de tarjeta de crédito inválida
9. Validación de email PayPal inválido
10. Fallo por balance insuficiente en cripto
11. Decrementación correcta de stock

#### Pruebas de PaymentFactory (4 pruebas)
1. CreditCardPaymentFactory crea instancia correcta
2. PaypalPaymentFactory crea instancia correcta
3. CryptoPaymentFactory crea instancia correcta
4. IDs de transacción únicos por cada pago

#### Otras Pruebas
- RentalServiceTest (Problema 1 - Videoclub): 3 pruebas
- ApplicationTest (Spring Boot): 1 prueba

---

### 💻 Cómo Ejecutar

#### Compilar el proyecto
```bash
mvn clean compile
```

#### Ejecutar todas las pruebas
```bash
mvn test
```

#### Ejecutar pruebas específicas
```bash
# Solo pruebas de Tienda Virtual
mvn test -Dtest=VirtualStoreTest

# Solo pruebas de Payment Factory
mvn test -Dtest=PaymentFactoryTest
```

#### Ejecutar demostración interactiva
```bash
mvn exec:java -Dexec.mainClass="eci.edu.byteProgramming.ejercicio.paper.util.VirtualStoreConsoleApp"
```

#### Resultado esperado de compilación y pruebas
```
[INFO] Tests run: 19, Failures: 0, Errors: 0
[INFO] BUILD SUCCESS
```

---

### 🔍 Diagrama de Flujo: Procesamiento de Compra

```
1. Cliente inicia compra en VirtualStore
         │
         ▼
2. VirtualStore valida disponibilidad en Inventory
         │
         ├─ Producto NO existe → Retorna FALSE
         │
         ├─ Stock insuficiente → Retorna FALSE
         │
         └─ Validación OK ↓
3. VirtualStore crea PaymentMethod usando PaymentFactory
         │
         ▼
4. ECIPayment.processPayment() procesa el pago
         │
         ├─ Validación de datos FALLA → paymentFailed event
         │                                      │
         │                                      ▼
         │                              PaymentEventObserver
         │                                      │
         │                                 Notification
         │                                (failure email)
         │
         └─ Validación OK → Procesamiento (sleep/delay)
                                    │
                                    ├─ EXITOSO → Evento onPaymentSuccess
                                    │               │
                                    │               ▼
                                    │       PaymentEventObserver:
                                    │        • Inventory.discountProduct()
                                    │        • Facturation.generateInvoice()
                                    │        • Notification.sendConfirmationEmail()
                                    │
                                    └─ FALLIDO → Evento onPaymentFailed
                                                    │
                                                    ▼
                                            Notification.sendFailureNotification()
```

---

### 📊 Matriz de Validación de Patrones

| Patrón | Ubicación | Implementación | Cumple |
|--------|-----------|---|---------|
| **Factory** | `PaymentFactory.java` + `*PaymentFactory.java` | Interfaz + 3 implementaciones concretas | ✅ |
| **Observer** | `PaymentObserver.java` + `PaymentEventObserver.java` | Interfaz + observador concreto | ✅ |
| **Facade** | `VirtualStore.java` | Encapsula Inventory, ECIPayment, Notification, Facturation | ✅ |

---

### 🎓 Lecciones Aprendidas

1. **Patrón Factory es esencial** para abstraer la creación de objetos complejos
2. **Patrón Observer desacopla** la lógica de negocio de acciones posteriores
3. **Patrón Facade simplifica** interfaces complejas para el cliente
4. **Constructor correcto es crítico** - los parámetros deben coincidir con asignaciones
5. **Testing completo** previene errores sutiles (como customerID = null)

---

## Solución - Problema 1: Videoclub de Don Mario

### Enfoque aplicado

Se resolvio con una estructura simple y extensible:

- **Creacional**: `MovieFactory` centraliza la instanciacion del catalogo inicial.
- **Estructural**: `RentalReceipt` compone la lista de peliculas seleccionadas y la presenta como un recibo unico.
- **Comportamiento**: `PricingStrategy` define el descuento segun la membresia sin alterar la logica principal.

### Clases agregadas

- `videoclub/Movie`
- `videoclub/MovieFormat`
- `videoclub/MembershipType`
- `videoclub/MovieFactory`
- `videoclub/PricingStrategy`
- `videoclub/BasicPricingStrategy`
- `videoclub/PremiumPricingStrategy`
- `videoclub/RentalService`
- `videoclub/RentalReceipt`
- `videoclub/VideoclubConsoleApp`

### Caso de prueba resuelto

Entrada usada:

```bash
Membresia del cliente: Premium
Seleccione peliculas (numeros separados por coma): 1,3
```

Salida esperada:

```bash
--- RECIBO DE ALQUILER ---
Cliente: Premium
Peliculas:
 - Interestellar (Fisica) - $8.000
 - Inception (Digital) - $5.000
Subtotal: $13.000
Descuento (20%): $2.600
Total a pagar: $10.400
--------------------------
¡Disfrute su pelicula!
```

### Prompts utilizados

```bash
1. Crear un sistema de alquiler para peliculas fisicas y digitales con membresia Basica y Premium.
2. Aplicar patrones creacional, estructural y de comportamiento en una solucion simple en Java.
3. Generar un recibo final con subtotal, descuento y total, validando que solo se alquilen peliculas disponibles.
```

### Validacion

Se agregaron pruebas unitarias para verificar:

- Calculo del descuento Premium del 20%.
- Ignorar peliculas no disponibles.
- Formato final del recibo.


Propmt que le envie para la solucion del problema :

Necesito que me ayudes a resolver este problema, te pasare el  problema y el contexto del ejercicio para que sepas de que se trata:

Don Mario acaba de abrir un videoclub moderno donde los clientes pueden alquilar peliculas fisicas o digitales. El problema es que su sistema anterior era un caos: todos los precios se calculaban igual sin importar el tipo de pelicula o membresia del cliente, y no habia forma de saber que peliculas estaban disponibles en tiempo real.

Con eso lo que debes hacer es :

Ayuda a Don Mario creando un sistema de alquiler que permita:

1. Registrar peliculas (fisicas o digitales) con su disponibilidad.
2. Que el cliente elija X peliculas para alquilar.
3. Calcular el precio total segun su tipo de membresia:
   - Basica: precio normal.
   - Premium: 20% de descuento.
4. Mostrar al finalizar un recibo con las peliculas, precio por unidad y total.
Informacion de la que tienes conocimiento:

### Peliculas Disponibles

- [Fisica] Interestellar - $8.000 - Disponible
- [Fisica] El Padrino - $7.000 - No disponible
- [Digital] Inception - $5.000 - Disponible
- [Digital] Matrix - $6.000 - Disponible

y ahora te pasare un caso de ejemplo para que puedas resolver con exito el ejercicio:

Membresia del cliente: Premium  
Seleccione peliculas (numeros separados por coma): 1,3

```text
--- RECIBO DE ALQUILER ---
Cliente: Premium
Peliculas:
 - Interestellar (Fisica) - $8.000
 - Inception (Digital) - $5.000
Subtotal: $13.000
Descuento (20%): $2.600
Total a pagar: $10.400
--------------------------
¡Disfrute su pelicula!
```


Ya con toda esta informacion necesito que generes una solucion de forma adecuada con java, utilizando patrones, reacionales (instanciación de objetos), estructurales (composición de clases u objetos) y de comportamiento (interacción y responsabilidad entre objetos). Esto con el fin de dejar una solucion sencilla y rapida a el problema. Para finalizar necesito que dejes el registro de codigo como bash en el Solucion.md 


