package com.hazem.library.service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;

import com.hazem.library.DAO.BookRepository;
import com.hazem.library.entity.Book;
import com.hazem.library.service.book.BookServiceImpl;

public class BookServiceTest {

    @InjectMocks
    private BookServiceImpl bookService;

    @Mock
    private BookRepository bookRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllBooks() {
        // Arrange
        Book book1 = new Book("Book Title 1", "Description 1", "Author 1", "2020", "ISBN001", 9.99F, 6);
        Book book2 = new Book("Book Title 2", "Description 2", "Author 2", "2021", "ISBN002", 19.99F, 12);
        List<Book> books = Arrays.asList(book1, book2);
        when(bookRepository.findAllByOrderByIdAsc()).thenReturn(books);

        // Act
        List<Book> result = bookService.index();
        // Assert
        assertEquals(2, result.size());
        assertEquals("Book Title 1", result.get(0).getTitle());
    }

    @Test
    void testGetBookById() {
        // Arrange
        Book book = new Book("Book Title 1", "Description 1", "Author 1", "2020", "ISBN001", 9.99F, 6);
        when(bookRepository.findById(1L)).thenReturn(Optional.of(book));

        // Act
        Book result = bookService.getBook(1L);

        // Assert
        assertTrue(result != null);
        assertEquals("Book Title 1", result.getTitle());
    }

    @Test
    void testSaveBook() {
        // Arrange
        Book book = new Book("Book Title 1", "Description 1", "Author 1", "2020", "ISBN001", 9.99F, 6);
        when(bookRepository.save(book)).thenReturn(book);

        // Act
        Book result = bookService.createBook(book);

        // Assert
        assertEquals("Book Title 1", result.getTitle());
    }

    @Test
    void testDeleteBook() {
        // Act
        bookService.deleteBook(1L);

        // Assert
        verify(bookRepository, times(1)).deleteById(1L);
    }
}
