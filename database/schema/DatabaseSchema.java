
import fabiorodrigues.bricks.data.DB;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Define o schema da base de dados. Chamado automaticamente no arranque via
 * Effect em App.java. Adiciona aqui as definições das tabelas.
 */
public class DatabaseSchema {

    private static final String TEXT_NOT_NULL = "TEXT NOT NULL ";
    private static final String PESSOAL_TIPO;
    private static final String VEICULO_TIPO;
    private static final String SUBSCRICAO_TIPO;
    private static final String PAGAMENTO_TIPO;
    private static final String ATIVA_BOOLEAN = "INTEGER NOT NULL DEFAULT 1 CHECK(ativa IN (0, 1))";
    private static final String FK_PESSOA = "REFERENCES pessoas(id) ON DELETE CASCADE";
    private static final String FK_VEICULO = "REFERENCES veiculos(id) ON DELETE CASCADE";
    private static final String FK_SUBSCRICAO = "REFERENCES subscricoes(id) ON DELETE CASCADE";

    static {
        PESSOAL_TIPO = textCheck("tipo", "'GARANTIA'", "'CONTRATO'", "'FATURA'", "'OUTRO'");
        VEICULO_TIPO = textCheck("tipo", "'SEGURO'", "'IUC'", "'INSPECAO'", "'OUTRO'");
        SUBSCRICAO_TIPO = textCheck(
            "tipo",
            "'STREAMING'",
            "'SOFTWARE'",
            "'SERVICO_ONLINE'",
            "'OUTRO'"
        );
        PAGAMENTO_TIPO = textCheck("modelo_pagamento", "'MENSAL'", "'ANUAL'");
    }

    private DatabaseSchema() {
    }

    /**
     * Cria as tabelas se não existirem. Seguro para chamar múltiplas vezes — usa
     * CREATE TABLE IF NOT EXISTS.
     */
    public static void create() {
        enableForeignKeys();
        createPessoas();
        createVeiculos();
        createSubscricoes();
        createDocumentosPessoal();
        createDocumentosVeiculo();
        createDocumentosSubscricao();
    }

    private static void enableForeignKeys() {
        try (Statement statement = DB.getConnection().createStatement()) {
            statement.execute("PRAGMA foreign_keys = ON");
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao ativar foreign keys no SQLite", e);
        }
    }

    private static void createPessoas() {
        DB
            .query()
            .createTableIfNotExists("pessoas")
            .column("id", "INTEGER PRIMARY KEY AUTOINCREMENT")
            .column("nome", "TEXT NOT NULL")
            .column("data", "TEXT NOT NULL")
            .execute();
    }

    private static void createVeiculos() {
        DB
            .query()
            .createTableIfNotExists("veiculos")
            .column("id", "INTEGER PRIMARY KEY AUTOINCREMENT")
            .column("nome", "TEXT NOT NULL")
            .column("data", "TEXT NOT NULL")
            .column("ano", "INTEGER NOT NULL")
            .column("matricula", "TEXT NOT NULL UNIQUE")
            .column("foto", "TEXT")
            .execute();
    }

    private static void createSubscricoes() {
        DB
            .query()
            .createTableIfNotExists("subscricoes")
            .column("id", "INTEGER PRIMARY KEY AUTOINCREMENT")
            .column("nome", "TEXT NOT NULL")
            .column("data", "TEXT NOT NULL")
            .column("logo", "TEXT")
            .execute();
    }

    private static void createDocumentosPessoal() {
        DB
            .query()
            .createTableIfNotExists("documentos_pessoal")
            .column("id", "INTEGER PRIMARY KEY AUTOINCREMENT")
            .column("pessoa_id", "INTEGER NOT NULL")
            .column("titulo", "TEXT NOT NULL")
            .column("tipo", PESSOAL_TIPO)
            .column("data_emissao", "TEXT NOT NULL")
            .column("data_validade", "TEXT NOT NULL")
            .column("notas", "TEXT")
            .column("FOREIGN KEY (pessoa_id)", FK_PESSOA)
            .execute();
    }

    private static void createDocumentosVeiculo() {
        DB
            .query()
            .createTableIfNotExists("documentos_veiculo")
            .column("id", "INTEGER PRIMARY KEY AUTOINCREMENT")
            .column("veiculo_id", "INTEGER NOT NULL")
            .column("titulo", "TEXT NOT NULL")
            .column("tipo", VEICULO_TIPO)
            .column("data_validade", "TEXT NOT NULL")
            .column("seguradora", "TEXT")
            .column("cobertura", "TEXT")
            .column("valor", "REAL NOT NULL DEFAULT 0")
            .column("notas", "TEXT")
            .column("FOREIGN KEY (veiculo_id)", FK_VEICULO)
            .execute();
    }

    private static void createDocumentosSubscricao() {
        DB
            .query()
            .createTableIfNotExists("documentos_subscricao")
            .column("id", "INTEGER PRIMARY KEY AUTOINCREMENT")
            .column("subscricao_id", "INTEGER NOT NULL")
            .column("titulo", "TEXT NOT NULL")
            .column("tipo", SUBSCRICAO_TIPO)
            .column("modelo_pagamento", PAGAMENTO_TIPO)
            .column("custo", "REAL NOT NULL DEFAULT 0")
            .column("plano", "TEXT")
            .column("data_renovacao", "TEXT NOT NULL")
            .column("ativa", ATIVA_BOOLEAN)
            .column("notas", "TEXT")
            .column("FOREIGN KEY (subscricao_id)", FK_SUBSCRICAO)
            .execute();
    }

    private static String textCheck(String column, String... values) {
        return TEXT_NOT_NULL + "CHECK(" + column + " IN (" + String.join(", ", values) + "))";
    }
}
