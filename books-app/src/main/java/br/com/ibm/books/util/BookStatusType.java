package br.com.ibm.books.util;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum BookStatusType {
    QUERO_LER("Quero ler"), LENDO("Lendo"), LIDO("Lido");

    private String description;

    public String getDescription(){
        return this.description;
    }
}
