package com.hazem.library.service.patron;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hazem.library.DAO.PatronRepository;
import com.hazem.library.entity.Patron;
import com.hazem.library.rest.exceptionHandler.NotFoundException;

@Component
public class PatronServiceImpl implements PatronService {

    private PatronRepository PatronRepository;

    @Autowired
	public PatronServiceImpl(PatronRepository thePatronRepository) {
		PatronRepository = thePatronRepository;
	}

    @Override
    public List<Patron> index() {
        return PatronRepository.findAllByOrderByIdAsc();
    }

    @Override
    public Patron getPatron(Long id) {
        Optional<Patron> result = PatronRepository.findById(id);

		Patron thePatron = null;

		if (result.isPresent()) {
			thePatron = result.get();
			return thePatron;
		} else {
			throw new NotFoundException("Patron not found id: " + id);
		}
    }

    @Override
    public Patron createPatron(Patron thePatron) {
        return PatronRepository.save(thePatron);
    }

    @Override
    public Patron deletePatron(Long id) {
        PatronRepository.deleteById(id);
        return null;
    }

    @Override
    public Patron updatePatron(Patron thePatron) {
        return PatronRepository.save(thePatron);
    }
    
}
