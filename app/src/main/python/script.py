import pickle
import pandas as pd
import numpy as np
def load_predict(m_periods):
  with open('/content/drive/MyDrive/Model/arima.pkl', 'rb') as pkl:
    model_load = pickle.load(pkl)
  n_periods = m_periods
  fc = model_load.predict(n_periods=n_periods)
  #index_of_fc = np.arange(len(df_pakistan.Pakistan), len(df_pakistan.Pakistan)+n_periods)

  # make series for plotting purpose

  #fc_series = pd.Series(fc, index=index_of_fc)
  #lower_series = pd.Series(confint[:, 0], index=index_of_fc)
  #upper_series = pd.Series(confint[:, 1], index=index_of_fc)
  return fc