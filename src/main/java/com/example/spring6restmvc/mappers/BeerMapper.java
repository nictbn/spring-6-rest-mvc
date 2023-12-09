package com.example.spring6restmvc.mappers;

import com.example.spring6restmvc.entities.Beer;
import com.example.spring6restmvc.model.BeerDto;
import org.mapstruct.Mapper;

@Mapper
public interface BeerMapper {
    Beer beerDtoToBeer(BeerDto dto);
    BeerDto beerToBeerDto(Beer beer);
}
