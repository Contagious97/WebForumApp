package com.example.message_microservice;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.BodyHandler;
import io.vertx.mysqlclient.MySQLConnectOptions;
import io.vertx.mysqlclient.MySQLPool;
import io.vertx.sqlclient.PoolOptions;

public class MainVerticle extends AbstractVerticle {



  MySQLPool client;

  MySQLConnectOptions connectOptions = new MySQLConnectOptions()
    .setPort(3306)
    .setHost("root")
    .setDatabase("webforum/messages")
    .setUser("root")
    .setPassword("password123");
  String connectionString = "mysql://root:password123@127.0.0.1:3306/messages";


  PoolOptions poolOptions = new PoolOptions().setMaxSize(5);
  @Override
  public void start(Promise<Void> startPromise) throws Exception {

    client = MySQLPool.pool(vertx,connectionString);
    client.getConnection(conn->{
      if (conn.succeeded())
        System.out.println("Connected to database");
      else System.out.println("Not connected");
    });
    client.query("CREATE TABLE IF NOT EXISTS `messages` (\n" +
      "  `id` int NOT NULL AUTO_INCREMENT,\n" +
      "  `content` varchar(255) DEFAULT NULL,\n" +
      "  `date` datetime DEFAULT NULL,\n" +
      "  `receiverId` int NOT NULL,\n" +
      "  `senderId` int NOT NULL,\n" +
      "  PRIMARY KEY (`id`)\n" +
      ") ENGINE=MyISAM AUTO_INCREMENT=12 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;\n").execute();

    MessageRepository logRepository = MessageRepository.create(client);

    MessageHandler logHandler = MessageHandler.create(logRepository,vertx);

    // Create a Router
    Router router = Router.router(vertx);
    // Mount the handler for all incoming requests at every path and HTTP method

    router.get("/messages").produces("application/json").handler(logHandler::all);
    router.post("/messages").consumes("application/json").handler(BodyHandler.create()).handler(logHandler::save);
    router.get("/messages/:userId").produces("application/json").handler(logHandler::get);

    // Create the HTTP server
    vertx.createHttpServer()
      // Handle every request using the router
      .requestHandler(router)
      // Start listening
      .listen(8888)
      // Print the port
      .onSuccess(server ->
        System.out.println(
          "HTTP server started on port " + server.actualPort()
        )
      );
  }


  /*private void startBackend(Handler<AsyncResult<SQLConnection>> next, Future<Void> fut){
    client.getConnection(ar -> {
      if (ar.succeeded()){
        next.handle(Future.succeededFuture(ar.result()));
      }
    });
  }*/
}
