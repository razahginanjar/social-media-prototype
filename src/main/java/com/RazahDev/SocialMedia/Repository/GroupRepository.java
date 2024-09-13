package com.RazahDev.SocialMedia.Repository;

import com.RazahDev.SocialMedia.Entities.Group;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GroupRepository extends JpaRepository<Group, String> {
}
