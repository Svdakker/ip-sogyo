export type SimulationRequest = {
    order: string[] | undefined,
    batchCultivation: BatchCultivationRequest[] | undefined
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