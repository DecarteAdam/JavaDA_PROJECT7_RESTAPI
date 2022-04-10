package com.nnk.springboot;

import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.repositories.CurvePointRepository;
import com.nnk.springboot.services.CurveService;
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
public class CurvePointTests {

	@Autowired
	private CurveService curveService;

	@Autowired
	private CurvePointRepository curvePointRepository;


	private CurvePoint curvePoint;

	@Before
	public void setUp(){
		curvePoint = new CurvePoint();
		curvePoint.setCurveId(10);
		curvePoint.setTerm(10d);
		curvePoint.setValue(10d);
	}

	@After
	public void cleanUp(){
		curvePointRepository.deleteAll();
	}

	@Test
	public void save(){
		curvePoint = curveService.save(curvePoint);
		Assert.assertNotNull(curvePoint.getId());
		Assert.assertEquals(curvePoint.getCurveId(), 10d, 10d);
	}

	@Test
	public void update(){
		curvePoint.setValue(20d);
		curvePoint = curveService.save(curvePoint);
		Assert.assertEquals(curvePoint.getValue(), 20d, 20d);
	}

	@Test
	public void find(){
		curvePoint = curveService.save(curvePoint);
		List<CurvePoint> listResult = curveService.findAll();
		Assert.assertTrue(listResult.size() > 0);
	}

	@Test
	public void delete(){
		curvePoint = curveService.save(curvePoint);
		Integer id = curvePoint.getId();
		curveService.deleteById(id);
		Optional<CurvePoint> bidList = curveService.findById(id);
		Assert.assertFalse(bidList.isPresent());
	}

}
