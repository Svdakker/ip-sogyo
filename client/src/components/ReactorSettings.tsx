import {FormProps, UpdateReactorSettings} from "../RequestTypes.tsx";
import classNames from "classnames";
import {useEffect, useState} from "react";

export const ReactorSettings = ( { labelStyling, inputStyling, constants, stateUpdaters }: FormProps ) => {

    const [showAdvanced, setShowAdvanced] = useState(false)

    const [reactorOptions, setReactorOptions] = useState([<></>])

    const [impellerOptions, setImpellerOptions] = useState([<></>])

    useEffect(() => {
        (stateUpdaters as UpdateReactorSettings).updateReactorType(constants?.reactors[constants?.reactors.length - 1]);
        (stateUpdaters as UpdateReactorSettings).updateImpellerType(constants?.impellers[constants?.impellers.length - 1]);
        if(constants) {
            setReactorOptions(constants?.reactors.map(function(val, index) {
                return <option key={index}>{val}</option>
            }))
            setImpellerOptions(constants?.impellers.map(function(val, index) {
                return <option key={index}>{val}</option>
            }))
        }
    }, [constants, stateUpdaters]);

    return (
        <>
            <div className="grid gap-4 mb-4 md:grid-cols-2">
                <div>
                    <label className={labelStyling}>Reactor:</label>
                    <select className={inputStyling} id="reactorType"
                            onChange={(e) => (stateUpdaters as UpdateReactorSettings).updateReactorType(e.target.value)}>
                        {reactorOptions}
                    </select>
                </div>
                <div>
                    <label className={labelStyling}>Impeller:</label>
                    <select className={inputStyling} id="impellerType"
                            onChange={(e) => (stateUpdaters as UpdateReactorSettings).updateImpellerType(e.target.value)}>
                        {impellerOptions}
                    </select>
                </div>
                <div>
                    <label className={labelStyling}>Number of impellers:</label>
                    <input className={inputStyling} id="numberOfImpellers" type="number" step="any" min="0"
                           placeholder={"-"} required
                           onChange={(e) => (stateUpdaters as UpdateReactorSettings).updateNumberOfImpellers(Number(e.target.value))}/>
                </div>
                <div>
                    <label className={labelStyling}>Agitator speed:</label>
                    <input className={inputStyling} id="agitatorSpeed" type="number" step="any" min="0"
                           placeholder={"/s"} required
                           onChange={(e) => (stateUpdaters as UpdateReactorSettings).updateAgitatorSpeed(Number(e.target.value))}/>
                </div>
                {showAdvanced && advancedReactor()}
                <div className="flex items-center">
                    <button onClick={() => setShowAdvanced(!showAdvanced)} className={classNames("text-left text-xs italic text-white font-bold")}>
                        Advanced reactor settings
                    </button>
                </div>
            </div>
        </>
    )

    function advancedReactor() {
        return (
            <>
                <div>
                    <label className={labelStyling}>Nominal volume:</label>
                    <input className={inputStyling} id="nominalVolume" type="number"
                           placeholder={"Total reactor volume (m3)"}
                           onChange={(e) => (stateUpdaters as UpdateReactorSettings).updateNominalVolume(Number(e.target.value))}/>
                </div>
                <div>
                    <label className={labelStyling}>Working volume:</label>
                    <input className={inputStyling} id="workingVolume" type="number" step="any" min="0"
                           placeholder={"m3"}
                           onChange={(e) => (stateUpdaters as UpdateReactorSettings).updateWorkingVolume(Number(e.target.value))}/>
                </div>
                <div>
                    <label className={labelStyling}>Height:</label>
                    <input className={inputStyling} id="height" type="number" step="any" min="0"
                           placeholder={"m"}
                           onChange={(e) => (stateUpdaters as UpdateReactorSettings).updateHeight(Number(e.target.value))}/>
                </div>
                <div>
                    <label className={labelStyling}>Width:</label>
                    <input className={inputStyling} id="width" type="number" step="any" min="0"
                           placeholder={"m"}
                           onChange={(e) => (stateUpdaters as UpdateReactorSettings).updateWidth(Number(e.target.value))}/>
                </div>
            </>
        )
    }
}
