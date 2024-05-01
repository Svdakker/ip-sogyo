import {FormProps} from "../Types.tsx";
import classNames from "classnames";
import {useState} from "react";

export const CultivationSettings = ( { update, labelStyling, inputStyling, constants }: FormProps ) => {
    const[microorganismInput, setMicroorganismInput] = useState<string | undefined>(undefined)
    const[accuracyInput, setAccuracyInput] = useState<number | undefined>(undefined)
    const[initialSugarConcentrationInput, setInitialSugarConcentrationInput] = useState<number | undefined>(undefined)
    const[initialCellDensityInput, setInitialCellDensityInput] = useState<number | undefined>(undefined)
    const[maxGrowthRateInput, setMaxGrowthRateInput] = useState<number | undefined>(undefined)
    const[maintenanceInput, setMaintenanceInput] = useState<number | undefined>(undefined)
    const[yieldInput, setYieldInput] = useState<number | undefined>(undefined)

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

    const updateCultivationSettings = (e: Event) => {
        e.preventDefault()
        const cultivationSettings = {
            microorganism: microorganismInput,
            accuracy: accuracyInput,
            initialSugarConcentration: initialSugarConcentrationInput,
            initialCellDensity: initialCellDensityInput,
            maxGrowthRate: maxGrowthRateInput,
            maintenance: maintenanceInput,
            yield: yieldInput,
        }
        update("cultivation", cultivationSettings)
    }

    return (
        <>
            <form onSubmit={(e) => e.preventDefault()} onChange={() => updateCultivationSettings} className="grid gap-4 mb-4 md:grid-cols-2">
                <div>
                    <label className={labelStyling}>Microorganism:</label>
                    <select onChange={(e) => { setMicroorganismInput(e.target.value) }}
                            className={inputStyling} id="microorganism">
                        {setPossibleMicroorganisms()}
                    </select>
                </div>
                <div>
                    <label className={labelStyling}>Accuracy:</label>
                    <input className={inputStyling} id="accuracy" type="number" step="any" min="0.001"
                           placeholder={"Calculation interval (/h)"} required
                           onChange={(e) => setAccuracyInput(Number(e.target.value))}/>
                </div>
                <div>
                    <label className={labelStyling}>Initial sugar concentration (Cs0):</label>
                    <input className={inputStyling} id="initialSugarConcentration" type="number" step="any" min="0"
                           placeholder={"kg/m3"} required
                           onChange={(e) => setInitialSugarConcentrationInput(Number(e.target.value))}/>
                </div>
                <div>
                    <label className={labelStyling}>Initial cell density (Cx0):</label>
                    <input className={inputStyling} id="initialCellDensity" type="number" step="any" min="0"
                           placeholder={"kg/m3"} required
                           onChange={(e) => setInitialCellDensityInput(Number(e.target.value))}/>
                </div>
                <button onClick={toggleAdvancedCultivationSettings} className={classNames("text-left text-xs italic text-white font-bold")}>
                    Advanced cultivation settings
                </button>
                <div className="hidden" id="cultivationadvanced">
                    <div>
                        <label className={labelStyling}>Maximum growth rate (mu):</label>
                        <input className={inputStyling} id="maxGrowthRate" type="number" step="any" min="0"
                               placeholder={"/h"}
                               onChange={(e) => setMaxGrowthRateInput(Number(e.target.value))}/>
                    </div>
                    <div>
                        <label className={labelStyling}>Maintenance coefficient (ms):</label>
                        <input className={inputStyling} id="maintenance" type="number" step="any" min="0"
                               placeholder={"kg/kgx/h"}
                               onChange={(e) => setMaintenanceInput(Number(e.target.value))}/>
                    </div>
                    <div>
                        <label className={labelStyling}>Yield biomass on sugar (Yxs):</label>
                        <input className={inputStyling} id="yield" type="number" step="any" min="0"
                               placeholder={"-"}
                               onChange={(e) => setYieldInput(Number(e.target.value))}/>
                    </div>
                </div>
            </form>
        </>
    )
}