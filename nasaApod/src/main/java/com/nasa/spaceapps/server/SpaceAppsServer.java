package com.nasa.spaceapps.server;

import java.util.EnumSet;

import javax.servlet.DispatcherType;
import javax.servlet.FilterRegistration;

import org.eclipse.jetty.servlets.CrossOriginFilter;

import com.nasa.spaceapps.resources.ApodResource;

import io.dropwizard.Application;
import io.dropwizard.setup.Environment;

public class SpaceAppsServer extends Application<SpaceAppsServerConfiguration>{

	@Override
	public void run(SpaceAppsServerConfiguration configuration, Environment environment)
			throws Exception {
		
		// Enable CORS headers
	    final FilterRegistration.Dynamic cors = environment.servlets().addFilter("CORS", CrossOriginFilter.class);

	    // Configure CORS parameters
	    cors.setInitParameter("allowedOrigins", "*");
	    cors.setInitParameter("allowedHeaders", "X-Requested-With,Content-Type,Accept,Origin");
	    cors.setInitParameter("allowedMethods", "OPTIONS,GET,PUT,POST,DELETE,HEAD");

	    // Add URL mapping
	    cors.addMappingForUrlPatterns(EnumSet.allOf(DispatcherType.class), true, "/*");
		
		ApodResource resource = new ApodResource();
        environment.jersey().register(resource);
	}
	
	public static void main(String[] args) throws Exception {
        (new SpaceAppsServer()).run(args);
    }

}
