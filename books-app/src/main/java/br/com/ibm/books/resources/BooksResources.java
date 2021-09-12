package br.com.ibm.books.resources;

import br.com.ibm.books.entities.Book;
import br.com.ibm.books.vo.BookVO;
import br.com.ibm.books.service.BooksService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/books")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
public class BooksResources {

    private final BooksService booksService;

    @GetMapping("/")
    @ApiOperation(value="View a list of all books", response = Book.class, responseContainer = "List")
    public ResponseEntity<?> getAllBooks(){
        try {
            return new ResponseEntity<>(
                    booksService.getAllBooks(),
                    HttpStatus.OK);
        } catch (Exception e) {
            return errorResponse();
        }
    }

    @GetMapping("/{id}")
    @ApiOperation(value="Find a books by its id", response = Book.class)
    public ResponseEntity<?> getBook(@PathVariable Long id){
        try {
            Optional<Book> optBook = booksService.getBookById(id);
            if (optBook.isPresent()) {
                return new ResponseEntity<>(
                        optBook.get(),
                        HttpStatus.OK);
            } else {
                return noBookFoundResponse(id);
            }
        } catch (Exception e) {
            return errorResponse();
        }
    }

    @PostMapping("")
    @ApiOperation(value="Save new books", response = Book.class)
    public ResponseEntity<?> createBook(@RequestBody BookVO bookVO){
        try {
            return new ResponseEntity<>(
                    booksService.saveNewBook(bookVO),
                    HttpStatus.CREATED);
        } catch (Exception e) {
            return errorResponse();
        }
    }

    @PutMapping("/{id}")
    @ApiOperation(value="Update a books with specific id", response = Book.class)
    public ResponseEntity<?> updateBook(@PathVariable Long id, @RequestBody BookVO bookVO){
        try {
            Optional<Book> optBook = booksService.getBookById(id);
            if (optBook.isPresent()) {
                return new ResponseEntity<>(
                        booksService.updateBook(optBook.get(), bookVO),
                        HttpStatus.OK);
            } else {
                return noBookFoundResponse(id);
            }
        } catch (Exception e) {
            return errorResponse();
        }
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value="Delete books with specific id", response = String.class)
    public ResponseEntity<?> deleteBook(@PathVariable Long id){
        try {
            Optional<Book> optBook = booksService.getBookById(id);
            if (optBook.isPresent()) {
                booksService.deleteBook(optBook.get());
                return new ResponseEntity<>(String.format("Book with id: %d was deleted", id), HttpStatus.OK);
            } else {
                return noBookFoundResponse(id);
            }
        } catch (Exception e) {
            return errorResponse();
        }
    }

    private ResponseEntity<String> errorResponse(){
        return new ResponseEntity<>("Something went wrong :(", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private ResponseEntity<String> noBookFoundResponse(Long id){
        return new ResponseEntity<>("No books found with id: " + id, HttpStatus.NOT_FOUND);
    }

}
