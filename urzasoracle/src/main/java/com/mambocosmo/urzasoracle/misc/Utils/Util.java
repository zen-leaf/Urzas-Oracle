package com.mambocosmo.urzasoracle.misc.Utils;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.channels.ReadableByteChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.Data;

@Data
public class Util {

    public static Path downloadTempFile(String from, String to) {
        Path absolutePath = Paths.get("/tmp/urza", to);
        try {
            InputStream stream = new BufferedInputStream(new URI(from).toURL().openStream());
            ReadableByteChannel bc = Channels.newChannel(stream);
            Files.createDirectories(Paths.get("/tmp/urza/"));
            FileOutputStream fos = new FileOutputStream(absolutePath.toFile());
            absolutePath.toFile().getPath();
            FileChannel fc = fos.getChannel();
            fc.transferFrom(bc, 0, Long.MAX_VALUE);
            fos.close();
            System.out.println("TEMPDIR " + absolutePath.toString());
            return absolutePath;

        } catch (IOException | URISyntaxException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;

    }

    public static Set<String> generateUniqueFieldNames(String insideOf) {

        ObjectMapper mapper = new ObjectMapper();
        JsonNode cardData = null;
        try {
            cardData = mapper.readTree(new File("urzasoracle/src/main/resources/json/oracles.json"));
            Set<String> params = new HashSet<>();
            String searchfor = insideOf;
            cardData.forEach(e -> {
                // System.out.println("CardData: " + e.properties().);
                if (e.get(searchfor) != null) {
                    params.add(e.get(searchfor).asText());

                    // .forEach(e2 -> System.out.println(e2))
                    // System.out.println(e.get(searchfor).asText());
                    // params.add(e.get(searchfor).asText());
                }
            });
            return params;
            // cardData.get(0).properties().forEach(e -> System.out.println(e.getKey()));
        } catch (

        IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        }
    }

}
