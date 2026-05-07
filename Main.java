public class Main {
    public static void main(String[] args) {

        // ── Prueba 1: tabla con tamaño primo aleatorio ──────────────────────
        Utils utils = new Utils();
        int size = utils.randomBuckets(3, 100);
        System.out.println("Creando tabla hash con " + size + " buckets\n");
        HashTable table = new HashTable(size);

        System.out.println("--- Insertando elementos ---\n");
        table.put("Perez",    'A');
        table.put("Garcia",   'B');
        table.put("Lopez",    'C');
        table.put("Martinez", 'D');
        table.put("Gomez",    'E');
        table.put("Herrera",  'F');
        table.put("Torres",   'G');
        table.put("Ramirez",  'H');

        table.display();

        // Busqueda
        System.out.println("--- Buscando elementos ---");
        System.out.println("get(\"Perez\")   = " + table.get("Perez"));
        System.out.println("get(\"Garcia\")  = " + table.get("Garcia"));
        System.out.println("get(\"Sanchez\") = " + table.get("Sanchez")); // no existe

        // Eliminacion
        System.out.println("\n--- Eliminando key=\"Garcia\" ---");
        table.remove("Garcia");
        table.display();

        // Buscar la clave eliminada
        System.out.println("--- Buscando \"Garcia\" despues de eliminar ---");
        System.out.println("get(\"Garcia\") = " + table.get("Garcia")); // debe ser null

        // ── Prueba 2: tabla pequeña para forzar "sin espacio" ───────────────
        System.out.println("\n======================================");
        System.out.println("Prueba con tabla de 5 buckets (forzar tabla llena)");
        System.out.println("======================================\n");

        HashTable small = new HashTable(5);
        small.put("Ana",   'X');
        small.put("Beto",  'Y');
        small.put("Carla", 'Z');
        small.put("Diana", 'W');
        small.put("Edwin", 'V');
        small.display();

        // Ahora la tabla esta llena, este debe fallar:
        small.put("Fiona", 'Q');
    }
}