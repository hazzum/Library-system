package com.hazem.library.rest.book;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hazem.library.entity.Book;
import com.hazem.library.rest.exceptionHandler.InternalServerErrorException;
import com.hazem.library.rest.exceptionHandler.NotFoundException;
import com.hazem.library.service.book.BookService;

@RestController
@RequestMapping("/books")
public class BookRestController {
    @Autowired
    private BookService bookService;

    // add mapping for GET /book
    @GetMapping("")
    public List<Book> index() {
        List<Book> theBooks = bookService.index();
        if (theBooks.isEmpty())
            throw new NotFoundException("No books found");
        return theBooks;
    }

    // add mapping for GET /books/{bookId}
    @GetMapping("{bookId}")
    public Book getBook(@PathVariable Long bookId) {
        return bookService.getBook(bookId);
    }

    // add mapping for POST /books - add new book
    @PostMapping("")
    public Book addBook(@RequestBody Book theBook) {
        try {
            return bookService.createBook(theBook);
        } catch (Exception e) {
            throw new InternalServerErrorException("Could not create book");
        }
    }

    // add mapping for PUT /books - update existing book
    @PutMapping("{bookId}")
    public Book updateBook(@RequestBody Book theBook, @PathVariable Long bookId) {
        Book tempBook = bookService.getBook(bookId);
        // throw exception if null
        if (tempBook == null)
            throw new NotFoundException("Book id not found - " + bookId);
        // update book
        theBook.setId(bookId);
        try {
            return bookService.updateBook(theBook);
        } catch (Exception e) {
            throw new InternalServerErrorException("Could not update book");
        }
    }

    // add mapping Delete /books/{bookId} - delete existing book
    @DeleteMapping("{bookId}")
    public String deleteBook(@PathVariable Long bookId) {
        Book tempBook = bookService.getBook(bookId);
        // throw exception if null
        if (tempBook == null)
            throw new NotFoundException("Book id not found - " + bookId);
        try {
            bookService.deleteBook(bookId);
            return "Deleted Book id - " + bookId;
        } catch (Exception e) {
            throw new InternalServerErrorException("Could not delete book");
        }
    }
}
