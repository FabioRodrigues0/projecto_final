
/**
 * Define o schema da base de dados. Chamado automaticamente no arranque via
 * Effect em App.java. Adiciona aqui as definições das tabelas.
 */
public class DatabaseSchema {

    private DatabaseSchema() {
    }

    /**
     * Cria as tabelas se não existirem. Seguro para chamar múltiplas vezes — usa
     * CREATE TABLE IF NOT EXISTS.
     */
    public static void create() {
        // Exemplo — descomenta e adapta:
        // DB.query()
        // .createTableIfNotExists("exemplo")
        // .column("id", "INTEGER PRIMARY KEY AUTOINCREMENT")
        // .column("nome", "TEXT NOT NULL")
        // .execute();
    }
}
