package com.example.spring6restmvc.controllers;

import com.example.spring6restmvc.configuration.SpringSecurityConfig;
import com.example.spring6restmvc.model.BeerDto;
import com.example.spring6restmvc.services.BeerService;
import com.example.spring6restmvc.services.BeerServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import static com.example.spring6restmvc.controllers.BeerController.BEER_PATH;
import static com.example.spring6restmvc.controllers.BeerController.BEER_PATH_ID;
import static com.example.spring6restmvc.controllers.BeerControllerIT.jwtRequestPostProcessor;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(BeerController.class)
@Import(SpringSecurityConfig.class)
class BeerControllerTest {
    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    BeerService beerService;

    // Only used to instantiate a beer entity
    BeerServiceImpl beerServiceImpl;

    @Captor
    ArgumentCaptor<UUID> uuidArgumentCaptor;

    @Captor
    ArgumentCaptor<BeerDto> beerArgumentCaptor;

    @BeforeEach
    void setup() {
        beerServiceImpl = new BeerServiceImpl();
    }

    @Test
    void getBeerById() throws Exception {
        BeerDto testBeerDto = beerServiceImpl.listBeers(null, null, false, 1, 25).getContent().get(0);

        given(beerService.getBeerById(testBeerDto.getId())).willReturn(Optional.of(testBeerDto));

        mockMvc.perform(get(BEER_PATH_ID, testBeerDto.getId())
                        .with(jwtRequestPostProcessor)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(testBeerDto.getId().toString())))
                .andExpect(jsonPath("$.beerName", is(testBeerDto.getBeerName())));
    }

    @Test
    void testListBeers() throws Exception {
        Page<BeerDto> beerPage = beerServiceImpl.listBeers(null, null, false, null, null);
        doReturn(beerPage).when(beerService).listBeers(any(), any(), any(), any(), any());

        mockMvc.perform(get(BEER_PATH)
                        .with(jwtRequestPostProcessor)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.content.length()", is(3)));
    }

    @Test
    void testCreateBeer() throws Exception {
        BeerDto beerDto = beerServiceImpl.listBeers(null, null, false, 1, 25).getContent().get(0);
        beerDto.setVersion(null);
        beerDto.setId(null);

        given(beerService.save(any(BeerDto.class))).willReturn(beerServiceImpl.listBeers(null, null, false, 1, 25).getContent().get(1));

        mockMvc.perform(post(BEER_PATH)
                        .with(jwtRequestPostProcessor)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(beerDto)))
                .andExpect(status().isCreated())
                .andExpect(header().exists("Location"));
    }

    @Test
    void testUpdateBeer() throws Exception {
        BeerDto beerDto = beerServiceImpl.listBeers(null, null, false, 1, 25).getContent().get(0);

        given(beerService.update(any(), any())).willReturn(Optional.of(beerDto));
        mockMvc.perform(put(BEER_PATH_ID, beerDto.getId())
                        .with(jwtRequestPostProcessor)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(beerDto)))
                .andExpect(status().isNoContent());
        verify(beerService).update(any(UUID.class), any(BeerDto.class));
    }

    @Test
    void testDeleteBeer() throws Exception {
        BeerDto beerDto = beerServiceImpl.listBeers(null, null, false, 1, 25).getContent().get(0);

        given(beerService.deleteById(any())).willReturn(Boolean.TRUE);
        mockMvc.perform(delete(BEER_PATH_ID, beerDto.getId())
                        .with(jwtRequestPostProcessor)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        verify(beerService).deleteById(uuidArgumentCaptor.capture());
        assertThat(beerDto.getId()).isEqualTo(uuidArgumentCaptor.getValue());
    }

    @Test
    void testPatchBeer() throws Exception {
        BeerDto beerDto = beerServiceImpl.listBeers(null, null, false, 1, 25).getContent().get(0);

        Map<String, Object> beerMap = new HashMap<>();
        beerMap.put("beerName", "New Name");

        given(beerService.patch(any(), any())).willReturn(Optional.of(BeerDto.builder().build()));
        mockMvc.perform(patch(BEER_PATH_ID, beerDto.getId())
                        .with(jwtRequestPostProcessor)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(beerMap)))
                .andExpect(status().isNoContent());
        verify(beerService).patch(uuidArgumentCaptor.capture(), beerArgumentCaptor.capture());
        assertThat(beerDto.getId()).isEqualTo(uuidArgumentCaptor.getValue());
        assertThat(beerMap.get("beerName")).isEqualTo(beerArgumentCaptor.getValue().getBeerName());
    }

    @Test
    void getBeerByIdNotFound() throws Exception {
        given(beerService.getBeerById(any(UUID.class))).willReturn(Optional.empty());
        mockMvc.perform(get(BEER_PATH_ID, UUID.randomUUID())
                .with(jwtRequestPostProcessor))
                .andExpect(status().isNotFound());
    }

    @Test
    void testCreateBeerNullBeerName() throws Exception {
        BeerDto beerDto = BeerDto.builder().build();
        given(beerService.save(any(BeerDto.class))).willReturn(beerServiceImpl.listBeers(null, null, false, 1, 25).getContent().get(1));
        MvcResult mvcResult = mockMvc.perform(post(BEER_PATH)
                        .with(jwtRequestPostProcessor)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(beerDto)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.length()", is(6))).andReturn();
    }

    @Test
    void testUpdateBeerBlankName() throws Exception {
        BeerDto beerDto = beerServiceImpl.listBeers(null, null, false, 1, 25).getContent().get(0);
        beerDto.setBeerName("");
        given(beerService.update(any(), any())).willReturn(Optional.of(beerDto));
        mockMvc.perform(put(BEER_PATH_ID, beerDto.getId())
                        .with(jwtRequestPostProcessor)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(beerDto)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.length()", is(1)));
    }
}