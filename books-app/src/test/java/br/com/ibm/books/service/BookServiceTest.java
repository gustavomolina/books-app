package br.com.ibm.books.service;

import br.com.ibm.books.entities.Book;
import br.com.ibm.books.repository.BooksRepository;
import br.com.ibm.books.util.BookStatusType;
import br.com.ibm.books.util.DefaultException;
import br.com.ibm.books.vo.BookStatusVO;
import br.com.ibm.books.vo.BookVO;
import lombok.var;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;
import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
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
    public void when2BooksInDatabaseThenGetListWithAllOfThem() {
        //given
        mockBooksInDatabase(2);

        //when
        List<BookVO> books = booksService.getAllBooks();

        //then
        assertEquals(2, books.size());
    }

    @Test
    public void when1BookInDatabaseThenGetItById() {
        //given
        mockBookInDatabase(1L);

        //when
        var book = booksService.getBookById(1L);

        //then
        assertTrue(book.isPresent());
    }

    @Test
    public void whenNoBookInDatabaseThenNotFoundGetItById() {
        //given
        mockNotFoundBooksWhenGetByIdInDatabase(1L);
        Exception exception = null;

        //when
        try {
            booksService.getBookById(1L);
        } catch (DefaultException e) {
            exception = e;
        }

        //then
        assertNotNull(exception);
    }

    @Test
    public void when1BookInDatabaseThenNotFoundWhenDeleteItById() {
        //given
        mockNotFoundBooksWhenDeleteInDatabase();
        Exception exception = null;

        //when
        try {
            booksService.deleteBook(mockBook(2L));
        } catch (DefaultException e) {
            exception = e;
        }

        //then
        assertNotNull(exception);
    }

    @Test
    public void when1BookInDatabaseThenUpdateById() {
        //given
        mockSaveBookIntoDatabase(1L);
        mockBookInDatabase(1L);

        //when
        Book oldBook = mockBook(1L);
        BookVO newBookVO = mockBookVO(1L);
        Book responseEntity = booksService.updateBook(1L, newBookVO);

        //then
        assertEquals(responseEntity.getId(), oldBook.getId());
    }

    @Test
    public void whenNoBookInDatabaseThenNotFoundUpdateById() {
        //given
        mockSaveBookIntoDatabase(1L);
        mockNotFoundBooksWhenGetByIdInDatabase(1L);
        Exception exception = null;
        BookVO newBookVO = mockBookVO(1L);

        //when
        try {
            booksService.updateBook(1L, newBookVO);
        } catch (DefaultException e) {
            exception = e;
        }

        //then
        assertNotNull(exception);
    }

    @Test
    public void whenNoBookInDatabaseThenCreate() {
        //given
        mockSaveBookIntoDatabase(1L);

        //when
        Book book = booksService.saveNewBook(mockBookVO(1L));

        //then
        assertNotNull(book);
    }

    private void mockBooksInDatabase(int bookCount) {
        when(bookRepository.findAll())
                .thenReturn(createBooksList(bookCount));
    }

    private void mockNotFoundBooksWhenDeleteInDatabase() {
        doThrow(DefaultException.class).when(bookRepository).delete(any(Book.class));
    }

    private void mockNotFoundBooksWhenGetByIdInDatabase(Long bookId) {
        doThrow(DefaultException.class).when(bookRepository).findById(bookId);
    }

    private void mockBookInDatabase(Long bookId) {
        when(bookRepository.findById(bookId))
                .thenReturn(Optional.of(mockBook(bookId)));
    }

    private void mockSaveBookIntoDatabase(Long bookId) {
        when(bookRepository.save(any(Book.class)))
                .thenReturn(mockBook(bookId));
    }

    private List<Book> createBooksList(int bookCount) {
        List<Book> books = new ArrayList<>();
        IntStream.range(0, bookCount)
                .forEach(number ->{
                    books.add(mockBook((long) number));
                });
        return books;
    }

    private Book mockBook(Long number) {
        Book book = new Book();
        book.setId(number);
        book.setTitle("Mocked Title");
        book.setAuthor("Mocked Author");
        book.setInclusionDate(LocalDate.now());
        book.setDateOfTheConclusion(null);
        book.setEvaluationGrade(10);
        book.setStatus(BookStatusType.LIDO);
        return book;
    }

    private BookVO mockBookVO(Long number) {
        BookVO vo = new BookVO();
        vo.setId(number);
        vo.setTitle("Mocked Title");
        vo.setAuthor("Mocked Author");
        vo.setInclusionDate(LocalDateTime.now());
        vo.setDateOfTheConclusion(null);
        vo.setEvaluationGrade(10);
        vo.setStatus(new BookStatusVO(BookStatusType.LIDO.getDescription(), BookStatusType.LIDO));
        return vo;
    }
}
