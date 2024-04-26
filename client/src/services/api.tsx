import {Output, Saved} from "../Types.tsx";

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
        return result as Saved
    } else {
        return {
            statusCode: response.status,
            statusText: response.statusText
        };
    }
}

export async function fetchResult() {
    const response = await fetch("modelr/api/simulation-result", {
        method: 'GET',
        headers: {
            Accept: "application/json",
            "Content-Type": "application/json",
        }
    })

    if (response.ok) {
        const result = await response.json()
        return result as Output
    } else {
        return {
            statusCode: response.status,
            statusTest: response.statusText
        }
    }
}

