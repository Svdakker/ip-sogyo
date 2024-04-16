interface UnitOperation {
    name: string
    icon: string
}

export const UnitOperation = ({ name, icon }: UnitOperation) => {

    return (
        <>
            <div>{name}</div>
            <img src={icon} alt={"image not found"}/>
        </>
    )
}