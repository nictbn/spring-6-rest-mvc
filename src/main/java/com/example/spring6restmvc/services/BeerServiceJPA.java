package com.example.spring6restmvc.services;

import com.example.spring6restmvc.mappers.BeerMapper;
import com.example.spring6restmvc.model.BeerDto;
import com.example.spring6restmvc.repositories.BeerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Primary
@RequiredArgsConstructor
public class BeerServiceJPA implements BeerService {
    private final BeerRepository beerRepository;
    private final BeerMapper beerMapper;
    @Override
    public List<BeerDto> listBeers() {
        return null;
    }

    @Override
    public Optional<BeerDto> getBeerById(UUID id) {
        return Optional.empty();
    }

    @Override
    public BeerDto save(BeerDto beerDto) {
        return null;
    }

    @Override
    public void update(UUID beerId, BeerDto beerDto) {

    }

    @Override
    public void deleteById(UUID beerId) {

    }

    @Override
    public void patch(UUID beerId, BeerDto beerDto) {

    }
}
