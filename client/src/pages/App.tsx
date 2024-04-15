import React from 'react'
import {runSimulation} from "../services/api.tsx";

const simulationResult = await runSimulation()

export const App = () : React.JSX.Element =>  {
    return (
        <div className="App">
            <h1>Welcome?</h1>
            <div>{simulationResult}</div>
        </div>
    )
}
