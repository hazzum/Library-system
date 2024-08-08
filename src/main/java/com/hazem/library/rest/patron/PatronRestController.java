package com.hazem.library.rest.patron;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hazem.library.entity.Patron;
import com.hazem.library.rest.exceptionHandler.InternalServerErrorException;
import com.hazem.library.rest.exceptionHandler.NotFoundException;
import com.hazem.library.service.patron.PatronService;

@RestController
@RequestMapping("/patrons")
public class PatronRestController {
    @Autowired
    private PatronService patronService;

    // add mapping for GET /patron
    @GetMapping("")
    public List<Patron> index() {
        List<Patron> thePatrons = patronService.index();
        if (thePatrons.isEmpty())
            throw new NotFoundException("No patrons found");
        return thePatrons;
    }

    // add mapping for GET /patrons/{patronId}
    @GetMapping("{patronId}")
    public Patron getPatron(@PathVariable Long patronId) {
        return patronService.getPatron(patronId);
    }

    // add mapping for POST /patrons - add new patron
    @PostMapping("")
    public Patron addPatron(@RequestBody Patron thePatron) {
        try {
            return patronService.createPatron(thePatron);
        } catch (Exception e) {
            throw new InternalServerErrorException("Could not create patron");
        }
    }

    // add mapping for PUT /patrons - update existing patron
    @PutMapping("{patronId}")
    public Patron updatePatron(@RequestBody Patron thePatron, @PathVariable Long patronId) {
        Patron tempPatron = patronService.getPatron(patronId);
        // throw exception if null
        if (tempPatron == null)
            throw new NotFoundException("Patron id not found - " + patronId);
        // update patron
        thePatron.setId(patronId);
        try {
            return patronService.updatePatron(thePatron);
        } catch (Exception e) {
            throw new InternalServerErrorException("Could not update patron");
        }
    }

    // add mapping Delete /patrons/{patronId} - delete existing patron
    @DeleteMapping("{patronId}")
    public String deletePatron(@PathVariable Long patronId) {
        Patron tempPatron = patronService.getPatron(patronId);
        // throw exception if null
        if (tempPatron == null)
            throw new NotFoundException("Patron id not found - " + patronId);
        try {
            patronService.deletePatron(patronId);
            return "Deleted Patron id - " + patronId;
        } catch (Exception e) {
            throw new InternalServerErrorException("Could not delete patron");
        }
    }
}
