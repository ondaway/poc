package com.ondaway.poc.commandbus.command;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.BooleanNode;
import com.fasterxml.jackson.databind.node.DoubleNode;
import java.io.IOException;

public class ReportStatusDeserializer extends JsonDeserializer<ReportStatus> {

    @Override
    public ReportStatus deserialize(JsonParser jp, DeserializationContext dc) throws IOException, JsonProcessingException {
        JsonNode node = jp.getCodec().readTree(jp);
 
        ReportStatus reportStatus = new ReportStatus();
        reportStatus.vehicle   = node.get("vehicle").asText();
        reportStatus.active    = (Boolean) ((BooleanNode) node.get("active")).booleanValue();
        reportStatus.longitude = (Float) ((DoubleNode) node.get("lng")).floatValue();
        reportStatus.latitude  = (Float) ((DoubleNode) node.get("lat")).floatValue();
        return reportStatus;
    }
    
}
