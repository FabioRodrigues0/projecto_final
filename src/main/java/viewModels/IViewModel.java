package viewModels;

import java.util.List;

/**
 * Define o contrato de IViewModel.
 */
public abstract interface IViewModel<T> {
    /**
     * Executa a operação nomeRecurso.
     *
     * @return resultado da operação
     */
    public default String nomeRecurso() {
        return "registo";
    }

    /**
     * Obtém os registos existentes.
     *
     * @return resultado da operação
     */
    public abstract List<T> ver();

    /**
     * Cria um novo registo.
     */
    public abstract void novo();

    /**
     * Atualiza um registo existente.
     *
     * @param id valor usado pela operação
     */
    public abstract void update(int id);

    /**
     * Remove um registo existente.
     *
     * @param id valor usado pela operação
     */
    public abstract void apagar(int id);
}
