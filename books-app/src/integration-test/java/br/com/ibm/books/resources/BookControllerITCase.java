package br.com.ibm.books.resources;

import br.com.ibm.books.entities.Book;
import br.com.ibm.books.repository.BooksRepository;
import br.com.ibm.books.vo.BookVO;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;
import java.util.Arrays;
import java.util.List;
import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class BookControllerITCase extends CommonITCase {

    private String baseURL;

    @LocalServerPort
    private int port;

    @Autowired
    private BooksRepository booksRepository;

    @Before
    public void setUp(){
        baseURL = "http://localhost:" + port;
    }

    @Test
    public void whenGetAllBooksThenReceiveSingleBook(){
        RestTemplate restTemplate = new RestTemplate();
        MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter = new MappingJackson2HttpMessageConverter();
        mappingJackson2HttpMessageConverter.setSupportedMediaTypes(Arrays.asList(MediaType.APPLICATION_JSON, MediaType.APPLICATION_OCTET_STREAM));
        restTemplate.getMessageConverters().add(mappingJackson2HttpMessageConverter);

        //given
        Book book = saveSingleBook();

        //when
        ResponseEntity<List<BookVO>> response = restTemplate.exchange(
        baseURL + "/api/books/",
            HttpMethod.GET,
            new HttpEntity<>(new HttpHeaders()),
            new ParameterizedTypeReference<List<BookVO>>() {});

        //then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody().size() >= 1);
    }

    @Test
    public void whenGetSingleBookByIdThenReceiveSingleBook(){
        RestTemplate restTemplate = new RestTemplate();
        MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter = new MappingJackson2HttpMessageConverter();
        mappingJackson2HttpMessageConverter.setSupportedMediaTypes(Arrays.asList(MediaType.APPLICATION_JSON, MediaType.APPLICATION_OCTET_STREAM));
        restTemplate.getMessageConverters().add(mappingJackson2HttpMessageConverter);

        //given
        Book book = saveSingleBook();

        //when
        ResponseEntity<Book> response = restTemplate.exchange(
        baseURL + "/api/books/" + book.getId(),
            HttpMethod.GET,
            new HttpEntity<>(new HttpHeaders()),
            Book.class);

        //then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(book, response.getBody());
    }

    @Test
    public void whenPostSingleBookThenItIsStoredInDb(){
        RestTemplate restTemplate = new RestTemplate();
        MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter = new MappingJackson2HttpMessageConverter();
        mappingJackson2HttpMessageConverter.setSupportedMediaTypes(Arrays.asList(MediaType.APPLICATION_JSON, MediaType.APPLICATION_OCTET_STREAM));
        restTemplate.getMessageConverters().add(mappingJackson2HttpMessageConverter);

        //given
        Book book = createSingleBook();

        //when
        ResponseEntity<Book> response = restTemplate.exchange(
        baseURL + "/api/books/",
            HttpMethod.POST,
            new HttpEntity<>(convertBookToDTO(book), new HttpHeaders()),
            Book.class);

        //then
        assertEquals(HttpStatus.CREATED, response.getStatusCode());

        Book responseBook = response.getBody();
        assertNotNull(responseBook.getId());
        assertEquals(book.getTitle(), responseBook.getTitle());
        assertEquals(book.getAuthor(), responseBook.getAuthor());
        assertEquals(book.getEvaluationGrade(), responseBook.getEvaluationGrade());

        Book savedBook = findBookInDbById(responseBook.getId()).get();
        assertEquals(responseBook.getId(), savedBook.getId());
        assertEquals(book.getTitle(), savedBook.getTitle());
        assertEquals(book.getAuthor(), savedBook.getAuthor());
        assertEquals(book.getEvaluationGrade(), savedBook.getEvaluationGrade());
    }

    @Test
    public void whenPostSingleBookWithBookAssignmentThenItIsStoredInDb(){
        RestTemplate restTemplate = new RestTemplate();
        MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter = new MappingJackson2HttpMessageConverter();
        mappingJackson2HttpMessageConverter.setSupportedMediaTypes(Arrays.asList(MediaType.APPLICATION_JSON, MediaType.APPLICATION_OCTET_STREAM));
        restTemplate.getMessageConverters().add(mappingJackson2HttpMessageConverter);

        //given
        Book book = createSingleBook();

        //when
        ResponseEntity<Book> response = restTemplate.exchange(
        baseURL + "/api/books/",
            HttpMethod.POST,
            new HttpEntity<>(convertBookToDTO(book), new HttpHeaders()),
            Book.class);

        //then
        assertEquals(HttpStatus.CREATED, response.getStatusCode());

        Book responseBook = response.getBody();
        assertNotNull(responseBook.getId());
        assertEquals(book.getTitle(), responseBook.getTitle());
        assertEquals(book.getAuthor(), responseBook.getAuthor());
        assertEquals(book.getEvaluationGrade(), responseBook.getEvaluationGrade());

        Book savedBook = findBookInDbById(responseBook.getId()).get();
        assertEquals(responseBook.getId(), savedBook.getId());
        assertEquals(book.getTitle(), savedBook.getTitle());
        assertEquals(book.getAuthor(), savedBook.getAuthor());
        assertEquals(book.getEvaluationGrade(), savedBook.getEvaluationGrade());
    }

    @Test
    public void whenPutSingleBookThenItIsUpdated(){
        RestTemplate restTemplate = new RestTemplate();
        MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter = new MappingJackson2HttpMessageConverter();
        mappingJackson2HttpMessageConverter.setSupportedMediaTypes(Arrays.asList(MediaType.APPLICATION_JSON, MediaType.APPLICATION_OCTET_STREAM));
        restTemplate.getMessageConverters().add(mappingJackson2HttpMessageConverter);

        //given
        Book book = saveSingleBook();
        book.setTitle(book.getTitle() + " Updated");

        //when
        ResponseEntity<Book> response = restTemplate.exchange(
        baseURL + "/api/books/" + book.getId(),
            HttpMethod.PUT,
            new HttpEntity<>(convertBookToDTO(book), new HttpHeaders()),
            Book.class);

        //then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        Assert.assertEquals(book.getTitle(), findBookInDbById(book.getId()).get().getTitle());
    }

    @Test
    public void whenDeleteSingleBookByIdThenItIsDeletedFromDb(){
        RestTemplate restTemplate = new RestTemplate();
        MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter = new MappingJackson2HttpMessageConverter();
        mappingJackson2HttpMessageConverter.setSupportedMediaTypes(Arrays.asList(MediaType.APPLICATION_JSON, MediaType.APPLICATION_OCTET_STREAM));
        restTemplate.getMessageConverters().add(mappingJackson2HttpMessageConverter);

        //given
        Book book = saveSingleBook();

        //when
        ResponseEntity<String> response = restTemplate.exchange(
        baseURL + "/api/books/" + book.getId(),
            HttpMethod.DELETE,
            new HttpEntity<>(new HttpHeaders()),
            String.class);

        //then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(String.format("Book with id: %d was deleted", book.getId()), response.getBody());
        assertFalse(findBookInDbById(book.getId()).isPresent());
    }
}
