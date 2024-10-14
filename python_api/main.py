from fastapi import FastAPI, HTTPException
from pydantic import BaseModel
import joblib
import numpy as np
from sklearn.preprocessing import StandardScaler
import tensorflow as tf

app = FastAPI()

# model = joblib.load("random_forest_model.joblib")
# scaler = StandardScaler()

pipeline = joblib.load("device_price_pipeline.joblib")

class Device(BaseModel):
    battery_power: int
    blue: int
    clock_speed: float
    dual_sim: int
    fc: float
    four_g: int
    int_memory: float
    m_dep: float
    mobile_wt: float
    n_cores: float
    pc: float
    px_height: float
    px_width: float
    ram: float
    sc_h: float
    sc_w: float
    talk_time: int
    three_g: int
    touch_screen: int
    wifi: int

@app.post("/predict_price")
async def predict_price(specs: Device):
    features = np.array([
        specs.battery_power, specs.blue, specs.clock_speed, specs.dual_sim,
        specs.fc, specs.four_g, specs.int_memory, specs.m_dep, specs.mobile_wt,
        specs.n_cores, specs.pc, specs.px_height, specs.px_width, specs.ram,
        specs.sc_h, specs.sc_w, specs.talk_time, specs.three_g,
        specs.touch_screen, specs.wifi
    ]).reshape(1, -1)

    prediction = pipeline.predict(features)

    price_categories = ["Low Cost", "Medium Cost", "High Cost", "Very High Cost"]
    predicted_price_category = price_categories[prediction[0]]

    return {"predicted_price_category": predicted_price_category}

# To run the app, use: uvicorn main:app --reload
# then open cmd and write the following:
# curl -X POST "http://127.0.0.1:8000/predict_price" -H "Content-Type: application/json" -d "{\"battery_power\": 1043, \"blue\": 1, \"clock_speed\": 1.8, \"dual_sim\": 1, \"fc\": 14, \"four_g\": 0, \"int_memory\": 5, \"m_dep\": 0.1, \"mobile_wt\": 193, \"n_cores\": 4, \"pc\": 16, \"px_height\": 226, \"px_width\": 1412, \"ram\": 3476, \"sc_h\": 12, \"sc_w\": 7, \"talk_time\": 2, \"three_g\": 0, \"touch_screen\": 1, \"wifi\": 0}"
# It must predict very high Cost
