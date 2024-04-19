export type Output = {
    id: Number
    duration: Number
    model: DataPoint[]
    costEstimation: CostEstimation
    powerConsumption: PowerConsumption
}

export type Model = {
    data: DataPoint[] | undefined
}

export type TableData = {
    duration: Number | undefined
    energyCosts: Number | undefined
    energyUsed: Number | undefined
}

export type DataPoint = {
    time: Number,
    cellDensity: Number,
    sugarConcentration: Number,
}

export type CostEstimation = {
    energy: Number
}

export type PowerConsumption = {
    operations: Number
}

export type FormStyling = {
    labelStyling: string
    inputStyling: string
}

export function isOutput(output: unknown): output is Output {
    return (output as Output).id !== undefined
}