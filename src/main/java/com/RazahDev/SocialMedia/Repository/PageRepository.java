package com.RazahDev.SocialMedia.Repository;

import com.RazahDev.SocialMedia.Entities.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PageRepository extends JpaRepository<Page, String> {
    Boolean existsByName(String name);
}
