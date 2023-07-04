package com.app.repositories;

import com.app.entities.UserEntity;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 *
 * @author luis.buelna
 */

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long>{
    
    Optional<UserEntity> findByUsername(String username);
    
    @Query("Select u FROM UserEntity u where u.username = ?1")
    Optional<UserEntity> getName(String username);
}
