export type Output = {
    id: number
    duration: number
    model: DataPoint[]
    costEstimation: CostEstimation
    powerConsumption: PowerConsumption
}

export type Model = {
    data: DataPoint[] | undefined
}

export type TableData = {
    duration: number | undefined
    energyCosts: number | undefined
    energyUsed: number | undefined
}

export type DataPoint = {
    time: number,
    cellDensity: number,
    sugarConcentration: number,
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
    return (output as Output).id !== undefined
}