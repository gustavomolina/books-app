package br.com.ibm.books.service;

import br.com.ibm.books.entities.Book;
import br.com.ibm.books.repository.BooksRepository;
import br.com.ibm.books.util.DefaultException;
import br.com.ibm.books.vo.BookStatusVO;
import br.com.ibm.books.vo.BookVO;
import lombok.RequiredArgsConstructor;
import lombok.var;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BooksServiceImpl implements BooksService {

    private final BooksRepository booksRepository;

    @Override
    @Transactional
    public List<BookVO> getAllBooks() {
        List<BookVO> bookVoList = new ArrayList<>();
        booksRepository.findAll().forEach(book -> bookVoList.add(convertBookToVO(book)));
        return bookVoList;
    }

    @Override
    @Transactional
    public Optional<Book> getBookById(Long id) {
        return booksRepository.findById(id);
    }

    @Override
    @Transactional
    public Book saveNewBook(BookVO bookVO) {
        var newBook = convertVOToBook(bookVO);
        return booksRepository.save(newBook);
    }

    @Override
    @Transactional
    public Book updateBook(Long bookId, BookVO newBookVO) {
        var oldBook = getBookById(bookId).orElseThrow(new DefaultException(HttpStatus.NOT_FOUND, "Book not found"));;

        return booksRepository.save(updateBookFromVO(oldBook, newBookVO));
    }

    @Override
    @Transactional
    public void deleteBook(Book book) {
        booksRepository.delete(book);
    }

    private Book convertVOToBook(BookVO bookVO) {
        Book book = new Book();
        book.setId(bookVO.getId());
        book.setTitle(bookVO.getTitle());
        book.setAuthor(bookVO.getAuthor());
        book.setEvaluationGrade(bookVO.getEvaluationGrade());
        book.setDateOfTheConclusion(bookVO.getDateOfTheConclusion() != null ? bookVO.getDateOfTheConclusion().toLocalDate() : null);
        book.setInclusionDate(bookVO.getInclusionDate() != null ? bookVO.getInclusionDate().toLocalDate() : null);
        book.setStatus(bookVO.getStatus().getValue());
        return book;
    }

    private BookVO convertBookToVO(Book book) {
        BookVO vo = new BookVO();
        vo.setId(book.getId());
        vo.setTitle(book.getTitle());
        vo.setAuthor(book.getAuthor());
        vo.setInclusionDate(book.getInclusionDate() != null ? book.getInclusionDate().atTime(00,00,00) : null);
        vo.setDateOfTheConclusion(book.getDateOfTheConclusion() != null ? book.getDateOfTheConclusion().atTime(00,00,00) : null);
        vo.setEvaluationGrade(book.getEvaluationGrade());
        vo.setStatus(new BookStatusVO(book.getStatus().getDescription(), book.getStatus()));
        return vo;
    }

    private Book updateBookFromVO(Book book, BookVO bookVO){
        if(Optional.ofNullable(bookVO.getTitle()).isPresent()){
            book.setTitle(bookVO.getTitle());
        }

        if (Optional.ofNullable((bookVO.getAuthor())).isPresent()) {
            book.setAuthor(bookVO.getAuthor());
        }

        if (Optional.ofNullable((bookVO.getEvaluationGrade())).isPresent()) {
            book.setEvaluationGrade(bookVO.getEvaluationGrade());
        }

        if (Optional.ofNullable((bookVO.getStatus())).isPresent()) {
            book.setStatus(bookVO.getStatus().getValue());
        }

        if (Optional.ofNullable((bookVO.getDateOfTheConclusion())).isPresent()) {
            book.setDateOfTheConclusion(bookVO.getDateOfTheConclusion().toLocalDate());
        }

        if (Optional.ofNullable((bookVO.getInclusionDate())).isPresent()) {
            book.setInclusionDate(bookVO.getInclusionDate().toLocalDate());
        }

        return book;
    }
}
