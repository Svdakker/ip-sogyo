import classNames from "classnames";
import {CultivationSettings} from "./CultivationSettings.tsx";
import {ReactorSettings} from "./ReactorSettings.tsx";
import {useState} from "react";
import {useSimulationRequest} from "../contexts/simulationRequestContext.tsx";
import {BatchCultivationRequest, UpdateCultivationSettings, UpdateReactorSettings,} from "../RequestTypes.tsx";
import {ArcherElement} from "react-archer";
import {UnitOperation} from "./Unit-Operation.tsx";

export const BatchCultivation = ({ icon, position, constants }: UnitOperation) => {
    const { simulationRequest, setSimulationRequest } = useSimulationRequest()

    const [openSettings, setOpenSettings] = useState(false)

    const [saved, setSaved] = useState(false)

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

    const saveOperationBatch = () => {
        const operation = findRequestBatch()
        if (operation != undefined) {
            updateRequestBatch(operation);
            setOpenSettings(!openSettings)
            setSaved(!saved)
        } else {
            alert("Please fill in all required settings for the batch-cultivation!")
        }
    }

    const findRequestBatch = (): BatchCultivationRequest | undefined => {
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

    const missingCultivationSettings = (): boolean => {
        return ( microorganismInput == undefined || accuracyInput == undefined || initialSugarConcentrationInput == undefined
            || checkInitialCellDensity() )
    }

    const checkInitialCellDensity = (): boolean => {
        if (position > 0) {
            return false
        } else {
            return initialCellDensityInput == undefined
        }
    }

    const missingReactorSettings = (): boolean => {
        return ( reactorTypeInput == undefined || impellerTypeInput == undefined || numberOfImpellersInput == undefined ||
            agitatorSpeedInput == undefined )
    }

    const labelStyling = classNames("block mb-2 text-sm font-medium text-white")

    const inputStyling = classNames("text-sm rounded-lg block w-full border focus:ring-offset-2 focus:ring-2 focus:ring-blue-500 focus:border-blue-500",
                                    "p-2.5 bg-gray-700 border-gray-600 placeholder-gray-400 text-white")

    return (
        <ArcherElement id={String(position)} key={position} relations={position > 0 ? [
            {
                targetId: `${String(Number(position - 1))}`,
                targetAnchor: 'right',
                sourceAnchor: 'left'
            }] : []}>
            <div key={position} className={"flex justify-center h-1/3"}>
                <img className={`max-h-full max-w-sm ${saved ? 'opacity-25' : 'opacity-100'}`} onClick={() => {if (!saved) { setOpenSettings(!openSettings) }}} src={icon} alt={"image not found"}/>
                {openSettings && batchForm()}
            </div>
        </ArcherElement>
    )

    function batchForm() {
        return (
            <form onSubmit={(e) => e.preventDefault()}
                  className="absolute top-2 right-1/5 p-4 rounded-xl bg-gray-900 shadow-2xl">
                <div>
                    <label className={classNames("block mb-2 text-md font-black text-white")}>BATCH-CULTIVATION</label>
                </div>
                <CultivationSettings position={position} labelStyling={labelStyling} inputStyling={inputStyling}
                                     constants={constants?.value} stateUpdaters={findStateUpdaters("cultivation")}/>
                <ReactorSettings position={position} labelStyling={labelStyling} inputStyling={inputStyling}
                                 constants={constants?.value} stateUpdaters={findStateUpdaters("reactor")}/>
                <button onClick={saveOperationBatch} className={classNames(
                    "bg-cyan-800 ring-4 ring-opacity-25 shadow-2xl",
                    "ring-cyan-700 rounded-full p-3 text-center",
                    "text-sm text-white font-black")}>
                    SAVE
                </button>
                <button onClick={() => setOpenSettings(!openSettings)} className={classNames("text-center text-xs text-white font-black mx-4")}>
                    Cancel
                </button>
            </form>
        )
    }

    function updateRequestBatch(operation: BatchCultivationRequest) {
        if (simulationRequest?.order != undefined) {
            setSimulationRequest({
                order: [...simulationRequest.order, "batch-cultivation"],
                batchCultivation: function() {
                    if (simulationRequest.batchCultivation != undefined) {
                        return [...simulationRequest.batchCultivation, operation]
                    } else {
                        return [operation]
                    }
                }(),
                centrifugation: simulationRequest.centrifugation
            })
        } else {
            setSimulationRequest({
                order: ["batch-cultivation"],
                batchCultivation: [operation],
                centrifugation: []
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