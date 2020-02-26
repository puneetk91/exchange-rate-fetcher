package com.exchangerate.entity;

import com.exchangerate.dto.CurrencyCode;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Timestamp;

@Table
@Entity(name = "exchange_rates")
public class ExchangeRate extends TrackableEntity {

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

    public ExchangeRate(CurrencyCode sourceCurrency, CurrencyCode targetCurrency, BigDecimal value, Timestamp valueAt) {
        super.sanitizeTimestamps();
        this.sourceCurrency = sourceCurrency;
        this.targetCurrency = targetCurrency;
        this.value = value;
        this.valueAt = valueAt;
    }

    public ExchangeRate(long id, CurrencyCode sourceCurrency, CurrencyCode targetCurrency, BigDecimal value, Timestamp valueAt) {
        super.sanitizeTimestamps();
        this.id = id;
        this.sourceCurrency = sourceCurrency;
        this.targetCurrency = targetCurrency;
        this.value = value;
        this.valueAt = valueAt;
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


    public static class ExchangeRateBuilder {
        private long id;
        private CurrencyCode sourceCurrency;
        private CurrencyCode targetCurrency;
        private BigDecimal value;
        private Timestamp valueAt;

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

        public ExchangeRate build() {
            return new ExchangeRate(id, sourceCurrency, targetCurrency, value, valueAt);
        }

        public String toString() {
            return "ExchangeRate.ExchangeRateBuilder(id=" + this.id + ", sourceCurrency=" + this.sourceCurrency + ", targetCurrency=" + this.targetCurrency + ", value=" + this.value + ", valueAt=" + this.valueAt + ")";
        }
    }
}
