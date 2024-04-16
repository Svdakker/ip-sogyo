export type Output = {
    id: Number
    duration: Number
}

export function isOutput(output: unknown): output is Output {
    return (output as Output).id !== undefined
}