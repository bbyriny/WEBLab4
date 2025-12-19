package org.bbyriny.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.bbyriny.dto.PointRequest;
import org.bbyriny.models.Point;
import org.bbyriny.repository.PointRepository;
import org.bbyriny.util.HitChecker;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@ApplicationScoped
public class PointService {

    private final PointRepository pointRepository;
    private final HitChecker hitChecker;

    public PointService() {
        this.pointRepository = null;
        this.hitChecker = null;
    }
    @Inject
    public PointService(PointRepository pointRepository, HitChecker hitChecker) {
        this.pointRepository = pointRepository;
        this.hitChecker = hitChecker;
    }

    public Point addPoint(PointRequest dtoPoint, String creator) {
        Long startTime = System.nanoTime();

        BigDecimal x = BigDecimal.valueOf(dtoPoint.getX());
        BigDecimal y = BigDecimal.valueOf(dtoPoint.getY());
        BigDecimal r = BigDecimal.valueOf(dtoPoint.getR());

        boolean isHit = hitChecker.isHit(x, y, r);

        Double executionTime = (System.nanoTime() - startTime)/1_000_000.0;

        String creationTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss"));

        Point point = new Point(
                dtoPoint.getX(),
                dtoPoint.getY(),
                dtoPoint.getR(),
                isHit,
                creationTime,
                executionTime,
                creator
        );
        Point savedPoint = pointRepository.save(point);

        return savedPoint;
    }

    public List<Point> getAllPoints(String creator) {
        return pointRepository.findAllByCreator(creator);
    }

    public int deleteAllPoints(String creator) {
        return pointRepository.deleteAllByCreator(creator);
    }

}
