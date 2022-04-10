package com.nnk.springboot.services;

import com.nnk.springboot.domain.Rule;
import com.nnk.springboot.repositories.RuleRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class RuleService {

    private final RuleRepository ruleRepository;

    /**
     * Save ruleName
     * @param rule to save
     * @return saved ruleName
     */
    public Rule save(Rule rule){
        return this.ruleRepository.save(rule);
    }

    /**
     * Find ruleName by its id
     * @param id of ruleName
     * @return ruleName
     */
    public Optional<Rule> findById(int id){
        return this.ruleRepository.findById(id);
    }

    /**
     * Find all ruleName
     * @return all ruleName
     */
    public List<Rule> findAll(){
        return this.ruleRepository.findAll();
    }

    /**
     * Delete ruleName by its id
     * @param id of ruleName to delete
     */
    public void deleteById(int id){
        this.ruleRepository.deleteById(id);
    }
}
