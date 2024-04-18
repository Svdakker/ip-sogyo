export type Output = {
    id: Number
    duration: Number
    model: DataPoint[]
}

export type Model = {
    data: DataPoint[] | undefined
}

export type TableData = {
    duration: Number | undefined
}

export type DataPoint = {
    time: Number,
    cellDensity: Number,
    sugarConcentration: Number,
}

export function isOutput(output: unknown): output is Output {
    return (output as Output).id !== undefined
}