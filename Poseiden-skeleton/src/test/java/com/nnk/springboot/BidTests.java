package com.nnk.springboot;

import com.nnk.springboot.domain.Bid;
import com.nnk.springboot.repositories.BidRepository;
import com.nnk.springboot.services.BidService;
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
public class BidTests {

	@Autowired
	private BidService bidService;

	@Autowired
	private BidRepository bidRepository;

	private Bid bid;

	@Before
	public void setUp(){
		bid = new Bid();
		bid.setAccount("Bid test account");
		bid.setType("Test type");
		bid.setBidQuantity(10d);
	}

	@After
	public void cleanUp(){
		bidRepository.deleteAll();
	}

	@Test
	public void save(){
		bid = bidService.save(bid);
		Assert.assertNotNull(bid.getId());
		Assert.assertEquals(bid.getBidQuantity(), 10d, 10d);
	}

	@Test
	public void update(){
		bid.setBidQuantity(20d);
		bid = bidService.save(bid);
		Assert.assertEquals(bid.getBidQuantity(), 20d, 20d);
	}

	@Test
	public void find(){
		bid = bidService.save(bid);
		List<Bid> listResult = bidService.findAll();
		Assert.assertTrue(listResult.size() > 0);
	}

	@Test
	public void delete(){
		bid = bidService.save(bid);
		Integer id = bid.getId();
		bidService.deleteById(id);
		Optional<Bid> bidList = bidService.findById(id);
		Assert.assertFalse(bidList.isPresent());
	}
}
