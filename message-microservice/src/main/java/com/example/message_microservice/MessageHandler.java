package com.example.message_microservice;

import io.vertx.core.Vertx;
import io.vertx.core.json.Json;
import io.vertx.ext.web.RoutingContext;

import java.util.logging.Level;
import java.util.logging.Logger;

public class MessageHandler {
  private static final Logger LOGGER = Logger.getLogger(MessageHandler.class.getSimpleName());

  MessageRepository messages;
  Vertx vertx;

  private MessageHandler(MessageRepository _messages, Vertx _vertx) {
    this.messages = _messages;
    this.vertx = _vertx;
  }

  //factory method
  public static MessageHandler create(MessageRepository messages, Vertx vertx) {
    return new MessageHandler(messages,vertx);
  }

  public void all(RoutingContext rc) {
//        var params = rc.queryParams();
//        var q = params.get("q");
//        var limit = params.get("limit") == null ? 10 : Integer.parseInt(params.get("q"));
//        var offset = params.get("offset") == null ? 0 : Integer.parseInt(params.get("offset"));
//        LOGGER.log(Level.INFO, " find by keyword: q={0}, limit={1}, offset={2}", new Object[]{q, limit, offset});
    this.messages.findAll()
      .onSuccess(
        data -> rc.response().end(Json.encode(data))
      ).onFailure(error ->{
        LOGGER.log(Level.INFO, error.getMessage());
      });
  }

  public void get(RoutingContext rc) {
    var params = rc.pathParams();
    int receiverId = Integer.valueOf(params.get("userName"));
    this.messages.findById(receiverId)
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
    var form = body.mapTo(CreateMessageRequest.class);

    this.messages.save(Message.of(form.getContent(), form.getReceiverId(),form.getSenderId()))
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
