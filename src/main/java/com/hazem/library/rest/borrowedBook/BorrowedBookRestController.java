package com.hazem.library.rest.borrowedBook;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.hazem.library.entity.BorrowedBook;
import com.hazem.library.rest.exceptionHandler.InternalServerErrorException;
import com.hazem.library.service.borrowedBook.BorrowedBookService;

@RestController
@RequestMapping("/")
public class BorrowedBookRestController {
    @Autowired
    private BorrowedBookService borrowedBookService;

    // add mapping for POST /borrow/{bookId}/patron/{patronId} - borrow new book
    @PostMapping("/borrow/{bookId}/patron/{patronId}")
    public BorrowedBook borrowBook(@PathVariable Long bookId, @PathVariable Long patronId) {
        try {
            return borrowedBookService.createNewBorrowedBook(bookId, patronId);
        } catch (Exception e) {
            throw new InternalServerErrorException("Could not borrow book");
        }
    }

    // add mapping for PUT /return/{bookId}/patron/{patronId} - return borrowed Book
    @PutMapping("/return/{bookId}/patron/{patronId}")
    public BorrowedBook updateBorrowedBook(@PathVariable Long bookId, @PathVariable Long patronId) {
        try {
            return borrowedBookService.returnBorrowedBook(bookId, patronId);
        } catch (Exception e) {
            throw new InternalServerErrorException("Could not borrow book");
        }
    }
}
