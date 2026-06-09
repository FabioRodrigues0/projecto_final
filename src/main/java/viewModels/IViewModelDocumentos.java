package viewModels;

import java.util.List;

public abstract interface IViewModelDocumentos<T> {
    public default String nomeDocumento() {
        return "documento";
    }

    public abstract List<T> verDocumentos();

    public abstract void novoDocumento(int id);

    public abstract void updateDocumento(int id);

    public abstract void apagarDocumento(int id);
}
