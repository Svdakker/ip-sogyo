import {BatchCultivation} from "../components/Unit-Operations.tsx";
import batch from "/src/assets/batch-reactor.png"
import {runSimulation} from "../services/api.tsx";
import {BatchGraph} from "../components/BatchGraph.tsx";
import {ResultTable} from "../components/ResultTable.tsx";
import {useState} from "react";
import {isOutput, Output} from "../Types.tsx";
import Chart from "chart.js/auto";
import {CategoryScale} from "chart.js";
import classNames from "classnames";

Chart.register(CategoryScale);

export const Simulation = () => {
    const [results, setResults] = useState<Output | null>(null)

    const batchRequirements = ["batch-cultivation", "accuracy", "initialSugarConcentration", "initialCellDensity", "maxGrowthRate", "maintenance", "yield"]

    const reactorRequirements = ["nominalVolume", "workingVolume", "height", "width", "impellerType", "numberOfImpellers", "agitatorSpeed"]

    const givenInput = () => { return {
        operationType: document.getElementById(batchRequirements[0])?.innerText.toLowerCase(),
        cultivationSettings: findCultivationInput(),
        reactorSettings: findReactorInput()
    } }

    const findCultivationInput = () => {
        return {
            accuracy: (document.getElementById(batchRequirements[1]) as HTMLInputElement).value,
            initialSugarConcentration: (document.getElementById(batchRequirements[2]) as HTMLInputElement).value,
            initialCellDensity: (document.getElementById(batchRequirements[3]) as HTMLInputElement).value,
            maxGrowthRate: (document.getElementById(batchRequirements[4]) as HTMLInputElement).value,
            maintenance: (document.getElementById(batchRequirements[5]) as HTMLInputElement).value,
            yield: (document.getElementById(batchRequirements[6]) as HTMLInputElement).value,
        }
    }

    const findReactorInput = () => {
        return {
            nominalVolume: (document.getElementById(reactorRequirements[0]) as HTMLInputElement).value,
            workingVolume: (document.getElementById(reactorRequirements[1]) as HTMLInputElement).value,
            height: (document.getElementById(reactorRequirements[2]) as HTMLInputElement).value,
            width: (document.getElementById(reactorRequirements[3]) as HTMLInputElement).value,
            impellerType: (document.getElementById(reactorRequirements[4]) as HTMLInputElement).value.toLowerCase(),
            numberOfImpellers: (document.getElementById(reactorRequirements[5]) as HTMLInputElement).value,
            agitatorSpeed: (document.getElementById(reactorRequirements[6]) as HTMLInputElement).value,
        }
    }

    const fetchResult = async () => {
        const operation = givenInput()
        const input = {
            order: [operation.operationType!],
            batchCultivation: operation
        }
        const result = await runSimulation(input)
        if(isOutput(result)) {
            setResults(result)
            document.getElementById("results-container")!.style.display = 'flex'
            document.getElementById("config-container")!.style.display = 'none'
        }
    }

    return (
        <div className="relative h-screen w-screen bg-cover bg-center bg-cyan-950 flex justify-center">
            <div id="config-container" className={classNames(
                                                    "block",
                                                    )}>
                <BatchCultivation onClick={fetchResult} icon={batch}/>
            </div>
            <div id="results-container" className={classNames("hidden w-screen h-screen")}>
                <div className="flex flex-wrap justify-center">
                        <div id="table-container" className="w-screen flex flex-wrap justify-center">
                            <ResultTable duration={results?.duration} energyCosts={results?.costEstimation.energy}
                            energyUsed={results?.powerConsumption.operations}/>
                        </div>
                        <div id="model-container" className="w-screen h-3/4 flex flex-wrap justify-center">
                            <BatchGraph data={results?.model}/>
                        </div>
                </div>
            </div>
        </div>
    )
}