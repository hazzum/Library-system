package com.hazem.library.service.borrowedBorrowedBook;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hazem.library.DAO.BorrowedBookRepository;
import com.hazem.library.entity.BorrowedBook;
import com.hazem.library.rest.exceptionHandler.NotFoundException;
import com.hazem.library.service.book.BookService;
import com.hazem.library.service.patron.PatronService;
import org.springframework.transaction.annotation.Transactional;

@Component
public class BorrowedBookServiceImpl implements BorrowedBookService {

    private BorrowedBookRepository BorrowedBookRepository;
    private BookService BookService;
    private PatronService PatronService;

    @Autowired
	public BorrowedBookServiceImpl(BorrowedBookRepository theBorrowedBookRepository, BookService theBookService, PatronService thePatronService) {
		BorrowedBookRepository = theBorrowedBookRepository;
        BookService = theBookService;
        PatronService = thePatronService;
	}

    @Override
    public List<BorrowedBook> index() {
        return BorrowedBookRepository.findAllByOrderByIdAsc();
    }

    @Override
    public BorrowedBook getBorrowedBook(Long id) {
        Optional<BorrowedBook> result = BorrowedBookRepository.findById(id);
		BorrowedBook theBorrowedBook = null;
		if (result.isPresent()) {
			theBorrowedBook = result.get();
			return theBorrowedBook;
		} else {
			throw new NotFoundException("BorrowedBook not found id: " + id);
		}
    }

    @Override
    @Transactional
    public BorrowedBook createNewBorrowedBook(Long bookId, Long patronId) {
        Patron thePatron = PatronService.getPatron(patronId);
        Book theBook = BookService.getBook(bookId);
        if (theBook.getStock()<1) {
            throw new BadRequestException(
                    "We have run out of this Book");
        }
        BorrowedBook theBorrowedBook = new BorrowedBook();
        return BorrowedBookRepository.save(theBorrowedBook);
    }

    @Override
    public BorrowedBook deleteBorrowedBook(Long id) {
        BorrowedBookRepository.deleteById(id);
        return null;
    }

    @Override
    public BorrowedBook updateBorrowedBook(BorrowedBook theBorrowedBook) {
        return BorrowedBookRepository.save(theBorrowedBook);
    }
    
}
