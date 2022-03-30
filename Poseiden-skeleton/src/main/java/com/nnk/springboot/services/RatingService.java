package com.nnk.springboot.services;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.repositories.RatingRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class RatingService {

    private final RatingRepository ratingRepository;

    /**
     * Save rating
     * @param rating to save
     * @return saved rating
     */
    public Rating save(Rating rating){
        return this.ratingRepository.save(rating);
    }

    /**
     * Find rating by its id
     * @param id of rating
     * @return bidList
     */
    public Optional<Rating> findById(int id){
        return this.ratingRepository.findById(id);
    }

    /**
     * Find all rating
     * @return all rating
     */
    public List<Rating> findAll(){
        return this.ratingRepository.findAll();
    }

    /**
     * Delete rating by its id
     * @param id of rating to delete
     */
    public void deleteById(int id){
        this.ratingRepository.deleteById(id);
    }
}
