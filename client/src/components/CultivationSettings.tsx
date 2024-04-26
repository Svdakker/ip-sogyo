import {FormProps} from "../Types.tsx";
import classNames from "classnames";

export const CultivationSettings = ( { labelStyling, inputStyling, constants }: FormProps ) => {

    const toggleAdvancedCultivationSettings = () => {
        if (document.getElementById("cultivationadvanced")!.style.display == "block") {
            document.getElementById("cultivationadvanced")!.style.display = "none"
        } else {
            document.getElementById("cultivationadvanced")!.style.display = "block"
        }
    }

    const setPossibleMicroorganisms = () => {
        return constants?.microorganisms.map(function(val) {
            return <option>{val}</option>
        })
    }

    return (
        <>
            <div className="grid gap-4 mb-4 md:grid-cols-2">
                <div>
                    <label className={labelStyling}>Microorganism:</label>
                    <select className={inputStyling} id="microorganism">
                        {setPossibleMicroorganisms()}
                    </select>
                </div>
                <div>
                    <label className={labelStyling}>Accuracy:</label>
                    <input className={inputStyling} id="accuracy" type="number" step="any" min="0.001"
                           placeholder={"Calculation interval (/h)"} required/>
                </div>
                <div>
                    <label className={labelStyling}>Initial sugar concentration (Cs0):</label>
                    <input className={inputStyling} id="initialSugarConcentration" type="number" step="any" min="0"
                           placeholder={"kg/m3"} required/>
                </div>
                <div>
                    <label className={labelStyling}>Initial cell density (Cx0):</label>
                    <input className={inputStyling} id="initialCellDensity" type="number" step="any" min="0"
                           placeholder={"kg/m3"} required/>
                </div>
                <button onClick={toggleAdvancedCultivationSettings} className={classNames("text-left text-xs italic text-white font-bold")}>
                    Advanced cultivation settings
                </button>
                <div className="hidden" id="cultivationadvanced">
                    <div>
                        <label className={labelStyling}>Maximum growth rate (mu):</label>
                        <input className={inputStyling} id="maxGrowthRate" type="number" step="any" min="0"
                               placeholder={"/h"}/>
                    </div>
                    <div>
                        <label className={labelStyling}>Maintenance coefficient (ms):</label>
                        <input className={inputStyling} id="maintenance" type="number" step="any" min="0"
                               placeholder={"kg/kgx/h"}/>
                    </div>
                    <div>
                        <label className={labelStyling}>Yield biomass on sugar (Yxs):</label>
                        <input className={inputStyling} id="yield" type="number" step="any" min="0"
                               placeholder={"-"}/>
                    </div>
                </div>
            </div>
        </>
    )
}