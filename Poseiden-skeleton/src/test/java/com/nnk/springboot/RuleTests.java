package com.nnk.springboot;

import com.nnk.springboot.domain.Rule;
import com.nnk.springboot.repositories.RuleRepository;
import com.nnk.springboot.services.RuleService;
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
public class RuleTests {

	@Autowired
	private RuleService ruleService;

	@Autowired
	private RuleRepository ruleRepository;

	private Rule rule;

	@Before
	public void setUp(){
		rule = new Rule();
		rule.setName("Rule Name");
		rule.setDescription("Description");
		rule.setJson("JSON");
		rule.setTemplate("Template");
	}

	@After
	public void cleanUp(){
		ruleRepository.deleteAll();
	}

	@Test
	public void save(){
		rule = ruleService.save(rule);
		Assert.assertNotNull(rule.getId());
		Assert.assertEquals("Rule Name", rule.getName());
	}

	@Test
	public void update(){
		rule.setName("Rule Name Update");
		rule = ruleService.save(rule);
		Assert.assertEquals("Rule Name Update", rule.getName());
	}

	@Test
	public void find(){
		rule = ruleService.save(rule);
		List<Rule> listResult = ruleService.findAll();
		Assert.assertTrue(listResult.size() > 0);
	}

	@Test
	public void delete(){
		rule = ruleService.save(rule);
		Integer id = rule.getId();
		ruleService.deleteById(id);
		Optional<Rule> ruleList = ruleService.findById(id);
		Assert.assertFalse(ruleList.isPresent());
	}
}
