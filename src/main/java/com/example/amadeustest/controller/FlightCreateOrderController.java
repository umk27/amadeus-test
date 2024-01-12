package com.example.amadeustest.controller;

import com.example.amadeustest.services.FlightCreateOrderBuilder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
public class FlightCreateOrderController {

    private final FlightCreateOrderBuilder flightCreateOrderBuilder;

    public FlightCreateOrderController(FlightCreateOrderBuilder flightCreateOrderBuilder) {
        this.flightCreateOrderBuilder = flightCreateOrderBuilder;
    }


   /* @GetMapping("/")
    public String get(){
        String str;
        try {
          str =  flightCreateOrderBuilder.build();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return str;
    }*/
}
