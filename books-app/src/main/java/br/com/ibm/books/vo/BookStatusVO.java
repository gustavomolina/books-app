package br.com.ibm.books.vo;

import br.com.ibm.books.util.BookStatusType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookStatusVO {
    private String label;

    @Enumerated(EnumType.STRING)
    private BookStatusType value;
}
