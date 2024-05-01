import classNames from "classnames";
import {CultivationSettings} from "./CultivationSettings.tsx";
import {ReactorSettings} from "./ReactorSettings.tsx";
import {useEffect, useState} from "react";
import {fetchConstants} from "../services/api.tsx";
import {Constants, isConstants} from "../Types.tsx";
import {useSimulationRequest} from "../contexts/simulationRequestContext.tsx";
import {BatchCultivationRequest, CultivationSettingsRequest, ReactorSettingsRequest} from "../RequestTypes.tsx";

interface UnitOperation {
    icon: string
}

export const BatchCultivation = ({ icon }: UnitOperation) => {
    const { simulationRequest, setSimulationRequest } = useSimulationRequest()

    const [ batchRequest, setBatchRequest ] = useState<BatchCultivationRequest | undefined>(undefined)

    const [ constants, setConstants] = useState<Constants | undefined>(undefined)

    useEffect(() => {
        retrieveConstants().then(constants => {
            if(isConstants(constants)) {
                setConstants(constants)
            }
        })
    }, []);

    async function retrieveConstants() {
        return await fetchConstants()
    }

    const saveOperation = () => {
        if(batchRequest != undefined) {
            updateRequest();
        } else {
            alert("Please configure all required batch-operation settings!")
        }
    }

    const toggleSettings = () => {
        if (document.getElementById("settings")!.style.display == "block") {
            document.getElementById("settings")!.style.display = "none"
        } else {
            document.getElementById("settings")!.style.display = "block"
        }
    }

    const labelStyling = classNames("block mb-2 text-sm font-medium text-white")

    const inputStyling = classNames("text-sm rounded-lg block w-full border",
                                    "p-2.5 bg-gray-700 border-gray-600 placeholder-gray-400 text-white")

    return (
        <>
            <img className="absolute w-1/6 h-1/4 top-1/2 left-1/2 -translate-x-1/2 -translate-y-1/2 overflow-hidden" onClick={toggleSettings} src={icon} alt={"image not found"}/>
            <div id="settings" className="absolute top-1/4 right-1/5 overflow-visible hidden p-4 rounded-xl bg-gray-900 shadow-2xl">
                <div>
                    <label className={classNames("block mb-2 text-md font-black text-white")} id="batch-cultivation">BATCH-CULTIVATION</label>
                </div>
                <CultivationSettings update={updateSettings} labelStyling={labelStyling} inputStyling={inputStyling} constants={constants?.value}/>
                <ReactorSettings update={updateSettings} labelStyling={labelStyling} inputStyling={inputStyling} constants={constants?.value}/>
                <button onClick={saveOperation} className={classNames(
                    "bg-cyan-800 ring-4 ring-opacity-25 shadow-2xl",
                    "ring-cyan-700 rounded-full p-3 text-center",
                    "text-sm text-white font-black"
                )}>SAVE
                </button>
            </div>
        </>
    )

    function updateSettings(type: string, settings: CultivationSettingsRequest | ReactorSettingsRequest) {
        let updatedBatchRequest = checkForRequest()
        if (type == "cultivation") {
            updatedBatchRequest.cultivationSettings = settings as CultivationSettingsRequest
        } else {
            updatedBatchRequest.reactorSettings = settings as ReactorSettingsRequest
        }
        setBatchRequest(updatedBatchRequest)
    }

    function checkForRequest(): BatchCultivationRequest {
        if (batchRequest != undefined) {
            return batchRequest
        } else {
            return {
                operationType: "batch-cultivation",
                cultivationSettings: undefined,
                reactorSettings: undefined
            }
        }
    }

    function updateRequest() {
        if (simulationRequest?.order != undefined) {
            let updatedRequest = simulationRequest
            updatedRequest.order!.push("batch-cultivation")
            updatedRequest.batchCultivation!.push(batchRequest!)
            setSimulationRequest(updatedRequest)
        } else {
            setSimulationRequest({
                order: [ "batch-cultivation" ],
                batchCultivation: [ batchRequest! ],
            })
        }
    }
}