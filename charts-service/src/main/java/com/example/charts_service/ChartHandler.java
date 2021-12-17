package com.example.charts_service;

import io.vertx.core.Vertx;
import io.vertx.core.json.Json;
import io.vertx.ext.web.RoutingContext;

import java.util.logging.Level;
import java.util.logging.Logger;

public class ChartHandler {
  private static final Logger LOGGER = Logger.getLogger(ChartHandler.class.getSimpleName());

  ChartRepository charts;
  Vertx vertx;

  private ChartHandler(ChartRepository _charts, Vertx _vertx) {
    this.charts = _charts;
    this.vertx = _vertx;
  }

  //factory method
  public static ChartHandler create(ChartRepository charts, Vertx vertx) {
    return new ChartHandler(charts,vertx);
  }

  public void all(RoutingContext rc) {
//        var params = rc.queryParams();
//        var q = params.get("q");
//        var limit = params.get("limit") == null ? 10 : Integer.parseInt(params.get("q"));
//        var offset = params.get("offset") == null ? 0 : Integer.parseInt(params.get("offset"));
//        LOGGER.log(Level.INFO, " find by keyword: q={0}, limit={1}, offset={2}", new Object[]{q, limit, offset});
    this.charts.findAll()
      .onSuccess(
        data -> rc.response().end(Json.encode(data))
      ).onFailure(error ->{
        LOGGER.log(Level.INFO, error.getMessage());
      });
  }

  public void get(RoutingContext rc) {
    var params = rc.pathParams();
    int receiverId = Integer.parseInt(params.get("chartsId"));
    this.charts.findById(receiverId)
      .onSuccess(
        post -> rc.response().end(Json.encode(post))
      )
      .onFailure(
        rc::fail
      );

  }


  public void save(RoutingContext rc) {
    //rc.getBodyAsJson().mapTo(PostForm.class)
    var body = rc.getBodyAsJson();
    LOGGER.log(Level.INFO, "request body: {0}", body);
    var form = body.mapTo(CreateChartRequest.class);

    this.charts.save(Chart.of(form.getData()))
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
