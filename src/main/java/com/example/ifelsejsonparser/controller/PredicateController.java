package com.example.ifelsejsonparser.controller;

import com.example.ifelsejsonparser.model.EvaluationInput;
import com.example.ifelsejsonparser.service.PredicateService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping(value = "/api/v1/predicate", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Predicate Controller")
public class PredicateController {
    PredicateService predicateService;

    @Autowired
    public void setPredicateService(PredicateService predicateService) {
        this.predicateService = predicateService;
    }

    /**
     * API to upload predicate logic
     */
    @Operation(
            summary = "Upload predicate",
            description = "Upload predicate as a json string to store (override the logic in test.json)."
    )
    @PostMapping(value = "/upload" , consumes = MediaType.APPLICATION_JSON_VALUE )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "success"),
            @ApiResponse(responseCode = "400", description = "Bad request", content = @Content(
                    schema = @Schema()
            )),
            @ApiResponse(responseCode = "401", description = "Authentication failure", content = @Content(
                    schema = @Schema()
            )),
            @ApiResponse(responseCode = "500", description = "Unexpected errors in service", content = @Content(
                    schema = @Schema()
            )),
    })
    public ResponseEntity<String> registerBatteries(
            @RequestBody(required = true) @Valid String predicate) {
        predicateService.uploadPredicate(predicate);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * API to evaluate predicate with the given input
     */
    @Operation(
            summary = "Evaluate predicate with the given input",
            description = "Evaluate stored predicate with the given input."
    )
    @PostMapping(value = "/evaluate" , consumes = MediaType.APPLICATION_JSON_VALUE )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "success"),
            @ApiResponse(responseCode = "400", description = "Bad request", content = @Content(
                    schema = @Schema()
            )),
            @ApiResponse(responseCode = "401", description = "Authentication failure", content = @Content(
                    schema = @Schema()
            )),
            @ApiResponse(responseCode = "500", description = "Unexpected errors in service", content = @Content(
                    schema = @Schema()
            )),
    })
    public ResponseEntity<Map<String, String>> registerBatteries(
            @RequestBody(required = true) @Valid EvaluationInput evaluationInput) {
        boolean result = predicateService.evaluate(evaluationInput);
        Map<String,String> response = new HashMap<>();
        response.put("result", String.valueOf(result));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
