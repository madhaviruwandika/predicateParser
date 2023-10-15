package com.example.ifelsejsonparser.model.filePaser;


import com.example.ifelsejsonparser.model.PredicateNode;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;

public interface Parser {
    public PredicateNode parse(File file) throws URISyntaxException, IOException;
}
