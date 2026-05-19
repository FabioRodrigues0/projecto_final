---
tags:
  - contexto/PC
  - tipo/trabalho_pratico
  - conceito/java
  - area/programacao
data: 19-05-2026
disciplina:
  - PC
---
# projeto_final

## Descrição

projeto_final

## Conceitos abordados

- [[variavel|Variáveis]] e [[tipo_primitivo|tipos de dados]]
- 

## Estrutura do Projeto

- `src/main/java/App.java` - Classe principal
- `build.gradle` - Configuração Gradle
- `config/checkstyle/sun_checks.xml` - Regras Checkstyle (Sun/Oracle)
- `config/formatter/eclipse-formatter.xml` - Formatação automática

## Como executar

```bash
# Compilar
./gradlew build

# Executar
./gradlew run

# Formatar código automaticamente
./gradlew spotlessApply

# Verificar estilo (Checkstyle)
./gradlew checkstyleMain

# Limpar build
./gradlew clean
```

## Dependências

- **Java 21** - Versão do JDK
- **Bricks latest.release** - UI declarativa JavaFX (inclui JavaFX 21.0.5)
  - Base de dados SQLite em `./data/database.db` (criada automaticamente)
  - Schema: `DatabaseSchema.java`
- **SQLite JDBC 3.45.1.0** - Driver SQLite
- **Checkstyle 13.3.0** - Verificação de estilo (Sun/Oracle conventions)

## Relações

- [[variavel]]
- [[tipo_primitivo]]
