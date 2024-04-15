import React from 'react'
import {runSimulation} from "../services/api.tsx";

export const App = () : React.JSX.Element =>  {
    return (
        <div className="App">
            <h1>Welcome?</h1>
            <button onClick={runSimulation}>Run simulation!</button>
        </div>
    )
}
