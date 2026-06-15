package models;

/**
 * Representa Documentos na aplicação.
 */
public abstract class Documentos {
    public int id;
    public String titulo;
    public String notas;

    /**
     * Cria uma nova instância.
     */
    public Documentos() {
    }

    /**
     * Cria uma nova instância.
     *
     * @param id     valor usado pela operação
     * @param titulo valor usado pela operação
     */
    public Documentos(int id, String titulo) {
        this.id = id;
        this.titulo = titulo;
    }

    /**
     * Cria uma nova instância.
     *
     * @param id     valor usado pela operação
     * @param titulo valor usado pela operação
     * @param notas  valor usado pela operação
     */
    public Documentos(int id, String titulo, String notas) {
        this.id = id;
        this.titulo = titulo;
        this.notas = notas;
    }

    /**
     * Executa a operação getId.
     *
     * @return resultado da operação
     */
    public abstract int getId();

    /**
     * Executa a operação setId.
     *
     * @param id valor usado pela operação
     */
    public abstract void setId(int id);

    /**
     * Executa a operação getTitulo.
     *
     * @return resultado da operação
     */
    public abstract String getTitulo();

    /**
     * Executa a operação setTitulo.
     *
     * @param titulo valor usado pela operação
     */
    public abstract void setTitulo(String titulo);

    /**
     * Executa a operação getNotas.
     *
     * @return resultado da operação
     */
    public abstract String getNotas();

    /**
     * Executa a operação setNotas.
     *
     * @param notas valor usado pela operação
     */
    public abstract void setNotas(String notas);
}
