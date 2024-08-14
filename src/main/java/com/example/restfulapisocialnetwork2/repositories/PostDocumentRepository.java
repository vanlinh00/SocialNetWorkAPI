package com.example.restfulapisocialnetwork2.repositories;

import com.example.restfulapisocialnetwork2.models.PostDocument;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

public interface PostDocumentRepository  extends ElasticsearchRepository<PostDocument, String> {
    List<PostDocument> findByDescribedContainingIgnoreCase(String keyword);
}

