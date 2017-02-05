package org.wildfly.swarm.examples.ds.subsystem;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.SQLException;
import java.util.List;

import javax.inject.Inject;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.sql.DataSource;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import org.wildfly.swarm.examples.ds.subsystem.entity.Benutzer;

/**
 * @author Bob McWhirter
 */
@Path("/")
public class MyResource {

	@Inject
	PersistenceHelper helper;

	@GET
	@Produces("text/plain")
	public String get() throws NamingException, SQLException {
		Context ctx = new InitialContext();
		DataSource ds = (DataSource) ctx.lookup("jboss/datasources/ExampleDS");
		Connection conn = ds.getConnection();
	
			List<Benutzer> benListe = helper.getEntityManager().createNamedQuery("Benutzer.findAll", Benutzer.class).getResultList();
		try {
			DatabaseMetaData dbm = conn.getMetaData();
			return "Howdy using connection Metadata-URL: " + dbm.getURL() + "Benutzer in DB:" + benListe.size() + " --  EM arbeitet";

		} finally {
			conn.close();
		}
	}
}
