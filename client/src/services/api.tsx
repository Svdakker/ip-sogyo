import {Output} from "../Types.tsx";

export async function runSimulation(input: object)  {
    const response: Response = await fetch("modelr/api/run-simulation", {
        method: 'POST',
        headers: {
            Accept: "application/json",
            "Content-Type": "application/json",
        },
        body: JSON.stringify(input),
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

