package com.hazem.library.service.borrowedBook;

import com.hazem.library.entity.BorrowedBook;

import java.util.List;

import org.springframework.stereotype.Service;

@Service
public interface BorrowedBookService {
    
    List<BorrowedBook> index();

    BorrowedBook getBorrowedBook(Long id);

    BorrowedBook createNewBorrowedBook(Long bookId, Long patronId);

    BorrowedBook returnBorrowedBook(Long bookId, Long patronId);

    BorrowedBook updateBorrowedBook(BorrowedBook theBorrowedBook);
}
