package com.gabor.partypeps.repositories;

import com.gabor.partypeps.models.dao.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * The repository for the User data model
 *
 * @author dragos.gabor
 */

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
}
