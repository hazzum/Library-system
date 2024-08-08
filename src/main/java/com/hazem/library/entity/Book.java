package com.hazem.library.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "book")
public class Book extends AbstractTimestampEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull(message = "Title cannot be null")
    @Size(min = 1, max = 100, message = "Title must be between 1 and 100 characters")
    @Column(name = "title")
    private String title;

    @Column(name = "description")
    private String description;

    @NotNull(message = "Author cannot be null")
    @Size(min = 1, max = 50, message = "Author must be between 1 and 50 characters")
    @Column(name = "author")
    private String author;
    
    @NotNull(message = "Publication Year cannot be null")
    @Pattern(regexp = "^\\d{4}$", message = "Publication Year must be a 4-digit number")
    @Column(name = "publication_year")
    private String publicationYear;

    @Column(name = "isbn")
    private String isbn;

    @Column(name = "price")
    private float price;

    @NotNull(message = "Stock is mandatory")
    @Min(value = 0, message = "Stock must be at least 0")
    @Column(name = "stock")
    private int stock;

    public Book() {

    }

    public Book(String title, String description, String author, String publicationYear, String isbn, float price,
            int stock) {
        this.title = title;
        this.description = description;
        this.author = author;
        this.publicationYear = publicationYear;
        this.isbn = isbn;
        this.price = price;
        this.stock = stock;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getPublicationYear() {
        return publicationYear;
    }

    public void setPublicationYear(String publicationYear) {
        this.publicationYear = publicationYear;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    @Override
    public String toString() {
        return "Book [id = " + id + ", title = " + title + ", description = " + description + ", author = " + author
                + "]";
    }
}
