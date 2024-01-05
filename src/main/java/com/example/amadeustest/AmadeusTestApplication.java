package com.example.amadeustest;

import com.example.amadeustest.services.FlightBookingService;
import com.example.amadeustest.services.AmadeusService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

import java.io.IOException;

@SpringBootApplication
@EnableFeignClients
public class AmadeusTestApplication {

    private static AmadeusService amadeusService;
    private static FlightBookingService jsonExample;

    public AmadeusTestApplication(AmadeusService amadeusService,
                                  FlightBookingService jsonExample) {
        this.amadeusService = amadeusService;
        this.jsonExample = jsonExample;
    }

    public static void main(String[] args) throws IOException {
        SpringApplication.run(AmadeusTestApplication.class, args);
      //    String json = amadeusService.getJson();

        //   System.out.println(json);

         jsonExample.parse();


    }

}
