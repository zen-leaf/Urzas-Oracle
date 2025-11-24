package com.mambocosmo.urzasoracle.misc.Utils;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.channels.ReadableByteChannel;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.data.domain.Page;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mambocosmo.urzasoracle.DTO.GenericDTO;
import com.mambocosmo.urzasoracle.converters.GenericConverter;
import com.mambocosmo.urzasoracle.entities.GenericEntity;

import lombok.Data;

@Data
public class Util {
    public static final String ANSI_CURSOR_UP = "\u001b[A";
    public static final String ANSI_ERASE_LINE = "\u001b[2K";

    public static void inlinePrint(String in) {
        System.out.println(in + ANSI_CURSOR_UP);
    }

    public static <I extends GenericEntity, O extends GenericDTO, C extends GenericConverter<I, O>> Page<O> batchConvert(
            Page<I> in, C converter) {
        AtomicInteger index = new AtomicInteger(0);
        Integer tot = in.getNumberOfElements();
        Page<O> converted = in.map(card -> {
            
            O out = converter.fromEToD(card);
            index.incrementAndGet();
            int progress = Math.round((Float.valueOf(index.get()) / tot) * 25);
            Util.inlinePrint(
                    "Converting cards for page display ["
                            + "#".repeat(progress) + " ".repeat(25 - progress) + "]");

            return out;
        });
        System.out.println(Util.ANSI_ERASE_LINE);
        System.out.println(Util.ANSI_CURSOR_UP + "Converting cards for page display [#########################] Done.");
        return converted;
    }

    public static String downloadTempFile(String from, String to) {
        try {
            InputStream stream = new BufferedInputStream(new URI(from).toURL().openStream());
            ReadableByteChannel bc = Channels.newChannel(stream);
            Files.createDirectories(Paths.get(System.getProperty("java.io.tmpdir") + "Urza\\"));
            FileOutputStream fos = new FileOutputStream(System.getProperty("java.io.tmpdir") + "Urza\\" + to);

            FileChannel fc = fos.getChannel();
            fc.transferFrom(bc, 0, Long.MAX_VALUE);
            fos.close();
            return System.getProperty("java.io.tmpdir") + "Urza\\" + to;

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
