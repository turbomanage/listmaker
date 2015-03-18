package com.example.listmaker.server.dao;

import com.example.listmaker.app.shared.domain.Contact;

import javax.servlet.ServletContext;
import javax.ws.rs.WebApplicationException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 * Reads in a list of fictitious contacts from text file and populates the db
 *
 */
public class ContactsReader {

    private static final Logger LOG = Logger.getLogger(ContactDao.class.getName());
    private static final List<Contact> contacts = new ArrayList<Contact>();

    public static List<Contact> all(ServletContext ctx) {
        if (contacts.size() > 0)
            return contacts;

        InputStream is = ctx.getResourceAsStream("/WEB-INF/contacts.txt");
        if (is == null) {
            LOG.warning("uh-oh, stream was null");
        }
        InputStreamReader isr = new InputStreamReader(is);
        BufferedReader r = new BufferedReader(isr);
        try {
            String s = r.readLine();
            while (s != null) {
                String[] split = s.split(",");
                Contact c = new Contact();
                c.setId(Integer.parseInt(split[0]));
                c.setFirstName(split[2]);
                c.setLastName(split[1]);
                contacts.add(c);
                // Oh geez, I hate infinite loops
                s = r.readLine();
            }
        } catch (IOException e) {
            throw new WebApplicationException(e);
        }
        LOG.info("returning " + contacts.size() + " contacts");
        return contacts;
    }
}
