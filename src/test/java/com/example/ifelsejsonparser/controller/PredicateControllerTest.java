package com.example.ifelsejsonparser.controller;

import com.example.ifelsejsonparser.model.EvaluationInput;
import com.example.ifelsejsonparser.service.PredicateService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.hamcrest.Matchers.containsString;

import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PredicateController.class)
public class PredicateControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    PredicateService predicateService;

    @Test
    public void shouldStorePredicateJsonInFile() throws Exception {
        doNothing().when(predicateService).uploadPredicate(Mockito.anyString());
        this.mockMvc.perform(post("/api/v1/predicate/upload")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\n" +
                                "\t\"properties\": {\n" +
                                "\t\t\"1\": {\n" +
                                "\t\t\t\"type\": \"String\",\n" +
                                "\t\t\t\"name\": \"a\"\n" +
                                "\t\t},\n" +
                                "\t\t\"2\": {\n" +
                                "\t\t\t\"type\": \"Integer\",\n" +
                                "\t\t\t\"name\": \"b\"\n" +
                                "\t\t}\n" +
                                "\t},\n" +
                                "\t\"logic\": {\n" +
                                "\t\t\"if\": {\n" +
                                "\t\t\t\"operator\" : \"AND\",\n" +
                                "\t\t\t\"operands\" : [\n" +
                                "\t\t\t\t{\n" +
                                "\t\t\t\t\t\"inputId\": 1,\n" +
                                "\t\t\t\t\t\"operator\": \"EQUAL\",\n" +
                                "\t\t\t\t\t\"value\": \"abc\"\n" +
                                "\t\t\t\t},\n" +
                                "\t\t\t\t{\n" +
                                "\t\t\t\t\t\"inputId\": 2,\n" +
                                "\t\t\t\t\t\"operator\": \"EQUAL\",\n" +
                                "\t\t\t\t\t\"value\": 4\n" +
                                "\t\t\t\t}\n" +
                                "\t\t\t]\n" +
                                "\t\t},\n" +
                                "\t\t\"result\": true,\n" +
                                "\t\t\"else\": {\n" +
                                "\t\t\t\"if\": {\n" +
                                "\t\t\t\t\"operator\" : \"AND\",\n" +
                                "\t\t\t\t\"operands\" :  [\n" +
                                "\t\t\t\t\t{\n" +
                                "\t\t\t\t\t\t\"inputId\": 2,\n" +
                                "\t\t\t\t\t\t\"operator\": \"LESS_THAN\",\n" +
                                "\t\t\t\t\t\t\"value\": 10\n" +
                                "\t\t\t\t\t}\n" +
                                "\t\t\t\t]\n" +
                                "\t\t\t},\n" +
                                "\t\t\t\"result\":  true,\n" +
                                "\t\t\t\"else\": {\n" +
                                "\t\t\t\t\"result\": false\n" +
                                "\t\t\t}\n" +
                                "\t\t}\n" +
                                "\t}\n" +
                                "}")
                )
                .andDo(print()).andExpect(status().isOk());
    }

    @Test
    public void shouldReturnErrorWhenThereIsNoContent() throws Exception {
        doNothing().when(predicateService).uploadPredicate(Mockito.anyString());
        this.mockMvc.perform(post("/api/v1/predicate/upload")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print()).andExpect(status().isBadRequest());
    }

    @Test
    public void shouldReturnResultFromPredicateEvaluation() throws Exception {
        when(predicateService.evaluate(Mockito.any(EvaluationInput.class))).thenReturn(true);
        this.mockMvc.perform(post("/api/v1/predicate/evaluate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"a\": \"abc\", \"b\":4}")
                )
                .andDo(print()).andExpect(status().isOk())
                .andExpect(content().string(containsString("true")));
    }


    @Test
    public void shouldReturnErrorFromPredicateEvaluationForInvalidInput() throws Exception {
        when(predicateService.evaluate(Mockito.any(EvaluationInput.class))).thenReturn(true);
        this.mockMvc.perform(post("/api/v1/predicate/evaluate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"a\": \"abc\", \"b\":\"p\"}")
                )
                .andDo(print()).andExpect(status().isBadRequest());
    }


}
