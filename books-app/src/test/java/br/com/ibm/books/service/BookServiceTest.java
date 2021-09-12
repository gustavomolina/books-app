package br.com.ibm.books.service;

import br.com.ibm.books.entities.Book;
import br.com.ibm.books.repository.BooksRepository;
import br.com.ibm.books.util.BookStatusType;
import br.com.ibm.books.vo.BookVO;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class BookServiceTest {

    BooksService booksService;
    @Mock
    BooksRepository bookRepository;

    @Before
    public void init() {
        booksService = new BooksServiceImpl(bookRepository);
    }

    @Test
    public void when2BooksInDatabase_thenGetListWithAllOfThem() {
        //given
        mockBooksInDatabase(2);

        //when
        List<BookVO> books = booksService.getAllBooks();

        //then
        assertEquals(2, books.size());
    }

    private void mockBooksInDatabase(int bookCount) {
        when(bookRepository.findAll())
                .thenReturn(createBooksList(bookCount));
    }

    private List<Book> createBooksList(int bookCount) {
        List<Book> books = new ArrayList<>();
        IntStream.range(0, bookCount)
                .forEach(number ->{
                    Book book = new Book();
                    book.setId(Long.valueOf(number));
                    book.setTitle("Mocked Title");
                    book.setTitle("Mocked Author");
                    book.setInclusionDate(LocalDate.now());
                    book.setDateOfTheConclusion(null);
                    book.setEvaluationGrade(number);
                    book.setStatus(BookStatusType.LIDO);
                    books.add(book);
                });
        return books;
    }
}
