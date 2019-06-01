package com.credible.api.repositories;

import com.credible.api.model.Story;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StoryRepository extends CrudRepository<Story, Long> {
    Story findByUserId(Long userId);
}
