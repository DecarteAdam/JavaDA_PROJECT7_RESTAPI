package com.nnk.springboot.services;

import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.repositories.RuleNameRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class RuleNameService {

    private final RuleNameRepository ruleNameRepository;

    /**
     * Save ruleName
     * @param ruleName to save
     * @return saved ruleName
     */
    public RuleName save(RuleName ruleName){
        return this.ruleNameRepository.save(ruleName);
    }

    /**
     * Find ruleName by its id
     * @param id of ruleName
     * @return ruleName
     */
    public Optional<RuleName> findById(int id){
        return this.ruleNameRepository.findById(id);
    }

    /**
     * Find all ruleName
     * @return all ruleName
     */
    public List<RuleName> findAll(){
        return this.ruleNameRepository.findAll();
    }

    /**
     * Delete ruleName by its id
     * @param id of ruleName to delete
     */
    public void deleteById(int id){
        this.ruleNameRepository.deleteById(id);
    }
}
