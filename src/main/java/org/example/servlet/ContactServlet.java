package org.example.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.example.model.Contact;
import org.example.repository.impl.ContactRepositoryImpl;
import org.example.service.impl.ContactServiceImpl;
import org.example.servlet.dto.ContactDto;
import org.example.servlet.mapper.ContactDtoMapperImpl;

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

@WebServlet(name = "contactServlet", value = "/contact")
public class ContactServlet extends HttpServlet {

    private final ContactDtoMapperImpl contactDtoMapper = new ContactDtoMapperImpl();
    private static final String CONTENT_TYPE = "application/json; charset=utf-8";
    private static final String DATE_TYPE = "yyyy-MM-dd HH:mm:ssXXX";
    private final ContactServiceImpl service;

    public ContactServlet(ContactServiceImpl contactService) {
        service = contactService;
    }

    ContactServlet(){
        service = new ContactServiceImpl(new ContactRepositoryImpl());
    }
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.setDateFormat(new SimpleDateFormat(DATE_TYPE));
        String json;
        if(req.getParameter("id") != null) {
            UUID uuid = UUID.fromString(req.getParameter("id"));
            Contact contact = service.findById(uuid);
            if(contact!=null) {
                ContactDto contactDto = contactDtoMapper.toDto(contact);
                json = objectMapper.writeValueAsString(contactDto);

            }
            else {
                resp.setStatus(404);
                json = objectMapper.writeValueAsString("Such id don't found");


            }
            if(resp.getWriter()!=null) {
                resp.setContentType(CONTENT_TYPE);
                PrintWriter pw = resp.getWriter();
                pw.write(json);
            }
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
        if(req.getParameter("name")!=null) {
            ContactDto contactDtoIn = new ContactDto();
            UUID id = UUID.randomUUID();
            contactDtoIn.setId(id);
            contactDtoIn.setUserId(req.getParameter("userId"));
            contactDtoIn.setName(req.getParameter("name"));
            contactDtoIn.setCompany(req.getParameter("company"));
            contactDtoIn.setMail(req.getParameter("mail"));
            contactDtoIn.setTelephone(req.getParameter("telephone"));
            contactDtoIn.setNotes(req.getParameter("notes"));
            Contact contact = contactDtoMapper.toEntity(contactDtoIn);
            Contact saved = service.save(contact);
            ContactDto contactDtoOut = contactDtoMapper.toDto(saved);
            json = objectMapper.writeValueAsString(contactDtoOut);

        }
        else{
            resp.setStatus(404);
            json = objectMapper.writeValueAsString("Name is empty");
        }
        if(resp.getWriter()!=null) {
            resp.setContentType(CONTENT_TYPE);
            PrintWriter pw= resp.getWriter();
            pw.write(json);
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        UUID id;
        String str= req.getParameter("id");
        if(str == null)
            id = UUID.randomUUID();
        else
            id = UUID.fromString(req.getParameter("id"));
        boolean isDeleted = service.delete(id);
        ObjectMapper objectMapper = new ObjectMapper();
        String json;
        if(isDeleted)
            json = objectMapper.writeValueAsString(true);
        else {
            resp.setStatus(404);
            json = objectMapper.writeValueAsString("Such id don't found");
        }
        if(resp.getWriter()!=null) {
            resp.setContentType(CONTENT_TYPE);
            PrintWriter pw= resp.getWriter();
            pw.write(json);
        }

    }

    protected void getAll(HttpServletResponse resp, ObjectMapper objectMapper)
            throws IOException {

        String json;
        List<Contact> contactList = service.findAll();
        if(!contactList.isEmpty()) {
            List<ContactDto> contactDtoList = new ArrayList<>();
            for (Contact contact : contactList) {
                contactDtoList.add(contactDtoMapper.toDto(contact));
            }
            json = objectMapper.writeValueAsString(contactDtoList);
        }
        else {
            resp.setStatus(404);
            json = objectMapper.writeValueAsString("List is empty");
        }
        if(resp.getWriter()!=null) {

            PrintWriter pw= resp.getWriter();
            pw.write(json);
        }
    }
}
