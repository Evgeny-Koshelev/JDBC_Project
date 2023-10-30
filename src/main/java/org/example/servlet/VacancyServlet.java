package org.example.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.example.model.Vacancy;
import org.example.service.impl.VacancyServiceImpl;
import org.example.servlet.dto.VacancyDto;
import org.example.servlet.mapper.VacancyDtoMapperImpl;

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

@WebServlet(name = "vacancyServlet", value = "/vacancy")
public class VacancyServlet extends HttpServlet {
    private final VacancyServiceImpl service = new VacancyServiceImpl();
    private final VacancyDtoMapperImpl vacancyDtoMapper = new VacancyDtoMapperImpl();
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
            Vacancy vacancy = service.findById(uuid);
            if(vacancy!=null) {
                VacancyDto vacancyDto = vacancyDtoMapper.toDto(vacancy);
                json = objectMapper.writeValueAsString(vacancyDto);

            }
            else {
                resp.setStatus(404);
                json = objectMapper.writeValueAsString("Such id don't found");

            }
            PrintWriter pw = resp.getWriter();
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
        req.setCharacterEncoding("UTF-8");
        resp.setContentType(DATE_TYPE);
        if(req.getParameter("nameVacancy")!= null && req.getParameter("notes") != null) {
            VacancyDto vacancyDtoIn = new VacancyDto();
            UUID id = UUID.randomUUID();
            vacancyDtoIn.setId(id);
            vacancyDtoIn.setUserId(req.getParameter("userId"));
            vacancyDtoIn.setNameVacancy(req.getParameter("nameVacancy"));
            UUID statusId = UUID.fromString(req.getParameter("statusId"));
            vacancyDtoIn.setStatusId(statusId);
            vacancyDtoIn.setCompany(req.getParameter("company"));
            vacancyDtoIn.setSalary(Integer.valueOf(req.getParameter("salary")));
            vacancyDtoIn.setNotes(req.getParameter("notes"));
            Vacancy vacancy = vacancyDtoMapper.toEntity(vacancyDtoIn);
            Vacancy saved = service.save(vacancy);
            VacancyDto vacancyDtoOut = vacancyDtoMapper.toDto(saved);
            json = objectMapper.writeValueAsString(vacancyDtoOut);
        }
        else{
            resp.setStatus(404);
            json = objectMapper.writeValueAsString("Notes or name_vacancy is empty");
        }
        PrintWriter pw = resp.getWriter();
        pw.write(json);
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws  IOException {
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
        List<Vacancy> vacancyList = service.findAll();
        if(!vacancyList.isEmpty()) {
            List<VacancyDto> vacancyDtoList = new ArrayList<>();
            for(Vacancy v:vacancyList){
                vacancyDtoList.add(vacancyDtoMapper.toDto(v));
            }
            json = objectMapper.writeValueAsString(vacancyDtoList);
        }
        else {
            resp.setStatus(404);
            json = objectMapper.writeValueAsString("List is empty");
        }
        PrintWriter pw= resp.getWriter();
        pw.write(json);
    }
}

