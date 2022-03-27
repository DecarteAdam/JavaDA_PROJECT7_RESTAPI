package com.nnk.springboot.services;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.repositories.BidListRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class BidListService {

    private final BidListRepository bidListRepository;

    /**
     * Save bidList
     * @param bidList to save
     * @return saved bidList
     */
    public BidList save(BidList bidList){
        return this.bidListRepository.save(bidList);
    }

    /**
     * Find bidList by its id
     * @param id of bidList
     * @return bidList
     */
    public Optional<BidList> findById(int id){
        return this.bidListRepository.findById(id);
    }

    /**
     * Find all bidLists
     * @return all bidLists
     */
    public List<BidList> findAll(){
        return this.bidListRepository.findAll();
    }

    /**
     * Delete bidList by its id
     * @param id of bidList to delete
     */
    public void deleteById(int id){
        this.bidListRepository.deleteById(id);
    }
}
