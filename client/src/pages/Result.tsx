import {useState} from "react";
import {runSimulation} from "../services/api.tsx";
import {Output, isOutput} from "../Types.tsx";

export const Result = () => {
    const [results, setResults] = useState<Output | null>(null)

    const fetchResult = async () => {
        const result = await runSimulation()
        if(isOutput(result)) {
            setResults(result)
        }
    }

    fetchResult()

    return (
        <>
            <div>Simulation completed!</div>
            <div id="resultContainer">{String(results?.duration)}</div>
        </>
    )
}