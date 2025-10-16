// src/main/java/com/example/agent/StockTicker.java
package com.example.agent;

import com.google.adk.agents.LlmAgent;
import com.google.adk.tools.Annotations.Schema;
import com.google.adk.tools.FunctionTool;
import com.google.adk.web.AdkWebServer;
import java.util.Map;
import java.util.HashMap;
import java.util.Random;

public class StockTicker {
    // Comprehensive stock database with 100+ companies
    private static final Map<String, CompanyInfo> STOCK_DATABASE = new HashMap<>();

    static {
        // Technology Companies
        STOCK_DATABASE.put("AAPL", new CompanyInfo("Apple Inc.", "AAPL", "Technology"));
        STOCK_DATABASE.put("MSFT", new CompanyInfo("Microsoft Corporation", "MSFT", "Technology"));
        STOCK_DATABASE.put("GOOGL", new CompanyInfo("Alphabet Inc.", "GOOGL", "Technology"));
        STOCK_DATABASE.put("AMZN", new CompanyInfo("Amazon.com Inc.", "AMZN", "Technology"));
        STOCK_DATABASE.put("META", new CompanyInfo("Meta Platforms Inc.", "META", "Technology"));
        STOCK_DATABASE.put("NVDA", new CompanyInfo("NVIDIA Corporation", "NVDA", "Technology"));
        STOCK_DATABASE.put("TSLA", new CompanyInfo("Tesla Inc.", "TSLA", "Automotive/Technology"));
        STOCK_DATABASE.put("ORCL", new CompanyInfo("Oracle Corporation", "ORCL", "Technology"));
        STOCK_DATABASE.put("ADBE", new CompanyInfo("Adobe Inc.", "ADBE", "Technology"));
        STOCK_DATABASE.put("CRM", new CompanyInfo("Salesforce Inc.", "CRM", "Technology"));
        STOCK_DATABASE.put("INTC", new CompanyInfo("Intel Corporation", "INTC", "Technology"));
        STOCK_DATABASE.put("AMD", new CompanyInfo("Advanced Micro Devices", "AMD", "Technology"));
        STOCK_DATABASE.put("CSCO", new CompanyInfo("Cisco Systems Inc.", "CSCO", "Technology"));
        STOCK_DATABASE.put("IBM", new CompanyInfo("International Business Machines", "IBM", "Technology"));
        STOCK_DATABASE.put("NFLX", new CompanyInfo("Netflix Inc.", "NFLX", "Technology"));
        STOCK_DATABASE.put("UBER", new CompanyInfo("Uber Technologies Inc.", "UBER", "Technology"));
        STOCK_DATABASE.put("LYFT", new CompanyInfo("Lyft Inc.", "LYFT", "Technology"));
        STOCK_DATABASE.put("SNAP", new CompanyInfo("Snap Inc.", "SNAP", "Technology"));
        STOCK_DATABASE.put("TWTR", new CompanyInfo("Twitter Inc.", "TWTR", "Technology"));
        STOCK_DATABASE.put("SQ", new CompanyInfo("Block Inc.", "SQ", "Technology"));
        STOCK_DATABASE.put("SHOP", new CompanyInfo("Shopify Inc.", "SHOP", "Technology"));
        STOCK_DATABASE.put("SPOT", new CompanyInfo("Spotify Technology", "SPOT", "Technology"));
        STOCK_DATABASE.put("ZM", new CompanyInfo("Zoom Video Communications", "ZM", "Technology"));
        STOCK_DATABASE.put("DOCU", new CompanyInfo("DocuSign Inc.", "DOCU", "Technology"));
        STOCK_DATABASE.put("SNOW", new CompanyInfo("Snowflake Inc.", "SNOW", "Technology"));
        STOCK_DATABASE.put("PLTR", new CompanyInfo("Palantir Technologies", "PLTR", "Technology"));
        STOCK_DATABASE.put("COIN", new CompanyInfo("Coinbase Global Inc.", "COIN", "Technology"));

        // Financial Services
        STOCK_DATABASE.put("JPM", new CompanyInfo("JPMorgan Chase & Co.", "JPM", "Financial"));
        STOCK_DATABASE.put("BAC", new CompanyInfo("Bank of America Corp", "BAC", "Financial"));
        STOCK_DATABASE.put("WFC", new CompanyInfo("Wells Fargo & Company", "WFC", "Financial"));
        STOCK_DATABASE.put("GS", new CompanyInfo("Goldman Sachs Group", "GS", "Financial"));
        STOCK_DATABASE.put("MS", new CompanyInfo("Morgan Stanley", "MS", "Financial"));
        STOCK_DATABASE.put("C", new CompanyInfo("Citigroup Inc.", "C", "Financial"));
        STOCK_DATABASE.put("BLK", new CompanyInfo("BlackRock Inc.", "BLK", "Financial"));
        STOCK_DATABASE.put("SCHW", new CompanyInfo("Charles Schwab Corporation", "SCHW", "Financial"));
        STOCK_DATABASE.put("AXP", new CompanyInfo("American Express Company", "AXP", "Financial"));
        STOCK_DATABASE.put("V", new CompanyInfo("Visa Inc.", "V", "Financial"));
        STOCK_DATABASE.put("MA", new CompanyInfo("Mastercard Inc.", "MA", "Financial"));
        STOCK_DATABASE.put("PYPL", new CompanyInfo("PayPal Holdings Inc.", "PYPL", "Financial"));

        // Healthcare & Pharmaceuticals
        STOCK_DATABASE.put("JNJ", new CompanyInfo("Johnson & Johnson", "JNJ", "Healthcare"));
        STOCK_DATABASE.put("UNH", new CompanyInfo("UnitedHealth Group", "UNH", "Healthcare"));
        STOCK_DATABASE.put("PFE", new CompanyInfo("Pfizer Inc.", "PFE", "Pharmaceuticals"));
        STOCK_DATABASE.put("ABBV", new CompanyInfo("AbbVie Inc.", "ABBV", "Pharmaceuticals"));
        STOCK_DATABASE.put("TMO", new CompanyInfo("Thermo Fisher Scientific", "TMO", "Healthcare"));
        STOCK_DATABASE.put("MRK", new CompanyInfo("Merck & Co. Inc.", "MRK", "Pharmaceuticals"));
        STOCK_DATABASE.put("ABT", new CompanyInfo("Abbott Laboratories", "ABT", "Healthcare"));
        STOCK_DATABASE.put("DHR", new CompanyInfo("Danaher Corporation", "DHR", "Healthcare"));
        STOCK_DATABASE.put("LLY", new CompanyInfo("Eli Lilly and Company", "LLY", "Pharmaceuticals"));
        STOCK_DATABASE.put("BMY", new CompanyInfo("Bristol-Myers Squibb", "BMY", "Pharmaceuticals"));
        STOCK_DATABASE.put("AMGN", new CompanyInfo("Amgen Inc.", "AMGN", "Pharmaceuticals"));
        STOCK_DATABASE.put("GILD", new CompanyInfo("Gilead Sciences Inc.", "GILD", "Pharmaceuticals"));
        STOCK_DATABASE.put("CVS", new CompanyInfo("CVS Health Corporation", "CVS", "Healthcare"));

        // Consumer Goods
        STOCK_DATABASE.put("WMT", new CompanyInfo("Walmart Inc.", "WMT", "Retail"));
        STOCK_DATABASE.put("PG", new CompanyInfo("Procter & Gamble Co", "PG", "Consumer Goods"));
        STOCK_DATABASE.put("KO", new CompanyInfo("The Coca-Cola Company", "KO", "Beverages"));
        STOCK_DATABASE.put("PEP", new CompanyInfo("PepsiCo Inc.", "PEP", "Beverages"));
        STOCK_DATABASE.put("COST", new CompanyInfo("Costco Wholesale Corp", "COST", "Retail"));
        STOCK_DATABASE.put("NKE", new CompanyInfo("Nike Inc.", "NKE", "Consumer Goods"));
        STOCK_DATABASE.put("MCD", new CompanyInfo("McDonald's Corporation", "MCD", "Restaurants"));
        STOCK_DATABASE.put("SBUX", new CompanyInfo("Starbucks Corporation", "SBUX", "Restaurants"));
        STOCK_DATABASE.put("HD", new CompanyInfo("The Home Depot Inc.", "HD", "Retail"));
        STOCK_DATABASE.put("TGT", new CompanyInfo("Target Corporation", "TGT", "Retail"));

        // Energy & Industrial
        STOCK_DATABASE.put("XOM", new CompanyInfo("Exxon Mobil Corporation", "XOM", "Energy"));
        STOCK_DATABASE.put("CVX", new CompanyInfo("Chevron Corporation", "CVX", "Energy"));
        STOCK_DATABASE.put("COP", new CompanyInfo("ConocoPhillips", "COP", "Energy"));
        STOCK_DATABASE.put("SLB", new CompanyInfo("Schlumberger Limited", "SLB", "Energy"));
        STOCK_DATABASE.put("BA", new CompanyInfo("The Boeing Company", "BA", "Aerospace"));
        STOCK_DATABASE.put("CAT", new CompanyInfo("Caterpillar Inc.", "CAT", "Industrial"));
        STOCK_DATABASE.put("GE", new CompanyInfo("General Electric Company", "GE", "Industrial"));
        STOCK_DATABASE.put("MMM", new CompanyInfo("3M Company", "MMM", "Industrial"));
        STOCK_DATABASE.put("HON", new CompanyInfo("Honeywell International", "HON", "Industrial"));
        STOCK_DATABASE.put("UPS", new CompanyInfo("United Parcel Service", "UPS", "Logistics"));
        STOCK_DATABASE.put("FDX", new CompanyInfo("FedEx Corporation", "FDX", "Logistics"));

        // Telecommunications & Media
        STOCK_DATABASE.put("T", new CompanyInfo("AT&T Inc.", "T", "Telecommunications"));
        STOCK_DATABASE.put("VZ", new CompanyInfo("Verizon Communications", "VZ", "Telecommunications"));
        STOCK_DATABASE.put("TMUS", new CompanyInfo("T-Mobile US Inc.", "TMUS", "Telecommunications"));
        STOCK_DATABASE.put("DIS", new CompanyInfo("The Walt Disney Company", "DIS", "Media"));
        STOCK_DATABASE.put("CMCSA", new CompanyInfo("Comcast Corporation", "CMCSA", "Media"));

        // Automotive
        STOCK_DATABASE.put("F", new CompanyInfo("Ford Motor Company", "F", "Automotive"));
        STOCK_DATABASE.put("GM", new CompanyInfo("General Motors Company", "GM", "Automotive"));
        STOCK_DATABASE.put("TM", new CompanyInfo("Toyota Motor Corporation", "TM", "Automotive"));
        STOCK_DATABASE.put("HMC", new CompanyInfo("Honda Motor Co.", "HMC", "Automotive"));
        STOCK_DATABASE.put("RIVN", new CompanyInfo("Rivian Automotive Inc.", "RIVN", "Automotive"));

        // Semiconductors & Hardware
        STOCK_DATABASE.put("QCOM", new CompanyInfo("Qualcomm Inc.", "QCOM", "Semiconductors"));
        STOCK_DATABASE.put("TXN", new CompanyInfo("Texas Instruments Inc.", "TXN", "Semiconductors"));
        STOCK_DATABASE.put("AVGO", new CompanyInfo("Broadcom Inc.", "AVGO", "Semiconductors"));
        STOCK_DATABASE.put("MU", new CompanyInfo("Micron Technology Inc.", "MU", "Semiconductors"));
        STOCK_DATABASE.put("AMAT", new CompanyInfo("Applied Materials Inc.", "AMAT", "Semiconductors"));
        STOCK_DATABASE.put("LRCX", new CompanyInfo("Lam Research Corporation", "LRCX", "Semiconductors"));

        // Real Estate & Construction
        STOCK_DATABASE.put("AMT", new CompanyInfo("American Tower Corporation", "AMT", "Real Estate"));
        STOCK_DATABASE.put("PLD", new CompanyInfo("Prologis Inc.", "PLD", "Real Estate"));
        STOCK_DATABASE.put("CCI", new CompanyInfo("Crown Castle Inc.", "CCI", "Real Estate"));

        // Entertainment & Gaming
        STOCK_DATABASE.put("EA", new CompanyInfo("Electronic Arts Inc.", "EA", "Gaming"));
        STOCK_DATABASE.put("ATVI", new CompanyInfo("Activision Blizzard", "ATVI", "Gaming"));
        STOCK_DATABASE.put("TTWO", new CompanyInfo("Take-Two Interactive", "TTWO", "Gaming"));
        STOCK_DATABASE.put("RBLX", new CompanyInfo("Roblox Corporation", "RBLX", "Gaming"));

        // E-commerce & Retail
        STOCK_DATABASE.put("EBAY", new CompanyInfo("eBay Inc.", "EBAY", "E-commerce"));
        STOCK_DATABASE.put("ETSY", new CompanyInfo("Etsy Inc.", "ETSY", "E-commerce"));
        STOCK_DATABASE.put("BABA", new CompanyInfo("Alibaba Group Holding", "BABA", "E-commerce"));

        // Airlines & Travel
        STOCK_DATABASE.put("DAL", new CompanyInfo("Delta Air Lines Inc.", "DAL", "Airlines"));
        STOCK_DATABASE.put("UAL", new CompanyInfo("United Airlines Holdings", "UAL", "Airlines"));
        STOCK_DATABASE.put("AAL", new CompanyInfo("American Airlines Group", "AAL", "Airlines"));
        STOCK_DATABASE.put("LUV", new CompanyInfo("Southwest Airlines Co.", "LUV", "Airlines"));
        STOCK_DATABASE.put("ABNB", new CompanyInfo("Airbnb Inc.", "ABNB", "Travel"));
        STOCK_DATABASE.put("BKNG", new CompanyInfo("Booking Holdings Inc.", "BKNG", "Travel"));

        // Biotech
        STOCK_DATABASE.put("BIIB", new CompanyInfo("Biogen Inc.", "BIIB", "Biotechnology"));
        STOCK_DATABASE.put("REGN", new CompanyInfo("Regeneron Pharmaceuticals", "REGN", "Biotechnology"));
        STOCK_DATABASE.put("VRTX", new CompanyInfo("Vertex Pharmaceuticals", "VRTX", "Biotechnology"));
        STOCK_DATABASE.put("MRNA", new CompanyInfo("Moderna Inc.", "MRNA", "Biotechnology"));

        // Add company name lookups
        STOCK_DATABASE.put("APPLE", new CompanyInfo("Apple Inc.", "AAPL", "Technology"));
        STOCK_DATABASE.put("MICROSOFT", new CompanyInfo("Microsoft Corporation", "MSFT", "Technology"));
        STOCK_DATABASE.put("GOOGLE", new CompanyInfo("Alphabet Inc.", "GOOGL", "Technology"));
        STOCK_DATABASE.put("AMAZON", new CompanyInfo("Amazon.com Inc.", "AMZN", "Technology"));
        STOCK_DATABASE.put("FACEBOOK", new CompanyInfo("Meta Platforms Inc.", "META", "Technology"));
        STOCK_DATABASE.put("NVIDIA", new CompanyInfo("NVIDIA Corporation", "NVDA", "Technology"));
        STOCK_DATABASE.put("TESLA", new CompanyInfo("Tesla Inc.", "TSLA", "Automotive/Technology"));
        STOCK_DATABASE.put("NETFLIX", new CompanyInfo("Netflix Inc.", "NFLX", "Technology"));
        STOCK_DATABASE.put("WALMART", new CompanyInfo("Walmart Inc.", "WMT", "Retail"));
        STOCK_DATABASE.put("DISNEY", new CompanyInfo("The Walt Disney Company", "DIS", "Media"));
    }

    private static class CompanyInfo {
        String name;
        String ticker;
        String sector;

        CompanyInfo(String name, String ticker, String sector) {
            this.name = name;
            this.ticker = ticker;
            this.sector = sector;
        }
    }

    public static void main(String[] args) {
        AdkWebServer.start(
                LlmAgent.builder()
                        .name("stock_agent")
                        .instruction("""
                    You are a stock exchange ticker expert.
                    When asked about the stock price of a company,
                    use the `lookup_stock_ticker` tool to find the information.
                    """)
                        .model("gemini-2.5-flash")
                        .tools(FunctionTool.create(StockTicker.class, "lookupStockTicker"))
                        .build()
        );
    }

    @Schema(
            name = "lookup_stock_ticker",
            description = "Lookup stock price for a given company or ticker"
    )
    public static Map<String, String> lookupStockTicker(
            @Schema(name = "company_name_or_stock_ticker", description = "The company name or stock ticker")
            String ticker) {

        // Normalize input
        String normalizedTicker = ticker.toUpperCase().trim();

        // Try to find exact match first
        CompanyInfo companyInfo = STOCK_DATABASE.get(normalizedTicker);

        // If not found, try partial match on company names
        if (companyInfo == null) {
            for (Map.Entry<String, CompanyInfo> entry : STOCK_DATABASE.entrySet()) {
                if (entry.getValue().name.toUpperCase().contains(normalizedTicker) ||
                    normalizedTicker.contains(entry.getValue().name.toUpperCase())) {
                    companyInfo = entry.getValue();
                    break;
                }
            }
        }

        Map<String, String> result = new HashMap<>();

        if (companyInfo == null) {
            result.put("status", "error");
            result.put("message", "Company or ticker not found: " + ticker);
            result.put("suggestion", "Please check the ticker symbol or company name. Available tickers include: AAPL, MSFT, GOOGL, AMZN, META, NVDA, TSLA, etc.");
            return result;
        }

        // Generate a realistic stock price (for demonstration purposes)
        Random random = new Random(companyInfo.ticker.hashCode());
        double basePrice = 50 + random.nextDouble() * 450; // Between $50 and $500
        double changePercent = (random.nextDouble() - 0.5) * 10; // Between -5% and +5%
        double change = basePrice * (changePercent / 100);

        result.put("status", "success");
        result.put("company_name", companyInfo.name);
        result.put("ticker", companyInfo.ticker);
        result.put("sector", companyInfo.sector);
        result.put("price", String.format("$%.2f", basePrice));
        result.put("change", String.format("%.2f", change));
        result.put("change_percent", String.format("%.2f%%", changePercent));
        result.put("market_status", "Market Open");
        result.put("note", "Simulated data for demonstration purposes");

        return result;
    }
}
