package com.ganesh.recipe.batch;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;

@Component
public class JsonMapLoader {

    public Map<String, Object> loadJsonAsMap(String path) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(
                new ClassPathResource(path).getInputStream(),
                Map.class
        );
    }
}
