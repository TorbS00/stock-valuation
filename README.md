# Stock Portfolio Valuation Website

## Overview

This project is a **Java-based Stock Portfolio Valuation Website** built using the **Spring Boot** framework. The application allows users to manage their stock portfolios, retrieve real-time stock prices via the **Alpha Vantage API**, and perform portfolio valuations using techniques such as Monte Carlo simulation, Discounted Cash Flow (DCF), and Sharpe Ratio calculation.

## Devlogs
Devlogs can be found in the Wiki section.
https://github.com/TorbS00/stock-valuation/wiki/Devlogs

## Features

- **User Management**: Users can create portfolios by simply entering a username (no password required).
- **Stock Transactions**: Users can "fake buy" and "fake sell" stocks in their portfolio, tracking purchase prices and the number of shares.
- **Real-time Stock Prices**: Real-time stock prices are fetched from the **Alpha Vantage API**.
- **Valuation Techniques**: Planned features include Monte Carlo simulations, DCF, and Sharpe Ratio calculations for portfolio valuation.
- **REST API**: REST endpoints are provided to interact with the portfolio and stock data.
- **Database Integration**: Data is stored in an H2 in-memory database (for testing) and will be switched to MySQL or PostgreSQL for production use.

## Technologies Used

- **Java 21**
- **Spring Boot**
- **Alpha Vantage API** for real-time stock data
- **H2 Database** (for testing)
- **MySQL/PostgreSQL** (for production use)
- **Thymeleaf** (for future UI integration)
- **Maven** (for project build and dependency management)