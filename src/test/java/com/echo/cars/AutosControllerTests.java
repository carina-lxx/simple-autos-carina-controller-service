package com.echo.cars;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AutosController.class) // fire enough the environment, also access to mockMvc to run request on http layers so don't need browser
public class AutosControllerTests {
    @Autowired
    MockMvc mockMvc;

    @MockBean
    AutosService autosService;

    ObjectMapper mapper = new ObjectMapper();

    // GET: /api/autos
        // GET:  /api/autos   returns a lit of all autos
    @Test
    void getAuto_noParms_exists_returnsAutosLists() throws Exception {
        // Arrange
        List<Automobile> automobiles = new ArrayList<>();
        for(int i = 0; i < 5; i++) {
            automobiles.add(new Automobile(1967+i, "Ford", "Mustang", "AABB"+i));
        }
        when(autosService.getAutos()).thenReturn(new AutosList(automobiles));
        // Act
        mockMvc.perform(get("/api/autos"))
                .andDo(print())
                // Assert
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.automobiles", hasSize(5)));
    }
        // GET:  /api/autos   returns 204 no autos found

    @Test
    void getAutos_noParms_non_returnsNoContent() throws Exception {
        // Arrange
        when(autosService.getAutos()).thenReturn(new AutosList());
        // Act
        mockMvc.perform(get("/api/autos"))
                .andDo(print())
        // Assert
                .andExpect(status().isNoContent());
    }

        // GET:  /api/autos?color=RED   returns all red cars
//    @Test
//    void getAutos_searchParmsColor_exists_returnsAutosList() throws Exception {
//        // Arrange
//        List<Automobile> automobiles = new ArrayList<>();
//        for(int i = 0; i < 4; i++) {
//           Automobile auto = new Automobile(1967+i, "Ford", "Mustang", "AABB"+i);
//           auto.setColor("White");
//           automobiles.add(auto);
//        }
//        when(autosService.getAutos(anyString(), anyString())).thenReturn(new AutosList(automobiles));
//        // Act
//        mockMvc.perform(get("/api/autos?color=White"))
//        // Assert
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.color", hasSize(4)));
//    }

    // GET:  /api/autos?make=Ford   returns all Ford


        // GET:  /api/autos?make=Ford&color=GREEN   returns green ford
    @Test
    void getAutos_searchParms_exists_returnsAutosList() throws Exception {
        // Arrange
        List<Automobile> automobiles = new ArrayList<>();
        for(int i = 0; i < 5; i++) {
            automobiles.add(new Automobile(1967+i, "Ford", "Mustang", "AABB"+i));
        }
        when(autosService.getAutos(anyString(), anyString())).thenReturn(new AutosList(automobiles));
        // Act
        mockMvc.perform(get("/api/autos?color=RED&make=Ford"))
        // Assert
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.automobiles", hasSize(5)));
    }
  //Post: /api/autos
        // /api/autos  returns created automobile
    @Test
    void addAuto_valid_returnsAuto() throws Exception {
        Automobile automobile = new Automobile(1967, "Ford", "Mustang", "AABBCC");
        when(autosService.addAuto(any(Automobile.class))).thenReturn(automobile);
        String json = "{\"year\":1967,\"make\":\"Ford\",\"model\":\"Mustang\",\"color\":null,\"owner\":null,\"vin\":\"AABBCC\"}";
        mockMvc.perform(post("/api/autos").contentType(MediaType.APPLICATION_JSON)
                                    .content(json))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("make").value("Ford"));

    }
        // /api/autos  returns error message due to bad request 400

    @Test
    void addAuto_badRequest_returns400() throws Exception {
        when(autosService.addAuto(any(Automobile.class))).thenThrow(InvalidAutoException.class);
        String json = "{\"year\":1967,\"make\":\"Ford\",\"model\":\"Mustang\",\"color\":null,\"owner\":null,\"vin\":\"AABBCC\"}";
        mockMvc.perform(post("/api/autos").contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andDo(print())
                .andExpect(status().isBadRequest());

    }


    // GET: /api/autos/{vin}
        // /api/autos/{vin} returns the requested auto

    @Test
    void getAuto_withVin_returnsAuto() throws Exception {
        Automobile auto = new Automobile(1999, "BMW", "E36", "AABBC");
        when(autosService.getAuto(anyString())).thenReturn(auto);
        mockMvc.perform(get("/api/autos/" + auto.getVin()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("vin").value(auto.getVin()));
    }

    // /api/autos/{vin} returns NoContent 204 auto not found

    // PATCH: /api/autos{vin}
        // /api/autos{vin} returns patched automobile

        // /api/autos{vin} returns NoContent auto not found
        // /api/autos{vin} returns 400 bad request (no payload, no changes, or already done)

    // DELETE: /api/autos/{vin}
        // /api/autos/{vin}  Returns 202, deleted request accepted
        // /api/autos/{vin} Returns 204, vehicle not found
}
