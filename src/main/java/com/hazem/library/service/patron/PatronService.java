package com.hazem.library.service.patron;

import com.hazem.library.entity.Patron;

import java.util.List;

import org.springframework.stereotype.Service;

@Service
public interface PatronService {
    
    List<Patron> index();

    Patron getPatron(Long id);

    Patron createPatron(Patron thePatron);

    Patron deletePatron(Long id);

    Patron updatePatron(Patron thePatron);
}
