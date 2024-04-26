export type Output = {
    value: {
        batchCultivation: BatchCultivation | undefined
    }
}

export type Saved = {
    value: number
}

export type BatchCultivation = {
    duration: number
    model: number[][]
    costEstimation: CostEstimation
    powerConsumption: PowerConsumption
}

export type Model = {
    data: number[][] | undefined
}

export type TableData = {
    duration: number | undefined
    energyCosts: number | undefined
    energyUsed: number | undefined
}

export type CostEstimation = {
    energy: number
}

export type PowerConsumption = {
    operations: number
}

export type FormStyling = {
    labelStyling: string
    inputStyling: string
}

export function isOutput(output: unknown): output is Output {
    return (output as Output).value !== undefined
}

export function isSaved(response: unknown): response is Saved {
    return (response as Saved).value !== undefined
}