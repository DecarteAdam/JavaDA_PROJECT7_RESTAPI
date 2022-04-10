package com.nnk.springboot;

import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.repositories.TradeRepository;
import com.nnk.springboot.services.TradeService;
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
public class TradeTests {

	@Autowired
	private TradeService tradeService;

	@Autowired
	private TradeRepository tradeRepository;

	private Trade trade;

	@Before
	public void setUp(){
		trade = new Trade();
		trade.setAccount("Trade Account");
		trade.setType("Type");
	}

	@After
	public void cleanUp(){
		tradeRepository.deleteAll();
	}

	@Test
	public void save(){
		trade = tradeService.save(trade);
		Assert.assertNotNull(trade.getTradeId());
		Assert.assertTrue(trade.getAccount().equals("Trade Account"));
	}

	@Test
	public void update(){
		trade.setAccount("Trade Account Update");
		trade = tradeService.save(trade);
		Assert.assertTrue(trade.getAccount().equals("Trade Account Update"));
	}

	@Test
	public void find(){
		trade = tradeService.save(trade);
		List<Trade> listResult = tradeService.findAll();
		Assert.assertTrue(listResult.size() > 0);
	}

	@Test
	public void delete(){
		trade = tradeService.save(trade);
		Integer id = trade.getTradeId();
		tradeService.deleteById(id);
		Optional<Trade> tradeList = tradeService.findById(id);
		Assert.assertFalse(tradeList.isPresent());
	}
}
