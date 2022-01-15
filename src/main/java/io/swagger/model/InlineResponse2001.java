package io.swagger.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.model.Address;
import io.swagger.model.Product;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.ArrayList;
import java.util.List;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * InlineResponse2001
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2021-12-24T12:46:09.397Z[GMT]")


public class InlineResponse2001   {
    @JsonProperty("name")
    private String name = null;

    @JsonProperty("surname")
    private String surname = null;

    @JsonProperty("phone")
    private String phone = null;

    @JsonProperty("email")
    private String email = null;

    @JsonProperty("status")
    private String status = null;

    @JsonProperty("id")
    private Integer id = null;

    @JsonProperty("products")
    @Valid
    private List<Product> products = null;

    @JsonProperty("address")
    private Address address = null;

    public InlineResponse2001 name(String name) {
        this.name = name;
        return this;
    }

    /**
     * Get status
     * @return status
     **/
    @Schema(required = true, description = "")
    @NotNull

    @Size(min=1)   public String getStatus() {
        return name;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public InlineResponse2001 status(String status) {
        this.status = status;
        return this;
    }

    /**
     * Get name
     * @return name
     **/
    @Schema(required = true, description = "")
    @NotNull

    @Size(min=1)   public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public InlineResponse2001 surname(String surname) {
        this.surname = surname;
        return this;
    }

    /**
     * Get surname
     * @return surname
     **/
    @Schema(required = true, description = "")
    @NotNull

    @Size(min=1)   public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public InlineResponse2001 phone(String phone) {
        this.phone = phone;
        return this;
    }

    /**
     * Get phone
     * @return phone
     **/
    @Schema(required = true, description = "")
    @NotNull

    @Size(min=1)   public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public InlineResponse2001 email(String email) {
        this.email = email;
        return this;
    }

    /**
     * Get email
     * @return email
     **/
    @Schema(required = true, description = "")
    @NotNull

    @Size(min=1)   public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public InlineResponse2001 id(Integer id) {
        this.id = id;
        return this;
    }

    /**
     * Get id
     * @return id
     **/
    @Schema(description = "")

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public InlineResponse2001 products(List<Product> products) {
        this.products = products;
        return this;
    }

    public InlineResponse2001 addProductsItem(Product productsItem) {
        if (this.products == null) {
            this.products = new ArrayList<Product>();
        }
        this.products.add(productsItem);
        return this;
    }

    /**
     * Get products
     * @return products
     **/
    @Schema(description = "")
    @Valid
    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public InlineResponse2001 address(Address address) {
        this.address = address;
        return this;
    }

    /**
     * Get address
     * @return address
     **/
    @Schema(description = "")

    @Valid
    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }


    @Override
    public boolean equals(java.lang.Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        InlineResponse2001 inlineResponse2001 = (InlineResponse2001) o;
        return Objects.equals(this.name, inlineResponse2001.name) &&
                Objects.equals(this.surname, inlineResponse2001.surname) &&
                Objects.equals(this.phone, inlineResponse2001.phone) &&
                Objects.equals(this.email, inlineResponse2001.email) &&
                Objects.equals(this.id, inlineResponse2001.id) &&
                Objects.equals(this.products, inlineResponse2001.products) &&
                Objects.equals(this.address, inlineResponse2001.address) &&
                Objects.equals(this.status, inlineResponse2001.status);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, surname, phone, email, id, products, address, status);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class InlineResponse2001 {\n");

        sb.append("    name: ").append(toIndentedString(name)).append("\n");
        sb.append("    surname: ").append(toIndentedString(surname)).append("\n");
        sb.append("    phone: ").append(toIndentedString(phone)).append("\n");
        sb.append("    email: ").append(toIndentedString(email)).append("\n");
        sb.append("    id: ").append(toIndentedString(id)).append("\n");
        sb.append("    products: ").append(toIndentedString(products)).append("\n");
        sb.append("    address: ").append(toIndentedString(address)).append("\n");
        sb.append("    status: ").append(toIndentedString(status)).append("\n");
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
