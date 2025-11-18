package com.mambocosmo.urzasoracle.misc.Utils;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;

@Service
public class JSONDataLoader {
    @Value("${urza.dataloader.json-location}")
    private String jsonResourcePath;

    private final ResourceLoader resourceLoader;

    public JSONDataLoader(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

    public String loadData() {
        try {
            Resource resource = resourceLoader.getResource("classpath:" + jsonResourcePath);

            if (!resource.exists()) {
                System.err.println("JSON file not found at classpath location: " + jsonResourcePath);
                return null;
            }

            try (InputStream inputStream = resource.getInputStream();
                    BufferedReader reader = new BufferedReader(
                            new InputStreamReader(inputStream, StandardCharsets.UTF_8))) {

                String jsonContent = reader.lines()
                        .collect(Collectors.joining("\n"));

                System.out.println("Loaded Json from classpath");

                return jsonContent;

            }
        } catch (Exception e) {
            System.err.println("❌ ERROR loading JSON data: " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }
}
