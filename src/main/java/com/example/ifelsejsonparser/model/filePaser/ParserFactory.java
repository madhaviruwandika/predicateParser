package com.example.ifelsejsonparser.model.filePaser;

/**
 *  Parser factory is introduced for future extension of different file format.
 *  Current implementation only covers the json parsing
 */
public class ParserFactory {
    private Parser jsonParser;
    private Parser xmlParser;

    public ParserFactory() {
        jsonParser = new JsonParser();
        xmlParser = new XMLParser();
    }

    public Parser getParser(String fileType) {
        switch (fileType){
            case Constant.json:
                return jsonParser;
            case Constant.xml:
                return xmlParser;
            default:
                return null;
        }
    }
}
