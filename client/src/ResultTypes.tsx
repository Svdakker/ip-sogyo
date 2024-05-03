import {Dispatch, SetStateAction} from "react";

export type Output = {
    value: {
        order: string[] | undefined
        output: BatchCultivation[] | undefined
    }
}

export type Saved = {
    value: number
}

export type Constants = {
    value: {
        microorganisms: string[]
        reactors: string[]
        impellers: string[]
    }
}

export type BatchCultivation = {
    duration: number
    model: number[][]
    costEstimation: CostEstimation
    powerConsumption: PowerConsumption
}

export type GraphData  = {
    model: number[][] | undefined,
    position: number,
}

export type TableData = {
    data: BatchCultivation[]
    switchGraph: Dispatch<SetStateAction<number>>,
}

export type CostEstimation = {
    energy: number
}

export type PowerConsumption = {
    operations: number
}

export function isOutput(output: unknown): output is Output {
    return (output as Output).value !== undefined
}

export function isSaved(response: unknown): response is Saved {
    return (response as Saved).value !== undefined
}

export function isConstants(constants: unknown): constants is Constants {
    return (constants as Constants).value !== undefined
}