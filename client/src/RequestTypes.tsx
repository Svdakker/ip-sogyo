import {Dispatch, SetStateAction} from "react";

export type SimulationRequest = {
    order: string[] | undefined,
    batchCultivation: BatchCultivationRequest[] | undefined
    centrifugation: CentrifugationRequest[] | undefined
}

export type BatchCultivationRequest = {
    operationType: string
    cultivationSettings: CultivationSettingsRequest | undefined
    reactorSettings: ReactorSettingsRequest | undefined
}

export type CultivationSettingsRequest = {
    microorganism: string | undefined,
    accuracy: number | undefined,
    initialSugarConcentration: number | undefined,
    initialCellDensity: number | undefined,
    maxGrowthRate: number | undefined,
    maintenance: number | undefined,
    yield: number | undefined,
}

export type ReactorSettingsRequest = {
    reactorType: string | undefined,
    nominalVolume: number | undefined,
    workingVolume: number | undefined,
    height: number | undefined,
    width: number | undefined,
    impellerType: string | undefined,
    numberOfImpellers: number | undefined,
    agitatorSpeed: number | undefined,
}

export type CentrifugationRequest = {
    operationType: string,
    centrifugationSettings: CentrifugationSettings | undefined
    centrifugeProperties: CentrifugeProperties | undefined
}

export type CentrifugationSettings = {
    frequencyOfRotation: number | undefined
    liquidFlowRate: number | undefined
    liquidVolume: number | undefined
}

export type CentrifugeProperties = {
    centrifugeType: string | undefined
    outerRadius: number | undefined
    innerRadius: number | undefined
    numberOfDisks: number | undefined
    diskAngle: number | undefined
    motorPower: number | undefined
}

export type FormProps = {
    position: number,
    labelStyling: string
    inputStyling: string
    constants: {
        microorganisms: string[]
        reactors: string[]
        impellers: string[]
        centrifuges: string[]
    } | undefined
    stateUpdaters: UpdateReactorSettings | UpdateCultivationSettings | Dispatch<SetStateAction<string | undefined>>
}

export type UpdateCultivationSettings = {
    updateMicroorganism: Dispatch<SetStateAction<string | undefined>>,
    updateAccuracy: Dispatch<SetStateAction<number | undefined>>,
    updateInitialSugarConcentration: Dispatch<SetStateAction<number | undefined>>,
    updateInitialCellDensity: Dispatch<SetStateAction<number | undefined>>,
    updateMaxGrowthRate: Dispatch<SetStateAction<number | undefined>>,
    updateMaintenance: Dispatch<SetStateAction<number | undefined>>,
    updateYield: Dispatch<SetStateAction<number | undefined>>,
}

export type UpdateReactorSettings = {
    updateReactorType: Dispatch<SetStateAction<string | undefined>>,
    updateNominalVolume: Dispatch<SetStateAction<number | undefined>>,
    updateWorkingVolume: Dispatch<SetStateAction<number | undefined>>,
    updateHeight: Dispatch<SetStateAction<number | undefined>>,
    updateWidth: Dispatch<SetStateAction<number | undefined>>,
    updateImpellerType: Dispatch<SetStateAction<string | undefined>>,
    updateNumberOfImpellers: Dispatch<SetStateAction<number | undefined>>,
    updateAgitatorSpeed: Dispatch<SetStateAction<number | undefined>>,
}