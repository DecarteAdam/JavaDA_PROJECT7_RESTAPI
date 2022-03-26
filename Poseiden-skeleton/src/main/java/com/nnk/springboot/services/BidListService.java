package com.nnk.springboot.services;

import com.nnk.springboot.repositories.BidListRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class BidListService {

    private final BidListRepository bidListRepository;


}
