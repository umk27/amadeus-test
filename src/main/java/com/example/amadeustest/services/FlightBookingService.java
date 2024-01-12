package com.example.amadeustest.services;

import com.example.amadeustest.amadeus.AmadeusClient;
import com.example.amadeustest.model.FlightOfferData;
import com.example.amadeustest.parsers.FlightOfferPriceParser;
import com.example.amadeustest.parsers.FlightOfferSearchParser;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;

@Service
public class FlightBookingService {

    private final AmadeusClient amadeusClient;
    private final AuthorizationRepository authorizationRepository;
    private final FlightOfferSearchParser flightOfferSearchParser;
    private final FlightOfferPriceParser flightOfferPriceParser;

    public FlightBookingService(AmadeusClient amadeusClient, AuthorizationRepository authorizationRepository, FlightOfferSearchParser flightOfferSearchParser, FlightOfferSearchParser flightOfferSearchParser1, FlightOfferPriceParser flightOfferPriceParser) {
        this.amadeusClient = amadeusClient;
        this.authorizationRepository = authorizationRepository;
        this.flightOfferSearchParser = flightOfferSearchParser1;
        this.flightOfferPriceParser = flightOfferPriceParser;
    }

    public void parse() throws IOException {

        File file1 = new File("C:\\Users\\Lenovo\\IdeaProjects\\amadeus-test\\file1");
        String str = "";
        Scanner scanner = new Scanner(file1);
        while (scanner.hasNext()) {
            str = scanner.nextLine();
        }

        List<FlightOfferData> flightOfferDataList = flightOfferSearchParser.parse(str);

       // System.out.println(flightOfferDataList);

        ObjectMapper mapper = new ObjectMapper();

        JsonNode jRoot = mapper.readTree(str);
        JsonNode jData = jRoot.path("data");

        ArrayNode arrayNode = mapper.createArrayNode();
        arrayNode.add(jData.get(0));

        JsonNode node1 = mapper.createObjectNode();
        ObjectNode objectNode = (ObjectNode) node1;
        objectNode = objectNode.putObject("data");
        objectNode.put("type", "flight-offers-pricing");
        objectNode.putArray("flightOffers").addAll(arrayNode);

        String body = node1.toString();

        String token = "Bearer " + authorizationRepository.authorization();

        String res = amadeusClient.flightOffersPrice(token, body);

        File file2 = new File("C:\\Users\\Lenovo\\IdeaProjects\\amadeus-test\\file2");

        FileWriter fileWriter = new FileWriter(file2);
        fileWriter.write(res);
        fileWriter.close();

        String price = flightOfferPriceParser.parse(res);

      //  System.out.println(price);


       // System.out.println("-------------------");

      //  System.out.println(res);


    }
}
