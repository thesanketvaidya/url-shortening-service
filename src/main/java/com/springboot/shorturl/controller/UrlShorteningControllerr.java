package com.springboot.shorturl.controller;

import java.net.URISyntaxException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.shorturl.constant.UrlConstants;
import com.springboot.shorturl.dto.ShortenRequest;
import com.springboot.shorturl.service.UrlShorteningService;

@RestController
@RequestMapping("/api")
public class UrlShorteningControllerr {

	@Autowired
	private UrlShorteningService urlShorteningService;

	@PostMapping(path = "/shortenurl")
	public ResponseEntity<?> shortenUrl(@RequestBody ShortenRequest urlrequestBody) {
		try {
			String longUrl = urlrequestBody.getLongUrl();
			String shortUrl = urlShorteningService.shortenUrl(longUrl);
			return ResponseEntity.ok(shortUrl);
		} catch (URISyntaxException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid Long Url");

		}
	}

	@GetMapping(path = "{shortUrl}")
	public ResponseEntity<?> redirectToLongUrl(@PathVariable(name = "shortUrl") String shortUrl) {
		String longUrl = urlShorteningService.getLongUrl(UrlConstants.BASE_URL + shortUrl);
		
		if (longUrl != null) {
            return ResponseEntity.status(HttpStatus.FOUND).header("Location", longUrl).build();
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Short URL not found");
        }
	}
}
