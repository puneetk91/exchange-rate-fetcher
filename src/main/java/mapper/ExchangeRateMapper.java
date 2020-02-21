package mapper;

import dto.ExchangeRate;
import dto.ExchangeRateDataClientResponse;
import org.apache.commons.collections4.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ExchangeRateMapper {

    public ExchangeRate mapToDto(entity.ExchangeRate source) {
        if (source == null) {
            return null;
        }
        return new ExchangeRate(source.getValue(), source.getSourceCurrency(), source.getTargetCurrency(),
                source.getValueAt());
    }

    public List<ExchangeRate> mapToDto(List<entity.ExchangeRate> exchangeRateList) {
        if (CollectionUtils.isEmpty(exchangeRateList)) {
            return new ArrayList<>();
        }
        return exchangeRateList.stream()
                .map(exchangeRate -> mapToDto(exchangeRate))
                .collect(Collectors.toList());
    }

    public entity.ExchangeRate mapToEntity(ExchangeRateDataClientResponse response) {
        if (response == null)
            return null;
        return new entity.ExchangeRate(response.getSourceCurrency(), response.getTargetCurrency(), response.getValue(),
                response.getValueAt());
    }

}
