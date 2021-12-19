package io.swagger.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.v3.oas.annotations.media.Schema;
import java.math.BigDecimal;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * Product in storage
 */
@Schema(description = "Product in storage")
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2021-12-19T09:20:14.611Z[GMT]")


public class Product   {
  @JsonProperty("name")
  private String name = null;

  @JsonProperty("Photo")
  private String photo = null;

  @JsonProperty("companyid")
  private BigDecimal companyid = null;

  @JsonProperty("productid")
  private BigDecimal productid = null;

  @JsonProperty("price")
  private BigDecimal price = null;

  @JsonProperty("count")
  private BigDecimal count = null;

  @JsonProperty("description")
  private String description = null;

  public Product name(String name) {
    this.name = name;
    return this;
  }

  /**
   * Get name
   * @return name
   **/
  @Schema(required = true, description = "")
      @NotNull

    public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Product photo(String photo) {
    this.photo = photo;
    return this;
  }

  /**
   * Get photo
   * @return photo
   **/
  @Schema(description = "")
  
    public String getPhoto() {
    return photo;
  }

  public void setPhoto(String photo) {
    this.photo = photo;
  }

  public Product companyid(BigDecimal companyid) {
    this.companyid = companyid;
    return this;
  }

  /**
   * Get companyid
   * @return companyid
   **/
  @Schema(required = true, description = "")
      @NotNull

    @Valid
    public BigDecimal getCompanyid() {
    return companyid;
  }

  public void setCompanyid(BigDecimal companyid) {
    this.companyid = companyid;
  }

  public Product productid(BigDecimal productid) {
    this.productid = productid;
    return this;
  }

  /**
   * Get productid
   * @return productid
   **/
  @Schema(required = true, description = "")
      @NotNull

    @Valid
    public BigDecimal getProductid() {
    return productid;
  }

  public void setProductid(BigDecimal productid) {
    this.productid = productid;
  }

  public Product price(BigDecimal price) {
    this.price = price;
    return this;
  }

  /**
   * Get price
   * @return price
   **/
  @Schema(required = true, description = "")
      @NotNull

    @Valid
    public BigDecimal getPrice() {
    return price;
  }

  public void setPrice(BigDecimal price) {
    this.price = price;
  }

  public Product count(BigDecimal count) {
    this.count = count;
    return this;
  }

  /**
   * Get count
   * @return count
   **/
  @Schema(required = true, description = "")
      @NotNull

    @Valid
    public BigDecimal getCount() {
    return count;
  }

  public void setCount(BigDecimal count) {
    this.count = count;
  }

  public Product description(String description) {
    this.description = description;
    return this;
  }

  /**
   * Get description
   * @return description
   **/
  @Schema(description = "")
  
    public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Product product = (Product) o;
    return Objects.equals(this.name, product.name) &&
        Objects.equals(this.photo, product.photo) &&
        Objects.equals(this.companyid, product.companyid) &&
        Objects.equals(this.productid, product.productid) &&
        Objects.equals(this.price, product.price) &&
        Objects.equals(this.count, product.count) &&
        Objects.equals(this.description, product.description);
  }

  @Override
  public int hashCode() {
    return Objects.hash(name, photo, companyid, productid, price, count, description);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Product {\n");
    
    sb.append("    name: ").append(toIndentedString(name)).append("\n");
    sb.append("    photo: ").append(toIndentedString(photo)).append("\n");
    sb.append("    companyid: ").append(toIndentedString(companyid)).append("\n");
    sb.append("    productid: ").append(toIndentedString(productid)).append("\n");
    sb.append("    price: ").append(toIndentedString(price)).append("\n");
    sb.append("    count: ").append(toIndentedString(count)).append("\n");
    sb.append("    description: ").append(toIndentedString(description)).append("\n");
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
