package com.hazem.library.service;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;

import com.hazem.library.DAO.BorrowedBookRepository;
import com.hazem.library.entity.Book;
import com.hazem.library.entity.BorrowedBook;
import com.hazem.library.entity.BorrowedBookStatus;
import com.hazem.library.entity.Patron;
import com.hazem.library.service.book.BookServiceImpl;
import com.hazem.library.service.borrowedBook.BorrowedBookServiceImpl;
import com.hazem.library.service.patron.PatronServiceImpl;

public class BorrowingServiceTest {

    @InjectMocks
    private BorrowedBookServiceImpl borrowingService;

    @Mock
    private BorrowedBookRepository borrowingRecordRepository;

    @Mock
    private BookServiceImpl BookService;

    @Mock
    private PatronServiceImpl PatronService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testBorrowBook() {
        // Arrange
        Book book = new Book("Book Title 1", "Description 1", "Author 1", "2020", "ISBN001", 9.99F, 6);
        Patron patron = new Patron("John", "Smith", "+2010000000", "john.smith@example.com");
        when(BookService.getBook(1L)).thenReturn(book);
        when(PatronService.getPatron(1L)).thenReturn(patron);

        BorrowedBook borrowingRecord = new BorrowedBook(1L, 1L, BorrowedBookStatus.Borrowed);
        when(borrowingRecordRepository.save(any(BorrowedBook.class))).thenReturn(borrowingRecord);

        // Act
        BorrowedBook result = borrowingService.createNewBorrowedBook(1L, 1L);

        // Assert
        assertEquals(BorrowedBookStatus.Borrowed, result.getStatus());
    }

    @Test
    void testReturnBook() {
        // Arrange
        Book book = new Book("Book Title 1", "Description 1", "Author 1", "2020", "ISBN001", 9.99F, 6);
        Patron patron = new Patron("John", "Smith", "+2010000000", "john.smith@example.com");
        when(BookService.getBook(1L)).thenReturn(book);
        when(PatronService.getPatron(1L)).thenReturn(patron);
        BorrowedBook borrowingRecord = new BorrowedBook(1L, 1L, BorrowedBookStatus.Borrowed);
        BorrowedBook updatedBorrowingRecord = new BorrowedBook(1L, 1L, BorrowedBookStatus.Returned);
        when(borrowingRecordRepository.findFirstByBook_idAndPatron_id(1L, 1L)).thenReturn(Optional.of(borrowingRecord));
        when(borrowingRecordRepository.save(any(BorrowedBook.class))).thenReturn(updatedBorrowingRecord);

        // Act
        BorrowedBook result = borrowingService.returnBorrowedBook(1L, 1L);

        // Assert
        assertEquals(BorrowedBookStatus.Returned, result.getStatus());
    }
}
