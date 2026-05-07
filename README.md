# Laboratorio 1 - Tabla Hash

Implementación de una tabla hash en Java con manejo de colisiones mediante sondeo bidireccional y eliminación lógica.

## Estructura del proyecto

```
src/
├── HashTable.java   # Lógica principal de la tabla hash
├── Main.java        # Casos de prueba
├── Node.java        # Nodo con llave (String), valor (char) y marcador de eliminación
└── Utils.java       # Generador de tamaño primo aleatorio
```

## Función hash

Recorre cada carácter de la llave acumulando su valor ASCII más 31:

```
hash = 0
hash = hash + ascii(caracter) + 31   ← por cada carácter
índice = hash % n
```

**Ejemplo con "Perez" (n=61, valor primo generado aleatoriamente):**
```
(0+80+31=111) → (111+101+31=243) → (243+114+31=388) → (388+101+31=520) → (520+122+31=673)
673 % 61 = 2
```

## Manejo de colisiones

Sondeo bidireccional (open addressing):
1. Se calcula el índice directo con la función hash
2. Si hay colisión, sondea hacia **atrás** (index-1, index-2, ...)
3. Si no hay espacio hacia atrás, sondea hacia **adelante** (index+1, index+2, ...)
4. Si la tabla está llena, imprime un mensaje de error

## Eliminación lógica (Tombstone)

Los elementos no se eliminan físicamente. Se marcan como `[DELETED]` para no romper la cadena de sondeo durante búsquedas futuras.

## Ejecución

```bash
javac src/*.java
java -cp src Main
```

## Tamaño de la tabla

El tamaño se genera aleatoriamente entre 3 y 99, garantizando siempre un número primo para mejor distribución de las llaves.
