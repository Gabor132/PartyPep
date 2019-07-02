package com.gabor.partypeps.repositories;

import com.gabor.partypeps.models.dao.Group;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GroupRepository extends JpaRepository<Group, Long> { }
