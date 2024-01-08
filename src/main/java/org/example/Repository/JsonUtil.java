package org.example.Repository;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;

public class JsonUtil {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static void saveToJsonFile(String filePath, Object data) throws IOException {
        objectMapper.writeValue(new File(filePath), data);
    }

    public static <T> T readFromJsonFile(String filePath, TypeReference<T> valueTypeRef) throws IOException {
        return objectMapper.readValue(new File(filePath), valueTypeRef);
    }
}
