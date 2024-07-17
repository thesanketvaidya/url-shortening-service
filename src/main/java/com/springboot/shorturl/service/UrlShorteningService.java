package com.springboot.shorturl.service;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springboot.shorturl.constant.UrlConstants;
import com.springboot.shorturl.entity.UrlMapping;
import com.springboot.shorturl.repositories.UrlMappingRepository;

import io.micrometer.common.util.StringUtils;

@Service
public class UrlShorteningService {


	@Autowired
	private UrlMappingRepository urlMappingRepository;

	public String shortenUrl(String longUrl) throws URISyntaxException {

		if (!isValidUrl(longUrl)) {
			throw new URISyntaxException("Invalid URL: ",longUrl);
		}

		UrlMapping urlMapping = new UrlMapping();
		urlMapping.setLongUrl(longUrl);
		urlMappingRepository.save(urlMapping);
		urlMapping.setShortUrl(UrlConstants.BASE_URL + urlMapping.getId());
		urlMappingRepository.save(urlMapping);
		return urlMapping.getShortUrl();
	}

	public String getLongUrl(String shortUrl) {

		UrlMapping urlMapping = urlMappingRepository.findByShortUrl(shortUrl);
		if (urlMapping != null) {
			return urlMapping.getLongUrl();
		}

		return null;

	}

	private boolean isValidUrl(String longUrl) {
		if (StringUtils.isEmpty(longUrl)) {
			return false;
		}
		try {
			new URI(longUrl);
			return true;
		} catch (URISyntaxException e) {
			return false;
		}
	}

}
