package com.example.spring6restmvc.services;

import com.example.spring6restmvc.model.BeerDto;
import com.example.spring6restmvc.model.BeerStyle;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;

@Slf4j
@Service
public class BeerServiceImpl implements BeerService {
    private Map<UUID, BeerDto> beerMap;

    public BeerServiceImpl() {
        this.beerMap = new HashMap<>();

        BeerDto beerDto1 = BeerDto.builder()
                .id(UUID.randomUUID())
                .version(1)
                .beerName("Galaxy Cat")
                .beerStyle(BeerStyle.PALE_ALE)
                .upc("12356")
                .price(new BigDecimal("12.99"))
                .quantityOnHand(122)
                .createdDate(LocalDateTime.now())
                .updateDate(LocalDateTime.now())
                .build();

        BeerDto beerDto2 = BeerDto.builder()
                .id(UUID.randomUUID())
                .version(1)
                .beerName("Crank")
                .beerStyle(BeerStyle.PALE_ALE)
                .upc("12356222")
                .price(new BigDecimal("11.99"))
                .quantityOnHand(392)
                .createdDate(LocalDateTime.now())
                .updateDate(LocalDateTime.now())
                .build();

        BeerDto beerDto3 = BeerDto.builder()
                .id(UUID.randomUUID())
                .version(1)
                .beerName("Sunshine City")
                .beerStyle(BeerStyle.IPA)
                .upc("12356")
                .price(new BigDecimal("13.99"))
                .quantityOnHand(144)
                .createdDate(LocalDateTime.now())
                .updateDate(LocalDateTime.now())
                .build();

        beerMap.put(beerDto1.getId(), beerDto1);
        beerMap.put(beerDto2.getId(), beerDto2);
        beerMap.put(beerDto3.getId(), beerDto3);
    }

    @Override
    public Page<BeerDto> listBeers(String beerName, BeerStyle beerStyle, Boolean showInventory, Integer pageNumber, Integer pageSize) {
        Pageable pageable = PageRequest.of(0, 25);
        ArrayList<BeerDto> beerDtos = new ArrayList<>(beerMap.values());
        return new PageImpl<>(beerDtos, pageable, beerDtos.size());
    }
    @Override
    public Optional<BeerDto> getBeerById(UUID id) {
        log.debug("Get BeerId in service was called with id: " + id);
        return Optional.of(beerMap.get(id));
    }

    @Override
    public BeerDto save(BeerDto beerDto) {
        BeerDto savedBeerDto = BeerDto.builder()
                .id(UUID.randomUUID())
                .createdDate(LocalDateTime.now())
                .updateDate(LocalDateTime.now())
                .version(beerDto.getVersion())
                .beerName(beerDto.getBeerName())
                .beerStyle(beerDto.getBeerStyle())
                .quantityOnHand(beerDto.getQuantityOnHand())
                .upc(beerDto.getUpc())
                .price(beerDto.getPrice())
                .build();
        beerMap.put(savedBeerDto.getId(), savedBeerDto);
        return savedBeerDto;
    }

    @Override
    public Optional<BeerDto> update(UUID beerId, BeerDto beerDto) {
        BeerDto existing = beerMap.get(beerId);
        existing.setBeerName(beerDto.getBeerName());
        existing.setPrice(beerDto.getPrice());
        existing.setUpc(beerDto.getUpc());
        existing.setQuantityOnHand(beerDto.getQuantityOnHand());
        existing.setVersion(beerDto.getVersion());
        existing.setBeerStyle(beerDto.getBeerStyle());
        beerMap.put(existing.getId(), existing);
        return Optional.of(existing);
    }

    @Override
    public Boolean deleteById(UUID beerId) {
        beerMap.remove(beerId);
        return Boolean.TRUE;
    }

    @Override
    public Optional<BeerDto> patch(UUID beerId, BeerDto beerDto) {
        BeerDto existing = beerMap.get(beerId);
        if (StringUtils.hasText(beerDto.getBeerName())) {
            existing.setBeerName(beerDto.getBeerName());
        }
        if (beerDto.getBeerStyle() != null) {
            existing.setBeerStyle(beerDto.getBeerStyle());
        }
        if (beerDto.getPrice() != null) {
            existing.setPrice(beerDto.getPrice());
        }
        if (beerDto.getQuantityOnHand() != null) {
            existing.setQuantityOnHand(beerDto.getQuantityOnHand());
        }
        if (StringUtils.hasText(beerDto.getUpc())) {
            existing.setUpc(beerDto.getUpc());
        }
        if (beerDto.getVersion() != null) {
            existing.setVersion(beerDto.getVersion());
        }
        beerMap.put(existing.getId(), existing);
        return Optional.of(existing);
    }
}
