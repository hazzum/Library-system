package com.hazem.library.service.book;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hazem.library.DAO.BookRepository;
import com.hazem.library.entity.Book;
import com.hazem.library.rest.exceptionHandler.InternalServerErrorException;
import com.hazem.library.rest.exceptionHandler.NotFoundException;

@Component
public class BookServiceImpl implements BookService {

    private final BookRepository BookRepository;

    @Autowired
    public BookServiceImpl(BookRepository theBookRepository) {
        BookRepository = theBookRepository;
    }

    @Override
    public List<Book> index() {
        try {
            return BookRepository.findAllByOrderByIdAsc();
        } catch (Exception e) {
            throw new InternalServerErrorException("Could not retrieve books");
        }
    }

    @Override
    public Book getBook(Long id) {
        Optional<Book> result;
        try {
            result = BookRepository.findById(id);
        } catch (Exception e) {
            throw new InternalServerErrorException("Could not retrieve book");
        }
        Book theBook;
        if (result.isPresent()) {
            theBook = result.get();
            return theBook;
        } else {
            throw new NotFoundException("Book not found id: " + id);
        }
    }

    @Override
    public Book createBook(Book theBook) {
        try {
            return BookRepository.save(theBook);
        } catch (Exception e) {
            throw new InternalServerErrorException("Could not create book");
        }
    }

    @Override
    public Book deleteBook(Long id) {
        try {
            BookRepository.deleteById(id);
        } catch (Exception e) {
            throw new InternalServerErrorException("Could not delete book");
        }
        return null;
    }

    @Override
    public Book updateBook(Book theBook) {
        try {
            return BookRepository.save(theBook);
        } catch (Exception e) {
            throw new InternalServerErrorException("Could not update book");
        }
    }

}
