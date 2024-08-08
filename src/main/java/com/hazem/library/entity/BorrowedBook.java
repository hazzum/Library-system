package com.hazem.library.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

@Entity
@Table(name = "borrowed_book", uniqueConstraints = @UniqueConstraint(columnNames = {"patron_id", "book_id"}))
public class BorrowedBook extends AbstractTimestampEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "resourceType")
    private BorrowedBookStatus status;

    @ManyToOne(targetEntity = Book.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "book_id", insertable = false, updatable = false)
    @JsonIgnore
    private Book book;

    @ManyToOne(targetEntity = Patron.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "patron_id", insertable = false, updatable = false)
    @JsonIgnore
    private Patron patron;

    @Column(name = "patron_id", nullable = false)
    private Long patron_id;

    @Column(name = "book_id", nullable = false)
    private Long book_id;

    public BorrowedBook() {

    }

    public BorrowedBook(Long bookId, Long patronId, BorrowedBookStatus status) {
        this.book_id = bookId;
        this.patron_id = patronId;
        this.status = status;
    }

    public Book getBook() {
        return this.book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public Patron getPatron() {
        return this.patron;
    }

    public void setPatron(Patron patron) {
        this.patron = patron;
    }

    public BorrowedBookStatus getStatus() {
        return this.status;
    }

    public void setStatus(BorrowedBookStatus status) {
        this.status = status;
    }

    public Long getPatron_id() {
        return this.patron_id;
    }

    public void setPatron_id(Long patron_id) {
        this.patron_id = patron_id;
    }

    public Long getBook_id() {
        return this.book_id;
    }

    public void setBook_id(Long book_id) {
        this.book_id = book_id;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Borrowed Book [id = " + id + ", Book id = " + book_id + ", Patron id = " + patron_id
                + patron.getLastName() + ", Status = " + status.toString()
                + "]";
    }
}
