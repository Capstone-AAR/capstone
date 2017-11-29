package com.capstone.demo.services;

import com.capstone.demo.models.Parent;
import com.capstone.demo.repositories.ParentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class ParentsService {
    private final ParentRepository parentDao;

    public boolean isLoggedIn() {
        boolean isAnonymousUser = SecurityContextHolder.getContext().getAuthentication() instanceof AnonymousAuthenticationToken;
        return !isAnonymousUser;
    }

    public Parent loggedInUser() {
        if (!isLoggedIn()) {
            return null;
        }
        return (Parent) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    public boolean isLoggedInAndTaskMatchesUser(Parent parent) {
        return isLoggedIn() && (loggedInUser().getId() == parent.getId());
    }

    @Autowired
    public ParentsService(ParentRepository parentDao){
        this.parentDao = parentDao;
    }

    //View parent users//

    public Iterable<Parent> findAll(){return parentDao.findAll();}

    public Parent findById(Long id){
        return parentDao.findOne(id);
    }

    public Parent save (Parent parent){
        return parentDao.save(parent);
    }

    public void delete(long id){
        parentDao.delete(id);
    }

//    public Parent findByEmail(String email) { return parentDao.findByEmail(email); }


}
