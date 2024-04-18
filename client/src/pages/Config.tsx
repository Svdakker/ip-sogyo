import {useNavigate} from "react-router-dom";
import {UnitOperation} from "../components/Unit-Operations.tsx";
import batch from "/src/assets/batch-cultivation.jpg"

export const Config = () => {
    const navigate = useNavigate()
    return (
        <>
            <UnitOperation name={"Batch cultivation"} icon={batch}/>
            <button onClick={() => navigate('/result')}>Go to simulation!</button>
        </>
    )
}