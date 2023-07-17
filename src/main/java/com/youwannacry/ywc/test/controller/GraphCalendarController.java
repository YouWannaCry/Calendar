package com.youwannacry.ywc.test.controller;

import com.youwannacry.ywc.test.dto.UserDto;
import com.youwannacry.ywc.test.service.CalendarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/calendar")
public class GraphCalendarController {
    @Autowired
    CalendarService calendarService;

    GraphCalendarController(CalendarService calendarService){
        this.calendarService = calendarService;
    }

    @MutationMapping
    public UserDto crearUsuario(
            @Argument UserDto userDto,
            @Argument String secretMessage
    ){
        return this.calendarService.crearUsuario(userDto, secretMessage);
    }

    @QueryMapping
    public List<UserDto> getAll(){
        return this.calendarService.getAll();
    }

    @QueryMapping
    public UserDto login(
            @Argument UserDto userDto
    ){
        return this.calendarService.login(userDto);
    }
}
