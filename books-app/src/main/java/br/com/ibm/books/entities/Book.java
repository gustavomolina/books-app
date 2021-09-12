package br.com.ibm.books.entities;

import br.com.ibm.books.util.BookStatusType;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Entity
@NoArgsConstructor
@Table(name = "book")
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty(position = 1)
    private Long id;

    @ApiModelProperty(position = 2)
    private String title;

    @ApiModelProperty(position = 3)
    private String author;

    @ApiModelProperty(position = 4)
    private LocalDateTime inclusionDate;

    @ApiModelProperty(position = 5)
    private LocalDateTime dateOfTheConclusion;

    @ApiModelProperty(position = 6)
    private Integer evaluationGrade;

    @ApiModelProperty(position = 7)
    @Enumerated(EnumType.STRING)
    private BookStatusType status;
}
