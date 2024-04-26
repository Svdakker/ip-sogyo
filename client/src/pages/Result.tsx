import Chart from "chart.js/auto";
import {CategoryScale} from "chart.js";
import {useEffect, useState} from "react";
import {isOutput, Output} from "../Types.tsx";
import {fetchResult} from "../services/api.tsx";
import classNames from "classnames";
import {ResultTable} from "../components/ResultTable.tsx";
import {BatchGraph} from "../components/BatchGraph.tsx";

Chart.register(CategoryScale);

export const Result = () => {
    const [results, setResults] = useState<Output | null>(null)

    async function retrieveResult() {
        const result = await fetchResult()
        if(isOutput(result)) {
            setResults(result)
        }
    }

    useEffect(() => {
        retrieveResult();
    }, []);

    return (
        <div className="relative h-screen w-screen bg-cover bg-center bg-cyan-950 flex justify-center">
            <div id="results-container" className={classNames("flex w-screen h-screen")}>
                <div className="flex flex-wrap justify-center">
                    <div id="table-container" className="w-screen flex flex-wrap justify-center">
                        <ResultTable duration={results?.value.batchCultivation?.duration} energyCosts={results?.value.batchCultivation?.costEstimation.energy}
                                     energyUsed={results?.value.batchCultivation?.powerConsumption.operations}/>
                    </div>
                    <div id="model-container" className="w-screen h-3/4 flex flex-wrap justify-center">
                        <BatchGraph data={results?.value.batchCultivation?.model}/>
                    </div>
                </div>
            </div>
        </div>
    )
}