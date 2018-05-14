package edu.immune.dropwizard.conf;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

import com.bendb.dropwizard.redis.JedisFactory;
import com.fasterxml.jackson.annotation.JsonProperty;

import io.dropwizard.Configuration;
import io.dropwizard.bundles.assets.AssetsBundleConfiguration;
import io.dropwizard.bundles.assets.AssetsConfiguration;

/**
 * Application Cofiguration
 * 
 * @author lalit
 *
 */
public class RestConfiguration extends Configuration implements AssetsBundleConfiguration {

	@Valid
	@NotNull
	@JsonProperty
	private final AssetsConfiguration assets = AssetsConfiguration.builder().build();

	@Override
	public AssetsConfiguration getAssetsConfiguration() {
		return assets;
	}

	@NotEmpty
	private String template;

	@JsonProperty
	public String getTemplate() {
		return template;
	}

	@JsonProperty
	public void setTemplate(String template) {
		this.template = template;
	}
	
	@NotNull
	@JsonProperty
	private JedisFactory redis;

	public JedisFactory getJedisFactory() {
		return redis;
	}

	public void setJedisFactory(JedisFactory jedisFactory) {
		this.redis = jedisFactory;
	}

}
