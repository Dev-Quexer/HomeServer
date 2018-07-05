package me.quexer.homeserver.serverapi.quickyapi.database;

import com.mongodb.ConnectionString;
import com.mongodb.async.client.MongoClient;
import com.mongodb.async.client.MongoClients;
import com.mongodb.async.client.MongoCollection;
import com.mongodb.async.client.MongoDatabase;
import org.bson.Document;

import java.text.MessageFormat;

public class MongoManager {

    private final String hostname;
    private final int port;

    private MongoClient client;
    private MongoDatabase database;

    public MongoManager(String hostname, int port, String database) {
        this.hostname = hostname;
        this.port = port;
        this.client = MongoClients.create(new ConnectionString(MessageFormat.format("mongodb://{0}:{1}", hostname, String.valueOf(port))));
        this.database = client.getDatabase(database);
    }

    public MongoCollection<Document> getCollection(String name) {
        return getDatabase().getCollection(name);

    }

    public String getHostname() {
        return hostname;
    }

    public int getPort() {
        return port;
    }

    public MongoClient getClient() {
        return client;
    }

    public MongoDatabase getDatabase() {
        return database;
    }
}
