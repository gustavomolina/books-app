package br.com.ibm.books.vo;

import br.com.ibm.books.util.BookStatusType;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)

public class BookResponseVO {
    private Long id;

    private String title;

    private String author;

    private LocalDateTime inclusionDate;

    private LocalDateTime dateOfTheConclusion;

    private Integer evaluationGrade;

    private BookStatusType status;
}
