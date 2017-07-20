/*
 * Copyright (C) 2016 Clover Network, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 *
 * You may obtain a copy of the License at
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.clover.remote.client.messages;

/**
 * Request object for requesting to adjust the tip amount of a payment acquired from a previous auth
 */
@SuppressWarnings(value="unused")
public class TipAdjustAuthRequest {
  private String paymentId;
  private String orderId;
  private long tipAmount;

  /**
   * Get the field value
   *
   * @return The unique identifier of the associated order
   */
  public String getOrderId() {
    return orderId;
  }

  /**
   * Set the field value
   *
   * @param orderId The unique identifier of the associated order
   */
  public void setOrderId(String orderId) {
    this.orderId = orderId;
  }

  /**
   * Get the field value
   *
   * @return The unique identifier of the associated payment
   */
  public String getPaymentId() {
    return paymentId;
  }

  /**
   * Set the field value
   *
   * @param paymentId The unique identifier of the associated payment
   */
  public void setPaymentId(String paymentId) {
    this.paymentId = paymentId;
  }

  /**
   * Get the field value
   *
   * @return The amount paid in tips
   */
  public long getTipAmount() {
    return tipAmount;
  }

  /**
   * Set the field value
   *
   * @param tipAmount The amount paid in tips
   */
  public void setTipAmount(long tipAmount) {
    this.tipAmount = tipAmount;
  }
}