package io.github.torbs00.stock_valuation.service;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

/**
 * @author TorbS00 on 24.09.2024.
 * @project StockMaster.
 */
@Service
public class StockService {

    private final String api_key = "AKSCBHNYYL2WYKZ3";
    private final String base_url = "https://www.alphavantage.co/query";

    public double getStockPrice(String ticker) {
        try {
            String apiUrl = base_url + "?function=TIME_SERIES_INTRADAY&symbol=" + ticker
                    + "&interval=1min&apikey=" + api_key;

            CloseableHttpClient httpClient = HttpClients.createDefault();
            HttpGet request = new HttpGet(apiUrl);
            CloseableHttpResponse response = httpClient.execute(request);

            try {
                HttpEntity entity = response.getEntity();
                if (entity != null) {
                    String result = EntityUtils.toString(entity);
                    JSONObject jsonObject = new JSONObject(result);
                    // Example of parsing the JSON response to get the latest price
                    JSONObject timeSeries = jsonObject.getJSONObject("Time Series (1min)");
                    String lastRefreshed = jsonObject.getJSONObject("Meta Data").getString("3. Last Refreshed");
                    String latestPrice = timeSeries.getJSONObject(lastRefreshed).getString("1. open");
                    return Double.parseDouble(latestPrice);
                }
            } finally {
                response.close();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }
}
