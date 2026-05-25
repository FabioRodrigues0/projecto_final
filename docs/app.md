# App — Visão Geral

Aplicação desktop de gestão pessoal que centraliza documentos importantes, veículos e subscrições digitais num único sítio, com alertas para datas que estão prestes a expirar.

## Propósito

No dia-a-dia, a informação que mais custa perder está dispersa por vários sítios: garantias guardadas em gavetas, contratos em PDFs no email, faturas em pastas no computador, datas de seguro/IUC/inspeção anotadas em papel, e subscrições digitais espalhadas por dezenas de serviços diferentes. O resultado é previsível: garantias que expiram sem se reclamar, inspeções esquecidas que dão multa, e subscrições a debitar todos os meses sem ninguém saber bem porquê.

A aplicação resolve este problema sendo **um ponto único de consulta** para esta informação pessoal, com **alertas antecipados** antes de qualquer data crítica expirar.

## Utilizador-alvo e Contexto de Uso

- **Quem**: utilizadores domésticos / particulares que querem organizar a sua vida administrativa pessoal ou familiar.
- **Onde**: aplicação desktop, instalada localmente no PC do utilizador.
- **Quando**: consulta periódica (ex.: início do mês) e sempre que entra um novo documento, fatura, contrato ou subscrição.
- **Porquê neste formato**: dados sensíveis (documentos pessoais, dados de veículos, faturas) mantidos **localmente em SQLite**, sem dependência de cloud nem partilha com terceiros.

## Funcionalidades Principais

- **Dashboard**: visão geral com contadores rápidos (documentos, veículos, subscrições, alertas ativos), lista das próximas expirações nos próximos 30 dias e atalhos para os módulos.
- **Documentos**: registo de garantias, contratos, faturas e outros documentos relevantes.
- **Veículos**: registo de viaturas com controlo das datas de seguro, IUC e inspeção.
- **Subscrições**: gestão de serviços digitais recorrentes (Netflix, Spotify, software, etc.) com custos e renovações.
- **Navegação**: sidebar lateral colapsável que dá acesso direto aos quatro ecrãs principais.

## Benefícios para o Utilizador

- **Centralização**: substitui a mistura de papelada física, emails e folhas de cálculo por um único local organizado.
- **Antecipação**: avisa antes de seguros, inspeções ou contratos expirarem, evitando multas, cortes de serviço e perdas de garantia.
- **Privacidade**: toda a informação fica em base de dados local — nada é enviado para a internet.
- **Visão financeira**: ao listar todas as subscrições num só sítio, torna visível o peso real dos pagamentos recorrentes e ajuda a decidir quais cancelar.

## Stack Técnico (resumo)

- **Java 21** como linguagem base.
- **Bricks** — biblioteca de UI declarativa sobre **JavaFX 21**, com estado reativo.
- **SQLite** local para persistência (`./data/database.db`, criada automaticamente no arranque).
- Arquitetura **View / ViewModel**: cada ecrã (`DashboardView`, `DocumentosView`, `VeiculosView`, `SubscricaoView`) tem o seu ViewModel com estado reativo (`State<T>`) que a UI observa.

## Estado Atual do Projeto

- Dashboard ligado a `DashboardViewModel` com contadores reativos.
- Quatro views base implementadas com navegação funcional via sidebar.
- Schema SQLite inicializado automaticamente no arranque da aplicação.
- **Próximos passos**: persistência por entidade (CRUD de documentos, veículos e subscrições), lógica de cálculo das próximas expirações e sistema de alertas.
