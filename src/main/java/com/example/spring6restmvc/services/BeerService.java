package com.example.spring6restmvc.services;

import com.example.spring6restmvc.model.Beer;

import java.util.List;
import java.util.UUID;

public interface BeerService {
    List<Beer> listBeers();

    Beer getBeerById(UUID id);

    Beer save(Beer beer);

    void update(UUID beerId, Beer beer);
    void deleteById(UUID beerId);
    void patch(UUID beerId, Beer beer);
}
