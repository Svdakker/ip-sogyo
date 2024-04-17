import {useState} from "react";
import {runSimulation} from "../services/api.tsx";
import {Output, isOutput} from "../Types.tsx";
import {BatchGraph} from "../components/BatchGraph";
import Chart from "chart.js/auto";
import { CategoryScale } from "chart.js";

Chart.register(CategoryScale);

export const Simulation = () => {
    const [results, setResults] = useState<Output | null>(null)

    const fetchResult = async () => {
        const result = await runSimulation()
        if(isOutput(result)) {
            setResults(result)
        }
    }

    return (
        <>
            <button onClick={fetchResult}>Run!</button>
            <div id="duration">{String(results?.duration)}</div>
            <BatchGraph data={results?.model}/>
        </>
    )
}