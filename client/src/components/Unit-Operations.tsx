import classNames from "classnames";
import {CultivationSettings} from "./CultivationSettings.tsx";
import {ReactorSettings} from "./ReactorSettings.tsx";
import {useEffect, useState} from "react";
import {fetchConstants} from "../services/api.tsx";
import {Constants, isConstants} from "../Types.tsx";

interface UnitOperation {
    onClick: () => Promise<void>
    icon: string
}

export const BatchCultivation = ({ onClick, icon }: UnitOperation) => {
    const [constants, setConstants] = useState<Constants | null>(null)

    useEffect(() => {
        retrieveConstants()
    }, []);

    async function retrieveConstants() {
        const constants = await fetchConstants()
        if(isConstants(constants)) {
            setConstants(constants)
        }
    }

    const toggleSettings = () => {
        if (document.getElementById("settings")!.style.display == "block") {
            document.getElementById("settings")!.style.display = "none"
        } else {
            document.getElementById("settings")!.style.display = "block"
        }
    }

    const labelStyling = classNames("block mb-2 text-sm font-medium text-white")

    const inputStyling = classNames("text-sm rounded-lg block w-full border",
                                    "p-2.5 bg-gray-700 border-gray-600 placeholder-gray-400 text-white")

    return (
        <>
            <img className="absolute w-1/6 h-1/4 top-1/2 left-1/2 -translate-x-1/2 -translate-y-1/2 overflow-hidden" onClick={toggleSettings} src={icon} alt={"image not found"}/>
            <form onSubmit={event => event.preventDefault()} id="settings" className="absolute top-1/4 right-1/5 overflow-visible hidden p-4 rounded-xl bg-gray-900 shadow-2xl">
                <div>
                    <label className={classNames("block mb-2 text-md font-black text-white")} id="batch-cultivation">BATCH-CULTIVATION</label>
                </div>
                <CultivationSettings labelStyling={labelStyling} inputStyling={inputStyling} constants={constants?.value}/>
                <ReactorSettings labelStyling={labelStyling} inputStyling={inputStyling} constants={constants?.value}/>
                <button onClick={onClick} className={classNames(
                    "bg-cyan-800 ring-4 ring-opacity-25 shadow-2xl",
                    "ring-cyan-700 rounded-full p-3 text-center",
                    "text-sm text-white font-black"
                )}>RUN!
                </button>
            </form>
        </>
    )
}