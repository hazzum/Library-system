package com.hazem.library.DAO;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hazem.library.entity.BorrowedBook;

@Repository
public interface BorrowedBookRepository extends JpaRepository<BorrowedBook, Long> {
    List<BorrowedBook> findAllByOrderByIdAsc();

    Optional<BorrowedBook> findFirstByBook_idAndPatron_id(Long Book_id, Long Patron_id);
}
