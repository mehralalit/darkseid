package edu.immune.dropwizard.health;

import com.codahale.metrics.health.HealthCheck;

/**
 * Services Health Check request
 * @author lalit
 *
 */
public class TemplateHealthCheck extends HealthCheck {

	private String template;

	public TemplateHealthCheck(String template) {
		super();
		this.template = template;
	}

	@Override
	protected Result check() throws Exception {
		final String saying = String.format(template, "TEST");
		if(!saying.contains("TEST")) {
			return Result.unhealthy("template doesn't include a name");
		}
		return Result.healthy();
	}

}
