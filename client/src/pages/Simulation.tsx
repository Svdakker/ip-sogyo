import {BatchCultivation} from "../components/Unit-Operations.tsx";
import batch from "/src/assets/batch-reactor.png"
import {runSimulation} from "../services/api.tsx";
import {isSaved} from "../Types.tsx";
import Chart from "chart.js/auto";
import {CategoryScale} from "chart.js";
import classNames from "classnames";
import {useNavigate} from "react-router-dom";

Chart.register(CategoryScale);

export const Simulation = () => {
    const navigate = useNavigate()

    const batchRequirements = ["batch-cultivation", "microorganism", "accuracy", "initialSugarConcentration", "initialCellDensity", "maxGrowthRate", "maintenance", "yield"]

    const reactorRequirements = ["nominalVolume", "workingVolume", "height", "width", "impellerType", "numberOfImpellers", "agitatorSpeed"]

    const givenInput = () => { return {
        operationType: document.getElementById(batchRequirements[0])?.innerText.toLowerCase(),
        cultivationSettings: findCultivationInput(),
        reactorSettings: findReactorInput()
    } }

    const findCultivationInput = () => {
        return {
            microorganism: (document.getElementById(batchRequirements[1]) as HTMLInputElement).value,
            accuracy: (document.getElementById(batchRequirements[2]) as HTMLInputElement).value,
            initialSugarConcentration: (document.getElementById(batchRequirements[3]) as HTMLInputElement).value,
            initialCellDensity: (document.getElementById(batchRequirements[4]) as HTMLInputElement).value,
            maxGrowthRate: (document.getElementById(batchRequirements[5]) as HTMLInputElement).value,
            maintenance: (document.getElementById(batchRequirements[6]) as HTMLInputElement).value,
            yield: (document.getElementById(batchRequirements[7]) as HTMLInputElement).value,
        }
    }

    const findReactorInput = () => {
        return {
            reactorType: "example",
            nominalVolume: (document.getElementById(reactorRequirements[0]) as HTMLInputElement).value,
            workingVolume: (document.getElementById(reactorRequirements[1]) as HTMLInputElement).value,
            height: (document.getElementById(reactorRequirements[2]) as HTMLInputElement).value,
            width: (document.getElementById(reactorRequirements[3]) as HTMLInputElement).value,
            impellerType: (document.getElementById(reactorRequirements[4]) as HTMLInputElement).value.toLowerCase(),
            numberOfImpellers: (document.getElementById(reactorRequirements[5]) as HTMLInputElement).value,
            agitatorSpeed: (document.getElementById(reactorRequirements[6]) as HTMLInputElement).value,
        }
    }

    const requestSimulation = async () => {
        const operation = givenInput()
        const input = {
            order: [operation.operationType!],
            batchCultivation: operation
        }
        const result = await runSimulation(input)
        if(isSaved(result)) {
            navigate("/result")
        }
    }

    return (
        <div className="relative h-screen w-screen bg-cover bg-center bg-cyan-950 flex justify-center">
            <div id="config-container" className={classNames("block")}>
                <BatchCultivation onClick={requestSimulation} icon={batch}/>
            </div>
        </div>
    )
}