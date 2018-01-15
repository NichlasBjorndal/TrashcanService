package io.swagger.model;

import java.util.Objects;
import java.util.ArrayList;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import java.math.BigDecimal;
import javax.validation.constraints.*;
import io.swagger.annotations.*;


@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaResteasyServerCodegen", date = "2018-01-15T13:36:32.917Z")
public class Transaction   {
  
  private String barcode = null;
  private String receiverCVR = null;
  private BigDecimal amount = null;

  /**
   **/
  
  @ApiModelProperty(value = "")
  @JsonProperty("barcode")
  public String getBarcode() {
    return barcode;
  }
  public void setBarcode(String barcode) {
    this.barcode = barcode;
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
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Transaction transaction = (Transaction) o;
    return Objects.equals(barcode, transaction.barcode) &&
        Objects.equals(receiverCVR, transaction.receiverCVR) &&
        Objects.equals(amount, transaction.amount);
  }

  @Override
  public int hashCode() {
    return Objects.hash(barcode, receiverCVR, amount);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Transaction {\n");
    
    sb.append("    barcode: ").append(toIndentedString(barcode)).append("\n");
    sb.append("    receiverCVR: ").append(toIndentedString(receiverCVR)).append("\n");
    sb.append("    amount: ").append(toIndentedString(amount)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(java.lang.Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }
}

