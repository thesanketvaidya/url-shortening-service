package com.springboot.shorturl.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.springboot.shorturl.entity.UrlMapping;

public interface UrlMappingRepository extends JpaRepository<UrlMapping, Long> {
	
	UrlMapping findByShortUrl(String shortUrl);

}
