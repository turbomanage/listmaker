package com.example.listmaker.server.dao;

import com.example.listmaker.common.domain.ListWrapper;
import com.example.listmaker.common.domain.NoteList;
import com.example.listmaker.common.domain.User;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.Ref;
import com.turbomanage.gwt.server.servlet.AuthFilter;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

@Path("api/noteList")
public class NoteListDao extends RestServiceDao<NoteList> {

    private static final Logger LOG = Logger.getLogger(NoteListDao.class.getName());

    @Override
    @Path("all")
    @GET
    public ListWrapper<NoteList> findAll() {
        return super.findAll();
    }

    @Override
    @Path("get")
    @GET
    public NoteList get(@QueryParam("id") Long id) {
        return super.get(id);
    }

    @Override
    @Path("save")
    @POST
    public NoteList save(NoteList list) {
        User loggedInUser = AuthFilter.getUser();
        if (list.getId() == null) {
            // insert
            // Add new subscription to go with it
            list.setDateCreated(new Date());
            list.setOwnerKey(Ref.create(loggedInUser));
            Ref<NoteList> listKey = Ref.create(this.put(list));
            LOG.info("Added list with id = " + list.getId());
            return list;
        } else {
            // update
            return super.save(list);
        }
    }

    @Override
    @Path("deleteMany")
    @POST
    public int removeMany(List<Long> ids) {
        try {
            // could eliminate loop with IN query, but same effect
            for (long id : ids) {
                // TODO delete corresponding items also
                Ref<NoteList> listKey = Ref.create(Key.create(clazz, id));
                // Delete list itself
                this.deleteKey(listKey.key());
            }
        } catch (RuntimeException e) {
            LOG.throwing(this.getClass().getSimpleName(), "execute", e);
            throw e;
        }

        return super.removeMany(ids);
    }

}