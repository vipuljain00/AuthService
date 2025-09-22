package org.example.repo;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

import org.example.entities.RefreshToken;

@Repository
public interface RefreshTokenRepo extends CrudRepository<RefreshToken, Long> {
    
    public Optional<RefreshToken> findByToken(String token);

}
