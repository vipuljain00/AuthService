package org.example.repo;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import org.example.entities.UserInfo;

@Repository
public interface UserInfoRepo extends CrudRepository<UserInfo, Long> {
    
    public UserInfo findByUsername(String userName);

}
