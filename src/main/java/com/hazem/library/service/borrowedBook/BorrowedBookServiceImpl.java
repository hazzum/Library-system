package com.hazem.library.service.borrowedBook;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.hazem.library.DAO.BorrowedBookRepository;
import com.hazem.library.entity.Book;
import com.hazem.library.entity.BorrowedBook;
import com.hazem.library.entity.BorrowedBookStatus;
import com.hazem.library.rest.exceptionHandler.BadRequestException;
import com.hazem.library.rest.exceptionHandler.InternalServerErrorException;
import com.hazem.library.rest.exceptionHandler.NotFoundException;
import com.hazem.library.service.book.BookService;
import com.hazem.library.service.patron.PatronService;

@Component
public class BorrowedBookServiceImpl implements BorrowedBookService {

    private final BorrowedBookRepository BorrowedBookRepository;
    private final BookService BookService;
    private final PatronService PatronService;

    @Autowired
    public BorrowedBookServiceImpl(BorrowedBookRepository theBorrowedBookRepository, BookService theBookService,
            PatronService thePatronService) {
        BorrowedBookRepository = theBorrowedBookRepository;
        BookService = theBookService;
        PatronService = thePatronService;
    }

    @Override
    public List<BorrowedBook> index() {
        try {
            return BorrowedBookRepository.findAllByOrderByIdAsc();
        } catch (Exception e) {
            throw new InternalServerErrorException("Could not retrieve borrowed books");
        }
    }

    @Override
    public BorrowedBook getBorrowedBook(Long id) {
        Optional<BorrowedBook> result;
        try {
            result = BorrowedBookRepository.findById(id);
        } catch (Exception e) {
            throw new InternalServerErrorException("Could not retrieve borrowed book");
        }
        BorrowedBook theBorrowedBook;
        if (result.isPresent()) {
            theBorrowedBook = result.get();
            return theBorrowedBook;
        } else {
            throw new NotFoundException("Borrowed Book record not found id: " + id);
        }
    }

    @Override
    public BorrowedBook getBorrowedBookByIds(Long bookId, Long patronId) {
        Optional<BorrowedBook> result;
        try {
            result = BorrowedBookRepository.findFirstByBook_idAndPatron_id(bookId, patronId);
        } catch (Exception e) {
            throw new InternalServerErrorException("Could not retrieve borrowed book");
        }
        BorrowedBook theBorrowedBook;
        if (result.isPresent()) {
            theBorrowedBook = result.get();
            return theBorrowedBook;
        } else {
            throw new NotFoundException("Borrowed Book record not found");
        }
    }

    @Override
    @Transactional
    public BorrowedBook createNewBorrowedBook(Long bookId, Long patronId) {
        BorrowedBook theBorrowedBook;
        Book theBook;
        try {
            PatronService.getPatron(patronId);
            theBook = BookService.getBook(bookId);
        } catch (Exception e) {
            throw new InternalServerErrorException("Could not verify book or patron");
        }
        if (theBook.getStock() < 1) {
            throw new BadRequestException(
                    "We have run out of this Book");
        }
        Optional<BorrowedBook> result;
        try {
            result = BorrowedBookRepository.findFirstByBook_idAndPatron_id(bookId, patronId);
        } catch (Exception e) {
            throw new InternalServerErrorException("Could not verify borrowed book");
        }
        if (result.isPresent()) {
            theBorrowedBook = result.get();
            if (theBorrowedBook.getStatus() == BorrowedBookStatus.Borrowed) {
                throw new BadRequestException("This Patron has already borrowed this book and did not return it");
            } else {
                theBorrowedBook.setStatus(BorrowedBookStatus.Borrowed);
            }
        } else {
            theBorrowedBook = new BorrowedBook(bookId, patronId, BorrowedBookStatus.Borrowed);
        }
        theBook.setStock(theBook.getStock() - 1);
        try {
            BookService.updateBook(theBook);
            return BorrowedBookRepository.save(theBorrowedBook);
        } catch (Exception e) {
            throw new InternalServerErrorException("Could not complete transaction, rolling back...");
        }

    }

    @Override
    @Transactional
    public BorrowedBook returnBorrowedBook(Long bookId, Long patronId) {
        Book theBook;
        BorrowedBook theBorrowedBook;
        Optional<BorrowedBook> result;
        try {
            theBook = BookService.getBook(bookId);
        } catch (Exception e) {
            throw new InternalServerErrorException("Could not verify book");
        }
        try {
            result = BorrowedBookRepository.findFirstByBook_idAndPatron_id(bookId, patronId);
        } catch (Exception e) {
            throw new InternalServerErrorException("Could not verify borrowed book record");
        }
        if (result.isPresent()) {
            theBorrowedBook = result.get();
            if (theBorrowedBook.getStatus() == BorrowedBookStatus.Returned) {
                throw new BadRequestException("This Patron has already returned this book");
            }
            theBorrowedBook.setStatus(BorrowedBookStatus.Returned);
            theBook.setStock(theBook.getStock() + 1);
            try {
                BookService.updateBook(theBook);
                return BorrowedBookRepository.save(theBorrowedBook);
            } catch (Exception e) {
                throw new InternalServerErrorException("Could not complete transaction, rolling back...");
            }
        } else {
            throw new BadRequestException("This patron has not borrowed this book previously");
        }
    }

    @Override
    public BorrowedBook deleteBorrowedBook(Long id) {
        try {
            BorrowedBookRepository.deleteById(id);
            return null;
        } catch (Exception e) {
            throw new InternalServerErrorException("Could not delete borrowed book");
        }

    }

}
