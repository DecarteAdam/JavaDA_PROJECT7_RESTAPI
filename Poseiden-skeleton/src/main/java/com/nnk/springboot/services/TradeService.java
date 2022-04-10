package com.nnk.springboot.services;

import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.repositories.TradeRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class TradeService {

    private final TradeRepository tradeRepository;

    /**
     * Save trade
     * @param trade to save
     * @return saved trade
     */
    public Trade save(Trade trade){
        return this.tradeRepository.save(trade);
    }

    /**
     * Find trade by its id
     * @param id of trade
     * @return trade
     */
    public Optional<Trade> findById(int id){
        return this.tradeRepository.findById(id);
    }

    /**
     * Find all trade
     * @return all trade
     */
    public List<Trade> findAll(){
        return this.tradeRepository.findAll();
    }

    /**
     * Delete trade by its id
     * @param id of trade to delete
     */
    public void deleteById(int id){
        this.tradeRepository.deleteById(id);
    }
}
