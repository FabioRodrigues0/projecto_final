import fabiorodrigues.bricks.data.DB;
import fabiorodrigues.bricks.data.config.SQLiteConfig;

/**
 * Configuração da ligação à base de dados. Por defeito usa SQLite — cria
 * ./data/database.db automaticamente. Para trocar de base de dados, descomenta
 * a configuração pretendida.
 */
public class DatabaseConfig {

    static {
        // SQLite (padrão) — sem configuração necessária
        DB.configure(new SQLiteConfig());

        // MySQL — descomentar e preencher credenciais
        // DB.configure(new MySQLConfig()
        // .host("localhost")
        // .port(3306)
        // .database("nome_db")
        // .user("root")
        // .password("")
        // );

        // PostgreSQL — descomentar e preencher credenciais
        // DB.configure(new PostgreSQLConfig()
        // .host("localhost")
        // .database("nome_db")
        // .user("postgres")
        // .password("")
        // );
    }
}
