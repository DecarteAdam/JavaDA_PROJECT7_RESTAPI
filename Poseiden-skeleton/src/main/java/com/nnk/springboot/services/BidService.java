package com.nnk.springboot.services;

import com.nnk.springboot.domain.Bid;
import com.nnk.springboot.repositories.BidRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class BidService {

    private final BidRepository bidRepository;

    /**
     * Save bidList
     * @param bid to save
     * @return saved bidList
     */
    public Bid save(Bid bid){
        return this.bidRepository.save(bid);
    }

    /**
     * Find bidList by its id
     * @param id of bidList
     * @return bidList
     */
    public Optional<Bid> findById(int id){
        return this.bidRepository.findById(id);
    }

    /**
     * Find all bidLists
     * @return all bidLists
     */
    public List<Bid> findAll(){
        return this.bidRepository.findAll();
    }

    /**
     * Delete bidList by its id
     * @param id of bidList to delete
     */
    public void deleteById(int id){
        this.bidRepository.deleteById(id);
    }
}
