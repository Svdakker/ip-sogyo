interface UnitOperation {
    requiredInput: string[]
    icon: string
}

export const UnitOperation = ({ requiredInput, icon }: UnitOperation) => {

    const listRequiredInput = () => {
        return requiredInput.map(requirement => {
            if (requirement == "batch-cultivation") {
                return (
                    <div key={requirement} id={requirement}>{requirement.toUpperCase()}</div>
                )
            } else {
                return (
                    <li key={requirement}>
                        <input type="number" id={requirement} placeholder={requirement}/>
                    </li>
                )
            }
        })
    }

    return (
        <>
            <img src={icon} alt={"image not found"}/>
            <ul>{listRequiredInput()}</ul>
        </>
    )
}