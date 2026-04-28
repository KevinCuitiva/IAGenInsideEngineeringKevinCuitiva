## Solucion - Problema 1: Videoclub de Don Mario

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


