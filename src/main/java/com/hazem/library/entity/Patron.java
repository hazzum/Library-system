package com.hazem.library.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "patron")
public class Patron extends AbstractTimestampEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull(message = "First name cannot be null")
    @Size(min = 1, max = 100, message = "First name must be between 2 and 100 characters")
    @Column(name = "first_name")
    private String firstName;

    @NotNull(message = "Last name cannot be null")
    @Size(min = 1, max = 100, message = "Last name must be between 2 and 100 characters")
    @Column(name = "last_name")
    private String lastName;

    @Pattern(regexp = "^\\+?[1-9]\\d{1,14}(?:[-\\s]\\d{1,15})*$", message = "Phone number must be in the format +<country_code> followed by digits")
    @Column(name = "phone")
    private String phone;

    @Email(message = "Email should be valid")
    @Column(name = "email")
    private String email;

    public Patron() {

    }

    public Patron(String firstName, String lastName, String phone, String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.phone = phone;
        this.email = email;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "Patron [id = " + id + ", name = " + firstName + " " + lastName + ", phone numner = " + phone
                + ", email = " + email + "]";
    }
}
