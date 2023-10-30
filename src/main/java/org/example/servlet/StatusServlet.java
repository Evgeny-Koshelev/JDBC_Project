package org.example.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.example.model.Status;
import org.example.service.impl.StatusServiceImpl;
import org.example.servlet.dto.StatusDto;
import org.example.servlet.mapper.StatusDtoMapperImpl;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@WebServlet(name = "statusServlet", value = "/status")
public class StatusServlet extends HttpServlet {

    private final StatusServiceImpl service = new StatusServiceImpl();

    private  final StatusDtoMapperImpl statusDtoMapper = new StatusDtoMapperImpl();

    private static final String CONTENT_TYPE = "application/json; charset=utf-8";

    private static final String DATE_TYPE = "yyyy-MM-dd HH:mm:ssXXX";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType(CONTENT_TYPE);
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.setDateFormat(new SimpleDateFormat(DATE_TYPE));
        String json;
        if(req.getParameter("id") != null) {
            UUID uuid = UUID.fromString(req.getParameter("id"));
            Status status = service.findById(uuid);
            PrintWriter pw = resp.getWriter();
            if(status!=null) {
                StatusDto statusDto = statusDtoMapper.toDto(status);
                json = objectMapper.writeValueAsString(statusDto);

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
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.setDateFormat(new SimpleDateFormat(DATE_TYPE));
        String json;
        resp.setContentType(CONTENT_TYPE);
        if(req.getParameter("nameStatus")!=null && req.getParameter("orderNum")!=null ) {
            StatusDto statusDtoIn = new StatusDto();
            UUID id = UUID.randomUUID();
            statusDtoIn.setId(id);
            statusDtoIn.setUserId(req.getParameter("userId"));
            statusDtoIn.setNameStatus(req.getParameter("nameStatus"));
            statusDtoIn.setOrderNum(Integer.valueOf(req.getParameter("orderNum")));
            Status status = statusDtoMapper.toEntity(statusDtoIn);
            Status saved = service.save(status);
            StatusDto statusDtoOut = statusDtoMapper.toDto(saved);
            json = objectMapper.writeValueAsString(statusDtoOut);
        }
        else{
            resp.setStatus(404);
            json = objectMapper.writeValueAsString("nameStatus or orderNum is empty");
        }

        PrintWriter pw = resp.getWriter();
        pw.write(json);
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        boolean isDeleted = service.delete(UUID.fromString(req.getParameter("id")));
        ObjectMapper objectMapper = new ObjectMapper();
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
        List<Status> statusList = service.findAll();
        if(!statusList.isEmpty()) {
            List<StatusDto> statusDtoList = new ArrayList<>();
            for (Status st : statusList) {
                statusDtoList.add(statusDtoMapper.toDto(st));
            }
            json = objectMapper.writeValueAsString(statusDtoList);
        }
        else {
            resp.setStatus(404);
            json = objectMapper.writeValueAsString("List is empty");
        }
        PrintWriter pw= resp.getWriter();
        pw.write(json);
    }
}
