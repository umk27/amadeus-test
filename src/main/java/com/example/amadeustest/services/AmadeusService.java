package com.example.amadeustest.services;

import com.example.amadeustest.amadeus.AmadeusClient;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

@Service
public class AmadeusService {

    private final AmadeusClient amadeusClient;

    private final AuthorizationRepository authorizationRepository;


    public AmadeusService(AmadeusClient amadeusClient, AuthorizationRepository authorizationRepository) {
        this.amadeusClient = amadeusClient;
        this.authorizationRepository = authorizationRepository;
    }

    public String getJson() {
        String token = "Bearer " + authorizationRepository.authorization();
        System.out.println(token);
        String json = amadeusClient.flightOffersSearch(token, "MOW", "BER",
                "2024-03-10", 1, 2);

        File file1 = new File("C:\\Users\\Lenovo\\IdeaProjects\\amadeus-test\\file1");
        try {
            FileWriter writer = new FileWriter(file1);
            writer.write(json);
            writer.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        /*ObjectNode objectNode;
        try {
            objectNode = new ObjectMapper().readValue(json, ObjectNode.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        JsonNode jsonNode1 = objectNode.get("data");
        System.out.println(jsonNode1);
        JsonNode jsonNode2 = jsonNode1.get(0);
        System.out.println(jsonNode2);*/
       // System.out.println(objectNode.get("data").get(0).get(0).get("type"));
        return json;
    }
}
