package com.hazem.library.service.book;

import com.hazem.library.entity.Book;

import java.util.List;

import org.springframework.stereotype.Service;

@Service
public interface BookService {
    
    List<Book> index();

    Book getBook(Long id);

    Book createBook(Book theBook);

    Book deleteBook(Long id);

    Book updateBook(Book theBook);
}
