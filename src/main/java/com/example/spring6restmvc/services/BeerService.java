package com.example.spring6restmvc.services;

import com.example.spring6restmvc.model.BeerDto;
import com.example.spring6restmvc.model.BeerStyle;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface BeerService {
    List<BeerDto> listBeers(String beerName, BeerStyle beerStyle, Boolean showInventory);
    Optional<BeerDto> getBeerById(UUID id);
    BeerDto save(BeerDto beerDto);
    Optional<BeerDto> update(UUID beerId, BeerDto beerDto);
    Boolean deleteById(UUID beerId);
    Optional<BeerDto> patch(UUID beerId, BeerDto beerDto);
}
