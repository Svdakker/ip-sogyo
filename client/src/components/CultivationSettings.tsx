import {FormProps, UpdateCultivationSettings} from "../RequestTypes.tsx";
import classNames from "classnames";
import {useState} from "react";

export const CultivationSettings = ( { position, labelStyling, inputStyling, constants, stateUpdaters }: FormProps ) => {

    const [showAdvanced, setShowAdvanced] = useState(false)

    const setPossibleMicroorganisms = () => {
        (stateUpdaters as UpdateCultivationSettings).updateMicroorganism(constants?.microorganisms[constants?.microorganisms.length - 1])
        return constants?.microorganisms.map(function(val) {
            return <option key={constants?.microorganisms.indexOf(val)}>{val}</option>
        })
    }

    return (
        <>
            <div className="grid gap-4 mb-4 md:grid-cols-2">
                <div>
                    <label className={labelStyling}>Microorganism:</label>
                    <select onChange={(e) => { (stateUpdaters as UpdateCultivationSettings).updateMicroorganism(e.target.value) }}
                            className={inputStyling} id="microorganism">
                        {setPossibleMicroorganisms()}
                    </select>
                </div>
                <div>
                    <label className={labelStyling}>Accuracy:</label>
                    <input className={inputStyling} id="accuracy" type="number" step="any" min="0.001"
                           placeholder={"Calculation interval (/h)"} required
                           onChange={(e) => (stateUpdaters as UpdateCultivationSettings).updateAccuracy(Number(e.target.value))}/>
                </div>
                <div>
                    <label className={labelStyling}>Initial sugar concentration (Cs0):</label>
                    <input className={inputStyling} id="initialSugarConcentration" type="number" step="any" min="0"
                           placeholder={"kg/m3"} required
                           onChange={(e) => (stateUpdaters as UpdateCultivationSettings).updateInitialSugarConcentration(Number(e.target.value))}/>
                </div>
                {!checkDisabled() && initialCellDensity()}
                {showAdvanced && advancedCultivation()}
                <div className="flex items-center">
                    <button onClick={() => setShowAdvanced(!showAdvanced)} className={classNames("text-left text-xs italic text-white font-bold")}>
                        Advanced cultivation settings
                    </button>
                </div>
            </div>
        </>
    )

    function initialCellDensity() {
        return (
            <div>
                <label className={labelStyling}>Initial cell density (Cx0):</label>
                <input className={inputStyling} id="initialCellDensity" type="number" step="any" min="0"
                       placeholder={"kg/m3"}
                       onChange={(e) => (stateUpdaters as UpdateCultivationSettings).updateInitialCellDensity(Number(e.target.value))}/>
            </div>
        )
    }

    function checkDisabled(): boolean {
        return position > 0;
    }

    function advancedCultivation() {
        return (
            <>
                <div>
                    <label className={labelStyling}>Maximum growth rate (mu):</label>
                    <input className={inputStyling} id="maxGrowthRate" type="number" step="any" min="0"
                           placeholder={"/h"}
                           onChange={(e) => (stateUpdaters as UpdateCultivationSettings).updateMaxGrowthRate(Number(e.target.value))}/>
                </div>
                <div>
                    <label className={labelStyling}>Maintenance coefficient (ms):</label>
                    <input className={inputStyling} id="maintenance" type="number" step="any" min="0"
                           placeholder={"kg/kgx/h"}
                           onChange={(e) => (stateUpdaters as UpdateCultivationSettings).updateMaintenance(Number(e.target.value))}/>
                </div>
                <div>
                    <label className={labelStyling}>Yield biomass on sugar (Yxs):</label>
                    <input className={inputStyling} id="yield" type="number" step="any" min="0"
                           placeholder={"-"}
                           onChange={(e) => (stateUpdaters as UpdateCultivationSettings).updateYield(Number(e.target.value))}/>
                </div>
            </>
        )
    }
}