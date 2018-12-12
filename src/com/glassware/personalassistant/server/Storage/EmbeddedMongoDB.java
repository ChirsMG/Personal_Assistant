package com.glassware.personalassistant.server.Storage;



import de.flapdoodle.embed.mongo.MongodExecutable;
import de.flapdoodle.embed.mongo.MongodStarter;
import de.flapdoodle.embed.mongo.config.IMongodConfig;
import de.flapdoodle.embed.mongo.config.MongodConfigBuilder;
import de.flapdoodle.embed.mongo.config.Net;
import de.flapdoodle.embed.mongo.distribution.Version;
import de.flapdoodle.embed.process.runtime.Network;

import java.io.IOException;
import java.net.ServerSocket;


/**
 * Wrapper around the MongoDB database
 */
public class EmbeddedMongoDB {
    /** The embedded MongoDB executable */
    private MongodExecutable mongodExecutable;
    /** The URL to connect on */
    private String connectionUrl;

    /**
     * Start MongoDB running
     * @throws IOException if an error occurs starting MongoDB
     */
    public void start() throws IOException {
        if (mongodExecutable == null) {
            int port = getFreePort();

            MongodStarter starter = MongodStarter.getDefaultInstance();
            IMongodConfig mongodConfig = new MongodConfigBuilder()
                    .version(Version.Main.V3_4)
                    .net(new Net("localhost", port, Network.localhostIsIPv6()))
                    .build();

            mongodExecutable = starter.prepare(mongodConfig);
            mongodExecutable.start();

            connectionUrl = "mongodb://localhost:" + port;
        }
    }

    /**
     * Stop MongoDB
     */
    public void stop() {
        if (mongodExecutable != null) {
            mongodExecutable.stop();
        }
    }

    /**
     * Get the URL to use to connect to the database
     * @return the connection URL
     */
    public String getConnectionUrl() {
        return connectionUrl;
    }

    /**
     * Get a free port to listen on
     * @return the port
     * @throws IOException if an error occurs finding a port
     */
    private static int getFreePort() throws IOException {
        ServerSocket s = new ServerSocket(0);
        System.out.println("Port "+s.getLocalPort());
        return s.getLocalPort();
    }
}