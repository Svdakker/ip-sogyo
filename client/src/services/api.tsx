export async function runSimulation()  {
    const response: Response = await fetch("modelr/api/run", {
        method: 'GET',
        headers: {
            Accept: "application/json",
            "Content-Type": "application/json",
        },
    })

    if (response.ok) {
        const result = await response.json()
        return (
            console.log(result)
        )
    } else {
        return (
            console.log("No simulation found")
        )
    }
}