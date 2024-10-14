# Device Price Prediction API

This project is a Java Spring Boot application that integrates with a Python-based FastAPI service for predicting the price category of devices based on their specifications. It also includes Jupyter Notebook experiments for testing various machine learning models for classification.

## Table of Contents
- [Overview](#overview)
- [Features](#features)
- [Requirements](#requirements)
- [Setup and Installation](#setup-and-installation)
- [Usage](#usage)
- [API Endpoints](#api-endpoints)

---

## Overview:
This application provides an API for managing devices and predicting their price categories based on technical specifications. The price prediction is achieved through a Python-based FastAPI microservice, which communicates with a machine learning model trained in Jupyter Notebook.

## Features:
- CRUD operations for managing device specifications.
- Integration with a FastAPI service to classify devices into price categories.
- Jupyter Notebook experiments to train and evaluate various ML models for classification.

## Requirements:
### Software:
- **Java Development Kit (JDK)** 11 or higher
- **Maven** (for dependency management)
- **Python 3.7+** (for running the FastAPI service and Jupyter Notebook)
- **FastAPI** and **Uvicorn** (for the prediction service)
- **Jupyter Notebook** (for running the notebook experiments)

### Python Libraries:
- **FastAPI**: `pip install fastapi`
- **Uvicorn**: `pip install uvicorn`
- **Scikit-Learn**: `pip install scikit-learn`
- **Joblib**: `pip install joblib`

## Setup and Installation:

### Step 1: Set Up the FastAPI Prediction Service

1. Install the required Python libraries:
   ```bash
   pip install fastapi uvicorn scikit-learn joblib
2. put the model pipeline in the same directory with main.py file
3. Run the FastAPI service:
   ```bash
   uvicorn main:app --reload
The service should now be running at http://127.0.0.1:8000/predict_price.

### Step 2: Set Up the Spring Boot Application
1. Clone the repository and navigate to the project directory.
2. Compile and run the application using Maven:
   ```bash
   ./mvnw spring-boot:run
3- The Spring Boot application should now be running at http://localhost:8080.

### Step 3: Access the H2 Database (Optional)
The application uses the H2 in-memory database for simplicity. You can access the H2 console at http://localhost:8080/h2-console with:
1. JDBC URL: jdbc:h2:mem:testdb
2. Username: sa
3. Password: (leave blank)

## Usage:

1. Start the FastAPI and Spring Boot applications.
2. Use any API client (e.g., Postman, curl) to test the endpoints.
3. You can also explore the device classification models by opening and running the classification_experiments.ipynb notebook.

## API Endpoints:

Device Management Endpoints:
1. Retrieve All Devices:
   GET /api/devices/
   Retrieves a list of all devices.

2. Retrieve Device by ID:
   GET /api/devices/{id}
   Retrieves details of a specific device by its ID.

3. Add a New Device:
   POST /api/devices/
   Adds a new device to the database.
   Request Body:
   {
  "batteryPower": 1043,
  "blue": 1,
  "clockSpeed": 1.8,
  "dualSim": 1,
  "fc": 14,
  "fourG": 0,
  "intMemory": 5,
  "mDep": 0.1,
  "mobileWt": 193,
  "nCores": 4,
  "pc": 16,
  "pxHeight": 226,
  "pxWidth": 1412,
  "ram": 3476,
  "scH": 12,
  "scW": 7,
  "talkTime": 2,
  "threeG": 0,
  "touchScreen": 1,
  "wifi": 0
}

4. Predict Price for a Device:
   POST /api/predict/{deviceId}
   Calls the FastAPI service to predict the price category for a device with a given deviceId and updates the predictedPriceCategory field in the database.

