package com.example.spring6restmvc.services;

import com.example.spring6restmvc.model.BeerDto;
import com.example.spring6restmvc.model.BeerStyle;
import org.springframework.data.domain.Page;

import java.util.Optional;
import java.util.UUID;

public interface BeerService {
    Page<BeerDto> listBeers(String beerName, BeerStyle beerStyle, Boolean showInventory, Integer pageNumber, Integer pageSize);
    Optional<BeerDto> getBeerById(UUID id);
    BeerDto save(BeerDto beerDto);
    Optional<BeerDto> update(UUID beerId, BeerDto beerDto);
    Boolean deleteById(UUID beerId);
    Optional<BeerDto> patch(UUID beerId, BeerDto beerDto);
}
