import {Output} from "../Types.tsx";

export async function runSimulation()  {
    const response: Response = await fetch("modelr/api/run", {
        method: 'GET',
        headers: {
            Accept: "application/json",
            "Content-Type": "application/json",
        },
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