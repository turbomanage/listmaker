package com.turbomanage.gwt.client.rest;

import org.fusesource.restygwt.client.MethodCallback;
import org.fusesource.restygwt.client.RestService;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import java.util.List;

/**
 * Created by david on 7/11/14.
 */
public interface RestApi<T> extends RestService {

    @GET
    @Path("own")
    public void getForOwner(MethodCallback<T> callback);

    @GET
    @Path("get")
    public void get(@QueryParam("id")Long id, MethodCallback<T> callback);

    @GET
    @Path("all")
    public void listAll(MethodCallback<ListResponse<T>> callback);

    @POST
    @Path("save")
    public void save(T obj, MethodCallback<T> callback);

    @POST
    @Path("saveMany")
    public void saveMany(List<T> obj, MethodCallback<Integer> callback);

    @POST
    @Path("delete")
    public void delete(Long id, MethodCallback<Integer> callback);

    @POST
    @Path("deleteMany")
    public void deleteMany(List<Long> obj, MethodCallback<Integer> callback);

}
