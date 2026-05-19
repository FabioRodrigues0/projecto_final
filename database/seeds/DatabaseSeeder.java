
/**
 * Dados iniciais da base de dados. Chamar DatabaseSeeder.run() no Effect initDB
 * do App.java se necessário. Só deve correr quando a base de dados está vazia.
 */
public class DatabaseSeeder {

    private DatabaseSeeder() {
    }

    /**
     * Insere dados iniciais nas tabelas. Exemplo de uso no App.java:
     *
     * private final Effect initDB = effect(() -> { DatabaseSchema.create();
     * DatabaseSeeder.run(); });
     */
    public static void run() {
        // Exemplo — descomenta e adapta:
        // DB.query()
        // .insertInto("exemplo")
        // .values(Map.of("nome", "Valor inicial"))
        // .execute();
    }
}
