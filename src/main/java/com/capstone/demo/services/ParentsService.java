package com.capstone.demo.services;

import com.capstone.demo.models.Parent;
import com.capstone.demo.repositories.ParentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("ParentsService")
public class ParentsService {
    private final ParentRepository parentDao;

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


}
