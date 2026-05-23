package com.documentmanager.models.identidade;

import java.io.Serializable1;
import java.time.LocalDate;
/**
 * SuperClasse identidade
 * -dentro daquilo que tinhamos defenido na aula,pessoa,veiculo e subscrição;
 * -esta classe tem os atributos comuns a todas as outras classes, ou seja, id,nome e data de criação;
 * -serializable para que possa ser guradada em ficheiros e posteriormente lida;
 */

public class Identidade implements Serializable {
    private static final long serialVersionUID = 1L;

    protected String id;
    protected String nome;
    protected LocalDate datacriação;

public Identidade(String id, String nome, LocalDate datacriação) {
    this.id = id;
    this.nome = nome;
    this.datacriação = datacriação;
}

/** 
 * getters e setters
 * id unico para cada identidade, nome e data de criação
 */

public String getId() {
    return id;  
}

public String getNome() {
    return nome;
}

public LocalDate getDatacriação() {
    return datacriação;
}

/** setters, altera nome e para no novo nom */

public void setNome(String nome) {
    if (nome != null && !nome.trim().isEmpty()) {
        this.nome = nome;
    }
}

// metodo e override */

@Override
public String toString() {
    return "Identidade{" +
            "id='" + id + '\'' +
            ", nome='" + nome + '\'' +
            ", datacriação=" + datacriação +
            '}';
}

@Override
public boolean equals(Object obj) {
    if (this == obj) return true;
    if (obj == null || getClass() != obj.getClass()) return false;
    Identidade that = (Identidade) obj;
    return id.equals(that.id);
}

@Override
public int hashCode() {
    return id !=null? id.hashCode() : 0;
}
}


