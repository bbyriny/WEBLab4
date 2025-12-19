package org.bbyriny.util;

import jakarta.enterprise.context.ApplicationScoped;
import java.math.BigDecimal;

@ApplicationScoped
public class HitChecker {

    public boolean isHit(BigDecimal x, BigDecimal y, BigDecimal r) {
        if (x.compareTo(BigDecimal.ZERO) >= 0 &&
                x.compareTo(r) <= 0 &&
                y.compareTo(BigDecimal.ZERO) >= 0 &&
                y.compareTo(r) <= 0) {
            return true;
        }

        BigDecimal halfR = r.divide(BigDecimal.valueOf(2));
        if (x.compareTo(BigDecimal.ZERO) <= 0 &&
                x.compareTo(r.negate()) >= 0 &&
                y.compareTo(BigDecimal.ZERO) >= 0 &&
                y.compareTo(halfR) <= 0 &&
                y.compareTo(x.negate().multiply(halfR).divide(r, BigDecimal.ROUND_HALF_UP)) <= 0) {
            return true;
        }

        if (x.compareTo(BigDecimal.ZERO) <= 0 &&
                x.compareTo(r.negate()) >= 0 &&
                y.compareTo(BigDecimal.ZERO) <= 0 &&
                y.compareTo(r.negate()) >= 0) {
            BigDecimal x2 = x.multiply(x);
            BigDecimal y2 = y.multiply(y);
            BigDecimal r2 = r.multiply(r);
            if (x2.add(y2).compareTo(r2) <= 0) {
                return true;
            }
        }
        return false;
    }
}
