package com.example.ifelsejsonparser.model.filePaser;

import com.example.ifelsejsonparser.model.PredicateNode;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;

public class JsonParser implements Parser {
    ObjectMapper mapper = new ObjectMapper();
    @Override
    public PredicateNode parse(File file) throws URISyntaxException, IOException {
        JsonNode node = mapper.readTree(file);

        JsonNode logic = node.get("logic");
        JsonNode props = node.get("properties");

        PredicateNode root = Util.buildNodeTree(logic, props);
        return root;
    }
}
