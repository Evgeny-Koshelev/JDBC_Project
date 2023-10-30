package org.example.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.example.model.Event;
import org.example.service.impl.EventServiceImpl;
import org.example.servlet.dto.EventDto;
import org.example.servlet.mapper.EventDtoMapperImpl;


import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@WebServlet(name = "eventServlet", value = "/event")
public class EventServlet extends HttpServlet {

    private final EventServiceImpl service = new EventServiceImpl();
    private final EventDtoMapperImpl eventDtoMapper = new EventDtoMapperImpl();
    private final DateTimeFormatter formatter  = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").withZone(ZoneId.systemDefault());
    private static final String CONTENT_TYPE = "application/json; charset=utf-8";
    private static final String DATE_TYPE = "yyyy-MM-dd HH:mm:ssXXX";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
        ObjectMapper objectMapper = createObject();
        resp.setContentType(CONTENT_TYPE);
        String json;
        if(req.getParameter("id") != null) {
            UUID uuid = UUID.fromString(req.getParameter("id"));
            Event event = service.findById(uuid);
            PrintWriter pw = resp.getWriter();
            if(event!=null) {
                EventDto eventDto = eventDtoMapper.toDto(event);
                json = objectMapper.writeValueAsString(eventDto);

            }
            else {
                resp.setStatus(404);
                json = objectMapper.writeValueAsString("Such id don't found");


            }
            pw.write(json);
        }
        else
            getAll(resp, objectMapper);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        ObjectMapper objectMapper = createObject();
        String json;
        resp.setContentType(CONTENT_TYPE);
        if(req.getParameter("notes")!=null) {
            EventDto eventDtoIn = new EventDto();
            String str = req.getParameter("beginDate");
            eventDtoIn.setBeginDate(ZonedDateTime.parse(str, formatter));
            UUID id = UUID.randomUUID();
            eventDtoIn.setId(id);
            eventDtoIn.setUserId(req.getParameter("userId"));
            eventDtoIn.setVacancyId(UUID.fromString(req.getParameter("vacancyId")));
            eventDtoIn.setNotes(req.getParameter("notes"));
            eventDtoIn.setIsCompleted(Boolean.valueOf(req.getParameter("isCompleted")));
            Event event = eventDtoMapper.toEntity(eventDtoIn);
            Event saved = service.save(event);
            EventDto eventDtoOut = eventDtoMapper.toDto(saved);
            json = objectMapper.writeValueAsString(eventDtoOut);

        }
        else{
            resp.setStatus(404);
            json = objectMapper.writeValueAsString("Notes is empty");
        }

        PrintWriter pw = resp.getWriter();
        pw.write(json);
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        boolean isDeleted = service.delete(UUID.fromString(req.getParameter("id")));
        ObjectMapper objectMapper = createObject();
        String json;
        if(isDeleted)
            json = objectMapper.writeValueAsString(true);
        else {
            resp.setStatus(404);
            json = objectMapper.writeValueAsString("Such id don't found");
        }
        resp.setContentType(CONTENT_TYPE);
        PrintWriter pw= resp.getWriter();
        pw.write(json);
    }

    protected void getAll(HttpServletResponse resp, ObjectMapper objectMapper)
            throws IOException {

        String json;
        List<Event> eventList = service.findAll();
        if(!eventList.isEmpty()) {
            List<EventDto> eventDtoList = new ArrayList<>();
            for (Event ev : eventList) {
                eventDtoList.add(eventDtoMapper.toDto(ev));
            }
            json = objectMapper.writeValueAsString(eventDtoList);
        }
        else {
            resp.setStatus(404);
            json = objectMapper.writeValueAsString("List is empty");
        }
        PrintWriter pw= resp.getWriter();
        pw.write(json);
    }

    private ObjectMapper createObject() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.setDateFormat(new SimpleDateFormat(DATE_TYPE));
        return objectMapper;
    }

}
