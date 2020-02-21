package entity;

import dto.CurrencyCode;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Timestamp;

@Entity(name = "exchange_rates")
public class ExchangeRate extends TrackableEntity {

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

}
