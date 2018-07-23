package com.swayam.demo.trx.cmt.plain.web.rest;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.swayam.demo.trx.cmt.plain.entity.Rating;
import com.swayam.demo.trx.cmt.plain.service.AuthorRatingService;
import com.swayam.demo.trx.cmt.plain.web.dto.AuthorRatingRequest;

//@RestController
//@RequestMapping(path = "/rest")
public class AuthorUserRestController {

	private static final Logger LOGGER = LoggerFactory.getLogger(AuthorUserRestController.class);

	private final AuthorRatingService authorRatingService;

	public AuthorUserRestController(AuthorRatingService authorRatingService) {
		this.authorRatingService = authorRatingService;
	}

//	@GetMapping(path = "/rating")
	public List<Rating> getAuthors() {
		return authorRatingService.getRatings();
	}

//	@PostMapping(path = "/author-rating", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
	public Map<String, Long> save(AuthorRatingRequest authorRatingRequest) {
		LOGGER.debug("authorRatingRequest: {}", authorRatingRequest);
		return authorRatingService.addAuthorRating(authorRatingRequest);
	}

}
