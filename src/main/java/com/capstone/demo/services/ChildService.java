package com.capstone.demo.services;

import com.capstone.demo.models.Child;
import com.capstone.demo.repositories.ChildRepository;
import com.capstone.demo.repositories.ParentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("ChildService")
public class ChildService {
    private final ChildRepository childDao;

    @Autowired
    public ChildService(ChildRepository childDao){
        this.childDao = childDao;
    }

    //View parent users//

    public Iterable<Child> findAll(){return childDao.findAll();}

    public Child findById(Long id){
        return childDao.findOne(id);
    }
    public Child save (Child child){
        return childDao.save(child);
    }
    public void delete(long id){
        childDao.delete(id);
    }


}