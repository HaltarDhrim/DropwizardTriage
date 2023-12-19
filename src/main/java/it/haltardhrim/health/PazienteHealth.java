package it.haltardhrim.health;

import com.codahale.metrics.health.HealthCheck;

public class PazienteHealth extends HealthCheck {

    public PazienteHealth() {
    }

    @Override
    protected Result check() throws Exception {
        return Result.healthy();
        //return Result.unhealthy("Check fallito");
    }
}
