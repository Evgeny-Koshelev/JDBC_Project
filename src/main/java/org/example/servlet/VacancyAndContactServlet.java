package org.example.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.model.VacancyAndContact;
import org.example.service.impl.VacancyAndContactServiceImpl;
import org.example.servlet.dto.VacancyAndContactDto;
import org.example.servlet.mapper.VacancyAndContactDtoMapperImpl;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@WebServlet(name = "vacancyAndContactServlet", value = "/vacAndCon")
public class VacancyAndContactServlet  extends HttpServlet {

    private final VacancyAndContactServiceImpl service = new VacancyAndContactServiceImpl();
    private final VacancyAndContactDtoMapperImpl vacancyAndContactDtoMapper = new VacancyAndContactDtoMapperImpl();
    private static final String CONTENT_TYPE = "application/json; charset=utf-8";
    private static final String VACANCY_ID = "vacancyId";
    private static final String CONTACT_ID = "contactId";
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType(CONTENT_TYPE);
        ObjectMapper objectMapper = new ObjectMapper();
        String vacancyIdStr = req.getParameter(VACANCY_ID);
        String contactIdStr = req.getParameter(CONTACT_ID);
        String json;
        if(vacancyIdStr !=null) {
            UUID vacancyId = UUID.fromString(vacancyIdStr);
            UUID contactId = UUID.fromString(contactIdStr);
            VacancyAndContact vacancyAndContact = service.findById(vacancyId,contactId);
            PrintWriter pw = resp.getWriter();
            if(vacancyAndContact!=null) {
                VacancyAndContactDto vacancyAndContactDto = vacancyAndContactDtoMapper.toDto(vacancyAndContact);
                json = objectMapper.writeValueAsString(vacancyAndContactDto);

            }
            else {
                resp.setStatus(404);
                json = objectMapper.writeValueAsString("No such data found");


            }
            pw.write(json);
        }
        else
            getAll(resp, objectMapper);
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        boolean isDeleted = service.delete(UUID.fromString(req.getParameter(VACANCY_ID)),
                UUID.fromString(req.getParameter(CONTACT_ID)));
        ObjectMapper objectMapper = new ObjectMapper();
        String json;
        if(isDeleted)
            json = objectMapper.writeValueAsString(true);
        else {
            resp.setStatus(404);
            json = objectMapper.writeValueAsString("Such object don't found");
        }
        resp.setContentType(CONTENT_TYPE);
        PrintWriter pw= resp.getWriter();
        pw.write(json);
    }


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        VacancyAndContactDto vacancyAndContactDtoIn = new VacancyAndContactDto();
        vacancyAndContactDtoIn.setVacancyId(UUID.fromString(req.getParameter(VACANCY_ID)));
        vacancyAndContactDtoIn.setContactId(UUID.fromString(req.getParameter(CONTACT_ID)));
        VacancyAndContact vacancyAndContact = vacancyAndContactDtoMapper.toEntity(vacancyAndContactDtoIn);
        VacancyAndContact saved = service.save(vacancyAndContact);
        VacancyAndContactDto vacancyAndContactDtoOut = vacancyAndContactDtoMapper.toDto(saved);
        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(vacancyAndContactDtoOut);
        resp.setContentType(CONTENT_TYPE);
        PrintWriter pw= resp.getWriter();
        pw.write(json);
    }

    protected void getAll(HttpServletResponse resp, ObjectMapper objectMapper)
            throws IOException {

        String json;
        List<VacancyAndContact> vacancyAndContactList = service.findAll();
        if(!vacancyAndContactList.isEmpty()) {
            List<VacancyAndContactDto> vacancyAndContactDtoListDtoList = new ArrayList<>();
            for (VacancyAndContact vc : vacancyAndContactList) {
                vacancyAndContactDtoListDtoList.add(vacancyAndContactDtoMapper.toDto(vc));
            }
            json = objectMapper.writeValueAsString(vacancyAndContactDtoListDtoList);
        }
        else {
            resp.setStatus(404);
            json = objectMapper.writeValueAsString("List is empty");
        }
        PrintWriter pw= resp.getWriter();
        pw.write(json);
    }

}
