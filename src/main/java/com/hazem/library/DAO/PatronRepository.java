package com.hazem.library.DAO;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hazem.library.entity.Patron;

@Repository
public interface PatronRepository extends JpaRepository<Patron, Long> {
    List<Patron> findAllByOrderByIdAsc();
}