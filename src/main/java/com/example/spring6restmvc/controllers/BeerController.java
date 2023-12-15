package com.example.spring6restmvc.controllers;

import com.example.spring6restmvc.model.BeerDto;
import com.example.spring6restmvc.model.BeerStyle;
import com.example.spring6restmvc.services.BeerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@RestController
public class BeerController {

    public static final String BEER_PATH = "/api/v1/beer";
    public static final String BEER_PATH_ID = BEER_PATH + "/{beerId}";
    private final BeerService beerService;

    @PatchMapping(BEER_PATH_ID)
    public ResponseEntity patchBeerById(@PathVariable("beerId") UUID beerId, @RequestBody BeerDto beerDto) {
        if (beerService.patch(beerId, beerDto).isEmpty()) {
            throw new NotFoundException();
        }
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping(BEER_PATH_ID)
    public ResponseEntity deleteById(@PathVariable("beerId") UUID beerId) {
        if (!beerService.deleteById(beerId)) {
            throw new NotFoundException();
        }
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @PutMapping(BEER_PATH_ID)
    public ResponseEntity updateById(@PathVariable("beerId") UUID beerId, @Validated @RequestBody BeerDto beerDto) {
        if (beerService.update(beerId, beerDto).isEmpty()) {
            throw new NotFoundException();
        }
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }
    @PostMapping(BEER_PATH)
    public ResponseEntity handlePost(@Validated @RequestBody BeerDto beerDto) {
        BeerDto savedBeerDto = beerService.save(beerDto);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Location", "/api/v1/beer/" + savedBeerDto.getId().toString());
        return new ResponseEntity(headers, HttpStatus.CREATED);
    }
    @GetMapping(BEER_PATH)
    public List<BeerDto> listBeers(
            @RequestParam(required = false, value = "beerName") String beerName,
            @RequestParam(required = false, value = "beerStyle")BeerStyle beerStyle
            ) {
        return beerService.listBeers(beerName, beerStyle);
    }

    @GetMapping(BEER_PATH_ID)
    public BeerDto getBeerById(@PathVariable("beerId") UUID id) {
        log.debug("Get beer by id in controller");
        return beerService.getBeerById(id).orElseThrow(NotFoundException::new);
    }
}
