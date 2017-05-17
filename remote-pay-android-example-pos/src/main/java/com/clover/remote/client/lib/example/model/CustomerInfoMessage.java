package com.clover.remote.client.lib.example.model;

/**
 * Created by glennbedwell on 5/4/17.
 */
public class CustomerInfoMessage extends PayloadMessage {
  public final CustomerInfo customerInfo;

  public CustomerInfoMessage(CustomerInfo customerInfo) {
    super("CustomerInfoMessage", RatingsMessageType.CUSTOMER_INFO);
    this.customerInfo = customerInfo;
  }

}
