package com.tech11.controller;

import com.tech11.dto.PasswordResetRequest;
import com.tech11.dto.RoleUpdateRequest;
import com.tech11.dto.UsersDTO;
import com.tech11.exception.UserException;
import com.tech11.model.Users;
import com.tech11.service.UsersService;
import com.tech11.util.Paginate;

import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;


@Path("/users")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class UsersController {
	
	
	@Inject
	private UsersService usersService;
	@GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{page}/{size}")
    public Response getAllUsers(
            @PathParam("page") int page,
            @PathParam("size") int size) {

		Paginate<UsersDTO> userList = usersService.findAll(page, size);

		return Response.ok(userList).build();
    }

	@GET
	@Path("/{id}")
	public Response getUserById(@PathParam("id") Long id) throws UserException {
		Users user =usersService.getUserById(id);
		return Response.ok(new UsersDTO(user)).build();
	}

	@POST
	public Response createUser(Users user) {
		return Response.status(Response.Status.CREATED).entity(usersService.createUser(user).getId()).build();
	}

	@PUT
	@Path("/update")
	public Response updateUser(Users user) throws UserException {
		Users update= usersService.updateUser(user);
		return Response.ok(new UsersDTO(update)).build();
	}

	@DELETE
	@Path("/{id}")
	public Response deleteUser(@PathParam("id") Long id) throws UserException {
		usersService.deleteUser(id);
		return Response.status(Response.Status.NO_CONTENT).build();
	}
	
	
	// Additional Essential endpoints
	
	// Reset Password
	@PUT
	@Path("/reset-password")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response resetPassword(PasswordResetRequest request) throws UserException {
		
		if (request.getId() == null) {
	        return Response.status(Response.Status.BAD_REQUEST)
	                       .entity("User ID must be provided.")
	                       .build();
	    }
	    if (request.getOldPassword() == null || request.getOldPassword().isBlank() ||
	        request.getNewPassword() == null || request.getNewPassword().isBlank()) {
	        return Response.status(Response.Status.BAD_REQUEST)
	                       .entity("Old and new passwords must be provided.")
	                       .build();
	    }
	    usersService.resetPassword(request);

	    return Response.status(Response.Status.OK)
	                   .entity("Password successfully reset for user ID: " + request.getId())
	                   .build();
	}

	// Update User Role
	@PUT
	@Path("/update-role")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response updateUserRole(RoleUpdateRequest request) throws UserException {
	    if (request.getUserRole() == null) {
	        throw new UserException("Role cannot be null.");
	    }

	    usersService.updateUserRole(request.getId(), request.getUserRole());

	    return Response.status(Response.Status.OK)
	                   .entity("Role successfully updated to " + request.getUserRole() + " for user ID: " + request.getId())
	                   .build();
	}

	
	 // Stubbed endpoints
    @PUT
    @Path("/deactivate/{id}")
    public Response deactivateUser(@PathParam("id") Long id) {
        return Response.status(Response.Status.NOT_IMPLEMENTED)
                .entity("This feature is not yet implemented.")
                .build();
    }

    @GET
    @Path("/findByEmail")
    public Response findUserByEmail(@QueryParam("email") String email) {
        return Response.status(Response.Status.NOT_IMPLEMENTED)
                .entity("This feature is not yet implemented.")
                .build();
    }
}
