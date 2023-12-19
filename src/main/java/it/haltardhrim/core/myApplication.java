package it.haltardhrim.core;

import io.dropwizard.core.Application;
import io.dropwizard.core.setup.Bootstrap;
import io.dropwizard.core.setup.Environment;
import it.haltardhrim.health.PazienteHealth;
import it.haltardhrim.resources.PazienteResource;

public class myApplication extends Application<myConfiguration> {

    public static void main(final String[] args) throws Exception {
        new myApplication().run(args);
    }

    @Override
    public String getName() {
        return "pazienti";
    }

    @Override
    public void initialize(final Bootstrap<myConfiguration> bs) {
        // TODO: application initialization
    }

    @Override
    public void run(final myConfiguration conf, final Environment envi) {
        // TODO registra risorse
        PazienteResource pazR = new PazienteResource();
        envi.jersey().register(pazR);

        // TODO registra healthcheck
        PazienteHealth pazH = new PazienteHealth();
        envi.healthChecks().register("paziente", pazH);
    }
}
