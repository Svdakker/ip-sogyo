import {UnitOperation} from "../components/Unit-Operations.tsx";
import batch from "/src/assets/batch-cultivation.jpg"
import {runSimulation} from "../services/api.tsx";
import {BatchGraph} from "../components/BatchGraph.tsx";
import {useState} from "react";
import {isOutput, Output} from "../Types.tsx";
import Chart from "chart.js/auto";
import {CategoryScale} from "chart.js";

Chart.register(CategoryScale);

export const Simulation = () => {
    const [results, setResults] = useState<Output | null>(null)

    const batchRequirements = ["batch-cultivation", "accuracy", "initialSugarConcentration", "initialCellDensity", "maxGrowthRate", "maintenance", "yield"]

    const givenInput = () => { return {
        operationType: document.getElementById(batchRequirements[0])?.innerText.toLowerCase(),
        accuracy: (document.getElementById(batchRequirements[1]) as HTMLInputElement).value,
        initialSugarConcentration: (document.getElementById(batchRequirements[2]) as HTMLInputElement).value,
        initialCellDensity: (document.getElementById(batchRequirements[3]) as HTMLInputElement).value,
        maxGrowthRate: (document.getElementById(batchRequirements[4]) as HTMLInputElement).value,
        maintenance: (document.getElementById(batchRequirements[5]) as HTMLInputElement).value,
        yield: (document.getElementById(batchRequirements[6]) as HTMLInputElement).value,
    } }

    const fetchResult = async () => {
        const input = givenInput()
        const result = await runSimulation(input)
        if(isOutput(result)) {
            setResults(result)
        }
    }

    return (
        <>
            <UnitOperation requiredInput={batchRequirements} icon={batch}/>
            <div id="results-container">
                <button onClick={fetchResult}>Run simulation!</button>
                <div id="duration">{String(results?.duration)}</div>
                <BatchGraph data={results?.model}/>
            </div>
        </>
    )
}