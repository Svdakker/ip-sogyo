import {useSimulationRequest} from "../contexts/simulationRequestContext.tsx";
import {useEffect, useState} from "react";
import classNames from "classnames";
import {ArcherElement} from "react-archer";
import {CentrifugationRequest} from "../RequestTypes.tsx";
import {UnitOperation} from "./Unit-Operation.tsx";

export const Centrifugation = ({icon, position, constants}: UnitOperation) => {
    const { simulationRequest, setSimulationRequest } = useSimulationRequest()

    const [openSettings, setOpenSettings] = useState(false)

    const [showAdvanced, setShowAdvanced] = useState(false)

    const [saved, setSaved] = useState(false)

    const [ frequencyOfRotationInput, setFrequencyOfRotationInput ] = useState<number | undefined>(undefined)
    const [ liquidFlowRateInput, setLiquidFlowRateInput ] = useState<number | undefined>(undefined)
    const [ liquidVolumeInput, setLiquidVolumeInput ] = useState<number | undefined>(undefined)
    const [ centrifugeTypeInput, setCentrifugeTypeInput ] = useState<string | undefined>(undefined)
    const [ outerRadiusInput, setOuterRadiusInput ] = useState<number | undefined>(undefined)
    const [ innerRadiusInput, setInnerRadiusInput ] = useState<number | undefined>(undefined)
    const [ diskAngleInput, setDiskAngleInput ] = useState<number | undefined>(undefined)
    const [ numberOfDisksInput, setNumberOfDisksInput ] = useState<number | undefined>(undefined)
    const [ motorPowerInput, setMotorPowerInput ] = useState<number | undefined>(undefined)

    const [centrifugeOptions, setCentrifugeOptions] = useState([<></>])

    useEffect(() => {
        setCentrifugeTypeInput(constants?.value.centrifuges[constants?.value.centrifuges.length - 1]);
        if(constants) {
            setCentrifugeOptions(constants?.value.centrifuges.map(function(val, index) {
                return <option key={index}>{val}</option>
            }))
        }
    }, [constants])

    const saveOperationCentrifugation = () => {
        const operation = findRequestCentrifugation()
        if (operation != undefined) {
            updateRequestCentrifugation(operation);
            setOpenSettings(!openSettings)
            setSaved(!saved)
        } else {
            alert("Please fill in all required settings for the centrifugation!")
        }
    }

    const findRequestCentrifugation = (): CentrifugationRequest | undefined => {
        if (missingCentrifugationInput() == false) {
            return {
                operationType: "centrifugation",
                centrifugationSettings: {
                    frequencyOfRotation: frequencyOfRotationInput,
                    liquidFlowRate: liquidFlowRateInput,
                    liquidVolume: liquidVolumeInput
                },
                centrifugeProperties: {
                    centrifugeType: centrifugeTypeInput,
                    outerRadius: outerRadiusInput,
                    innerRadius: innerRadiusInput,
                    diskAngle: diskAngleInput,
                    numberOfDisks: numberOfDisksInput,
                    motorPower: motorPowerInput
                }
            }
        }
    }

    const missingCentrifugationInput = (): boolean => {
        return ( centrifugeTypeInput == undefined || frequencyOfRotationInput == undefined || liquidFlowRateInput == undefined)
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
                {openSettings && centrifugationForm()}
            </div>
        </ArcherElement>
    )

    function checkDisabled(): boolean {
        return position > 0;
    }

    function centrifugationForm() {
        return (
            <form onSubmit={(e) => e.preventDefault()}
                  className="absolute top-2 right-1/5 p-4 rounded-xl bg-gray-900 shadow-2xl">
                <div>
                    <label className={classNames("block mb-2 text-md font-black text-white")}>CENTRIFUGATION</label>
                </div>
                <div className="grid gap-4 mb-4 md:grid-cols-2">
                    <div>
                        <label className={labelStyling}>Rotation frequency:</label>
                        <input className={inputStyling} id="frequency" type="number" step="any" min="0.001"
                               placeholder={"Frequency (rpm)"} required
                               onChange={(e) => setFrequencyOfRotationInput(Number(e.target.value))}/>
                    </div>
                    <div>
                        <label className={labelStyling}>Flow rate:</label>
                        <input className={inputStyling} id="flow" type="number" step="any" min="0.001"
                               placeholder={"Liquid flow rate (m3/h)"} required
                               onChange={(e) => setLiquidFlowRateInput(Number(e.target.value))}/>
                    </div>
                    {!checkDisabled() && liquidVolume()}
                    <div>
                        <label className={labelStyling}>Centrifuge:</label>
                        <select onChange={(e) => {
                            setCentrifugeTypeInput(e.target.value)
                        }}
                                className={inputStyling} id="centrifuge">
                            {centrifugeOptions}
                        </select>
                    </div>
                    {showAdvanced && advancedCentrifuge()}
                    <div className="flex items-center">
                        <button onClick={() => setShowAdvanced(!showAdvanced)}
                                className={classNames("text-left text-xs italic text-white font-bold")}>
                            Advanced centrifugation settings
                        </button>
                    </div>
                </div>
                <button onClick={saveOperationCentrifugation} className={classNames(
                    "bg-cyan-800 ring-4 ring-opacity-25 shadow-2xl",
                    "ring-cyan-700 rounded-full p-3 text-center",
                    "text-sm text-white font-black")}>
                    SAVE
                </button>
                <button onClick={() => setOpenSettings(!openSettings)}
                        className={classNames("text-center text-xs text-white font-black mx-4")}>
                    Cancel
                </button>
            </form>
        )
    }

    function updateRequestCentrifugation(operation: CentrifugationRequest) {
        if (simulationRequest?.order != undefined) {
            let updatedRequest = simulationRequest
            updatedRequest.order!.push("centrifugation")
            updatedRequest.centrifugation!.push(operation)
            setSimulationRequest(updatedRequest)
        } else {
            alert("Centrifugation is not supported as first operation in the cascade")
        }
    }

    function liquidVolume() {
        return (
            <div>
                <label className={labelStyling}>Liquid volume:</label>
                <input className={inputStyling} id="volume" type="number" step="any" min="0"
                       placeholder={"kg/m3"}
                       onChange={(e) => setLiquidVolumeInput(Number(e.target.value))}/>
            </div>
        )
    }

    function advancedCentrifuge() {
        return (
            <>
                <div>
                    <label className={labelStyling}>Outer radius:</label>
                    <input className={inputStyling} id="outerRadius" type="number"
                           placeholder={"Outer radius (m)"}
                           onChange={(e) => setOuterRadiusInput(Number(e.target.value))}/>
                </div>
                <div>
                    <label className={labelStyling}>Inner radius:</label>
                    <input className={inputStyling} id="innerRadius" type="number" step="any" min="0"
                           placeholder={"Inner radius (m)"}
                           onChange={(e) => setInnerRadiusInput(Number(e.target.value))}/>
                </div>
                <div>
                    <label className={labelStyling}>Number of disks:</label>
                    <input className={inputStyling} id="disks" type="number" step="1" min="0"
                           placeholder={"Number of disks"}
                           onChange={(e) => setNumberOfDisksInput(Number(e.target.value))}/>
                </div>
                <div>
                    <label className={labelStyling}>Disk angle:</label>
                    <input className={inputStyling} id="angle" type="number" step="any" min="0"
                           placeholder={"Disk angle (deg)"}
                           onChange={(e) => setDiskAngleInput(Number(e.target.value))}/>
                </div>
                <div>
                    <label className={labelStyling}>Motor power:</label>
                    <input className={inputStyling} id="power" type="number" step="any" min="0"
                           placeholder={"Motor power (kW)"}
                           onChange={(e) => setMotorPowerInput(Number(e.target.value))}/>
                </div>
            </>
        )
    }
}