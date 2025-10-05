package org.example.serializer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.common.header.Headers;
import org.apache.kafka.common.serialization.Serializer;
import org.example.models.UserInfoDto;

import java.util.Map;

public class UserInfoSerializer implements Serializer<UserInfoDto> {
    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {
        Serializer.super.configure(configs, isKey);
    }

    @Override
    public byte[] serialize(String s, UserInfoDto userInfoDto) {
        byte[] bytes = null;
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            bytes = objectMapper.writeValueAsString(userInfoDto).getBytes();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bytes;
    }

    @Override
    public void close() {
        Serializer.super.close();
    }
}
