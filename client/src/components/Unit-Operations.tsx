import classNames from "classnames";
import {CultivationSettings} from "./CultivationSettings.tsx";
import {ReactorSettings} from "./ReactorSettings.tsx";
import {useEffect, useState} from "react";
import {fetchConstants} from "../services/api.tsx";
import {Constants, isConstants} from "../ResultTypes.tsx";
import {useSimulationRequest} from "../contexts/simulationRequestContext.tsx";
import {BatchCultivationRequest, UpdateCultivationSettings, UpdateReactorSettings,} from "../RequestTypes.tsx";

interface UnitOperation {
    icon: string
}

export const BatchCultivation = ({ icon }: UnitOperation) => {
    const { simulationRequest, setSimulationRequest } = useSimulationRequest()

    const [ constants, setConstants] = useState<Constants | undefined>(undefined)

    const[microorganismInput, setMicroorganismInput] = useState<string | undefined>(undefined)
    const[accuracyInput, setAccuracyInput] = useState<number | undefined>(undefined)
    const[initialSugarConcentrationInput, setInitialSugarConcentrationInput] = useState<number | undefined>(undefined)
    const[initialCellDensityInput, setInitialCellDensityInput] = useState<number | undefined>(undefined)
    const[maxGrowthRateInput, setMaxGrowthRateInput] = useState<number | undefined>(undefined)
    const[maintenanceInput, setMaintenanceInput] = useState<number | undefined>(undefined)
    const[yieldInput, setYieldInput] = useState<number | undefined>(undefined)

    const[reactorTypeInput, setReactorTypeInput] = useState<string | undefined>(undefined)
    const[nominalVolumeInput, setNominalVolumeInput] = useState<number | undefined>(undefined)
    const[workingVolumeInput, setWorkingVolumeInput] = useState<number | undefined>(undefined)
    const[heightInput, setHeightInput] = useState<number | undefined>(undefined)
    const[widthInput, setWidthInput] = useState<number | undefined>(undefined)
    const[impellerTypeInput, setImpellerTypeInput] = useState<string | undefined>(undefined)
    const[numberOfImpellersInput, setNumberOfImpellersInput] = useState<number | undefined>(undefined)
    const[agitatorSpeedInput, setAgitatorSpeedInput] = useState<number | undefined>(undefined)

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

    const toggleSettings = () => {
        if (document.getElementById("settings")!.style.display == "block") {
            document.getElementById("settings")!.style.display = "none"
        } else {
            document.getElementById("settings")!.style.display = "block"
        }
    }

    const saveOperation = () => {
        const operation = findRequest()
        if (operation != undefined) {
            updateRequest(operation);
            toggleSettings()
        } else {
            alert("Please fill in all required settings for the batch-cultivation!")
        }
    }

    const findRequest = (): BatchCultivationRequest | undefined => {
        if (missingCultivationSettings() == false && missingReactorSettings() == false) {
            return {
                operationType: "batch-cultivation",
                cultivationSettings: {
                    microorganism: microorganismInput,
                    accuracy: accuracyInput,
                    initialSugarConcentration: initialSugarConcentrationInput,
                    initialCellDensity: initialCellDensityInput,
                    maxGrowthRate: maxGrowthRateInput,
                    maintenance: maintenanceInput,
                    yield: yieldInput
                },
                reactorSettings: {
                    reactorType: reactorTypeInput,
                    nominalVolume: nominalVolumeInput,
                    workingVolume: workingVolumeInput,
                    height: heightInput,
                    width: widthInput,
                    impellerType: impellerTypeInput,
                    numberOfImpellers: numberOfImpellersInput,
                    agitatorSpeed: agitatorSpeedInput
                }
            }
        }
    }

    const missingCultivationSettings = (): Boolean => {
        return ( microorganismInput == undefined || accuracyInput == undefined || initialSugarConcentrationInput == undefined
            || initialCellDensityInput == undefined )
    }

    const missingReactorSettings = (): Boolean => {
        return ( reactorTypeInput == undefined || impellerTypeInput == undefined || numberOfImpellersInput == undefined ||
            agitatorSpeedInput == undefined )
    }

    const labelStyling = classNames("block mb-2 text-sm font-medium text-white")

    const inputStyling = classNames("text-sm rounded-lg block w-full border",
                                    "p-2.5 bg-gray-700 border-gray-600 placeholder-gray-400 text-white")

    return (
        <>
            <img className="absolute w-1/6 h-1/4 top-1/2 left-1/2 -translate-x-1/2 -translate-y-1/2 overflow-hidden" onClick={toggleSettings} src={icon} alt={"image not found"}/>
            <form onSubmit={(e) => e.preventDefault()} id="settings" className="absolute top-1/4 right-1/5 overflow-visible hidden p-4 rounded-xl bg-gray-900 shadow-2xl">
                <div>
                    <label className={classNames("block mb-2 text-md font-black text-white")} id="batch-cultivation">BATCH-CULTIVATION</label>
                </div>
                <CultivationSettings labelStyling={labelStyling} inputStyling={inputStyling} constants={constants?.value} stateUpdaters={findStateUpdaters("cultivation")}/>
                <ReactorSettings labelStyling={labelStyling} inputStyling={inputStyling} constants={constants?.value} stateUpdaters={findStateUpdaters("reactor")}/>
                <button onClick={saveOperation} className={classNames(
                    "bg-cyan-800 ring-4 ring-opacity-25 shadow-2xl",
                    "ring-cyan-700 rounded-full p-3 text-center",
                    "text-sm text-white font-black"
                )}>SAVE
                </button>
            </form>
        </>
    )

    function updateRequest(operation: BatchCultivationRequest) {
        if (simulationRequest?.order != undefined) {
            let updatedRequest = simulationRequest
            updatedRequest.order!.push("batch-cultivation")
            updatedRequest.batchCultivation!.push(operation)
            setSimulationRequest(updatedRequest)
        } else {
            setSimulationRequest({
                order: ["batch-cultivation"],
                batchCultivation: [operation],
            })
        }
    }

    function findStateUpdaters(type: string): UpdateCultivationSettings | UpdateReactorSettings {
        if (type == "cultivation") {
            return {
                updateMicroorganism: setMicroorganismInput,
                updateAccuracy: setAccuracyInput,
                updateInitialSugarConcentration: setInitialSugarConcentrationInput,
                updateInitialCellDensity: setInitialCellDensityInput,
                updateMaxGrowthRate: setMaxGrowthRateInput,
                updateMaintenance: setMaintenanceInput,
                updateYield: setYieldInput,
            } as UpdateCultivationSettings
        } else {
            return {
                updateReactorType: setReactorTypeInput,
                updateNominalVolume: setNominalVolumeInput,
                updateWorkingVolume: setWorkingVolumeInput,
                updateHeight: setHeightInput,
                updateWidth: setWidthInput,
                updateImpellerType: setImpellerTypeInput,
                updateNumberOfImpellers: setNumberOfImpellersInput,
                updateAgitatorSpeed: setAgitatorSpeedInput,
            } as UpdateReactorSettings
        }
    }
}