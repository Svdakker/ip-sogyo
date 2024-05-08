import {FormProps} from "../RequestTypes.tsx";
import {Dispatch, SetStateAction} from "react";

export const CentrifugeType = ({ labelStyling, inputStyling, constants, stateUpdaters }: FormProps) => {

    const setPossibleCentrifuges = () => {
        (stateUpdaters as Dispatch<SetStateAction<string | undefined>>)(constants?.centrifuges[constants?.centrifuges.length - 1])
        return constants?.centrifuges.map(function(val, index) {
            return <option key={index}>{val}</option>
        })
    }

    return (
        <>
            <div>
                <label className={labelStyling}>Centrifuge:</label>
                <select onChange={(e) => {
                    (stateUpdaters as Dispatch<SetStateAction<string | undefined>>)(e.target.value)
                }}
                        className={inputStyling} id="centrifuge">
                    {setPossibleCentrifuges()}
                </select>
            </div>
        </>
    )
}