package com.nnk.springboot;

import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.repositories.RatingRepository;
import com.nnk.springboot.services.RatingService;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Optional;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RatingTests {

	@Autowired
	private RatingService ratingService;

	@Autowired
	private RatingRepository ratingRepository;

	private Rating rating;

	@Before
	public void setUp(){
		rating = new Rating();
		rating.setMoodysRating("Moodys Rating");
		rating.setSandPRating("Sand PRating");
		rating.setFitchRating("Fitch Rating");
		rating.setOrderNumber(10);
	}

	@After
	public void cleanUp(){
		ratingRepository.deleteAll();
	}

	@Test
	public void save(){
		rating = ratingService.save(rating);
		Assert.assertNotNull(rating.getId());
		Assert.assertEquals(rating.getOrderNumber(), 10d, 10d);
	}

	@Test
	public void update(){
		rating.setOrderNumber(20);
		rating = ratingService.save(rating);
		Assert.assertEquals(rating.getOrderNumber(), 20d, 20d);
	}

	@Test
	public void find(){
		rating = ratingService.save(rating);
		List<Rating> listResult = ratingService.findAll();
		Assert.assertTrue(listResult.size() > 0);
	}

	@Test
	public void delete(){
		rating = ratingService.save(rating);
		Integer id = rating.getId();
		ratingService.deleteById(id);
		Optional<Rating> bidList = ratingService.findById(id);
		Assert.assertFalse(bidList.isPresent());
	}
}
