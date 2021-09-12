package br.com.ibm.books.service;

import br.com.ibm.books.entities.Book;
import br.com.ibm.books.vo.BookVO;

import java.util.List;
import java.util.Optional;

public interface BooksService {

    List<BookVO> getAllBooks();

    Optional<Book> getBookById(Long id);

    Book saveNewBook(BookVO bookVO);

    Book updateBook(Long bookId, BookVO newBookVO);

    void deleteBook(Book book);
}
