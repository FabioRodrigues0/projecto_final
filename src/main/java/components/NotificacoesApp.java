package components;

import fabiorodrigues.bricks.components.Notification;
import fabiorodrigues.bricks.components.NotificationType;
import fabiorodrigues.bricks.core.BricksApplication;
import java.util.List;
import models.Expiracoes;
import models.TipoExpiracoes;
import viewModels.IViewModel;
import viewModels.IViewModelDocumentos;

public final class NotificacoesApp {

    private NotificacoesApp() {
    }

    public static void criado(BricksApplication app, IViewModel<?> vm) {
        sucesso(app, "Criação concluída: " + vm.nomeRecurso() + ".");
    }

    public static void atualizado(BricksApplication app, IViewModel<?> vm) {
        sucesso(app, "Atualização concluída: " + vm.nomeRecurso() + ".");
    }

    public static void removido(BricksApplication app, IViewModel<?> vm) {
        aviso(app, "Remoção concluída: " + vm.nomeRecurso() + ".");
    }

    public static void documentoCriado(BricksApplication app, IViewModelDocumentos<?> vm) {
        sucesso(app, "Criação concluída: " + vm.nomeDocumento() + ".");
    }

    public static void documentoAtualizado(BricksApplication app, IViewModelDocumentos<?> vm) {
        sucesso(app, "Atualização concluída: " + vm.nomeDocumento() + ".");
    }

    public static void documentoRemovido(BricksApplication app, IViewModelDocumentos<?> vm) {
        aviso(app, "Remoção concluída: " + vm.nomeDocumento() + ".");
    }

    public static void notificacoesSistemaAtivadas(
                                                   BricksApplication app, List<Expiracoes> expiracoes
    ) {
        if (expiracoes == null || expiracoes.isEmpty()) {
            Notification
                .toast(
                    app,
                    "Notificações ativadas. Não existem alertas pendentes.",
                    NotificationType.INFO
                );
            return;
        }

        expiracoes
            .forEach(
                expiracao -> Notification
                    .system(
                        app,
                        tituloExpiracao(expiracao),
                        mensagemExpiracao(expiracao),
                        tipoExpiracao(expiracao)
                    )
            );
        Notification
            .toast(
                app,
                "Notificações do sistema ativadas para " + expiracoes.size() + " alerta(s).",
                NotificationType.SUCCESS
            );
    }

    private static void sucesso(BricksApplication app, String mensagem) {
        Notification.toast(app, mensagem, NotificationType.SUCCESS);
    }

    private static void aviso(BricksApplication app, String mensagem) {
        Notification.toast(app, mensagem, NotificationType.WARNING);
    }

    private static String tituloExpiracao(Expiracoes expiracao) {
        return expiracao.tipo() == TipoExpiracoes.EXPIRADO ? expiracao
            .titulo() + " expirado" : expiracao.titulo() + " a vencer";
    }

    private static String mensagemExpiracao(Expiracoes expiracao) {
        int dias = expiracao.tipo() == TipoExpiracoes.EXPIRADO ? -expiracao.dias() : expiracao
            .dias();
        String prazo = DiasRestantes.texto(dias);

        if (expiracao.subTitulo() == null || expiracao.subTitulo().isBlank()) {
            return prazo;
        }

        return expiracao.subTitulo() + " - " + prazo;
    }

    private static NotificationType tipoExpiracao(Expiracoes expiracao) {
        return expiracao
            .tipo() == TipoExpiracoes.EXPIRADO ? NotificationType.ERROR : NotificationType.WARNING;
    }
}
