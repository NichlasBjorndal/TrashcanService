package io.swagger.model;

import java.util.Objects;
import java.util.ArrayList;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import java.math.BigDecimal;
import javax.validation.constraints.*;
import io.swagger.annotations.*;


@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaResteasyServerCodegen", date = "2018-01-13T13:54:24.517Z")
public class Transaction   {
  
  private String barcodeUUID = null;
  private String receriverCVR = null;
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
  @JsonProperty("receriverCVR")
  public String getReceriverCVR() {
    return receriverCVR;
  }
  public void setReceriverCVR(String receriverCVR) {
    this.receriverCVR = receriverCVR;
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
    return Objects.equals(barcodeUUID, transaction.barcodeUUID) &&
        Objects.equals(receriverCVR, transaction.receriverCVR) &&
        Objects.equals(amount, transaction.amount);
  }

  @Override
  public int hashCode() {
    return Objects.hash(barcodeUUID, receriverCVR, amount);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Transaction {\n");
    
    sb.append("    barcodeUUID: ").append(toIndentedString(barcodeUUID)).append("\n");
    sb.append("    receriverCVR: ").append(toIndentedString(receriverCVR)).append("\n");
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

