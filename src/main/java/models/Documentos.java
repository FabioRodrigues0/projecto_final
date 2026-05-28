package models;

public abstract class Documentos {
    public int id;
    public String titulo;
    public String notas;

    public Documentos() {
    }

    public Documentos(int id, String titulo) {
        this.id = id;
        this.titulo = titulo;
    }

    public Documentos(int id, String titulo, String notas) {
        this.id = id;
        this.titulo = titulo;
        this.notas = notas;
    }

    public abstract int getId();

    public abstract void setId(int id);

    public abstract String getTitulo();

    public abstract void setTitulo(String titulo);

    public abstract String getNotas();

    public abstract void setNotas(String notas);
}
