package com.example.amadeustest.services;

import com.example.amadeustest.amadeus.AmadeusClient;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

@Service
public class FlightCreateOrderBuilder {

    private final AmadeusClient amadeusClient;
    private final AuthorizationRepository authorizationRepository;

    public FlightCreateOrderBuilder(AmadeusClient amadeusClient, AuthorizationRepository authorizationRepository) {
        this.amadeusClient = amadeusClient;
        this.authorizationRepository = authorizationRepository;
    }

    public void build() throws IOException {

        ObjectMapper mapper = new ObjectMapper();

        ObjectNode oRoot = mapper.createObjectNode();
        //  ObjectNode oData = mapper.createObjectNode();
        File file1 = new File("C:\\Users\\Lenovo\\IdeaProjects\\amadeus-test\\file2");
        String str = "";
        Scanner scanner = new Scanner(file1);
        while (scanner.hasNext()) {
            str = scanner.nextLine();
        }

        JsonNode jFlightOffersPricing = mapper.readTree(str);
        //  System.out.println(jFlightOffersPricing);

        JsonNode jFlightOffers = jFlightOffersPricing.path("data").path("flightOffers");
        //  System.out.println(jFlightOffers);
        ArrayNode aFlightOffers = mapper.createArrayNode();
        aFlightOffers.add(jFlightOffers.get(0));


        //  oData.put("type", "flight-order")
        //         .putArray("flightOffers").addAll(aFlightOffers);


        //  System.out.println(oData);

        ObjectNode oTraveler = mapper.createObjectNode();

        oTraveler.put("id", "1")
                .put("dateOfBirth", "1982-01-16");
        oTraveler.putObject("name").put("firstName", "Vasiliy")
                .put("lastName", "Pupkin");
        oTraveler.put("gender", "MALE");
        ArrayNode aTravelers = mapper.createArrayNode();
        //  aTravelers.add(oTraveler);

        ObjectNode oPhone = mapper.createObjectNode();
        oPhone.put("deviceType", "MOBILE")
                .put("countryCallingCode", "8")
                .put("number", "888888888");
        ArrayNode aPhones = mapper.createArrayNode();
        aPhones.add(oPhone);

        //  ObjectNode oContact = mapper.createObjectNode();
        oTraveler.putObject("contact").put("emailAddress", "pupkin@mail.ru")
                .putArray("phones").addAll(aPhones);
        aTravelers.add(oTraveler);

        ObjectNode oDocument = mapper.createObjectNode();
        //  ObjectNode oDocuments = mapper.createObjectNode();
        ArrayNode aDocuments = mapper.createArrayNode();
        oDocument.put("documentType", "PASSPORT");
        oDocument.put("birthPlace", "Moscow");
        oDocument.put("issuanceLocation", "Moscow");
        oDocument.put("issuanceDate", "2015-04-14");
        oDocument.put("number", "00000000");
        oDocument.put("expiryDate", "2025-04-14");
        oDocument.put("issuanceCountry", "RU");
        oDocument.put("validityCountry", "RU");
        oDocument.put("nationality", "RU");
        oDocument.put("holder", true);
        aDocuments.add(oDocument);

        // oDocuments.putArray("documents").addAll(aDocuments);

        oTraveler.putArray("documents").addAll(aDocuments);


        ObjectNode oData = oRoot.putObject("data");

        oData.put("type", "flight-order")
                .putArray("flightOffers").addAll(aFlightOffers);

        oData.putArray("travelers").addAll(aTravelers);
        // System.out.println(oData);
        // System.out.println(String.valueOf(oData));
        String token = "Bearer " + authorizationRepository.authorization();
        String result = amadeusClient.flightCreateOrders(token, oRoot.toString());
        JsonNode jResult = mapper.readTree(result);
        // JsonNode jData = jResult.path("data");
        //  String id = String.valueOf(jData.get("id"));

        System.out.println(String.valueOf(jResult));
        // return String.valueOf(oRoot);
    }
}
