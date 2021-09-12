package br.com.ibm.books.service;

import br.com.ibm.books.config.H2DatabaseConfig4Test;
import br.com.ibm.books.entities.Book;
import br.com.ibm.books.vo.BookVO;
import br.com.ibm.books.repository.BooksRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { H2DatabaseConfig4Test.class })
public class BookServiceITCase {

    @Autowired
    private BooksRepository booksRepository;
    private BooksService booksService;


    @Before
    public void init() {
        booksService = new BooksServiceImpl(booksRepository);
    }


    @Test
    public void whenCreated_thenIsSavedInDb() {
        //given
        BookVO bookVO = BookVO.builder()
                                    .title("Test")
                                .build();

        //when
        booksService.saveNewBook(bookVO);

        //then
        List<Book> books = (List<Book>) booksRepository.findAll();

        assertNotNull(books.get(0));
        assertEquals("Test", books.get(0).getTitle());
    }
}
