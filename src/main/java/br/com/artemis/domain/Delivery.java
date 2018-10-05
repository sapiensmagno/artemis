package br.com.artemis.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Delivery.
 */
@Entity
@Table(name = "delivery")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Delivery implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @OneToMany(mappedBy = "delivery")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<DeliveryStatus> deliveryStatuses = new HashSet<>();

    @ManyToOne
    private Purchase purchase;

    // jhipster-needle-entity-add-field - Jhipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Set<DeliveryStatus> getDeliveryStatuses() {
        return deliveryStatuses;
    }

    public Delivery deliveryStatuses(Set<DeliveryStatus> deliveryStatuses) {
        this.deliveryStatuses = deliveryStatuses;
        return this;
    }

    public Delivery addDeliveryStatus(DeliveryStatus deliveryStatus) {
        this.deliveryStatuses.add(deliveryStatus);
        deliveryStatus.setDelivery(this);
        return this;
    }

    public Delivery removeDeliveryStatus(DeliveryStatus deliveryStatus) {
        this.deliveryStatuses.remove(deliveryStatus);
        deliveryStatus.setDelivery(null);
        return this;
    }

    public void setDeliveryStatuses(Set<DeliveryStatus> deliveryStatuses) {
        this.deliveryStatuses = deliveryStatuses;
    }

    public Purchase getPurchase() {
        return purchase;
    }

    public Delivery purchase(Purchase purchase) {
        this.purchase = purchase;
        return this;
    }

    public void setPurchase(Purchase purchase) {
        this.purchase = purchase;
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
        Delivery delivery = (Delivery) o;
        if (delivery.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), delivery.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Delivery{" +
            "id=" + getId() +
            "}";
    }
}
