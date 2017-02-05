package org.wildfly.swarm.examples.ds.subsystem;

import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.ClassLoaderAsset;
import org.wildfly.swarm.Swarm;
import org.wildfly.swarm.datasources.DatasourcesFraction;
import org.wildfly.swarm.examples.ds.subsystem.entity.Benutzer;
import org.wildfly.swarm.jaxrs.JAXRSArchive;
import org.wildfly.swarm.jaxrs.JAXRSFraction;

/**
 * @author Bob McWhirter
 */
public class Main {

	static String driverModule;

	public static void main(String[] args) throws Exception {

		Swarm swarm = new Swarm();

		swarm.fraction(datasourceWithMysql());
		driverModule = "com.mysql";

		// Start the swarm
		swarm.start();
		JAXRSArchive appDeployment = ShrinkWrap.create(JAXRSArchive.class);
		appDeployment.addPackage(MyResource.class.getPackage());
		appDeployment.addPackage(Benutzer.class.getPackage());
		appDeployment.addAllDependencies();
		appDeployment.addAsWebInfResource(
				new ClassLoaderAsset("META-INF/persistence.xml", MyResource.class.getClassLoader()),
				"classes/META-INF/persistence.xml");
		appDeployment.addModule(driverModule);

		// Deploy your app
		swarm.deploy(appDeployment);

	}

	private static DatasourcesFraction datasourceWithMysql() {
		return new DatasourcesFraction().jdbcDriver("com.mysql", (d) -> {
			d.driverClassName("com.mysql.jdbc.Driver");
			d.xaDatasourceClass("com.mysql.jdbc.jdbc2.optional.MysqlXADataSource");
			d.driverModuleName("com.mysql");
		}).dataSource("ExampleDS", (ds) -> {
			ds.driverName("com.mysql");
			ds.connectionUrl("jdbc:mysql://172.17.0.2:3306/dbuser");
			ds.userName("root");
			ds.password("myMysql");
		});
	}

}
