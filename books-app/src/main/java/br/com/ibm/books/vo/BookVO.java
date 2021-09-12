package br.com.ibm.books.vo;

import br.com.ibm.books.util.BookStatusType;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookVO {
    private Long id;

    private String title;

    private String author;

    private LocalDateTime inclusionDate;

    private LocalDateTime dateOfTheConclusion;

    private Integer evaluationGrade;

    @Enumerated(EnumType.STRING)
    private BookStatusVO status;
}
