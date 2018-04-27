package com.app.contact_loader.service;

import com.app.contact_loader.exception.NotFoundException;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.regex.Pattern;

import static java.sql.ResultSet.CONCUR_READ_ONLY;
import static java.sql.ResultSet.TYPE_FORWARD_ONLY;

@Repository
@Service
public class ContactServiceImpl implements ContactService {
    private final Logger log = LoggerFactory.getLogger(getClass());

    private static final String QUERY = "SELECT * FROM contacts";
    private static final int FETCH_SIZE = 7000;

    @Autowired
    private DataSource dataSource;

    public void getAll(String regex, PrintWriter printWriter) {
        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement(TYPE_FORWARD_ONLY, CONCUR_READ_ONLY)) {

            connection.setAutoCommit(false);
            statement.setFetchSize(FETCH_SIZE);

            try (ResultSet rs = statement.executeQuery(QUERY)) {
                makeJson(rs, printWriter, regex);
            }

        } catch (SQLException e) {
            log.error(e.getLocalizedMessage());
        }
    }

    private void makeJson(ResultSet rs, PrintWriter printWriter, String regex) {
        Pattern pattern = Pattern.compile(regex);
        JsonFactory factory = new JsonFactory();
        int counter = 0;

        try (JsonGenerator generator = factory.createGenerator(printWriter)) {
            generator.writeStartArray();

            while (rs.next()) {
                if (!pattern.matcher(rs.getString(2)).matches()) {
                    generator.writeStartObject();
                    generator.writeNumberField("id", rs.getLong(1));
                    generator.writeStringField("name", rs.getString(2));
                    generator.writeEndObject();
                    counter++;
                }
            }

            if (counter == 0) {
                throw new NotFoundException("Contacts not found by filter - " + regex);
            }

            generator.writeEndArray();
            log.info(counter + " lines match found.");
        } catch (SQLException | IOException e) {
            log.error(e.getLocalizedMessage());
        }
    }
}