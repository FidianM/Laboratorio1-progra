public class HashTable {
    private Node[] buckets;
    private int size; // n = cantidad de buckets

    public HashTable(int size) {
        this.size = size;
        this.buckets = new Node[size];
    }

    /**
     * Funcion hash: recorre cada caracter de la llave (String) y acumula:
     *   hash = hash + ascii(caracter) + 31
     * Al final retorna hash % size.
     *
     * Ejemplo con "Perez" (size=11):
     *   hash = 0
     *   hash = 0   + 80  + 31 = 111   ('P')
     *   hash = 111 + 101 + 31 = 243   ('e')
     *   hash = 243 + 114 + 31 = 388   ('r')
     *   hash = 388 + 101 + 31 = 520   ('e')
     *   hash = 520 + 122 + 31 = 673   ('z')
     *   indice = 673 % size
     */
    private int hash(String key) {
        int h = 0;
        for (char c : key.toCharArray()) {
            h = h + (int) c + 31;
        }
        int index = Math.abs(h) % size;

        // Mostrar el calculo paso a paso
        System.out.print("  hash(\"" + key + "\") -> ");
        int temp = 0;
        for (char c : key.toCharArray()) {
            System.out.print("(" + temp + "+" + (int) c + "+31=" + (temp + (int) c + 31) + ") ");
            temp = temp + (int) c + 31;
        }
        System.out.println("-> " + temp + " % " + size + " = " + index);

        return index;
    }

    /**
     * Inserta un par (key, value) en la tabla.
     *
     * Estrategia de colision vertical (open addressing bidireccional):
     *   1. Calcular index = hash(key)
     *   2. Si buckets[index] esta libre -> insertar ahi
     *   3. Si esta ocupado -> sondear hacia ATRAS (index-1, index-2, ...)
     *   4. Si no hay espacio hacia atras -> sondear hacia ADELANTE (index+1, index+2, ...)
     *   5. Si no hay espacio en ninguna direccion -> imprimir mensaje de error
     */
    public void put(String key, char value) {
        System.out.println("put(\"" + key + "\",'" + value + "'):");
        int index = hash(key);
        System.out.println("  -> index calculado = " + index);

        // Caso: la clave ya existe -> actualizar valor
        int existingSlot = findSlot(key);
        if (existingSlot != -1) {
            buckets[existingSlot].value = value;
            buckets[existingSlot].deleted = false;
            System.out.println("  -> Clave \"" + key + "\" ya existia en bucket[" + existingSlot + "], valor actualizado.");
            return;
        }

        // Bucket destino esta libre
        if (isAvailable(index)) {
            buckets[index] = new Node(key, value);
            System.out.println("  -> Insertado en bucket[" + index + "] (posicion directa).");
            return;
        }

        // Colision: sondeo hacia ATRAS
        System.out.println("  ** Colision en bucket[" + index + "] - sondeando hacia atras...");
        for (int step = 1; step < size; step++) {
            int backIndex = index - step;
            if (backIndex < 0) break;
            if (isAvailable(backIndex)) {
                buckets[backIndex] = new Node(key, value);
                System.out.println("  -> Insertado en bucket[" + backIndex + "] (sondeo hacia atras, paso " + step + ").");
                return;
            }
        }

        // Sondeo hacia ADELANTE
        System.out.println("  ** No hay espacio hacia atras - sondeando hacia adelante...");
        for (int step = 1; step < size; step++) {
            int fwdIndex = index + step;
            if (fwdIndex >= size) break;
            if (isAvailable(fwdIndex)) {
                buckets[fwdIndex] = new Node(key, value);
                System.out.println("  -> Insertado en bucket[" + fwdIndex + "] (sondeo hacia adelante, paso " + step + ").");
                return;
            }
        }

        // Sin espacio disponible en toda la tabla
        System.out.println("  !! No es posible almacenar la clave \"" + key
                + "\": no hay posiciones disponibles en la tabla.");
    }

    /**
     * Busca la clave en la tabla.
     */
    public Character get(String key) {
        int slot = findSlot(key);
        if (slot != -1) {
            return buckets[slot].value;
        }
        return null;
    }

    /**
     * Elimina la clave de la tabla usando un marcador logico (tombstone = DELETED).
     */
    public void remove(String key) {
        int slot = findSlot(key);
        if (slot != -1) {
            buckets[slot].deleted = true;
            System.out.println("remove(\"" + key + "\") -> marcado como DELETED en bucket[" + slot + "].");
        } else {
            System.out.println("remove(\"" + key + "\") -> clave no encontrada.");
        }
    }

    /**
     * Localiza el bucket donde esta almacenada una clave.
     * Replica el patron de sondeo de put():
     *   posicion directa -> hacia atras -> hacia adelante
     * Los nodos marcados como DELETED se saltan.
     * Un bucket null SI detiene la busqueda en esa direccion.
     *
     * @return indice del bucket, o -1 si no se encuentra.
     */
    private int findSlot(String key) {
        int index = hash(key);

        // Revisar posicion directa
        if (buckets[index] != null && !buckets[index].deleted
                && buckets[index].key.equals(key)) {
            return index;
        }

        // Sondear hacia atras
        for (int step = 1; step < size; step++) {
            int backIndex = index - step;
            if (backIndex < 0) break;
            if (buckets[backIndex] == null) break;
            if (!buckets[backIndex].deleted && buckets[backIndex].key.equals(key)) {
                return backIndex;
            }
        }

        // Sondear hacia adelante
        for (int step = 1; step < size; step++) {
            int fwdIndex = index + step;
            if (fwdIndex >= size) break;
            if (buckets[fwdIndex] == null) break;
            if (!buckets[fwdIndex].deleted && buckets[fwdIndex].key.equals(key)) {
                return fwdIndex;
            }
        }

        return -1;
    }

    // Un bucket esta disponible si es null o si tiene un tombstone (DELETED)
    private boolean isAvailable(int index) {
        return buckets[index] == null || buckets[index].deleted;
    }

    public void display() {
        System.out.println("\n=== Estado de la Tabla Hash (n=" + size
                + ", hash = suma(ascii(c)+31) % " + size + ") ===");
        for (int i = 0; i < size; i++) {
            String content = (buckets[i] == null) ? "vacio" : buckets[i].toString();
            System.out.println("Bucket[" + i + "]: " + content);
        }
        System.out.println("=============================================\n");
    }
}