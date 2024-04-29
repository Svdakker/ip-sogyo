import {FormProps} from "../Types.tsx";
import classNames from "classnames";

export const ReactorSettings = ( { labelStyling, inputStyling, constants }: FormProps ) => {

    const toggleAdvancedReactorSettings = () => {
        if (document.getElementById("reactoradvanced")!.style.display == "block") {
            document.getElementById("reactoradvanced")!.style.display = "none"
        } else {
            document.getElementById("reactoradvanced")!.style.display = "block"
        }
    }

    const setPossibleReactors = () => {
        return constants?.reactors.map(function(val) {
            return <option>{val}</option>
        })
    }

    const setPossibleImpellers = () => {
        return constants?.impellers.map(function(val) {
            return <option>{val}</option>
        })
    }

    return (
        <>
            <div className="grid gap-4 mb-4 md:grid-cols-2">
                <div>
                    <label className={labelStyling}>Reactor:</label>
                    <select className={inputStyling} id="reactorType">
                        {setPossibleReactors()}
                    </select>
                </div>
                <div>
                    <label className={labelStyling}>Impeller:</label>
                    <select className={inputStyling} id="impellerType">
                        {setPossibleImpellers()}
                    </select>
                </div>
                <div>
                    <label className={labelStyling}>Number of impellers:</label>
                    <input className={inputStyling} id="numberOfImpellers" type="number" step="any" min="0"
                           placeholder={"-"} required/>
                </div>
                <div>
                    <label className={labelStyling}>Agitator speed:</label>
                    <input className={inputStyling} id="agitatorSpeed" type="number" step="any" min="0"
                           placeholder={"/s"} required/>
                </div>
                <button onClick={toggleAdvancedReactorSettings} className={classNames("text-left text-xs italic text-white font-bold")}>
                    Advanced reactor settings
                </button>
                <div className="hidden" id="reactoradvanced">
                    <div>
                        <label className={labelStyling}>Nominal volume:</label>
                        <input className={inputStyling} id="nominalVolume" type="number"
                               placeholder={"Total reactor volume (m3)"}/>
                    </div>
                    <div>
                        <label className={labelStyling}>Working volume:</label>
                        <input className={inputStyling} id="workingVolume" type="number" step="any" min="0"
                               placeholder={"m3"}/>
                    </div>
                    <div>
                        <label className={labelStyling}>Height:</label>
                        <input className={inputStyling} id="height" type="number" step="any" min="0"
                               placeholder={"m"}/>
                    </div>
                    <div>
                        <label className={labelStyling}>Width:</label>
                        <input className={inputStyling} id="width" type="number" step="any" min="0"
                               placeholder={"m"}/>
                    </div>
                </div>
            </div>
        </>
    )
}