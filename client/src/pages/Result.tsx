import Chart from "chart.js/auto";
import {CategoryScale} from "chart.js";
import {useEffect, useState} from "react";
import {BatchCultivation, isOutput, Output} from "../ResultTypes.tsx";
import {fetchResult} from "../services/api.tsx";
import classNames from "classnames";
import {ResultTable} from "../components/ResultTable.tsx";
import {BatchGraph} from "../components/BatchGraph.tsx";

Chart.register(CategoryScale);

export const Result = () => {
    const [results, setResults] = useState<Output | null>(null)

    async function retrieveResult() {
        return await fetchResult()
    }

    useEffect(() => {
        retrieveResult().then(result => {
            if (isOutput(result)) {
                setResults(result)
            }
        });
    }, []);

    const displayResults = (type: string) => {
        return results?.value.order?.map((operation, position) => {
            if (operation == "batch-cultivation") {
                const batch = results?.value.output![position!]
                return addResultComponents(type, batch);
            }
        })
    }

    return (
        <div className="relative h-screen w-screen bg-cover bg-center bg-cyan-950 flex justify-center">
            <div id="results-container" className={classNames("flex w-screen h-screen")}>
                <div className="flex flex-wrap justify-center">
                    <div id="table-container" className="w-screen flex flex-wrap justify-center">
                        {displayResults("table")}
                    </div>
                    <div id="model-container" className="w-screen h-3/4 flex flex-wrap justify-center">
                        {displayResults("graph")}
                    </div>
                </div>
            </div>
        </div>
    )

    function addResultComponents(type: string, batch: BatchCultivation) {
        if (type == "table") {
            return (
                <ResultTable duration={batch.duration} energyCosts={batch.costEstimation.energy}
                             energyUsed={batch.powerConsumption.operations}/>
            )
        } else {
            return (
                <BatchGraph data={batch.model}/>
            )
        }
    }
}