package io.github.torbs00.stock_valuation.service;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

@Service
public class StockService {

    private final String apiKey;
    private final WebClient restClient;

    public StockService(@Value("${api_key}") String apiKey, @Value("${base_url}") String baseUrl, WebClient.Builder webClientBuilder) {
        this.apiKey = apiKey;
        this.restClient = webClientBuilder.baseUrl(baseUrl).build();
    }

    /**
     * Gets the stock name from AlphaVantage.
     *
     * @param ticker
     * @return Most likely stock name from ticker
     */
    public String getStockName(String ticker) {
        try {
            String response = restClient.get()
                    .uri(uriBuilder -> uriBuilder
                            .queryParam("function", "SYMBOL_SEARCH")
                            .queryParam("keywords", ticker)
                            .queryParam("apikey", apiKey)
                            .build())
                    .retrieve()
                    .bodyToMono(String.class)
                    .block();

            JSONObject jsonObject = new JSONObject(response);
            JSONArray bestMatches = jsonObject.getJSONArray("bestMatches");
            if (!bestMatches.isEmpty()) {
                return bestMatches.getJSONObject(0).getString("2. name");
            }
        } catch (WebClientResponseException ex) {
            throw new RuntimeException("Error retrieving stock name", ex);
        }
        return "Unknown Company";
    }

    /**
     * Gets the stock price from AlphaVantage.
     *
     * @param ticker
     * @return Stock price
     */
    public double getStockPrice(String ticker) {
        try {
            String response = restClient.get()
                    .uri(uriBuilder -> uriBuilder
                            .queryParam("function", "TIME_SERIES_INTRADAY")
                            .queryParam("symbol", ticker)
                            .queryParam("interval", "1min")
                            .queryParam("apikey", apiKey)
                            .build())
                    .retrieve()
                    .bodyToMono(String.class)
                    .block();

            JSONObject jsonObject = new JSONObject(response);
            if (!jsonObject.has("Time Series (1min)")) {
                throw new IllegalStateException("Stock not found");
            }

            JSONObject timeSeries = jsonObject.getJSONObject("Time Series (1min)");
            String lastRefreshed = jsonObject.getJSONObject("Meta Data").getString("3. Last Refreshed");
            String latestPrice = timeSeries.getJSONObject(lastRefreshed).getString("1. open");

            return Double.parseDouble(latestPrice);
        } catch (WebClientResponseException ex) {
            throw new IllegalStateException("Error retrieving stock price", ex);
        }
    }

}
