package com.example.user_microservice;

import io.vertx.core.Vertx;
import io.vertx.core.json.Json;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.client.WebClient;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LogHandler {
  private static final Logger LOGGER = Logger.getLogger(LogHandler.class.getSimpleName());

  LogRepository logs;
  Vertx vertx;

  private LogHandler(LogRepository _logs, Vertx _vertx) {
    this.logs = _logs;
    this.vertx = _vertx;
  }

  //factory method
  public static LogHandler create(LogRepository logs, Vertx vertx) {
    return new LogHandler(logs,vertx);
  }

  public void all(RoutingContext rc) {
//        var params = rc.queryParams();
//        var q = params.get("q");
//        var limit = params.get("limit") == null ? 10 : Integer.parseInt(params.get("q"));
//        var offset = params.get("offset") == null ? 0 : Integer.parseInt(params.get("offset"));
//        LOGGER.log(Level.INFO, " find by keyword: q={0}, limit={1}, offset={2}", new Object[]{q, limit, offset});
    this.logs.findAll()
      .onSuccess(
        data -> rc.response().end(Json.encode(data))
      ).onFailure(error ->{
        LOGGER.log(Level.INFO, error.getMessage());
      });
  }

  public void get(RoutingContext rc) {
    var params = rc.pathParams();
    String userName = params.get("userName");
    this.logs.findById(userName)
      .onSuccess(
        post -> rc.response().end(Json.encode(post))
      )
      .onFailure(
        throwable -> rc.fail(throwable)
      );

  }


  public void save(RoutingContext rc) {
    //rc.getBodyAsJson().mapTo(PostForm.class)
    var body = rc.getBodyAsJson();
    LOGGER.log(Level.INFO, "request body: {0}", body);
    var form = body.mapTo(CreateLogRequest.class);

    this.logs.save(Log.of(form.getContent(), form.getUserName()))
      .onSuccess(
        savedId -> rc.response()
          .putHeader("Location", "/posts/" + savedId)
          .setStatusCode(201)
          .end()
      ).onFailure(error->{
        LOGGER.log(Level.INFO, error.getMessage());
      });
  }

}
