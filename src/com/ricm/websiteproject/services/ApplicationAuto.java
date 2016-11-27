package com.ricm.websiteproject.services;

import java.util.concurrent.TimeUnit;

import metrics_influxdb.HttpInfluxdbProtocol;
import metrics_influxdb.InfluxdbReporter;
import metrics_influxdb.api.measurements.CategoriesMetricMeasurementTransformer;

import org.glassfish.jersey.server.ResourceConfig;

import com.codahale.metrics.MetricFilter;
import com.codahale.metrics.MetricRegistry;
import com.codahale.metrics.ScheduledReporter;
import com.codahale.metrics.jersey2.InstrumentedResourceMethodApplicationListener;

public class ApplicationAuto extends ResourceConfig {
	public ApplicationAuto() {
		System.out.println("DEBUT RESOURCE");
		MetricRegistry mr = new MetricRegistry();
		ScheduledReporter reporter = InfluxdbReporter
				.forRegistry(mr)
				.protocol(
						new HttpInfluxdbProtocol("http", "localhost", 8086,
								"root", "root", "metrics"))
				.convertRatesTo(TimeUnit.SECONDS)
				.convertDurationsTo(TimeUnit.MILLISECONDS)
				.filter(MetricFilter.ALL)
				.skipIdleMetrics(false)
				.tag("cluster", "CL01")
				.tag("client", "OurImportantClient")
				.tag("server", "localhost")
				.transformer(
						new CategoriesMetricMeasurementTransformer("module",
								"artifact")).build();
		packages("com.ricm.websiteproject.services");
		register(new InstrumentedResourceMethodApplicationListener(mr));

		reporter.start(10, TimeUnit.SECONDS);
		System.out.println("FIN RESOURCE");
	}
}