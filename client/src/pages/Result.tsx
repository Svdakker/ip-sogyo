import Chart from "chart.js/auto";
import {CategoryScale} from "chart.js";
import {useEffect, useState} from "react";
import {isOutput, Output} from "../ResultTypes.tsx";
import {fetchResult} from "../services/api.tsx";
import {ResultTable} from "../components/ResultTable.tsx";
import {BatchGraph} from "../components/BatchGraph.tsx";

Chart.register(CategoryScale);

export const Result = () => {
    const [results, setResults] = useState<Output | null>(null)

    const [graph, setGraph] = useState(-1)

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

    const displayGraphs = (index: number) => {
        if (index != -1) {
            const batch = results?.value.output![index]
            return (
                <BatchGraph position={index + 1} key={index} model={batch?.model}/>
            )
        }
    }

    const createTable = () => {
        if (results?.value.output) {
            return (
                <ResultTable switchGraph={setGraph} data={results?.value.output}></ResultTable>
            )
        }
    }

    return (
        <div className="relative h-screen w-screen flex justify-center">
                <div className="flex flex-wrap justify-center">
                    <div id="table-container" className="w-screen flex flex-wrap justify-center">
                        {createTable()}
                    </div>
                    <div id="model-container" className="w-screen h-3/4 flex flex-wrap justify-center">
                        {displayGraphs(graph)}
                    </div>
                </div>
        </div>
    )
}