package com.hazem.library.service.book;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hazem.library.DAO.BookRepository;
import com.hazem.library.entity.Book;
import com.hazem.library.rest.exceptionHandler.NotFoundException;

@Component
public class BookServiceImpl implements BookService {

    private BookRepository BookRepository;

    @Autowired
	public BookServiceImpl(BookRepository theBookRepository) {
		BookRepository = theBookRepository;
	}

    @Override
    public List<Book> index() {
        return BookRepository.findAllByOrderByIdAsc();
    }

    @Override
    public Book getBook(Long id) {
        Optional<Book> result = BookRepository.findById(id);

		Book theBook = null;

		if (result.isPresent()) {
			theBook = result.get();
			return theBook;
		} else {
			throw new NotFoundException("Book not found id: " + id);
		}
    }

    @Override
    public Book createBook(Book theBook) {
        return BookRepository.save(theBook);
    }

    @Override
    public Book deleteBook(Long id) {
        BookRepository.deleteById(id);
        return null;
    }

    @Override
    public Book updateBook(Book theBook) {
        return BookRepository.save(theBook);
    }
    
}
