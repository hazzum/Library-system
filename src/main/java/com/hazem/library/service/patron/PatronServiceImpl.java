package com.hazem.library.service.patron;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hazem.library.DAO.PatronRepository;
import com.hazem.library.entity.Patron;
import com.hazem.library.rest.exceptionHandler.InternalServerErrorException;
import com.hazem.library.rest.exceptionHandler.NotFoundException;

@Component
public class PatronServiceImpl implements PatronService {

    private final PatronRepository PatronRepository;

    @Autowired
    public PatronServiceImpl(PatronRepository thePatronRepository) {
        PatronRepository = thePatronRepository;
    }

    @Override
    public List<Patron> index() {
        try {
            return PatronRepository.findAllByOrderByIdAsc();
        } catch (Exception e) {
            throw new InternalServerErrorException("Could not retrieve patrons");
        }
    }

    @Override
    public Patron getPatron(Long id) {
        Optional<Patron> result = null;
        try {
            result = PatronRepository.findById(id);
        } catch (Exception e) {
            throw new InternalServerErrorException("Could not retrieve patron");
        }
        Patron thePatron;
        if (result.isPresent()) {
            thePatron = result.get();
            return thePatron;
        } else {
            throw new NotFoundException("Patron not found id: " + id);
        }
    }

    @Override
    public Patron createPatron(Patron thePatron) {
        try {
            return PatronRepository.save(thePatron);
        } catch (Exception e) {
            throw new InternalServerErrorException("Could not create patron");
        }
    }

    @Override
    public Patron deletePatron(Long id) {
        try {
            PatronRepository.deleteById(id);
        } catch (Exception e) {
            throw new InternalServerErrorException("Could not delete patron");
        }
        return null;
    }

    @Override
    public Patron updatePatron(Patron thePatron) {
        try {
            return PatronRepository.save(thePatron);
        } catch (Exception e) {
            throw new InternalServerErrorException("Could not update patron");
        }
    }

}
