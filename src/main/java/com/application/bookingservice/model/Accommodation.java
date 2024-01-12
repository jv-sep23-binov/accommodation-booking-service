package com.application.bookingservice.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import org.hibernate.type.SqlTypes;

@Entity
@Data
@Table(name = "accommodations")
@SQLDelete(sql = "UPDATE accommodations SET is_deleted = true WHERE id=?")
@Where(clause = "is_deleted=false")
@Accessors(chain = true)
public class Accommodation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @JdbcTypeCode(SqlTypes.VARCHAR)
    @Column(nullable = false)
    private Type type;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "address_id", nullable = false)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Address address;
    @Column(nullable = false)
    private String size;
    @Column(nullable = false)
    private String amenities;
    @Column(nullable = false)
    private BigDecimal price;
    @Column(nullable = false)
    private Integer availableUnits;
    @Column(nullable = false)
    private boolean isDeleted = false;

    public enum Type {
        HOUSE,
        APARTMENT
    }
}
