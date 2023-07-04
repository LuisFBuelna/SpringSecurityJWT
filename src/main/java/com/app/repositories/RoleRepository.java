package com.app.repositories;

import com.app.entities.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author luis.buelna
 */

@Repository
public interface RoleRepository extends JpaRepository<RoleEntity, Long>{
    
}
