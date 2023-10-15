package com.example.ifelsejsonparser.model.filePaser;

import com.example.ifelsejsonparser.model.Operand;
import com.example.ifelsejsonparser.model.Operator;
import com.example.ifelsejsonparser.model.Predicate;
import com.example.ifelsejsonparser.model.PredicateNode;
import com.fasterxml.jackson.databind.JsonNode;

import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class Util {
    public static File getFileFromResource(String fileName) throws URISyntaxException {

        ClassLoader classLoader = Util.class.getClassLoader();
        URL resource = classLoader.getResource(fileName);
        if (resource == null) {
            throw new IllegalArgumentException("file not found! " + fileName);
        } else {
            return new File(resource.toURI());
        }

    }
    public static Operand getOperand(String type, JsonNode value){
        switch (type) {
            case Constant.typeString:
                Operand<String> opStr = new Operand<>();
                opStr.setValue(value.asText());
                return opStr;
            case Constant.typeInteger:
                Operand<Integer> opInt = new Operand<>();
                opInt.setValue(value.asInt());
                return opInt;
            default:
                return null;
        }
    }

    public static Predicate buildPredicate(JsonNode predicate, JsonNode props) {
        Predicate p = new Predicate();
        p.setOperator(Operator.valueOf(predicate.get(Constant.atrOperator).asText()));

        JsonNode operands = predicate.get(Constant.atrOperands);
        List<Operand> operandsList = new ArrayList<>();
        if(operands != null && operands.isArray()) {
            for(JsonNode j : operands) {
                JsonNode id = j.get(Constant.atrInputId);
                JsonNode value = j.get(Constant.atrValue);
                String type = props.get(id.asText()).get(Constant.atrType).asText();

                Operand operand = Util.getOperand(type, value);
                operand.setInputId(id.asInt());
                operand.setOperator(Operator.valueOf(j.get(Constant.atrOperator).asText()));
                operandsList.add(operand);
            }
        }

        p.setOperands(operandsList);
        return p;
    }

    public static PredicateNode buildNodeTree(JsonNode jsonNode, JsonNode props){
        JsonNode ifStat = jsonNode.get(Constant.atrIf);
        JsonNode elseStat = jsonNode.get(Constant.atrElse);
        JsonNode result = jsonNode.get(Constant.atrResult);

        PredicateNode node = new PredicateNode();

        if(result!= null) {
            node.setResult(result.asBoolean());
        }
        if(ifStat != null && !ifStat.isEmpty()) {
            node.setPredicate(Util.buildPredicate(ifStat, props));
        }

        if(elseStat != null && !elseStat.isEmpty()) {
            node.setElseStat(buildNodeTree(elseStat, props));
        }

        return node;
    }
}
