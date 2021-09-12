package br.com.ibm.books.resources;

import br.com.ibm.books.entities.Book;
import br.com.ibm.books.repository.BooksRepository;
import br.com.ibm.books.util.BookStatusType;
import br.com.ibm.books.vo.BookStatusVO;
import br.com.ibm.books.vo.BookVO;
import lombok.var;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.TestPropertySource;

import java.math.BigDecimal;
import java.util.Optional;

@TestPropertySource( properties = {
        "spring.datasource.url=jdbc:h2:mem:test",
        "spring.jpa.properties.hibernate.dialect = org.hibernate.dialect.H2Dialect"
})
public class CommonITCase {

    @Autowired
    private BooksRepository bookRepository;

    protected Book createSingleBook(){
        Book book = new Book();
        book.setTitle("Test Book ");
        book.setAuthor("Description ");
        book.setStatus(BookStatusType.LIDO);
        return book;
    }

    protected BookVO convertBookToDTO(Book book) {
        return new BookVO().builder()
                .title(book.getTitle())
                .author(book.getAuthor())
                .status(new BookStatusVO(book.getStatus().getDescription(), book.getStatus()))
                .build();
    }

    protected Book saveSingleBook(){
        return bookRepository.save(createSingleBook());
    }

    protected Optional<Book> findBookInDbById(Long id) {
        return bookRepository.findById(id);
    }
}
