import React, {createContext, useContext, useState} from "react";
import {SimulationRequest} from "../RequestTypes.tsx";

type ContextType = {
    simulationRequest: SimulationRequest | undefined
    setSimulationRequest: (simulationRequest: SimulationRequest) => void
}

export const SimulationRequestContext = createContext<ContextType>({
    simulationRequest: undefined,
    setSimulationRequest() { }
})

type Props = React.PropsWithChildren;

export const SimulationContextProvider = (props: Props) => {
    const { children } = props;

    const[simulationRequest, setSimulationRequest] = useState<SimulationRequest | undefined>(undefined)

    return <SimulationRequestContext.Provider value={{
        simulationRequest: simulationRequest,
        setSimulationRequest: setSimulationRequest
    }}>
        {children}
    </SimulationRequestContext.Provider>
}

export const useSimulationRequest = () => {
    const context = useContext(SimulationRequestContext);

    if (context === undefined) {
        throw new Error('useSimulation must be used within a MancalaGameProvider');
    }

    return context;
}