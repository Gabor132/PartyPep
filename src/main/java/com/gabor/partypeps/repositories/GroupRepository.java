package com.gabor.partypeps.repositories;

import com.gabor.partypeps.models.dao.Group;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface GroupRepository extends JpaRepository<Group, Long> {

    @Query("SELECT g FROM Group g WHERE g.name = :name")
    Group getGroupByName(@Param("name") String name);
}
