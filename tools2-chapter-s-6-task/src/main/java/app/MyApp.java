package app;

import java.util.List;

import javax.ejb.EJB;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import ejb.Calculation;
@Path("/calc")
public class MyApp {

    @EJB
    private CalculationService calculationService;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createCalculation(CalculationRequest request) {
        try {
            int result = calculationService.performCalculation(request.getNumber1(), request.getNumber2(), request.getOperation());
            return Response.ok(new CalculationResponse(result)).build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        }
    }
    @GET
    @Path("/calculations")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllCalculations() {
        try {
            List<Calculation> calculations = calculationService.getAllCalculations();
            return Response.ok(calculations).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        }
    }
}