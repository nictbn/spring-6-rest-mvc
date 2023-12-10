package com.example.spring6restmvc.controllers;

import com.example.spring6restmvc.entities.Beer;
import com.example.spring6restmvc.mappers.BeerMapper;
import com.example.spring6restmvc.model.BeerDto;
import com.example.spring6restmvc.repositories.BeerRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
class BeerControllerIT {
    @Autowired
    BeerController beerController;
    @Autowired
    BeerRepository beerRepository;

    @Autowired
    BeerMapper beerMapper;

    @Test
    void testListBeers() {
        List<BeerDto> dtos = beerController.listBeers();
        assertThat(dtos.size()).isEqualTo(3);
    }

    @Rollback
    @Transactional
    @Test
    void testEmptyList() {
        beerRepository.deleteAll();
        List<BeerDto> dtos = beerController.listBeers();
        assertThat(dtos.size()).isEqualTo(0);
    }

    @Test
    void testGetById() {
        Beer beer = beerRepository.findAll().get(0);
        BeerDto dto = beerController.getBeerById(beer.getId());
        assertThat(dto).isNotNull();
    }

    @Test
    void testBeerIdNotFound() {
        assertThrows(NotFoundException.class, () -> {
            beerController.getBeerById(UUID.randomUUID());
        });
    }

    @Test
    @Transactional
    @Rollback
    void saveBeerTest() {
        BeerDto beerDto = BeerDto.builder()
                .beerName("New Beer")
                .build();
        ResponseEntity responseEntity = beerController.handlePost(beerDto);
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(201));
        assertThat(responseEntity.getHeaders().getLocation()).isNotNull();
        String[] locationUUID = responseEntity.getHeaders().getLocation().getPath().split("/");
        UUID savedUUID = UUID.fromString(locationUUID[4]);

        Beer beer = beerRepository.findById(savedUUID).get();
        assertThat(beer).isNotNull();
    }

    @Transactional
    @Rollback
    @Test
    void updateExistingBeer() {
        Beer beer = beerRepository.findAll().get(0);
        BeerDto beerDto = beerMapper.beerToBeerDto(beer);
        beerDto.setId(null);
        beerDto.setVersion(null);
        final String beerName = "UPDATED";
        beerDto.setBeerName(beerName);

        ResponseEntity responseEntity = beerController.updateById(beer.getId(), beerDto);
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(204));

        Beer updatedBeer = beerRepository.findById(beer.getId()).get();
        assertThat(updatedBeer.getBeerName()).isEqualTo(beerName);
    }

    @Test
    void testUpdateNotFound() {
        assertThrows(NotFoundException.class, () -> {
            beerController.updateById(UUID.randomUUID(), BeerDto.builder().build());
        });
    }

    @Test
    @Transactional
    @Rollback
    void deleteByIdFound() {
        Beer beer = beerRepository.findAll().get(0);
        ResponseEntity responseEntity = beerController.deleteById(beer.getId());
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(204));
        assertThat(beerRepository.findById(beer.getId()).isEmpty());
    }

    @Test
    void testDeleteNotFound() {
        assertThrows(NotFoundException.class, () -> {
            beerController.deleteById(UUID.randomUUID());
        });
    }
}