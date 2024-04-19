import {FormStyling} from "../Types.tsx";

export const ReactorSettings = ( { labelStyling, inputStyling }: FormStyling ) => {

    return (
        <>
            <div className="grid gap-4 mb-4 md:grid-cols-2">
                <div>
                    <label className={labelStyling}>Nominal volume:</label>
                    <input className={inputStyling} id="nominalVolume" type="number" placeholder={"Total reactor volume (m3)"} required/>
                </div>
                <div>
                    <label className={labelStyling}>Working volume:</label>
                    <input className={inputStyling} id="workingVolume" type="number" placeholder={"m3"} required/>
                </div>
                <div>
                    <label className={labelStyling}>Height:</label>
                    <input className={inputStyling} id="height" type="number" placeholder={"m"}
                           required/>
                </div>
                <div>
                    <label className={labelStyling}>Width:</label>
                    <input className={inputStyling} id="width" type="number" placeholder={"m"} required/>
                </div>
                <div>
                    <label className={labelStyling}>Impeller type:</label>
                    <input className={inputStyling} id="impellerType" type="text" placeholder={"Impeller type"} required/>
                </div>
                <div>
                    <label className={labelStyling}>Number of impellers:</label>
                    <input className={inputStyling} id="numberOfImpellers" type="number" placeholder={"-"} required/>
                </div>
            </div>
        </>
    )
}