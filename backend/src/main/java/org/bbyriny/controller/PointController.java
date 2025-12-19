package org.bbyriny.controller;

import jakarta.inject.Inject;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.SecurityContext;
import org.bbyriny.dto.PointRequest;
import org.bbyriny.models.Point;
import org.bbyriny.service.PointService;
import org.bbyriny.util.Secured;

import java.util.List;

@Path("/points")
@Secured
public class PointController {

    private final PointService pointService;

    public PointController() {
        pointService = null;
    }

    @Inject
    public PointController(PointService pointService) {
        this.pointService = pointService;
    }

    @POST
    public Response addPoint(PointRequest point, @Context SecurityContext context) {
        String creator = context.getUserPrincipal().getName();
        Point savedPoint = pointService.addPoint(point, creator);
        return Response.ok(savedPoint).build();
    }

    @GET
    public Response getPoints(@Context SecurityContext context) {
        String creator = context.getUserPrincipal().getName();
        List<Point> points = pointService.getAllPoints(creator);
        return Response.ok(points).build();
    }

    @DELETE
    public Response deletePoint(@Context SecurityContext context) {
        String creator = context.getUserPrincipal().getName();
        pointService.deleteAllPoints(creator);
        return Response.ok().build();
    }


}
