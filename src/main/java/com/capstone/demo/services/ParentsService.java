package com.capstone.demo.services;

import com.capstone.demo.models.Child;
import com.capstone.demo.models.Parent;
import com.capstone.demo.repositories.ChildRepository;
import com.capstone.demo.repositories.ParentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

/////////////////////////////////////////////////////////////////////////
// Service class is a special class used to provide more elaborate ways
// to query and interact with data, and tables.
/////////////////////////////////////////////////////////////////////////
@Service
public class ParentsService {
    private final ParentRepository parentDao;
    private final ChildRepository childDao;

    //////////////////////////////////
    // Validate if user has a session.
    //////////////////////////////////
    public boolean isLoggedIn() {

        boolean isAnonymousUser = SecurityContextHolder.getContext().getAuthentication() instanceof AnonymousAuthenticationToken;

        return !isAnonymousUser;
    }

    ///////////////////////////////////
    // Return user in current session.
    ///////////////////////////////////
    public Parent loggedInUser() {

        if (!isLoggedIn()) {
            return null;

        }

        return (Parent) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    ////////////////////////////////////////////////
    // Validate if task mathes with logged in user.
    ////////////////////////////////////////////////
    public boolean isLoggedInAndTaskMatchesUser(Parent parent) {

        return isLoggedIn() && (loggedInUser().getId() == parent.getId());
    }

    ///////////////////////////////
    // Autowire parent repository.
    ///////////////////////////////
    @Autowired
    public ParentsService(ParentRepository parentDao, ChildRepository childDao) {
        this.parentDao = parentDao;
        this.childDao = childDao;
    }

    public Iterable<Parent> findAll() {
        return parentDao.findAll();
    }

    public Parent findById(Long id) {
        return parentDao.findOne(id);
    }

    public Parent save(Parent parent) {
        return parentDao.save(parent);
    }

    public void delete(long id) {
        parentDao.delete(id);
    }

    public Child findChild(Long id) {
        return childDao.findByUserId(id);
    }

}
