package com.ondaway.poc.commandbus.command;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;

import java.io.IOException;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class ReportStatusTest {
    private ObjectMapper mapper;
    
    @Before public void setup() {
        mapper = new ObjectMapper();
        SimpleModule module = new SimpleModule();
        module.addDeserializer(ReportStatus.class, new ReportStatusDeserializer());
        mapper.registerModule(module);
    }
    
    @Test public void canDeserialize() throws IOException {
        ReportStatus reportStatusCommand = mapper.readValue(getClass().getResourceAsStream("/ReportStatus.json"), ReportStatus.class);

        assertEquals("38400000-8cf0-11bd-b23e-10b96e4ef00d", reportStatusCommand.vehicle);
        assertEquals(Boolean.TRUE, reportStatusCommand.active);
        assertEquals(1.1, reportStatusCommand.longitude, 0.0001);
        assertEquals(2.2, reportStatusCommand.latitude,  0.0001);
    }
}
