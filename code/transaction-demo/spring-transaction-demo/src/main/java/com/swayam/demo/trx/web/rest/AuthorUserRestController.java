package com.swayam.demo.trx.web.rest;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.swayam.demo.trx.entity.Rating;
import com.swayam.demo.trx.service.AuthorRatingService;
import com.swayam.demo.trx.web.dto.AuthorRatingRequest;

@RestController
@RequestMapping(path = "/rest")
public class AuthorUserRestController {

	private static final Logger LOGGER = LoggerFactory.getLogger(AuthorUserRestController.class);

	private final AuthorRatingService authorRatingService;

	public AuthorUserRestController(AuthorRatingService authorRatingService) {
		this.authorRatingService = authorRatingService;
	}

	@GetMapping(path = "/rating")
	public List<Rating> getAuthors() {
		return authorRatingService.getRatings();
	}

	@PostMapping(path = "/author-rating", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
	public Map<String, Long> save(AuthorRatingRequest authorRatingRequest) {
		LOGGER.debug("authorRatingRequest: {}", authorRatingRequest);
		return authorRatingService.addAuthorRating(authorRatingRequest);
	}

}
