import {Output} from "../Types.tsx";

export async function runSimulation()  {
    const response: Response = await fetch("modelr/api/run", {
        method: 'POST',
        headers: {
            Accept: "application/json",
            "Content-Type": "application/json",
        },
        body: JSON.stringify({
            operationType: "batch",
            accuracy: 1.0,
            initialSugarConcentration: 20,
            initialCellDensity: 0.12,
            maxGrowthRate: 0.27,
            maintenance: 0.00703,
            yield: 0.4,
        }),
    })

    if (response.ok) {
        const result = await response.json();
        return result as Output;
    } else {
        return {
            statusCode: response.status,
            statusText: response.statusText
        };
    }
}
