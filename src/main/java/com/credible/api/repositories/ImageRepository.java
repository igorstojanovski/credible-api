package com.credible.api.repositories;

import com.credible.api.model.Upload;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ImageRepository extends CrudRepository<Upload, Long> {
}
