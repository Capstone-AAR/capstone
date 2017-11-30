package com.capstone.demo.services;

import com.capstone.demo.models.Parent;
import com.capstone.demo.models.User;
import com.capstone.demo.repositories.TaskRepository;
import com.capstone.demo.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository userDao;

    public boolean isLoggedIn() {
        boolean isAnonymousUser = SecurityContextHolder.getContext().getAuthentication() instanceof AnonymousAuthenticationToken;
        return !isAnonymousUser;
    }

    public User loggedInUser() {
        if (!isLoggedIn()) {
            return null;
        }
        return (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    public boolean isLoggedInAndTaskMatchesUser(Parent parent) {
        return isLoggedIn() && (loggedInUser().getId() == parent.getId());
    }

    @Autowired
    public UserService(UserRepository userDao) {
        this.userDao = userDao;
    }

    public Iterable<User> findAll() {
        return userDao.findAll();
    }

    public User findById(Long id) {
        return userDao.findOne(id);
    }

    public User save(User user) {
        return userDao.save(user);
    }

    public void delete(long id) {
        userDao.delete(id);
    }

}
