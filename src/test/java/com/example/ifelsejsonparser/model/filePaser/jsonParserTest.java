package com.example.ifelsejsonparser.model.filePaser;

import com.example.ifelsejsonparser.model.PredicateNode;
import com.example.ifelsejsonparser.model.filePaser.JsonParser;
import com.example.ifelsejsonparser.model.filePaser.Util;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URISyntaxException;

public class jsonParserTest {
    @Test
    public void testPredicateExecution() throws URISyntaxException, IOException {
        JsonParser jsonParser = new JsonParser();
        PredicateNode predicateNode =  jsonParser.parse(Util.getFileFromResource("test.json"));

        // input 1 | a="abc", b=12
        Assertions.assertEquals(false, predicateNode.findResult("abc", 12));
        // input 2 | a="ppp", b=12
        Assertions.assertEquals(false, predicateNode.findResult("ppp", 12));
        // input 3 | a="abc", b=4
        Assertions.assertEquals(true, predicateNode.findResult("abc", 4));
        // input 4 | a="abc", b=7
        Assertions.assertEquals(true, predicateNode.findResult("abc", 4));

    }
}
