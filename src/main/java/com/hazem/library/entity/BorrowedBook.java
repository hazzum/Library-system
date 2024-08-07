package com.hazem.library.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "borrowed_book")
public class BorrowedBook {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "book_id", nullable = false)
    private Book book;

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

    @ManyToOne
    @JoinColumn(name = "patron_id", nullable = false)
    private Patron patron;

    @Enumerated(EnumType.STRING)
    @Column(name = "resourceType")
    private BorrowedBookStatus status;

    @Override
    public String toString() {
        return "Borrowed Book [id = " + id + ", Book = " + book.getTitle() + ", Patron = " + patron.getFirstName()
                + patron.getLastName() + ", Status = " + status.toString()
                + "]";
    }
}
