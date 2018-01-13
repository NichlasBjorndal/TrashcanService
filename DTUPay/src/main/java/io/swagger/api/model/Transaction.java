package io.swagger.api.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;

import java.math.BigDecimal;
import java.util.Objects;


@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaResteasyServerCodegen", date = "2018-01-13T14:21:38.880Z")
public class Transaction   {
  
  private String barcodeUUID = null;
  private String receiverCVR = null;
  private BigDecimal amount = null;

  /**
   **/
  
  @ApiModelProperty(value = "")
  @JsonProperty("barcodeUUID")
  public String getBarcodeUUID() {
    return barcodeUUID;
  }
  public void setBarcodeUUID(String barcodeUUID) {
    this.barcodeUUID = barcodeUUID;
  }

  /**
   **/
  
  @ApiModelProperty(value = "")
  @JsonProperty("receiverCVR")
  public String getReceiverCVR() {
    return receiverCVR;
  }
  public void setReceiverCVR(String receiverCVR) {
    this.receiverCVR = receiverCVR;
  }

  /**
   **/
  
  @ApiModelProperty(value = "")
  @JsonProperty("amount")
  public BigDecimal getAmount() {
    return amount;
  }
  public void setAmount(BigDecimal amount) {
    this.amount = amount;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Transaction transaction = (Transaction) o;
    return Objects.equals(barcodeUUID, transaction.barcodeUUID) &&
        Objects.equals(receiverCVR, transaction.receiverCVR) &&
        Objects.equals(amount, transaction.amount);
  }

  @Override
  public int hashCode() {
    return Objects.hash(barcodeUUID, receiverCVR, amount);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Transaction {\n");
    
    sb.append("    barcodeUUID: ").append(toIndentedString(barcodeUUID)).append("\n");
    sb.append("    receiverCVR: ").append(toIndentedString(receiverCVR)).append("\n");
    sb.append("    amount: ").append(toIndentedString(amount)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }
}

