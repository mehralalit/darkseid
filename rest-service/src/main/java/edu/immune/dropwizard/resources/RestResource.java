package edu.immune.dropwizard.resources;

import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.codahale.metrics.annotation.Timed;

import edu.immune.dropwizard.api.Spend;
import edu.immune.dropwizard.api.User;
import edu.immune.dropwizard.db.RedisOperations;
import edu.immune.dropwizard.util.SpendManager;
import edu.immune.dropwizard.util.UserRecordManager;
import redis.clients.jedis.JedisPool;

@Path("/rest-service")
@Produces(value = MediaType.APPLICATION_JSON)
public class RestResource {

	private Logger logger = LoggerFactory.getLogger(RestResource.class);

	public RestResource() {
		super();
	}

	/**
	 * @param spend
	 *            spend data in json format
	 * @param token
	 *            authorization token
	 * @param jedisPool
	 *            JedisPool reference
	 * @return Http 200 if record is added successfully otherwise Http 400
	 */
	@POST
	@Path("spend")
	@Timed
	@Consumes(MediaType.APPLICATION_JSON)
	public Response addSpend(@Valid Spend spend, @HeaderParam(value = HttpHeaders.AUTHORIZATION) String token,
			@Context JedisPool jedisPool) {
		Response response;
		logger.info("token: {}, spend: {}", token, spend.toString());
		try {
			// to replace token scheme from received value
			token = token.split(" ")[1];
			SpendManager.addSpend(token, spend, jedisPool);
//			Using Local Cache
			String phone = UserRecordManager.getPhone(token);
			User user = RedisOperations.getValue(jedisPool.getResource(), phone);
			response = Response.ok(user.getSpends()).build();
		} catch (Exception e) {
			logger.error("exception adding new spend", e);
			response = Response.status(Status.BAD_REQUEST).build();
		}
		return response;
	}

}
