package com.exchangerate.entity;

import com.exchangerate.dto.CurrencyCode;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Timestamp;

@Table
@Entity(name = "exchange_rates")
public class ExchangeRate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "source_currency_code", nullable = false)
    @Enumerated(value = EnumType.STRING)
    private CurrencyCode sourceCurrency;

    @Column(name = "target_currency_code", nullable = false)
    @Enumerated(value = EnumType.STRING)
    private CurrencyCode targetCurrency;

    @Column(name = "value", nullable = false)
    private BigDecimal value;

    @Column(name = "value_at", nullable = false)
    private Timestamp valueAt;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false)
    private Timestamp createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private Timestamp updatedAt;

    public ExchangeRate() {
    }

    public ExchangeRate(CurrencyCode sourceCurrency, CurrencyCode targetCurrency, BigDecimal value, Timestamp valueAt) {
        this.sourceCurrency = sourceCurrency;
        this.targetCurrency = targetCurrency;
        this.value = value;
        this.valueAt = valueAt;
    }

    public ExchangeRate(long id, CurrencyCode sourceCurrency, CurrencyCode targetCurrency, BigDecimal value, Timestamp valueAt,
                        Timestamp createdAt, Timestamp updatedAt) {
        this.id = id;
        this.sourceCurrency = sourceCurrency;
        this.targetCurrency = targetCurrency;
        this.value = value;
        this.valueAt = valueAt;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public static ExchangeRateBuilder builder() {
        return new ExchangeRateBuilder();
    }

    public BigDecimal getValue() {
        return value;
    }

    public CurrencyCode getSourceCurrency() {
        return sourceCurrency;
    }

    public CurrencyCode getTargetCurrency() {
        return targetCurrency;
    }

    public Timestamp getValueAt() {
        return valueAt;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public Timestamp getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Timestamp updatedAt) {
        this.updatedAt = updatedAt;
    }


    public static class ExchangeRateBuilder {
        private long id;
        private CurrencyCode sourceCurrency;
        private CurrencyCode targetCurrency;
        private BigDecimal value;
        private Timestamp valueAt;
        private Timestamp createdAt;
        private Timestamp updatedAt;

        ExchangeRateBuilder() {
        }

        public ExchangeRateBuilder id(long id) {
            this.id = id;
            return this;
        }

        public ExchangeRateBuilder sourceCurrency(CurrencyCode sourceCurrency) {
            this.sourceCurrency = sourceCurrency;
            return this;
        }

        public ExchangeRateBuilder targetCurrency(CurrencyCode targetCurrency) {
            this.targetCurrency = targetCurrency;
            return this;
        }

        public ExchangeRateBuilder value(BigDecimal value) {
            this.value = value;
            return this;
        }

        public ExchangeRateBuilder valueAt(Timestamp valueAt) {
            this.valueAt = valueAt;
            return this;
        }

        public ExchangeRateBuilder createdAt(Timestamp valueAt) {
            this.createdAt = createdAt;
            return this;
        }

        public ExchangeRateBuilder updatedAt(Timestamp updatedAt) {
            this.updatedAt = updatedAt;
            return this;
        }

        public ExchangeRate build() {
            return new ExchangeRate(id, sourceCurrency, targetCurrency, value, valueAt, createdAt, updatedAt);
        }

        public String toString() {
            return "ExchangeRate.ExchangeRateBuilder(id=" + this.id + ", sourceCurrency=" + this.sourceCurrency + ", " +
                    "targetCurrency=" + this.targetCurrency + ", value=" + this.value + ", valueAt=" + this.valueAt + ", " +
                    "createdAt=" + this.createdAt + ", updatedAt=" + this.updatedAt + ")";
        }
    }
}
