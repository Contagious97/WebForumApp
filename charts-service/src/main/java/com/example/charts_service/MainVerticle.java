package com.example.charts_service;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.BodyHandler;
import io.vertx.ext.web.handler.CorsHandler;
import io.vertx.mysqlclient.MySQLConnectOptions;
import io.vertx.mysqlclient.MySQLPool;
import io.vertx.sqlclient.PoolOptions;
import io.vertx.sqlclient.Tuple;

public class MainVerticle extends AbstractVerticle {



  MySQLPool client;

  MySQLConnectOptions connectOptions = new MySQLConnectOptions()
    .setPort(3306)
    .setHost("root")
    .setDatabase("webforum/charts")
    .setUser("root")
    .setPassword("password123");
  String connectionString = "mysql://root:password123@127.0.0.1:3306/charts";


  PoolOptions poolOptions = new PoolOptions().setMaxSize(5);
  @Override
  public void start(Promise<Void> startPromise) throws Exception {

    client = MySQLPool.pool(vertx,connectionString);
    client.getConnection(conn->{
      if (conn.succeeded())
        System.out.println("Connected to database");
      else System.out.println("Not connected");
    });
    client.query("CREATE TABLE IF NOT EXISTS `charts` (\n" +
      "  `id` int NOT NULL AUTO_INCREMENT,\n" +
      "  `data` varchar(10000) DEFAULT NULL,\n" +
      "  `date` datetime DEFAULT NULL,\n" +
      "  PRIMARY KEY (`id`)\n" +
      ") ENGINE=MyISAM AUTO_INCREMENT=12 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;\n").execute();

    String initialData = "{\"labels\":[\"January\",\"February\",\"March\",\"April\",\"May\",\"June\",\"July\"],\"datasets\":[{\"label\":\"Temperature\",\"data\":[7,7,10,15,20,23,26]},{\"label\":\"Precipitation\",\"data\":[8.1,14.9,41.0,31.4,42.6,57.5,36.0]}]}";

    client.preparedQuery("INSERT INTO charts(data) values(?)").execute(Tuple.of(initialData));
    ChartRepository chartRepository = ChartRepository.create(client);

    ChartHandler chartHandler = ChartHandler.create(chartRepository,vertx);

    // Create a Router
    Router router = Router.router(vertx);
    // Mount the handler for all incoming requests at every path and HTTP method
    router.route().handler(CorsHandler.create(".*."));

    router.get("/charts").produces("application/json").handler(chartHandler::all);
    router.post("/charts").consumes("application/json").handler(BodyHandler.create()).handler(chartHandler::save);
    router.get("/charts/:chartsId").produces("application/json").handler(chartHandler::get);

    // Create the HTTP server
    vertx.createHttpServer()
      // Handle every request using the router
      .requestHandler(router)
      // Start listening
      .listen(8890)
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
