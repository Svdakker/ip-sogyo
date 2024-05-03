import {BatchCultivation} from "../components/Unit-Operations.tsx";
import batch from "/src/assets/batch-reactor.png"
import {runSimulation} from "../services/api.tsx";
import {isSaved} from "../ResultTypes.tsx";
import Chart from "chart.js/auto";
import {CategoryScale} from "chart.js";
import classNames from "classnames";
import {useNavigate} from "react-router-dom";
import {useSimulationRequest} from "../contexts/simulationRequestContext.tsx";
import {useState} from "react";
import {ArcherContainer} from "react-archer";

Chart.register(CategoryScale);

export const Simulation = () => {
    const { simulationRequest } = useSimulationRequest()

    const [operations, setOperations] = useState<number[]>([])

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

    const addBatchCultivation = () => {
        setOperations([...operations, operations.length])
    }

    return (
            <div className="relative h-screen w-screen flex flex-wrap justify-center">
                <div className="h-[10%] basis-full flex justify-center">
                    <button onClick={addBatchCultivation} className={classNames("text-center text-lg text-white font-black")}>+ Batch-cultivation</button>
                </div>
                <ArcherContainer key="config-container" startMarker={true} endMarker={false} strokeColor={"black"} offset={10}
                                 className={classNames("basis-full h-3/4 mx-4 bg-white border-8 border-cyan-800 rounded-md")}>
                    <div className={`flex flex-wrap justify-center gap-[10%] items-center size-full relative overflow-visible`}>
                        {operations.map((operation) => (<BatchCultivation key={operation} position={operation} icon={batch}/>))}
                    </div>
                </ArcherContainer>
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