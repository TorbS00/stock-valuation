package io.github.torbs00.stock_valuation.service;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * @author TorbS00 on 24.09.2024.
 * @project stock-valuation.
 */
@Service
public class StockService {

    private final String apikey;
    private final String baseUrl;

    public StockService(@Value("${api_key}") String apikey, @Value("${base_url}") String baseUrl) {
        this.apikey = apikey;
        this.baseUrl = baseUrl;
    }

    public String getStockName(String ticker) {
        try {
            String apiUrl = baseUrl + "?function=SYMBOL_SEARCH&keywords=" + ticker
                    + "&apikey=" + apikey;

            CloseableHttpClient httpClient = HttpClients.createDefault();
            HttpGet request = new HttpGet(apiUrl);
            CloseableHttpResponse response = httpClient.execute(request);
            try {
                HttpEntity entity = response.getEntity();
                if (entity != null) {
                    String result = EntityUtils.toString(entity);
                    JSONObject jsonObject = new JSONObject(result);
                    JSONArray bestMatches = jsonObject.getJSONArray("bestMatches");
                    if (!bestMatches.isEmpty()) {
                        JSONObject match = bestMatches.getJSONObject(0);
                        return match.getString("2. name");
                    }
                }
            } finally {
                response.close();
            }
        } catch (Exception ex) {
            throw new RuntimeException("Could not send HTTP request to Alpha Venture", ex);
        }
        return "Unknown Company";
    }
    public double getStockPrice(String ticker) {
        try {
            String apiUrl = baseUrl + "?function=TIME_SERIES_INTRADAY&symbol=" + ticker
                    + "&interval=1min&apikey=" + apikey;

            CloseableHttpClient httpClient = HttpClients.createDefault();
            HttpGet request = new HttpGet(apiUrl);
            CloseableHttpResponse response = httpClient.execute(request);
            try {
                HttpEntity entity = response.getEntity();
                if (entity != null) {
                    String result = EntityUtils.toString(entity);
                    JSONObject jsonObject = new JSONObject(result);

                    if (!jsonObject.has("Time Series (1min)")) {
                        throw new IllegalStateException("Stock not found");
                    }

                    JSONObject timeSeries = jsonObject.getJSONObject("Time Series (1min)");
                    String lastRefreshed = jsonObject.getJSONObject("Meta Data").getString("3. Last Refreshed");
                    String latestPrice = timeSeries.getJSONObject(lastRefreshed).getString("1. open");
                    return Double.parseDouble(latestPrice);
                }
            } finally {
                response.close();
            }
        } catch (Exception ex) {
            throw new IllegalStateException("Stock not found", ex);
        }
        return 0;
    }
}
