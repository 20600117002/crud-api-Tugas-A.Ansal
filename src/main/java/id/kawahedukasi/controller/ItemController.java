package id.kawahedukasi.controller;

import id.kawahedukasi.model.Item;

import javax.json.JsonObject;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

@Path("/item")
public class ItemController {

    @POST
    @Transactional
    public Response create(JsonObject request){
        Item item = new Item();
        item.name = request.getString("name");
        item.count = request.getInt("count");
        item.price = request.getInt("price");
        item.type = request.getString("type");
        item.description = request.getString("description");

        item.persist();

        return Response.ok().entity(new HashMap<>()).build();
    }

    // Modify by id
    @PUT
    @Path("/{id}")
    @Transactional
    public Response update(@PathParam("id") Integer id, JsonObject request){
        Item item = Item.findById(id);
        item.name = request.getString("name");
        item.count = request.getInt("count");
        item.price = request.getInt("price");
        item.type = request.getString("type");
        item.description = request.getString("description");

        item.persist();

        return Response.ok().entity(new HashMap<>()).build();
    }

    // Get semua data
    @GET
    public Response list(){
        return Response.ok().entity(Item.listAll()).build();
    }

    //Get by Id
    @GET
    @Path("/{id}")
    public Response getById(@PathParam("id") Integer id){
        Item item = Item.findById(id);
        if(item == null){
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(Map.of("message", "Item Not Found"))
                    .build();
        }
        return Response.ok().entity(item).build();
    }

    //GET By Type
    @GET
    @Path("/type/{type}")
    public Response getByType(@PathParam("type") String type){
        if(!type.equalsIgnoreCase("Televisi") && !type.equalsIgnoreCase("Hp")){
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(Map.of("message", "Invalid Type"))
                    .build();
        }
        List <Item> itemList = Item.find("UPPER(type) = ?1", type.toUpperCase(Locale.ROOT)).list();
        return Response.ok().entity(itemList).build();
    }

    // Delete by Id
    @DELETE
    @Path("/{id}")
    @Transactional
    public Response delete(@PathParam("id") Integer id){
        Item.deleteById(id);

        return Response.ok().entity(new HashMap<>()).build();
    }

}
