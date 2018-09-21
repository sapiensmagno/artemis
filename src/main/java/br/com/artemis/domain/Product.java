package br.com.artemis.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Product.
 */
@Entity
@Table(name = "product")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Product implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "img_url")
    private String imgURL;

    @NotNull
    @Column(name = "price_value", precision=10, scale=2, nullable = false)
    private BigDecimal priceValue;

    @OneToMany(mappedBy = "product")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<SpecialOffer> specialOffers = new HashSet<>();

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "product_supplier",
               joinColumns = @JoinColumn(name="products_id", referencedColumnName="id"),
               inverseJoinColumns = @JoinColumn(name="suppliers_id", referencedColumnName="id"))
    private Set<Supplier> suppliers = new HashSet<>();

    @ManyToOne
    private PriceRule priceRule;

    // jhipster-needle-entity-add-field - Jhipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Product name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public Product description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImgURL() {
        return imgURL;
    }

    public Product imgURL(String imgURL) {
        this.imgURL = imgURL;
        return this;
    }

    public void setImgURL(String imgURL) {
        this.imgURL = imgURL;
    }

    public BigDecimal getPriceValue() {
        return priceValue;
    }

    public Product priceValue(BigDecimal priceValue) {
        this.priceValue = priceValue;
        return this;
    }

    public void setPriceValue(BigDecimal priceValue) {
        this.priceValue = priceValue;
    }

    public Set<SpecialOffer> getSpecialOffers() {
        return specialOffers;
    }

    public Product specialOffers(Set<SpecialOffer> specialOffers) {
        this.specialOffers = specialOffers;
        return this;
    }

    public Product addSpecialOffer(SpecialOffer specialOffer) {
        this.specialOffers.add(specialOffer);
        specialOffer.setProduct(this);
        return this;
    }

    public Product removeSpecialOffer(SpecialOffer specialOffer) {
        this.specialOffers.remove(specialOffer);
        specialOffer.setProduct(null);
        return this;
    }

    public void setSpecialOffers(Set<SpecialOffer> specialOffers) {
        this.specialOffers = specialOffers;
    }

    public Set<Supplier> getSuppliers() {
        return suppliers;
    }

    public Product suppliers(Set<Supplier> suppliers) {
        this.suppliers = suppliers;
        return this;
    }

    public Product addSupplier(Supplier supplier) {
        this.suppliers.add(supplier);
        return this;
    }

    public Product removeSupplier(Supplier supplier) {
        this.suppliers.remove(supplier);
        return this;
    }

    public void setSuppliers(Set<Supplier> suppliers) {
        this.suppliers = suppliers;
    }

    public PriceRule getPriceRule() {
        return priceRule;
    }

    public Product priceRule(PriceRule priceRule) {
        this.priceRule = priceRule;
        return this;
    }

    public void setPriceRule(PriceRule priceRule) {
        this.priceRule = priceRule;
    }
    // jhipster-needle-entity-add-getters-setters - Jhipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Product product = (Product) o;
        if (product.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), product.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Product{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", description='" + getDescription() + "'" +
            ", imgURL='" + getImgURL() + "'" +
            ", priceValue='" + getPriceValue() + "'" +
            "}";
    }
}
