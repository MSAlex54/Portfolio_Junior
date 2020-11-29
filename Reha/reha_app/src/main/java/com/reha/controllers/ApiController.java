package com.reha.controllers;

import com.reha.model.dto.EventDto;
import com.reha.services.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ApiController {

    private final EventService eventService;

    @Autowired
    public ApiController(EventService eventService) {
        this.eventService = eventService;
    }

    @RequestMapping(value = "/api/events", method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public List<EventDto> msg() {
        return eventService.getForBoard();
    }

}
