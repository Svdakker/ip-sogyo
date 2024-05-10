import React from 'react'
import ReactDOM from 'react-dom/client'
import './index.css'
import {App} from "./pages/App.tsx";
import {BrowserRouter} from "react-router-dom";
import { SimulationContextProvider } from './contexts/simulationRequestContext.tsx';

ReactDOM.createRoot(document.getElementById('root')!).render(
  <React.StrictMode>
      <SimulationContextProvider>
          <BrowserRouter>
              <App></App>
          </BrowserRouter>
      </SimulationContextProvider>
  </React.StrictMode>,
)
