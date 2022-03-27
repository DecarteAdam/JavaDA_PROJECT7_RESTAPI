package com.nnk.springboot.services;

import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.repositories.CurvePointRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@AllArgsConstructor
@Service
public class CurveService {

    private final CurvePointRepository curvePointRepository;

    /**
     * Save curvePoint
     * @param bidList to save
     * @return saved curvePoint
     */
    public CurvePoint save(CurvePoint bidList){
        return this.curvePointRepository.save(bidList);
    }

    /**
     * Find curvePoint by its id
     * @param id of curvePoint
     * @return curvePoint
     */
    public Optional<CurvePoint> findById(int id){
        return this.curvePointRepository.findById(id);
    }

    /**
     * Find all curvePoints
     * @return all curvePoints
     */
    public List<CurvePoint> findAll(){
        return this.curvePointRepository.findAll();
    }

    /**
     * Delete curvePoint by its id
     * @param id of curvePoint to delete
     */
    public void deleteById(int id){
        this.curvePointRepository.deleteById(id);
    }
}
