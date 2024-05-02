import {BatchCultivation} from "../components/Unit-Operations.tsx";
import batch from "/src/assets/batch-reactor.png"
import {runSimulation} from "../services/api.tsx";
import {isSaved} from "../ResultTypes.tsx";
import Chart from "chart.js/auto";
import {CategoryScale} from "chart.js";
import classNames from "classnames";
import {useNavigate} from "react-router-dom";
import {useSimulationRequest} from "../contexts/simulationRequestContext.tsx";

Chart.register(CategoryScale);

export const Simulation = () => {
    const { simulationRequest } = useSimulationRequest()

    const navigate = useNavigate()

    const requestSimulation = async () => {
        const input = findInput()
        if (input) {
            const result = await runSimulation(input)
            if (isSaved(result)) {
                navigate("/result")
            }
        }
    }

    return (
        <div className="relative h-screen w-screen bg-cover bg-center bg-cyan-950 flex flex-wrap justify-center">
            <div className="h-1/6 basis-full"></div>
            <div id="config-container" className={classNames("basis-full h-3/4")}>
                <BatchCultivation icon={batch}/>
            </div>
            <button onClick={requestSimulation} className={classNames(
                "w-1/6 mt-3 mb-4 bg-cyan-800 ring-4 ring-opacity-25 shadow-2xl",
                "ring-cyan-700 rounded-full p-3 text-center",
                "text-lg text-white font-black"
            )}>
                RUN SIMULATION!
            </button>
        </div>
    )

    function findInput() {
        if (simulationRequest != undefined) {
            return simulationRequest
        } else {
            alert("There is no simulation request to run")
        }
    }
}